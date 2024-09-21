package com.example.app_gym.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.controllers.ClienteController;
import com.example.app_gym.controllers.RutinaSemanalController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;
import com.example.app_gym.models.RutinaSemanal;

import java.util.List;

public class IndexSemanaActivity extends AppCompatActivity {

    private ClienteController clienteController;
    private RutinaSemanalController rutinaSemanalController;
    private TextView tvInformacionCliente;
    private RecyclerView recyclerSemanas;
    private RutinaSemanalAdapter rutinaSemanalAdapter;
    private List<RutinaSemanal> listaRutinasSemanales;
    private int clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_semana);

        // Inicializar la base de datos y controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        clienteController = new ClienteController(dbHelper.getWritableDatabase());
        rutinaSemanalController = new RutinaSemanalController(dbHelper.getWritableDatabase());

        // Referencia al TextView donde mostrarás la información del cliente
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);

        // Obtener el ID del cliente desde el Intent
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Obtener el cliente por su ID y mostrar la información
        if (clienteId != -1) {
            Cliente cliente = clienteController.obtenerCliente(clienteId);
            if (cliente != null) {
                mostrarInformacionCliente(cliente);
            }
        }

        // Configurar el RecyclerView
        recyclerSemanas = findViewById(R.id.recyclerSemanas);
        recyclerSemanas.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la lista de rutinas semanales del cliente y configurar el adaptador
        listaRutinasSemanales = rutinaSemanalController.obtenerRutinasSemanalesDeCliente(clienteId);
        rutinaSemanalAdapter = new RutinaSemanalAdapter(listaRutinasSemanales, rutinaSemanalController);
        recyclerSemanas.setAdapter(rutinaSemanalAdapter);
    }

    // Método para mostrar la información del cliente
    private void mostrarInformacionCliente(Cliente cliente) {
        String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                cliente.getEdad() + " años\n" +
                "Plan: " + cliente.getEstado() + "\n" +
                "Se unió: " + cliente.getFechaEntrada();
        tvInformacionCliente.setText(informacion);
    }

    // Método para actualizar la lista de rutinas semanales del RecyclerView
    private void actualizarListaRutinasSemanales() {
        listaRutinasSemanales.clear();
        listaRutinasSemanales.addAll(rutinaSemanalController.obtenerRutinasSemanalesDeCliente(clienteId));
        rutinaSemanalAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de rutinas semanales cuando se vuelve a la actividad
        actualizarListaRutinasSemanales();
    }
}
