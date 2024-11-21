package com.example.app_gym.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.R;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Video;
import com.example.app_gym.negocio.VideoNegocio;

public class ActualizarVideoActivity extends AppCompatActivity {

    private VideoNegocio videoNegocio;
    private EditText etDescripcionVideo, etUrlVideo;
    private Button btnGuardarVideo, btnVolver;
    private int videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_video);

        // Inicializar la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        videoNegocio = new VideoNegocio(db);

        // Referencias a los componentes de la vista
        etDescripcionVideo = findViewById(R.id.etDescripcionVideo);
        etUrlVideo = findViewById(R.id.etUrlVideo);
        btnGuardarVideo = findViewById(R.id.btnGuardarVideo);
        btnVolver = findViewById(R.id.btnVolver);

        // Obtener el ID del video a actualizar desde el Intent
        videoId = getIntent().getIntExtra("video_id", -1);

        // Cargar los datos del video
        Video video = videoNegocio.obtenerVideo(videoId);
        if (video != null) {
            cargarDatosVideo(video);
        }

        // Configuración del botón "Guardar"
        btnGuardarVideo.setOnClickListener(v -> actualizarVideo());

        // Configuración del botón "Volver"
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para cargar los datos del video en los campos de la vista
    private void cargarDatosVideo(Video video) {
        etDescripcionVideo.setText(video.getDescripcion());
        etUrlVideo.setText(video.getVideoUrl());
    }

    // Método para actualizar el video
    private void actualizarVideo() {
        String descripcion = etDescripcionVideo.getText().toString().trim();
        String url = etUrlVideo.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (descripcion.isEmpty() || url.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar los datos del video
        Video video = new Video();
        video.setId(videoId);
        video.setDescripcion(descripcion);
        video.setVideoUrl(url);

        int resultado = videoNegocio.actualizarVideo(video);
        if (resultado > 0) {
            Toast.makeText(this, "Video actualizado correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Indica que la operación fue exitosa
            finish(); // Cierra la actividad y regresa a la anterior
        } else {
            Toast.makeText(this, "Error al actualizar el video", Toast.LENGTH_SHORT).show();
        }
    }
}
