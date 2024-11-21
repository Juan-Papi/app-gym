package com.example.app_gym.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_gym.R;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.RutinaSemanal;
import com.example.app_gym.negocio.RutinaSemanalNegocio;

import java.util.Calendar;

public class ActualizarSemanaActivity extends AppCompatActivity {

    private RutinaSemanalNegocio rutinaSemanalNegocio;
    private EditText etNombreSemana, etFechaSemana;
    private Button btnGuardarSemana;
    private int rutinaSemanalId; // ID de la rutina semanal que se recibirá desde la pantalla anterior
    private int clienteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_semana);

        // Inicializar la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        rutinaSemanalNegocio = new RutinaSemanalNegocio(dbHelper.getWritableDatabase());

        // Obtener el rutinaSemanalId pasado desde IndexSemanaActivity
        rutinaSemanalId = getIntent().getIntExtra("rutina_semanal_id", -1);
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Referenciar los componentes de la vista
        etNombreSemana = findViewById(R.id.etNombreSemana);
        etFechaSemana = findViewById(R.id.etFechaSemana);
        btnGuardarSemana = findViewById(R.id.btnGuardarSemana);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Cargar los datos existentes de la rutina semanal
        cargarDatosRutinaSemanal();

        // Configurar el DatePickerDialog para seleccionar la fecha
        etFechaSemana.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
                String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                etFechaSemana.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.show();
        });

        // Configurar el botón Guardar
        btnGuardarSemana.setOnClickListener(v -> actualizarRutinaSemanal());

        // Configurar el botón Volver
        btnVolver.setOnClickListener(v -> finish());
    }

    private void cargarDatosRutinaSemanal() {
        if (rutinaSemanalId != -1) {
            RutinaSemanal rutinaSemanal = rutinaSemanalNegocio.obtenerRutinaSemanal(rutinaSemanalId);
            if (rutinaSemanal != null) {
                etNombreSemana.setText(rutinaSemanal.getNombre());
                etFechaSemana.setText(rutinaSemanal.getFecha());
            } else {
                Toast.makeText(this, "No se encontraron los datos de la rutina semanal", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void actualizarRutinaSemanal() {
        // Obtener los valores ingresados por el usuario
        String nombre = etNombreSemana.getText().toString().trim();
        String fecha = etFechaSemana.getText().toString().trim();

        if (nombre.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar la rutina semanal
        RutinaSemanal rutinaSemanal = new RutinaSemanal();
        rutinaSemanal.setId(rutinaSemanalId);
        rutinaSemanal.setNombre(nombre);
        rutinaSemanal.setFecha(fecha);
        rutinaSemanal.setClienteId(clienteId);

        int resultado = rutinaSemanalNegocio.actualizarRutinaSemanal(rutinaSemanal);

        if (resultado > 0) {
            Toast.makeText(this, "Rutina semanal actualizada exitosamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Indicar que la operación fue exitosa
            finish(); // Finalizar la actividad y volver a la pantalla anterior
        } else {
            Toast.makeText(this, "Error al actualizar la rutina semanal", Toast.LENGTH_SHORT).show();
        }
    }
}
