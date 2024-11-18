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
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;
import com.example.app_gym.models.DetalleEjercicio;
import com.example.app_gym.models.Ejercicio;
import com.example.app_gym.negocio.ClienteNegocio;
import com.example.app_gym.negocio.DetalleEjercicioNegocio;
import com.example.app_gym.negocio.EjercicioNegocio;

import java.util.ArrayList;
import java.util.List;

public class ActualizarDetalleEjercicioActivity extends AppCompatActivity {

    private EjercicioNegocio ejercicioNegocio;
    private DetalleEjercicioNegocio detalleEjercicioNegocio;
    private ClienteNegocio clienteNegocio;
    private TextView tvInformacionCliente;
    private Spinner spinnerEjercicios;
    private EditText etSeries, etRepeticiones;
    private int detalleEjercicioId;
    private int clienteId;
    private List<Ejercicio> listaEjercicios;
    private DetalleEjercicio detalleEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_detalle_ejercicio);

        // Inicializar la base de datos y controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ejercicioNegocio = new EjercicioNegocio(dbHelper.getWritableDatabase());
        detalleEjercicioNegocio = new DetalleEjercicioNegocio(dbHelper.getWritableDatabase());
        clienteNegocio = new ClienteNegocio(dbHelper.getWritableDatabase());

        // Obtener el ID del detalle de ejercicio
        detalleEjercicioId = getIntent().getIntExtra("detalle_ejercicio_id", -1);
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Referenciar elementos de la vista
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);
        spinnerEjercicios = findViewById(R.id.spinnerEjercicios);
        etSeries = findViewById(R.id.etSeries);
        etRepeticiones = findViewById(R.id.etRepeticiones);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // Cargar los datos del detalle de ejercicio
        detalleEjercicio = detalleEjercicioNegocio.obtenerDetalleEjercicio(detalleEjercicioId);

        // Obtener la información del cliente y mostrarla
        Cliente cliente = clienteNegocio.obtenerCliente(clienteId);
        if (cliente != null) {
            mostrarInformacionCliente(cliente);
        }

        // Cargar los ejercicios en el Spinner
        cargarEjercicios();

        // Llenar los campos con los datos actuales
        etSeries.setText(String.valueOf(detalleEjercicio.getSeries()));
        etRepeticiones.setText(String.valueOf(detalleEjercicio.getRepeticiones()));

        // Seleccionar el ejercicio actual en el Spinner
        int ejercicioPosicion = getPosicionEjercicio(detalleEjercicio.getEjercicioId());
        spinnerEjercicios.setSelection(ejercicioPosicion);

        // Configurar el botón "Guardar"
        btnGuardar.setOnClickListener(v -> actualizarDetalleEjercicio());
    }

    private void mostrarInformacionCliente(Cliente cliente) {
        String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                cliente.getEdad() + " años\n" +
                "Plan: " + cliente.getEstado() + "\n" +
                "Se unió: " + cliente.getFechaEntrada();
        tvInformacionCliente.setText(informacion);
    }

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

    private int getPosicionEjercicio(int ejercicioId) {
        for (int i = 0; i < listaEjercicios.size(); i++) {
            if (listaEjercicios.get(i).getId() == ejercicioId) {
                return i;
            }
        }
        return 0;
    }

    private void actualizarDetalleEjercicio() {
        if (etSeries.getText().toString().isEmpty() || etRepeticiones.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int series = Integer.parseInt(etSeries.getText().toString());
        int repeticiones = Integer.parseInt(etRepeticiones.getText().toString());
        int ejercicioId = listaEjercicios.get(spinnerEjercicios.getSelectedItemPosition()).getId();

        detalleEjercicio.setEjercicioId(ejercicioId);
        detalleEjercicio.setSeries(series);
        detalleEjercicio.setRepeticiones(repeticiones);

        int result = detalleEjercicioNegocio.actualizarDetalleEjercicio(detalleEjercicio);
        if (result > 0) {
            Toast.makeText(this, "Ejercicio actualizado correctamente.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Enviar resultado de éxito
            finish(); // Cerrar actividad
        } else {
            Toast.makeText(this, "Error al actualizar el ejercicio.", Toast.LENGTH_SHORT).show();
        }
    }
}
