package com.example.app_gym.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Cliente;
import com.example.app_gym.entities.DetalleEjercicio;
import com.example.app_gym.entities.RutinaDiaria;
import com.example.app_gym.negocio.ClienteNegocio;
import com.example.app_gym.negocio.DetalleEjercicioNegocio;
import com.example.app_gym.negocio.RutinaDiariaNegocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IndexDetalleEjercicioActivity extends AppCompatActivity {

    private ClienteNegocio clienteNegocio;
    private DetalleEjercicioNegocio detalleEjercicioNegocio;
    private RutinaDiariaNegocio rutinaDiariaNegocio;
    private TextView tvInformacionCliente, tvTitulo;
    private RecyclerView recyclerDetalles;
    private DetalleEjercicioAdapter detalleEjercicioAdapter;
    private List<DetalleEjercicio> listaDetallesEjercicio;
    private int clienteId;
    private int rutinaDiariaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_detalle_ejercicio);

        // Inicializar la base de datos y controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        detalleEjercicioNegocio = new DetalleEjercicioNegocio(dbHelper.getWritableDatabase());
        rutinaDiariaNegocio= new RutinaDiariaNegocio(dbHelper.getWritableDatabase());
        clienteNegocio = new ClienteNegocio(dbHelper.getWritableDatabase());

        // Obtener los IDs del cliente y de la rutina diaria desde el Intent
        clienteId = getIntent().getIntExtra("cliente_id", -1);
        rutinaDiariaId = getIntent().getIntExtra("rutina_diaria_id", -1);

        // Inicializar TextView del cliente y del título
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);
        tvTitulo = findViewById(R.id.tvTitulo);

        // Obtener la información del cliente y mostrarla
        Cliente cliente = clienteNegocio.obtenerCliente(clienteId);
        if (cliente != null) {
            mostrarInformacionCliente(cliente);
        }

        // Obtener la rutina diaria y establecer el título
        // Obtener la rutina diaria y establecer el título
        RutinaDiaria rutinaDiaria = rutinaDiariaNegocio.obtenerRutinaDiaria(rutinaDiariaId);
        if (rutinaDiaria != null) {
            try {
                // Establecer el formato de fecha de entrada
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = sdf.parse(rutinaDiaria.getFecha());

                // Crear un formato de salida de la fecha en español
                SimpleDateFormat sdfDisplay = new SimpleDateFormat("EEEE dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

                // Formatear la fecha
                String titulo = sdfDisplay.format(date);
                tvTitulo.setText(titulo.substring(0, 1).toUpperCase() + titulo.substring(1)); // Capitalizar la primera letra
            } catch (ParseException e) {
                tvTitulo.setText(rutinaDiaria.getNombre() + " " + rutinaDiaria.getFecha());
            }
        }

        // Configurar el RecyclerView
        recyclerDetalles = findViewById(R.id.recyclerDetalles);
        recyclerDetalles.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la lista de detalles de ejercicio de la rutina diaria
        listaDetallesEjercicio = detalleEjercicioNegocio.obtenerDetallesDeEjercicio(rutinaDiariaId);

        // Configurar el adaptador
        detalleEjercicioAdapter = new DetalleEjercicioAdapter(listaDetallesEjercicio, new DetalleEjercicioAdapter.OnDetalleEjercicioClickListener() {
            @Override
            public void onEditarClick(int position) {
                DetalleEjercicio detalleEjercicio = listaDetallesEjercicio.get(position);
                Intent intent = new Intent(IndexDetalleEjercicioActivity.this, ActualizarDetalleEjercicioActivity.class);
                intent.putExtra("detalle_ejercicio_id", detalleEjercicio.getId());
                intent.putExtra("cliente_id", clienteId);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onEliminarClick(int position) {
                DetalleEjercicio detalleEjercicio = listaDetallesEjercicio.get(position);

                new AlertDialog.Builder(IndexDetalleEjercicioActivity.this)
                        .setTitle("Eliminar Detalle de Ejercicio")
                        .setMessage("¿Estás seguro de que deseas eliminar este detalle de ejercicio?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            // Realizar la acción de eliminación
                            int resultado = detalleEjercicioNegocio.eliminarDetalleEjercicio(detalleEjercicio.getId());
                            if (resultado > 0) {
                                listaDetallesEjercicio.remove(position);
                                detalleEjercicioAdapter.notifyItemRemoved(position);
                                detalleEjercicioAdapter.notifyItemRangeChanged(position, listaDetallesEjercicio.size());
                                Toast.makeText(IndexDetalleEjercicioActivity.this, "Detalle de ejercicio eliminado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(IndexDetalleEjercicioActivity.this, "Error al eliminar el detalle de ejercicio", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }


        });

        recyclerDetalles.setAdapter(detalleEjercicioAdapter);

        // Configurar el botón "Nuevo Ejercicio" si existe en la vista
        Button btnNuevoEjercicio = findViewById(R.id.btnNuevoEjercicio);
        btnNuevoEjercicio.setOnClickListener(v -> {
            Intent intent = new Intent(IndexDetalleEjercicioActivity.this, DetalleEjercicioActivity.class);
            intent.putExtra("rutina_diaria_id", rutinaDiariaId);
            intent.putExtra("cliente_id", clienteId);
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
            // Actualizar la lista de detalles de ejercicios
            actualizarListaDetalles();
        }
    }

    // Método para actualizar la lista de detalles de ejercicios
    private void actualizarListaDetalles() {
        listaDetallesEjercicio.clear();
        listaDetallesEjercicio.addAll(detalleEjercicioNegocio.obtenerDetallesDeEjercicio(rutinaDiariaId));
        detalleEjercicioAdapter.notifyDataSetChanged();
    }
}
