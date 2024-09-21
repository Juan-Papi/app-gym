package com.example.app_gym;

import android.content.Intent; // Agregar esta importación
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.controllers.ClienteController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;
import com.example.app_gym.views.ClienteActivity;
import com.example.app_gym.views.ClienteAdapter;
import com.example.app_gym.views.IndexEjercicioActivity;
import com.example.app_gym.views.IndexSemanaActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClienteAdapter.OnClienteClickListener {

    private RecyclerView recyclerClientes;
    private ClienteAdapter clienteAdapter;
    private List<Cliente> listaClientes;
    private ClienteController clienteController;
    private static final int REQUEST_CODE_ADD_CLIENTE = 1;  // Código de solicitud para agregar un cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        clienteController = new ClienteController(dbHelper.getWritableDatabase());

        // Referenciamos el RecyclerView
        recyclerClientes = findViewById(R.id.recyclerClientes);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this));

        // Obtenemos la lista de clientes desde el controlador
        listaClientes = clienteController.obtenerTodosLosClientes();

        // Configuramos el adapter y lo asociamos al RecyclerView
        clienteAdapter = new ClienteAdapter(listaClientes, this);
        recyclerClientes.setAdapter(clienteAdapter);

        // Referenciar el botón NUEVO CLIENTE
        Button btnNuevoCliente = findViewById(R.id.btnNuevoCliente);

        // Configurar el evento onClick para navegar a ClienteActivity
        btnNuevoCliente.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClienteActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_CLIENTE);  // Esperar un resultado de ClienteActivity
        });

        Button btnEjercicios = findViewById(R.id.btnEjercicios);
        btnEjercicios.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IndexEjercicioActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onVerClick(int position) {
        Cliente cliente = listaClientes.get(position);
        Intent intent = new Intent(MainActivity.this, IndexSemanaActivity.class);
        intent.putExtra("cliente_id", cliente.getId()); // Pasar el ID del cliente
        startActivity(intent);
    }

    @Override
    public void onEditarClick(int position) {
        // Acción para editar el cliente (aún por implementar)
    }

    @Override
    public void onEliminarClick(int position) {
        // Acción para eliminar el cliente
        Cliente cliente = listaClientes.get(position);
        clienteController.eliminarCliente(cliente.getId());
        listaClientes.remove(position);
        clienteAdapter.notifyItemRemoved(position);
    }

    // Sobrescribir onActivityResult para recibir el resultado de ClienteActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CLIENTE && resultCode == RESULT_OK) {
            // Cliente agregado, recargar la lista de clientes
            listaClientes.clear();
            listaClientes.addAll(clienteController.obtenerTodosLosClientes());
            clienteAdapter.notifyDataSetChanged();
        }
    }
}