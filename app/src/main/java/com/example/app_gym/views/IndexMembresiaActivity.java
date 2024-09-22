package com.example.app_gym.views;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.controllers.ClienteController;
import com.example.app_gym.controllers.MembresiaController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;
import com.example.app_gym.models.Membresia;

import java.util.List;

public class IndexMembresiaActivity extends AppCompatActivity implements MembresiaAdapter.OnMembresiaClickListener {

    private RecyclerView recyclerMembresias;
    private MembresiaAdapter membresiaAdapter;
    private List<Membresia> listaMembresias;
    private MembresiaController membresiaController;
    private ClienteController clienteController;
    private int clienteId;
    private TextView tvInformacionCliente; // TextView para mostrar la información del cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_membresia);

        // Inicializa la base de datos y los controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        membresiaController = new MembresiaController(dbHelper.getWritableDatabase());
        clienteController = new ClienteController(dbHelper.getWritableDatabase());

        // Obtiene el clienteId de la intención
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Obtiene la información del cliente y muestra en la vista
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);
        mostrarInformacionCliente();

        // Configura el RecyclerView para mostrar las membresías del cliente
        recyclerMembresias = findViewById(R.id.recyclerMembresias);
        recyclerMembresias.setLayoutManager(new LinearLayoutManager(this));

        listaMembresias = membresiaController.obtenerMembresiasPorCliente(clienteId);
        membresiaAdapter = new MembresiaAdapter(listaMembresias, this);
        recyclerMembresias.setAdapter(membresiaAdapter);

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            // Cerrar esta actividad
            finish();
        });

        // Configura el botón "Nueva Membresía"
        Button btnNuevaMembresia = findViewById(R.id.btnNuevaMembresia);
        btnNuevaMembresia.setOnClickListener(v -> {
            Intent intent = new Intent(IndexMembresiaActivity.this, MembresiaActivity.class);
            intent.putExtra("cliente_id", clienteId);
            startActivityForResult(intent, 1); // Usar startActivityForResult
        });
    }

    // Método para manejar el resultado de MembresiaActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Actualiza la lista de membresías
            listaMembresias.clear();
            listaMembresias.addAll(membresiaController.obtenerMembresiasPorCliente(clienteId));
            membresiaAdapter.notifyDataSetChanged();
        }
    }


    // Método para mostrar la información del cliente
    private void mostrarInformacionCliente() {
        Cliente cliente = clienteController.obtenerCliente(clienteId);

        if (cliente != null) {
            String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                    cliente.getEdad() + " años\n" +
                    "Plan: " + cliente.getEstado() + "\n" +
                    "Se unió: " + cliente.getFechaEntrada();
            tvInformacionCliente.setText(informacion);
        }
    }

    @Override
    public void onVerClick(int position) {
        // Acción para ver una membresía
    }

    @Override
    public void onEditarClick(int position) {
        // Acción para editar una membresía
    }

    @Override
    public void onEliminarClick(int position) {
        // Acción para eliminar una membresía
        Membresia membresia = listaMembresias.get(position);
        membresiaController.eliminarMembresia(membresia.getId());
        listaMembresias.remove(position);
        membresiaAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onPdfClick(int position) {
        // Acción para generar PDF
    }
}
