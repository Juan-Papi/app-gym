package com.example.app_gym.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.controllers.EjercicioController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Ejercicio;
import com.example.app_gym.views.EjercicioAdapter;

import java.util.List;

public class IndexEjercicioActivity extends AppCompatActivity {

    private RecyclerView recyclerEjercicios;
    private EjercicioAdapter ejercicioAdapter;
    private List<Ejercicio> listaEjercicios;
    private EjercicioController ejercicioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_ejercicio);

        // Inicializar la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ejercicioController = new EjercicioController(dbHelper.getWritableDatabase());

        // Referenciamos el RecyclerView
        recyclerEjercicios = findViewById(R.id.recyclerEjercicios);
        recyclerEjercicios.setLayoutManager(new LinearLayoutManager(this));

        // Obtenemos la lista de ejercicios desde el controlador
        listaEjercicios = ejercicioController.obtenerTodosLosEjerciciosConRelaciones();

        // Configuramos el adaptador
        ejercicioAdapter = new EjercicioAdapter(listaEjercicios, ejercicioController,this);
        recyclerEjercicios.setAdapter(ejercicioAdapter);

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            // Cerrar esta actividad
            finish();
        });

        Button btnNuevoEjercicio = findViewById(R.id.btnNuevoEjercicio);
        btnNuevoEjercicio.setOnClickListener(v -> {
            // Intent para abrir EjercicioActivity
            Intent intent = new Intent(this, EjercicioActivity.class);
            startActivity(intent);
        });

        Button btnVideos = findViewById(R.id.btnVideo);
        btnVideos.setOnClickListener(v -> {
            Intent intent = new Intent(this, IndexVideoActivity.class);
            startActivity(intent);
        });

        Button btnCategorias = findViewById(R.id.btnCategorias);
        btnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexEjercicioActivity.this, IndexCategoriaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de ejercicios
        actualizarListaEjercicios();
    }

    private void actualizarListaEjercicios() {
        listaEjercicios = ejercicioController.obtenerTodosLosEjerciciosConRelaciones();
        ejercicioAdapter = new EjercicioAdapter(listaEjercicios, ejercicioController,this);
        recyclerEjercicios.setAdapter(ejercicioAdapter);
        ejercicioAdapter.notifyDataSetChanged();
    }

}
