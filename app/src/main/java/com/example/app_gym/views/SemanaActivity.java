package com.example.app_gym.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_gym.R;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.RutinaSemanal;
import com.example.app_gym.negocio.RutinaSemanalNegocio;

import java.util.Calendar;

public class SemanaActivity extends AppCompatActivity {

    private RutinaSemanalNegocio rutinaSemanalNegocio;
    private EditText etNombreSemana, etFechaSemana;
    private Button btnGuardarSemana, btnVolver;
    private int clienteId; // ID del cliente que se recibirá desde la pantalla anterior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semana);

        // Inicializar la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        rutinaSemanalNegocio = new RutinaSemanalNegocio(dbHelper.getWritableDatabase());

        // Obtener el clienteId pasado desde IndexSemanaActivity
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Referenciar los componentes de la vista
        etNombreSemana = findViewById(R.id.etNombreSemana);
        etFechaSemana = findViewById(R.id.etFechaSemana);
        btnGuardarSemana = findViewById(R.id.btnGuardarSemana);
        btnVolver = findViewById(R.id.btnVolver);

        // Configuración del DatePickerDialog para abrirse al hacer clic en el EditText
        etFechaSemana.setOnClickListener(v -> {
            // Obtener la fecha actual
            final Calendar calendario = Calendar.getInstance();
            int anio = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            // Mostrar el DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SemanaActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // Ajustar el mes (+1 porque enero es 0)
                        String fechaSeleccionada = year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth);
                        etFechaSemana.setText(fechaSeleccionada); // Mostrar la fecha seleccionada en el EditText
                    },
                    anio, mes, dia);
            datePickerDialog.show();
        });

        // Configurar el botón Guardar
        btnGuardarSemana.setOnClickListener(v -> guardarRutinaSemanal());

        // Configurar el botón Volver
        btnVolver.setOnClickListener(v -> finish());
    }

    private void guardarRutinaSemanal() {
        // Obtener los valores ingresados por el usuario
        String nombre = etNombreSemana.getText().toString().trim();
        String fecha = etFechaSemana.getText().toString().trim();

        if (nombre.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear una nueva instancia de RutinaSemanal
        RutinaSemanal nuevaRutina = new RutinaSemanal();
        nuevaRutina.setNombre(nombre);
        nuevaRutina.setFecha(fecha);
        nuevaRutina.setClienteId(clienteId);

        // Guardar la nueva rutina semanal utilizando el controlador
        long resultado = rutinaSemanalNegocio.agregarRutinaSemanal(nuevaRutina);

        if (resultado != -1) {
            Toast.makeText(this, "Rutina semanal guardada exitosamente", Toast.LENGTH_SHORT).show();
            finish(); // Finalizar la actividad y volver a la pantalla anterior
        } else {
            Toast.makeText(this, "Error al guardar la rutina semanal", Toast.LENGTH_SHORT).show();
        }
    }
}
