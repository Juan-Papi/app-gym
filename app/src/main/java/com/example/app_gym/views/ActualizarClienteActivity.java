package com.example.app_gym.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.R;
import com.example.app_gym.interfaces.IClienteNegocio;
import com.example.app_gym.proxies.ClienteNegocioProxy;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Cliente;
import com.example.app_gym.negocio.ClienteNegocio;

import java.util.Calendar;

public class ActualizarClienteActivity extends AppCompatActivity {
    private IClienteNegocio clienteNegocio;
    private EditText etNombre, etApellido, etEdad, etFechaEntrada, etObs;
    private Spinner spinnerEstado;
    private Button btnGuardar, btnVolver;
    private int clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_cliente);

        // Inicializar la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ClienteNegocio realNegocio = new ClienteNegocio(db);
        clienteNegocio = new ClienteNegocioProxy(realNegocio);

        // Referenciar los elementos de la vista
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEdad = findViewById(R.id.etEdad);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        etFechaEntrada = findViewById(R.id.etFechaEntrada);
        etObs = findViewById(R.id.etObs);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);

        // Configuración del Spinner para "Activo" e "Inactivo"
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estado_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);

        // Obtener el ID del cliente a actualizar
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Cargar los datos del cliente
        Cliente cliente = clienteNegocio.obtenerCliente(clienteId);
        if (cliente != null) {
            cargarDatosCliente(cliente);
        }

        // Configuración del DatePicker para seleccionar la fecha
        etFechaEntrada.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
                String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                etFechaEntrada.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.show();
        });

        // Configuración del botón "Guardar"
        btnGuardar.setOnClickListener(v -> actualizarCliente());

        // Configuración del botón "Volver"
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para cargar los datos del cliente en los campos de la vista
    private void cargarDatosCliente(Cliente cliente) {
        etNombre.setText(cliente.getNombre());
        etApellido.setText(cliente.getApellido());
        etEdad.setText(String.valueOf(cliente.getEdad()));
        etFechaEntrada.setText(cliente.getFechaEntrada());
        etObs.setText(cliente.getObs());

        // Seleccionar el estado en el Spinner
        if (cliente.getEstado().equalsIgnoreCase("Activo")) {
            spinnerEstado.setSelection(0);
        } else {
            spinnerEstado.setSelection(1);
        }
    }

    // Método para actualizar el cliente
    private void actualizarCliente() {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();
        String estado = spinnerEstado.getSelectedItem().toString();
        String fechaEntrada = etFechaEntrada.getText().toString().trim();
        String obs = etObs.getText().toString().trim();

        // Validación de campos
        if (nombre.isEmpty() || apellido.isEmpty() || edadStr.isEmpty() || fechaEntrada.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad = Integer.parseInt(edadStr);

        // Actualizar los datos del cliente
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEdad(edad);
        cliente.setEstado(estado);
        cliente.setFechaEntrada(fechaEntrada);
        cliente.setObs(obs);

        int resultado = clienteNegocio.actualizarCliente(cliente);
        if (resultado > 0) {
            Toast.makeText(this, "Cliente actualizado correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);  // Indica que la operación fue exitosa
            finish();  // Cierra la actividad y regresa a la anterior
        } else {
            Toast.makeText(this, "Error al actualizar el cliente", Toast.LENGTH_SHORT).show();
        }
    }
}
