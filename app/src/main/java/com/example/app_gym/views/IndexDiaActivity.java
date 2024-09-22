package com.example.app_gym.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.controllers.ClienteController;
import com.example.app_gym.controllers.RutinaDiariaController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Cliente;
import com.example.app_gym.models.RutinaDiaria;
import java.util.List;

public class IndexDiaActivity extends AppCompatActivity {

    private ClienteController clienteController;
    private RutinaDiariaController rutinaDiariaController;
    private TextView tvInformacionCliente;
    private RecyclerView recyclerDias;
    private RutinaDiariaAdapter rutinaDiariaAdapter;
    private List<RutinaDiaria> listaRutinasDiarias;
    private int clienteId;
    private int semanaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_dia);

        // Inicializar la base de datos y controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        clienteController = new ClienteController(dbHelper.getWritableDatabase());
        rutinaDiariaController = new RutinaDiariaController(dbHelper.getWritableDatabase());

        // Obtener los IDs del cliente y de la semana desde el Intent
        clienteId = getIntent().getIntExtra("cliente_id", -1);
        semanaId = getIntent().getIntExtra("rutina_semanal_id", -1);

        // Mostrar la información del cliente
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);
        Cliente cliente = clienteController.obtenerCliente(clienteId);
        if (cliente != null) {
            mostrarInformacionCliente(cliente);
        }

        // Configurar el RecyclerView
        recyclerDias = findViewById(R.id.recyclerDias);
        recyclerDias.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la lista de rutinas diarias de la semana y configurar el adaptador
        listaRutinasDiarias = rutinaDiariaController.obtenerRutinasDiariasDeSemana(semanaId);
        Log.d("IndexDiaActivity", "Rutinas diarias obtenidas: " + listaRutinasDiarias.size());

        rutinaDiariaAdapter = new RutinaDiariaAdapter(listaRutinasDiarias, new RutinaDiariaAdapter.OnRutinaDiariaClickListener() {
            @Override
            public void onVerClick(int position) {
                // Acción al hacer clic en "Ver"
            }

            @Override
            public void onEditarClick(int position) {
                // Acción al hacer clic en "Editar"
            }

            @Override
            public void onEliminarClick(int position) {
                // Acción al hacer clic en "Eliminar"
            }

            @Override
            public void onPdfClick(int position) {
                // Acción al hacer clic en "PDF"
            }
        });
        recyclerDias.setAdapter(rutinaDiariaAdapter);

        Button btnNuevoDia = findViewById(R.id.btnNuevoDia);
        btnNuevoDia.setOnClickListener(v -> {
            // Intent para abrir DiaActivity
            Intent intent = new Intent(IndexDiaActivity.this, DiaActivity.class);
            intent.putExtra("semana_id", semanaId); // Pasar también el ID de la rutina semanal
            startActivityForResult(intent, 1); // Usar startActivityForResult para obtener el resultado
        });
    }

    // Método para mostrar la información del cliente
    private void mostrarInformacionCliente(Cliente cliente) {
        String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                cliente.getEdad() + " años\n" +
                "Plan: " + cliente.getEstado() + "\n" +
                "Se unió: " + cliente.getFechaEntrada();
        tvInformacionCliente.setText(informacion);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Log.d("IndexDiaActivity", "onActivityResult called - Updating list");
            // Actualizar la lista de rutinas diarias
            actualizarListaRutinasDiarias();
        }
    }


    // Método para actualizar la lista de rutinas diarias
    private void actualizarListaRutinasDiarias() {
        listaRutinasDiarias.clear();
        listaRutinasDiarias.addAll(rutinaDiariaController.obtenerRutinasDiariasDeSemana(semanaId));
        rutinaDiariaAdapter.notifyDataSetChanged();
    }

}
