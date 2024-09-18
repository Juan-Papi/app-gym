package com.example.app_gym.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.app_gym.R;
import com.example.app_gym.controllers.EjercicioController;
import com.example.app_gym.controllers.CategoriaController;
import com.example.app_gym.controllers.VideoController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Categoria;
import com.example.app_gym.models.Ejercicio;
import com.example.app_gym.models.Video;

import java.util.List;

public class EjercicioActivity extends AppCompatActivity {
    private EjercicioController ejercicioController;
    private CategoriaController categoriaController;
    private VideoController videoController;
    private EditText etNombreEjercicio, etDescripcionEjercicio;
    private Spinner spinnerCategoria, spinnerVideo;
    private Button btnGuardarEjercicio;

    private List<Categoria> listaCategorias;
    private List<Video> listaVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        // Inicializa la base de datos y los controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ejercicioController = new EjercicioController(db);
        categoriaController = new CategoriaController(db);
        videoController = new VideoController(db);

        // Referencias a los componentes de la vista
        etNombreEjercicio = findViewById(R.id.etNombreEjercicio);
        etDescripcionEjercicio = findViewById(R.id.etDescripcionEjercicio);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerVideo = findViewById(R.id.spinnerVideo);
        btnGuardarEjercicio = findViewById(R.id.btnGuardarEjercicio);

        // Cargar opciones en el Spinner de Categoría
        listaCategorias = categoriaController.obtenerTodasLasCategorias();
        ArrayAdapter<Categoria> adapterCategorias = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorias);

        // Cargar opciones en el Spinner de Video
        listaVideos = videoController.obtenerTodosLosVideos();
        ArrayAdapter<Video> adapterVideos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaVideos);
        adapterVideos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVideo.setAdapter(adapterVideos);

        // Configura el botón Guardar
        btnGuardarEjercicio.setOnClickListener(v -> {
            String nombre = etNombreEjercicio.getText().toString();
            String descripcion = etDescripcionEjercicio.getText().toString();

            // Obtener el ID de la categoría seleccionada
            Categoria categoriaSeleccionada = (Categoria) spinnerCategoria.getSelectedItem();
            int categoriaId = categoriaSeleccionada.getId();

            // Obtener el ID del video seleccionado
            Video videoSeleccionado = (Video) spinnerVideo.getSelectedItem();
            int videoId = videoSeleccionado.getId();

            Ejercicio nuevoEjercicio = new Ejercicio();
            nuevoEjercicio.setNombre(nombre);
            nuevoEjercicio.setDescripcion(descripcion);
            nuevoEjercicio.setCategoriaId(categoriaId);
            nuevoEjercicio.setVideoId(videoId);

            ejercicioController.crearNuevoEjercicio(nuevoEjercicio);
            Toast.makeText(EjercicioActivity.this, "Ejercicio guardado", Toast.LENGTH_SHORT).show();
        });
    }
}
