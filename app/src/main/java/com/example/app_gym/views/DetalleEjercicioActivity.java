package com.example.app_gym.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_gym.R;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Cliente;
import com.example.app_gym.entities.Ejercicio;
import com.example.app_gym.entities.DetalleEjercicio;
import com.example.app_gym.negocio.ClienteNegocio;
import com.example.app_gym.negocio.DetalleEjercicioNegocio;
import com.example.app_gym.negocio.EjercicioNegocio;

import java.util.ArrayList;
import java.util.List;

public class DetalleEjercicioActivity extends AppCompatActivity {

    private EjercicioNegocio ejercicioNegocio;
    private DetalleEjercicioNegocio detalleEjercicioNegocio;
    private ClienteNegocio clienteNegocio;
    private TextView tvInformacionCliente;
    private Spinner spinnerEjercicios;
    private EditText etSeries, etRepeticiones;
    private int rutinaDiariaId;
    private int clienteId;
    private List<Ejercicio> listaEjercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ejercicio);

        // Inicializar la base de datos y controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ejercicioNegocio = new EjercicioNegocio(dbHelper.getWritableDatabase());
        detalleEjercicioNegocio = new DetalleEjercicioNegocio(dbHelper.getWritableDatabase());
        clienteNegocio = new ClienteNegocio(dbHelper.getWritableDatabase());

        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);

        // Obtener el ID de la rutina diaria desde el Intent
        rutinaDiariaId = getIntent().getIntExtra("rutina_diaria_id", -1);
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Obtener la información del cliente y mostrarla
        Cliente cliente = clienteNegocio.obtenerCliente(clienteId);
        if (cliente != null) {
            mostrarInformacionCliente(cliente);
        }

        // Referenciar los elementos de la vista
        spinnerEjercicios = findViewById(R.id.spinnerEjercicios);
        etSeries = findViewById(R.id.etSeries);
        etRepeticiones = findViewById(R.id.etRepeticiones);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // Cargar ejercicios en el Spinner
        cargarEjercicios();

        // Configurar el botón "Guardar"
        btnGuardar.setOnClickListener(v -> guardarDetalleEjercicio());
    }
    private void mostrarInformacionCliente(Cliente cliente) {
        String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                cliente.getEdad() + " años\n" +
                "Plan: " + cliente.getEstado() + "\n" +
                "Se unió: " + cliente.getFechaEntrada();
        tvInformacionCliente.setText(informacion);
    }
    // Método para cargar los ejercicios en el Spinner
    private void cargarEjercicios() {
        listaEjercicios = ejercicioNegocio.obtenerTodosLosEjerciciosConRelaciones();
        List<String> nombresEjercicios = new ArrayList<>();

        for (Ejercicio ejercicio : listaEjercicios) {
            nombresEjercicios.add(ejercicio.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresEjercicios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEjercicios.setAdapter(adapter);
    }

    // Método para guardar el detalle del ejercicio
    private void guardarDetalleEjercicio() {
        if (etSeries.getText().toString().isEmpty() || etRepeticiones.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int series = Integer.parseInt(etSeries.getText().toString());
        int repeticiones = Integer.parseInt(etRepeticiones.getText().toString());
        int ejercicioId = listaEjercicios.get(spinnerEjercicios.getSelectedItemPosition()).getId();

        DetalleEjercicio detalleEjercicio = new DetalleEjercicio();
        detalleEjercicio.setRutinaDiariaId(rutinaDiariaId);
        detalleEjercicio.setEjercicioId(ejercicioId);
        detalleEjercicio.setSeries(series);
        detalleEjercicio.setRepeticiones(repeticiones);

        long result = detalleEjercicioNegocio.agregarDetalleEjercicio(detalleEjercicio);
        if (result != -1) {
            Toast.makeText(this, "Ejercicio guardado correctamente.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Enviar resultado de éxito
            finish(); // Cierra la actividad y regresa a la pantalla anterior
        } else {
            Toast.makeText(this, "Error al guardar el ejercicio.", Toast.LENGTH_SHORT).show();
        }
    }
}
