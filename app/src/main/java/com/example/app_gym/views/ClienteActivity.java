package com.example.app_gym.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.R;
import com.example.app_gym.controllers.ClienteController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

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

        // Configuración del botón Volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            finish();  // Cierra la actividad actual y vuelve a la actividad anterior
        });

        // Configura el botón Guardar
        // Configura el botón Guardar
        btnGuardar.setOnClickListener(v -> {
            // Captura los valores del formulario
            String nombre = etNombre.getText().toString().trim();
            String apellido = etApellido.getText().toString().trim();
            String edadStr = etEdad.getText().toString().trim();
            String estado = etEstado.getText().toString().trim();
            String fechaEntrada = etFechaEntrada.getText().toString().trim();
            String obs = etObs.getText().toString().trim();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese el nombre.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (apellido.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese el apellido.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (edadStr.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese la edad.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!esNumeroValido(edadStr)) {
                Toast.makeText(this, "La edad debe ser un número válido.", Toast.LENGTH_SHORT).show();
                return;
            }

            int edad = Integer.parseInt(edadStr);

            if (edad <= 0) {
                Toast.makeText(this, "La edad debe ser mayor que 0.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (estado.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese el estado del cliente.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (fechaEntrada.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese la fecha de entrada.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!esFechaValida(fechaEntrada)) {
                Toast.makeText(this, "La fecha debe tener el formato AAAA-MM-DD.", Toast.LENGTH_SHORT).show();
                return;
            }

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

            // Devolver un resultado a la actividad anterior
            setResult(RESULT_OK);  // Indica que la operación fue exitosa
            finish();  // Cierra la actividad y vuelve a MainActivity
        });
    }


    // Método para validar si es un número válido
    private boolean esNumeroValido(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Método para validar la fecha en formato "AAAA-MM-DD"
    private boolean esFechaValida(String fecha) {
        // Expresión regular para validar el formato
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        if (!Pattern.matches(regex, fecha)) {
            return false;
        }

        // Verificar que la fecha sea válida
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Para que no acepte fechas no válidas como "2024-02-30"

        try {
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
