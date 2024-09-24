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
import com.example.app_gym.controllers.RutinaDiariaController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.RutinaDiaria;
import java.util.Calendar;

public class ActualizarDiaActivity extends AppCompatActivity {

    private RutinaDiariaController rutinaDiariaController;
    private Spinner spinnerDias;
    private EditText etFecha;
    private int rutinaDiariaId;
    private RutinaDiaria rutinaDiariaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_dia);

        // Inicializar la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        rutinaDiariaController = new RutinaDiariaController(dbHelper.getWritableDatabase());

        // Obtener el rutinaDiariaId desde el Intent
        rutinaDiariaId = getIntent().getIntExtra("rutina_diaria_id", -1);

        // Referenciar los elementos de la vista
        spinnerDias = findViewById(R.id.spinnerDias);
        etFecha = findViewById(R.id.etFecha);
        Button btnActualizar = findViewById(R.id.btnActualizar);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Configurar el Spinner con los días de la semana
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dias_semana, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDias.setAdapter(adapter);

        // Cargar la información del día que se va a actualizar
        cargarRutinaDiaria();

        // Configurar el DatePickerDialog para seleccionar la fecha
        etFecha.setOnClickListener(v -> mostrarDatePicker());

        // Configurar el botón "Actualizar"
        btnActualizar.setOnClickListener(v -> actualizarDia());

        // Configurar el botón "Volver"
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para cargar los datos de la rutina diaria
    private void cargarRutinaDiaria() {
        rutinaDiariaActual = rutinaDiariaController.obtenerRutinaDiaria(rutinaDiariaId);

        if (rutinaDiariaActual != null) {
            // Seleccionar el día correspondiente en el spinner
            String[] diasArray = getResources().getStringArray(R.array.dias_semana);
            for (int i = 0; i < diasArray.length; i++) {
                if (diasArray[i].equalsIgnoreCase(rutinaDiariaActual.getNombre())) {
                    spinnerDias.setSelection(i);
                    break;
                }
            }
            // Establecer la fecha actual
            etFecha.setText(rutinaDiariaActual.getFecha());
        } else {
            Toast.makeText(this, "No se pudo cargar la información del día.", Toast.LENGTH_SHORT).show();
            finish();
        }
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

    // Método para actualizar el día
    private void actualizarDia() {
        String diaSeleccionado = spinnerDias.getSelectedItem().toString();
        String fecha = etFecha.getText().toString();

        if (fecha.isEmpty()) {
            Toast.makeText(this, "Por favor, seleccione una fecha.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar los datos de la rutina diaria
        rutinaDiariaActual.setNombre(diaSeleccionado);
        rutinaDiariaActual.setFecha(fecha);

        int result = rutinaDiariaController.actualizarRutinaDiaria(rutinaDiariaActual);
        if (result > 0) {
            Toast.makeText(this, "Día actualizado correctamente.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Establecer el resultado como RESULT_OK
            finish(); // Cierra la actividad y regresa al index
        } else {
            Toast.makeText(this, "Error al actualizar el día.", Toast.LENGTH_SHORT).show();
        }
    }
}
