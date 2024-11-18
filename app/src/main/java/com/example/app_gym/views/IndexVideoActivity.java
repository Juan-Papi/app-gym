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
import com.example.app_gym.models.Video;
import com.example.app_gym.negocio.VideoNegocio;

import java.util.List;

public class IndexVideoActivity extends AppCompatActivity {

    private RecyclerView recyclerVideos;
    private VideoAdapter videoAdapter;
    private List<Video> listaVideos;
    private VideoNegocio videoNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_video);

        // Inicializamos la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        videoNegocio = new VideoNegocio(dbHelper.getWritableDatabase());

        // Referenciamos el RecyclerView
        recyclerVideos = findViewById(R.id.recyclerVideos);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));

        // Obtenemos la lista de videos desde el controlador
        listaVideos = videoNegocio.obtenerTodosLosVideos();

        // Configuramos el adapter
        videoAdapter = new VideoAdapter(listaVideos);
        recyclerVideos.setAdapter(videoAdapter);

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            // Cerrar esta actividad
            finish();
        });

        Button btnNuevoVideo = findViewById(R.id.btnNuevoVideo);
        btnNuevoVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar CategoriaActivity
                Intent intent = new Intent(IndexVideoActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de categorías
        actualizarListaVideos();
    }

    private void actualizarListaVideos() {
        List<Video> videosActualizados = videoNegocio.obtenerTodosLosVideos();
        // Asumiendo que ya tienes un método en tu adaptador para actualizar la lista
        ((VideoAdapter)recyclerVideos.getAdapter()).actualizarVideos(videosActualizados);
    }
}
