package com.example.app_gym.views;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.controllers.CategoriaController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Categoria;

import java.util.List;

public class IndexCategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerCategorias;
    private CategoriaAdapter categoriaAdapter;
    private List<Categoria> listaCategorias;
    private CategoriaController categoriaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_categoria);

        // Inicializamos la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        categoriaController = new CategoriaController(dbHelper.getWritableDatabase());

        // Referenciamos el RecyclerView
        recyclerCategorias = findViewById(R.id.recyclerCategorias);
        recyclerCategorias.setLayoutManager(new LinearLayoutManager(this));

        // Obtenemos la lista de categorías desde el controlador
        listaCategorias = categoriaController.obtenerTodasLasCategorias();

        // Configuramos el adapter
        categoriaAdapter = new CategoriaAdapter(listaCategorias);
        recyclerCategorias.setAdapter(categoriaAdapter);
    }
}
