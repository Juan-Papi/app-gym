package com.example.app_gym;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.controllers.ClienteController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;
import com.example.app_gym.views.ClienteAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClienteAdapter.OnClienteClickListener {

    private RecyclerView recyclerClientes;
    private ClienteAdapter clienteAdapter;
    private List<Cliente> listaClientes;
    private ClienteController clienteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Usar el mismo layout

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
    }

    @Override
    public void onVerClick(int position) {
        // Acción para ver el cliente (aún por implementar)
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
}
