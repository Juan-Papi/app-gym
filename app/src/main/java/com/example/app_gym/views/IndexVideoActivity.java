package com.example.app_gym.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.controllers.VideoController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Video;
import com.example.app_gym.views.VideoAdapter;

import java.util.List;

public class IndexVideoActivity extends AppCompatActivity {

    private RecyclerView recyclerVideos;
    private VideoAdapter videoAdapter;
    private List<Video> listaVideos;
    private VideoController videoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_video);

        // Inicializamos la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        videoController = new VideoController(dbHelper.getWritableDatabase());

        // Referenciamos el RecyclerView
        recyclerVideos = findViewById(R.id.recyclerVideos);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));

        // Obtenemos la lista de videos desde el controlador
        listaVideos = videoController.obtenerTodosLosVideos();

        // Configuramos el adapter
        videoAdapter = new VideoAdapter(listaVideos);
        recyclerVideos.setAdapter(videoAdapter);
    }
}
