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

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            // Cerrar esta actividad
            finish();
        });

        btnGuardarEjercicio.setOnClickListener(v -> {
            // Capturar los valores del formulario
            String nombre = etNombreEjercicio.getText().toString().trim();
            String descripcion = etDescripcionEjercicio.getText().toString().trim();
            Categoria categoriaSeleccionada = (Categoria) spinnerCategoria.getSelectedItem();
            Video videoSeleccionado = (Video) spinnerVideo.getSelectedItem();

            // Validar que el nombre no esté vacío
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese el nombre del ejercicio.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar que la descripción no esté vacía
            if (descripcion.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese la descripción del ejercicio.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar que se haya seleccionado una categoría
            if (categoriaSeleccionada == null) {
                Toast.makeText(this, "Por favor, seleccione una categoría.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar que se haya seleccionado un video
            if (videoSeleccionado == null) {
                Toast.makeText(this, "Por favor, seleccione un video.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener los IDs de la categoría y el video seleccionados
            int categoriaId = categoriaSeleccionada.getId();
            int videoId = videoSeleccionado.getId();

            // Crear un nuevo ejercicio
            Ejercicio nuevoEjercicio = new Ejercicio();
            nuevoEjercicio.setNombre(nombre);
            nuevoEjercicio.setDescripcion(descripcion);
            nuevoEjercicio.setCategoriaId(categoriaId);
            nuevoEjercicio.setVideoId(videoId);

            // Guardar el ejercicio en la base de datos
            long result = ejercicioController.crearNuevoEjercicio(nuevoEjercicio);
            if (result != -1) {
                Toast.makeText(this, "Ejercicio guardado", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);  // Indica que se guardó correctamente
                finish();  // Finaliza la actividad y vuelve a la anterior
            } else {
                Toast.makeText(this, "Error al guardar el ejercicio", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
