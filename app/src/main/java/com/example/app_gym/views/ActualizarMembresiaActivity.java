package com.example.app_gym.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
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

import java.util.Calendar;

public class ActualizarMembresiaActivity extends AppCompatActivity {

    private MembresiaController membresiaController;
    private ClienteController clienteController;
    private EditText etFechaInicio, etFechaVence;
    private TextView tvInformacionCliente;
    private int clienteId, membresiaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_membresia);

        // Inicializa la base de datos y los controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        membresiaController = new MembresiaController(dbHelper.getWritableDatabase());
        clienteController = new ClienteController(dbHelper.getWritableDatabase());

        // Obtiene el clienteId y membresiaId de la intención
        clienteId = getIntent().getIntExtra("cliente_id", -1);
        membresiaId = getIntent().getIntExtra("membresia_id", -1);

        // Referencia a los componentes de la vista
        etFechaInicio = findViewById(R.id.etFechaInicio);
        etFechaVence = findViewById(R.id.etFechaVence);
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);

        // Muestra la información del cliente y membresía
        mostrarInformacionCliente();
        cargarDatosMembresia();

        // Configuración del botón Volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> finish());

        // Configura el botón Guardar
        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> actualizarMembresia());

        // Configuración del DatePickerDialog para etFechaInicio y etFechaVence
        abrirDatePickerDirectamente(etFechaInicio);
        abrirDatePickerDirectamente(etFechaVence);
    }

    private void mostrarInformacionCliente() {
        Cliente cliente = clienteController.obtenerCliente(clienteId);

        if (cliente != null) {
            String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                    cliente.getEdad() + " años\n" +
                    "Se unió: " + cliente.getFechaEntrada();
            tvInformacionCliente.setText(informacion);
        }
    }

    private void cargarDatosMembresia() {
        Membresia membresia = membresiaController.obtenerMembresia(membresiaId);

        if (membresia != null) {
            etFechaInicio.setText(membresia.getFechaInicio());
            etFechaVence.setText(membresia.getFechaVencimiento());
        } else {
            Toast.makeText(this, "Error al cargar los datos de la membresía", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si no se pueden cargar los datos
        }
    }

    private void abrirDatePickerDirectamente(EditText editText) {
        editText.setFocusable(false);
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
                String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                editText.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.show();
        });
    }

    private void actualizarMembresia() {
        String fechaInicio = etFechaInicio.getText().toString();
        String fechaVence = etFechaVence.getText().toString();

        if (fechaInicio.isEmpty() || fechaVence.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Membresia membresia = new Membresia();
        membresia.setId(membresiaId);
        membresia.setFechaInicio(fechaInicio);
        membresia.setFechaVencimiento(fechaVence);
        membresia.setClienteId(clienteId);

        int result = membresiaController.actualizarMembresia(membresia);
        if (result > 0) {
            Toast.makeText(this, "Membresía actualizada correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar la membresía", Toast.LENGTH_SHORT).show();
        }
    }
}
