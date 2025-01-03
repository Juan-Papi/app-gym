package com.example.app_gym.views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Cliente;
import com.example.app_gym.entities.Membresia;
import com.example.app_gym.negocio.ClienteNegocio;
import com.example.app_gym.negocio.MembresiaNegocio;

import java.util.List;

public class IndexMembresiaActivity extends AppCompatActivity implements MembresiaAdapter.OnMembresiaClickListener {

    private RecyclerView recyclerMembresias;
    private MembresiaAdapter membresiaAdapter;
    private List<Membresia> listaMembresias;
    private MembresiaNegocio membresiaNegocio;
    private ClienteNegocio clienteNegocio;
    private int clienteId;
    private TextView tvInformacionCliente; // TextView para mostrar la información del cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_membresia);

        // Inicializa la base de datos y los controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        membresiaNegocio = new MembresiaNegocio(dbHelper.getWritableDatabase());
        clienteNegocio = new ClienteNegocio(dbHelper.getWritableDatabase());

        // Obtiene el clienteId de la intención
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Obtiene la información del cliente y muestra en la vista
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);
        mostrarInformacionCliente();

        // Configura el RecyclerView para mostrar las membresías del cliente
        recyclerMembresias = findViewById(R.id.recyclerMembresias);
        recyclerMembresias.setLayoutManager(new LinearLayoutManager(this));

        listaMembresias = membresiaNegocio.obtenerMembresiasPorCliente(clienteId);
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
        if (resultCode == RESULT_OK) {
            // Actualiza la lista de membresías si se agregó o editó una membresía
            listaMembresias.clear();
            listaMembresias.addAll(membresiaNegocio.obtenerMembresiasPorCliente(clienteId));
            membresiaAdapter.notifyDataSetChanged();
        }
    }


    // Método para mostrar la información del cliente
    private void mostrarInformacionCliente() {
        Cliente cliente = clienteNegocio.obtenerCliente(clienteId);

        if (cliente != null) {
            String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                    cliente.getEdad() + " años\n" +
                    "Plan: " + cliente.getEstado() + "\n" +
                    "Se unió: " + cliente.getFechaEntrada();
            tvInformacionCliente.setText(informacion);
        }
    }

    @Override
    public void onEditarClick(int position) {
        // Obtén la membresía seleccionada de la lista
        Membresia membresiaSeleccionada = listaMembresias.get(position);

        // Inicia la actividad ActualizarMembresiaActivity
        Intent intent = new Intent(IndexMembresiaActivity.this, ActualizarMembresiaActivity.class);
        intent.putExtra("cliente_id", clienteId); // Pasa el ID del cliente
        intent.putExtra("membresia_id", membresiaSeleccionada.getId()); // Pasa el ID de la membresía seleccionada
        startActivityForResult(intent, 2); // Utiliza un código de solicitud diferente para la edición
    }

    @Override
    public void onEliminarClick(int position) {
        // Obtén la membresía seleccionada de la lista
        Membresia membresiaSeleccionada = listaMembresias.get(position);

        // Muestra el diálogo de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Membresía")
                .setMessage("¿Estás seguro de que deseas eliminar esta membresía?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Llama al controlador para eliminar la membresía
                    int resultado = membresiaNegocio.eliminarMembresia(membresiaSeleccionada.getId());

                    if (resultado > 0) {
                        // Elimina la membresía de la lista y notifica al adaptador
                        listaMembresias.remove(position);
                        membresiaAdapter.notifyItemRemoved(position);
                        membresiaAdapter.notifyItemRangeChanged(position, listaMembresias.size());
                        Toast.makeText(IndexMembresiaActivity.this, "Membresía eliminada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IndexMembresiaActivity.this, "Error al eliminar la membresía", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
