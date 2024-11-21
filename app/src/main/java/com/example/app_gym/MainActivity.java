package com.example.app_gym;

import android.app.AlertDialog;
import android.content.Intent; // Agregar esta importación
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Cliente;
import com.example.app_gym.negocio.ClienteNegocio;
import com.example.app_gym.views.ActualizarClienteActivity;
import com.example.app_gym.views.ClienteActivity;
import com.example.app_gym.views.ClienteAdapter;
import com.example.app_gym.views.IndexEjercicioActivity;
import com.example.app_gym.views.IndexSemanaActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClienteAdapter.OnClienteClickListener {

    private RecyclerView recyclerClientes;
    private ClienteAdapter clienteAdapter;
    private List<Cliente> listaClientes;
    private ClienteNegocio clienteNegocio;
    private static final int REQUEST_CODE_ADD_CLIENTE = 1;  // Código de solicitud para agregar un cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        clienteNegocio = new ClienteNegocio(dbHelper.getWritableDatabase());

        // Referenciamos el RecyclerView
        recyclerClientes = findViewById(R.id.recyclerClientes);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this));

        // Obtenemos la lista de clientes desde el controlador
        listaClientes = clienteNegocio.obtenerTodosLosClientes();

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
        Cliente cliente = listaClientes.get(position);
        Intent intent = new Intent(MainActivity.this, ActualizarClienteActivity.class);
        intent.putExtra("cliente_id", cliente.getId()); // Pasar el ID del cliente
        startActivityForResult(intent, REQUEST_CODE_ADD_CLIENTE); // Cambiar a startActivityForResult
    }


    @Override
    public void onEliminarClick(int position) {
        // Obtener el cliente que se va a eliminar
        Cliente cliente = listaClientes.get(position);

        // Mostrar un AlertDialog para confirmar la eliminación
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Cliente")
                .setMessage("¿Estás seguro de que deseas eliminar a " + cliente.getNombre() + " " + cliente.getApellido() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Eliminar el cliente de la base de datos
                    int result = clienteNegocio.eliminarCliente(cliente.getId());

                    if (result > 0) {
                        // Recargar la lista de clientes desde la base de datos
                        listaClientes.clear();
                        listaClientes.addAll(clienteNegocio.obtenerTodosLosClientes());

                        // Notificar al adaptador que los datos han cambiado
                        clienteAdapter.notifyDataSetChanged();

                        Toast.makeText(this, "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error al eliminar el cliente", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null) // El botón "No" cierra el diálogo sin hacer nada
                .show();
    }


    // Sobrescribir onActivityResult para recibir el resultado de ClienteActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == REQUEST_CODE_ADD_CLIENTE)) {
            // Cliente agregado o actualizado, recargar la lista de clientes
            listaClientes.clear();
            listaClientes.addAll(clienteNegocio.obtenerTodosLosClientes());
            clienteAdapter.notifyDataSetChanged();
        }
    }

}