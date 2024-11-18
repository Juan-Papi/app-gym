package com.example.app_gym.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_gym.R;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;
import com.example.app_gym.models.Membresia;
import com.example.app_gym.negocio.ClienteNegocio;
import com.example.app_gym.negocio.MembresiaNegocio;

import java.util.Calendar;

public class MembresiaActivity extends AppCompatActivity {

    private MembresiaNegocio membresiaNegocio;
    private ClienteNegocio clienteNegocio;
    private EditText etFechaInicio, etFechaVence;
    private TextView tvInformacionCliente;
    private int clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membresia);

        // Inicializa la base de datos y los controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        membresiaNegocio = new MembresiaNegocio(dbHelper.getWritableDatabase());
        clienteNegocio= new ClienteNegocio(dbHelper.getWritableDatabase());

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
        btnVolver.setOnClickListener(v -> finish());  // Cierra la actividad actual y vuelve a la actividad anterior

        // Configura el botón Guardar
        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> guardarMembresia());

        // Configuración del DatePickerDialog para que se abra al hacer clic
        abrirDatePickerDirectamente(etFechaInicio);
        abrirDatePickerDirectamente(etFechaVence);
    }

    private void mostrarInformacionCliente() {
        Cliente cliente = clienteNegocio.obtenerCliente(clienteId);

        if (cliente != null) {
            String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                    cliente.getEdad() + " años\n" +
                    "Se unió: " + cliente.getFechaEntrada();
            tvInformacionCliente.setText(informacion);
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

        long result = membresiaNegocio.agregarMembresia(nuevaMembresia);
        if (result != -1) {
            Toast.makeText(this, "Membresía guardada correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Establece el resultado como RESULT_OK
            finish(); // Cierra la actividad y regresa al index
        } else {
            Toast.makeText(this, "Error al guardar la membresía", Toast.LENGTH_SHORT).show();
        }
    }
}
