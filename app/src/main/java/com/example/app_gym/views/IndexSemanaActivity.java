package com.example.app_gym.views;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Cliente;
import com.example.app_gym.entities.RutinaSemanal;
import com.example.app_gym.negocio.ClienteNegocio;
import com.example.app_gym.negocio.RutinaSemanalNegocio;

import java.util.List;
public class IndexSemanaActivity extends AppCompatActivity {

    private ClienteNegocio clienteNegocio;
    private RutinaSemanalNegocio rutinaSemanalNegocio;
    private TextView tvInformacionCliente;
    private RecyclerView recyclerSemanas;
    private RutinaSemanalAdapter rutinaSemanalAdapter;
    private List<RutinaSemanal> listaRutinasSemanales;
    private int clienteId;
    private SQLiteDatabase db; // Añade esta variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_semana);

        // Inicializar la base de datos y controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase(); // Obtén la instancia de la base de datos
        clienteNegocio = new ClienteNegocio(db);
        rutinaSemanalNegocio = new RutinaSemanalNegocio(db);

        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        if (clienteId != -1) {
            Cliente cliente = clienteNegocio.obtenerCliente(clienteId);
            if (cliente != null) {
                mostrarInformacionCliente(cliente);
            }
        }

        recyclerSemanas = findViewById(R.id.recyclerSemanas);
        recyclerSemanas.setLayoutManager(new LinearLayoutManager(this));

        listaRutinasSemanales = rutinaSemanalNegocio.obtenerRutinasSemanalesDeCliente(clienteId);
        rutinaSemanalAdapter = new RutinaSemanalAdapter(listaRutinasSemanales, this, clienteId, db); // Pasa la instancia de la base de datos
        recyclerSemanas.setAdapter(rutinaSemanalAdapter);

        // Configurar el botón "Nueva Semana" para navegar a SemanaActivity
        Button btnNuevaSemana = findViewById(R.id.btnNuevaSemana);
        btnNuevaSemana.setOnClickListener(v -> {
            Intent intent = new Intent(IndexSemanaActivity.this, SemanaActivity.class);
            intent.putExtra("cliente_id", clienteId); // Pasa el clienteId a la nueva actividad
            startActivity(intent);
        });

        Button btnEstadoCliente = findViewById(R.id.btnEstadoCliente);
        btnEstadoCliente.setOnClickListener(v -> {
            Intent intent = new Intent(IndexSemanaActivity.this, IndexMembresiaActivity.class);
            intent.putExtra("cliente_id", clienteId); // Pasa el clienteId a la nueva actividad
            startActivity(intent);
        });
    }

    private void mostrarInformacionCliente(Cliente cliente) {
        String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                cliente.getEdad() + " años\n" +
                "Plan: " + cliente.getEstado() + "\n" +
                "Se unió: " + cliente.getFechaEntrada();
        tvInformacionCliente.setText(informacion);
    }

    private void actualizarListaRutinasSemanales() {
        listaRutinasSemanales.clear();
        listaRutinasSemanales.addAll(rutinaSemanalNegocio.obtenerRutinasSemanalesDeCliente(clienteId));
        rutinaSemanalAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarListaRutinasSemanales();
    }
}
