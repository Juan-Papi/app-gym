package com.example.app_gym.views;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_gym.R;
import com.example.app_gym.controllers.MembresiaController;
import com.example.app_gym.controllers.ClienteController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;
import com.example.app_gym.models.Membresia;

public class MembresiaActivity extends AppCompatActivity {

    private MembresiaController membresiaController;
    private ClienteController clienteController;
    private EditText etFechaInicio, etFechaVence;
    private TextView tvInformacionCliente;
    private int clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membresia);

        // Inicializa la base de datos y los controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        membresiaController = new MembresiaController(dbHelper.getWritableDatabase());
        clienteController = new ClienteController(dbHelper.getWritableDatabase());

        // Obtiene el clienteId de la intención
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Referencia a los componentes de la vista
        etFechaInicio = findViewById(R.id.etFechaInicio);
        etFechaVence = findViewById(R.id.etFechaVence);
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);

        // Muestra la información del cliente
        mostrarInformacionCliente();

        // Configuración del botón Volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            finish();  // Cierra la actividad actual y vuelve a la actividad anterior
        });

        // Configura el botón Guardar
        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> {
            guardarMembresia();
        });
    }

    // Método para mostrar la información del cliente
    private void mostrarInformacionCliente() {
        Cliente cliente = clienteController.obtenerCliente(clienteId);

        if (cliente != null) {
            String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                    cliente.getEdad() + " años\n" +
                    "Se unió: " + cliente.getFechaEntrada();
            tvInformacionCliente.setText(informacion);
        }
    }
    private void guardarMembresia() {
        String fechaInicio = etFechaInicio.getText().toString();
        String fechaVence = etFechaVence.getText().toString();

        if (fechaInicio.isEmpty() || fechaVence.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Membresia nuevaMembresia = new Membresia();
        nuevaMembresia.setFechaInicio(fechaInicio);
        nuevaMembresia.setFechaVencimiento(fechaVence);
        nuevaMembresia.setClienteId(clienteId);

        long result = membresiaController.crearNuevaMembresia(nuevaMembresia);
        if (result != -1) {
            Toast.makeText(this, "Membresía guardada correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Establece el resultado como RESULT_OK
            finish(); // Cierra la actividad y regresa al index
        } else {
            Toast.makeText(this, "Error al guardar la membresía", Toast.LENGTH_SHORT).show();
        }
    }
}
