package com.example.app_gym.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_gym.R;
import com.example.app_gym.controllers.RutinaSemanalController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.RutinaSemanal;

public class SemanaActivity extends AppCompatActivity {

    private RutinaSemanalController rutinaSemanalController;
    private EditText etNombreSemana, etFechaSemana;
    private Button btnGuardarSemana, btnVolver;
    private int clienteId; // ID del cliente que se recibirá desde la pantalla anterior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semana);

        // Inicializar la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        rutinaSemanalController = new RutinaSemanalController(dbHelper.getWritableDatabase());

        // Obtener el clienteId pasado desde IndexSemanaActivity
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Referenciar los componentes de la vista
        etNombreSemana = findViewById(R.id.etNombreSemana);
        etFechaSemana = findViewById(R.id.etFechaSemana);
        btnGuardarSemana = findViewById(R.id.btnGuardarSemana);
        btnVolver = findViewById(R.id.btnVolver);

        // Configurar el botón Guardar
        btnGuardarSemana.setOnClickListener(v -> guardarRutinaSemanal());

        // Configurar el botón Volver
        btnVolver.setOnClickListener(v -> finish());
    }

    private void guardarRutinaSemanal() {
        // Obtener los valores ingresados por el usuario
        String nombre = etNombreSemana.getText().toString();
        String fecha = etFechaSemana.getText().toString();

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
        long resultado = rutinaSemanalController.crearNuevaRutinaSemanal(nuevaRutina);

        if (resultado != -1) {
            Toast.makeText(this, "Rutina semanal guardada exitosamente", Toast.LENGTH_SHORT).show();
            finish(); // Finalizar la actividad y volver a la pantalla anterior
        } else {
            Toast.makeText(this, "Error al guardar la rutina semanal", Toast.LENGTH_SHORT).show();
        }
    }
}
