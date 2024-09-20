package com.example.app_gym.views;

import android.os.Bundle;

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
        ejercicioAdapter = new EjercicioAdapter(listaEjercicios, ejercicioController);
        recyclerEjercicios.setAdapter(ejercicioAdapter);
    }
}
