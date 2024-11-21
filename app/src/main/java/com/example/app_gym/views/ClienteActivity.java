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
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Cliente;
import com.example.app_gym.negocio.ClienteNegocio;

import java.util.Calendar;

public class ClienteActivity extends AppCompatActivity {
    private ClienteNegocio clienteNegocio;
    private EditText etNombre, etApellido, etEdad, etFechaEntrada, etObs;
    private Spinner spinnerEstado;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        // Inicializa la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        clienteNegocio = new ClienteNegocio(db);

        // Referencias a los componentes de la vista
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEdad = findViewById(R.id.etEdad);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        etFechaEntrada = findViewById(R.id.etFechaEntrada);
        etObs = findViewById(R.id.etObs);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Configuración del Spinner para seleccionar "Activo" o "Inactivo"
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estado_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);

        // Configuración del botón Volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> finish());  // Cierra la actividad actual y vuelve a la actividad anterior

        // Configuración del DatePickerDialog para seleccionar la fecha
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

        // Configura el botón Guardar
        btnGuardar.setOnClickListener(v -> {
            // Captura los valores del formulario
            String nombre = etNombre.getText().toString().trim();
            String apellido = etApellido.getText().toString().trim();
            String edadStr = etEdad.getText().toString().trim();
            String estado = spinnerEstado.getSelectedItem().toString();
            String fechaEntrada = etFechaEntrada.getText().toString().trim();
            String obs = etObs.getText().toString().trim();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || edadStr.isEmpty() || fechaEntrada.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                return;
            }

            int edad = Integer.parseInt(edadStr);

            // Crea un nuevo cliente
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(nombre);
            nuevoCliente.setApellido(apellido);
            nuevoCliente.setEdad(edad);
            nuevoCliente.setEstado(estado);
            nuevoCliente.setFechaEntrada(fechaEntrada);
            nuevoCliente.setObs(obs);

            // Guarda el cliente en la base de datos
            clienteNegocio.agregarCliente(nuevoCliente);
            Toast.makeText(ClienteActivity.this, "Cliente guardado", Toast.LENGTH_SHORT).show();

            // Limpiar el formulario después de guardar
            etNombre.setText("");
            etApellido.setText("");
            etEdad.setText("");
            spinnerEstado.setSelection(0); // Restablece el spinner a la primera opción
            etFechaEntrada.setText("");
            etObs.setText("");

            // Devolver un resultado a la actividad anterior
            setResult(RESULT_OK);  // Indica que la operación fue exitosa
            finish();  // Cierra la actividad y vuelve a MainActivity
        });
    }
}
