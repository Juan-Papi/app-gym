package com.example.app_gym.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.R;
import com.example.app_gym.controllers.VideoController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Video;

public class VideoActivity extends AppCompatActivity {

    private VideoController videoController;
    private EditText etDescripcionVideo, etUrlVideo;
    private Button btnGuardarVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Inicializa la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        videoController = new VideoController(db);

        // Referencias a los componentes de la vista
        etDescripcionVideo = findViewById(R.id.etDescripcionVideo);
        etUrlVideo = findViewById(R.id.etUrlVideo);
        btnGuardarVideo = findViewById(R.id.btnGuardarVideo);

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            // Cerrar esta actividad
            finish();
        });

        btnGuardarVideo.setOnClickListener(v -> {
            String descripcion = etDescripcionVideo.getText().toString();
            String url = etUrlVideo.getText().toString();

            if (!descripcion.isEmpty() && !url.isEmpty()) {
                Video nuevoVideo = new Video();
                nuevoVideo.setDescripcion(descripcion);
                nuevoVideo.setVideoUrl(url);

                // Guarda el video en la base de datos
                long resultado = videoController.crearNuevoVideo(nuevoVideo);

                if (resultado != -1) {
                    Toast.makeText(VideoActivity.this, "Video guardado", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // Indica que el video fue guardado exitosamente
                    finish(); // Cierra esta actividad y regresa
                } else {
                    Toast.makeText(VideoActivity.this, "Error al guardar el video", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(VideoActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
