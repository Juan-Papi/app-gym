package com.example.app_gym.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.R;
import com.example.app_gym.controllers.ClienteController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;

public class ClienteActivity extends AppCompatActivity {
    private ClienteController clienteController;
    private EditText etNombre, etApellido, etEdad, etEstado, etFechaEntrada, etObs;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        // Inicializa la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        clienteController = new ClienteController(db);

        // Referencias a los componentes de la vista
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEdad = findViewById(R.id.etEdad);
        etEstado = findViewById(R.id.etEstado);
        etFechaEntrada = findViewById(R.id.etFechaEntrada);
        etObs = findViewById(R.id.etObs);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Configura el botón Guardar
        btnGuardar.setOnClickListener(v -> {
            // Captura los valores del formulario
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            int edad = Integer.parseInt(etEdad.getText().toString());
            String estado = etEstado.getText().toString();
            String fechaEntrada = etFechaEntrada.getText().toString();
            String obs = etObs.getText().toString();

            // Crea un nuevo cliente
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(nombre);
            nuevoCliente.setApellido(apellido);
            nuevoCliente.setEdad(edad);
            nuevoCliente.setEstado(estado);
            nuevoCliente.setFechaEntrada(fechaEntrada);
            nuevoCliente.setObs(obs);

            // Guarda el cliente en la base de datos
            clienteController.crearNuevoCliente(nuevoCliente);
            Toast.makeText(ClienteActivity.this, "Cliente guardado", Toast.LENGTH_SHORT).show();

            // Limpiar el formulario después de guardar
            etNombre.setText("");
            etApellido.setText("");
            etEdad.setText("");
            etEstado.setText("");
            etFechaEntrada.setText("");
            etObs.setText("");
        });
    }
}
