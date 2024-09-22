package com.example.app_gym.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_gym.R;
import com.example.app_gym.controllers.RutinaDiariaController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.RutinaDiaria;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import java.util.List;

public class DiaActivity extends AppCompatActivity {

    private RutinaDiariaController rutinaDiariaController;
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
        rutinaDiariaController = new RutinaDiariaController(dbHelper.getWritableDatabase());

        // Obtener el semanaId desde el Intent
        semanaId = getIntent().getIntExtra("semana_id", -1);

        // Cargar las rutinas diarias existentes para validar los días
        listaRutinasDiarias = rutinaDiariaController.obtenerRutinasDiariasDeSemana(semanaId);

        // Referenciar los elementos de la vista
        spinnerDias = findViewById(R.id.spinnerDias);
        etFecha = findViewById(R.id.etFecha);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Configurar el Spinner con los días de la semana
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dias_semana, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDias.setAdapter(adapter);

        // Configurar el botón "Guardar"
        btnGuardar.setOnClickListener(v -> guardarDia());

        // Configurar el botón "Volver"
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para guardar el día
    private void guardarDia() {
        String diaSeleccionado = spinnerDias.getSelectedItem().toString();
        String fecha = etFecha.getText().toString();

        if (fecha.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese una fecha.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato de la fecha
        if (!esFechaValida(fecha)) {
            Toast.makeText(this, "Formato de fecha incorrecto. Use AAAA-MM-DD.", Toast.LENGTH_SHORT).show();
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

        long result = rutinaDiariaController.crearNuevaRutinaDiaria(nuevaRutinaDiaria);
        if (result != -1) {
            Toast.makeText(this, "Día guardado correctamente.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Establecer el resultado como RESULT_OK
            finish(); // Cierra la actividad y regresa al index
        } else {
            Toast.makeText(this, "Error al guardar el día.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para validar la fecha en formato "AAAA-MM-DD"
    private boolean esFechaValida(String fecha) {
        // Expresión regular para validar el formato
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        if (!Pattern.matches(regex, fecha)) {
            return false;
        }

        // Verificar que la fecha sea válida
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Para que no acepte fechas no válidas como "2024-02-30"

        try {
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
