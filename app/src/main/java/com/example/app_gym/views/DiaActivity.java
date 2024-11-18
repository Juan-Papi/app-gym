package com.example.app_gym.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_gym.R;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.RutinaDiaria;
import com.example.app_gym.negocio.RutinaDiariaNegocio;

import java.util.Calendar;
import java.util.List;

public class DiaActivity extends AppCompatActivity {

    private RutinaDiariaNegocio rutinaDiariaNegocio;
    private Spinner spinnerDias;
    private EditText etFecha;
    private int semanaId;
    private List<RutinaDiaria> listaRutinasDiarias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia);

        // Inicializar la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        rutinaDiariaNegocio = new RutinaDiariaNegocio(dbHelper.getWritableDatabase());

        // Obtener el semanaId desde el Intent
        semanaId = getIntent().getIntExtra("semana_id", -1);

        // Cargar las rutinas diarias existentes para validar los días
        listaRutinasDiarias = rutinaDiariaNegocio.obtenerRutinasDiariasDeSemana(semanaId);

        // Referenciar los elementos de la vista
        spinnerDias = findViewById(R.id.spinnerDias);
        etFecha = findViewById(R.id.etFecha);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Configurar el Spinner con los días de la semana
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dias_semana, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDias.setAdapter(adapter);

        // Configurar el DatePickerDialog para seleccionar la fecha
        etFecha.setOnClickListener(v -> mostrarDatePicker());

        // Configurar el botón "Guardar"
        btnGuardar.setOnClickListener(v -> guardarDia());

        // Configurar el botón "Volver"
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para mostrar el DatePickerDialog
    private void mostrarDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            etFecha.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    // Método para guardar el día
    private void guardarDia() {
        String diaSeleccionado = spinnerDias.getSelectedItem().toString();
        String fecha = etFecha.getText().toString();

        if (fecha.isEmpty()) {
            Toast.makeText(this, "Por favor, seleccione una fecha.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el día ya existe
        for (RutinaDiaria rutinaDiaria : listaRutinasDiarias) {
            if (rutinaDiaria.getNombre().equalsIgnoreCase(diaSeleccionado)) {
                Toast.makeText(this, "El día seleccionado ya existe en la semana.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Si no está duplicado, creamos la nueva rutina diaria
        RutinaDiaria nuevaRutinaDiaria = new RutinaDiaria();
        nuevaRutinaDiaria.setNombre(diaSeleccionado);
        nuevaRutinaDiaria.setFecha(fecha);
        nuevaRutinaDiaria.setRutinaSemanalId(semanaId);

        long result = rutinaDiariaNegocio.agregarRutinaDiaria(nuevaRutinaDiaria);
        if (result != -1) {
            Toast.makeText(this, "Día guardado correctamente.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Establecer el resultado como RESULT_OK
            finish(); // Cierra la actividad y regresa al index
        } else {
            Toast.makeText(this, "Error al guardar el día.", Toast.LENGTH_SHORT).show();
        }
    }
}
