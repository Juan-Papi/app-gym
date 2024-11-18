package com.example.app_gym.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Categoria;
import com.example.app_gym.negocio.CategoriaNegocio;

import java.util.List;

public class IndexCategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerCategorias;
    private CategoriaAdapter categoriaAdapter;
    private List<Categoria> listaCategorias;
    private CategoriaNegocio categoriaNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_categoria);

        // Inicializamos la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        categoriaNegocio = new CategoriaNegocio(dbHelper.getWritableDatabase());

        // Referenciamos el RecyclerView
        recyclerCategorias = findViewById(R.id.recyclerCategorias);
        recyclerCategorias.setLayoutManager(new LinearLayoutManager(this));

        // Obtenemos la lista de categorías desde el controlador
        listaCategorias = categoriaNegocio.obtenerTodasLasCategorias();

        // Configuramos el adapter
        categoriaAdapter = new CategoriaAdapter(listaCategorias);
        recyclerCategorias.setAdapter(categoriaAdapter);

        Button btnVolver = findViewById(R.id.btnVolverToE);
        btnVolver.setOnClickListener(v -> {
            // Cerrar esta actividad
            finish();
        });

        Button btnNuevaCategoria = findViewById(R.id.btnNuevaCategoria);
        btnNuevaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar CategoriaActivity
                Intent intent = new Intent(IndexCategoriaActivity.this, CategoriaActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de categorías
        actualizarListaCategorias();
    }

    private void actualizarListaCategorias() {
        List<Categoria> categoriasActualizadas = categoriaNegocio.obtenerTodasLasCategorias();
        // Asumiendo que ya tienes un método en tu adaptador para actualizar la lista
        ((CategoriaAdapter)recyclerCategorias.getAdapter()).actualizarCategorias(categoriasActualizadas);
    }

}
