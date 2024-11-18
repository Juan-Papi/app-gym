package com.example.app_gym.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import com.example.app_gym.R;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Categoria;
import com.example.app_gym.models.Ejercicio;
import com.example.app_gym.models.Video;
import com.example.app_gym.negocio.CategoriaNegocio;
import com.example.app_gym.negocio.EjercicioNegocio;
import com.example.app_gym.negocio.VideoNegocio;

import java.util.List;

public class ActualizarEjercicioActivity extends AppCompatActivity {
    private EjercicioNegocio ejercicioNegocio;
    private CategoriaNegocio categoriaNegocio;
    private VideoNegocio videoNegocio;
    private EditText etNombreEjercicio, etDescripcionEjercicio;
    private Spinner spinnerCategoria, spinnerVideo;
    private Button btnGuardarEjercicio;
    private int ejercicioId;

    private List<Categoria> listaCategorias;
    private List<Video> listaVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_ejercicio);

        // Inicializa la base de datos y los controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ejercicioNegocio = new EjercicioNegocio(db);
        categoriaNegocio = new CategoriaNegocio(db);
        videoNegocio = new VideoNegocio(db);

        // Referencias a los componentes de la vista
        etNombreEjercicio = findViewById(R.id.etNombreEjercicio);
        etDescripcionEjercicio = findViewById(R.id.etDescripcionEjercicio);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerVideo = findViewById(R.id.spinnerVideo);
        btnGuardarEjercicio = findViewById(R.id.btnGuardarEjercicio);

        // Cargar opciones en el Spinner de Categoría
        listaCategorias = categoriaNegocio.obtenerTodasLasCategorias();
        ArrayAdapter<Categoria> adapterCategorias = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorias);

        // Cargar opciones en el Spinner de Video
        listaVideos = videoNegocio.obtenerTodosLosVideos();
        ArrayAdapter<Video> adapterVideos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaVideos);
        adapterVideos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVideo.setAdapter(adapterVideos);


        // Obtener el ID del ejercicio a actualizar
        ejercicioId = getIntent().getIntExtra("ejercicio_id", -1);

        // Cargar los datos del ejercicio
        Ejercicio ejercicio = ejercicioNegocio.obtenerEjercicio(ejercicioId);
        if (ejercicio != null) {
            cargarDatosEjercicio(ejercicio);
        }

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> finish());

        btnGuardarEjercicio.setOnClickListener(v -> actualizarEjercicio());
    }

    // Método para cargar los datos del ejercicio en los campos de la vista
    private void cargarDatosEjercicio(Ejercicio ejercicio) {
        etNombreEjercicio.setText(ejercicio.getNombre());
        etDescripcionEjercicio.setText(ejercicio.getDescripcion());

        // Seleccionar la categoría en el Spinner
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getId() == ejercicio.getCategoriaId()) {
                spinnerCategoria.setSelection(i);
                break;
            }
        }

        // Seleccionar el video en el Spinner comparando por ID
        for (int i = 0; i < listaVideos.size(); i++) {
            if (listaVideos.get(i).getId() == ejercicio.getVideoId()) {
                spinnerVideo.setSelection(i);
                break;
            }
        }
    }


    // Método para actualizar el ejercicio
    private void actualizarEjercicio() {
        String nombre = etNombreEjercicio.getText().toString().trim();
        String descripcion = etDescripcionEjercicio.getText().toString().trim();
        Categoria categoriaSeleccionada = (Categoria) spinnerCategoria.getSelectedItem();
        Video videoSeleccionado = (Video) spinnerVideo.getSelectedItem();

        // Validación de los campos
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el nombre del ejercicio.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese la descripción del ejercicio.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (categoriaSeleccionada == null) {
            Toast.makeText(this, "Por favor, seleccione una categoría.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (videoSeleccionado == null) {
            Toast.makeText(this, "Por favor, seleccione un video.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Imprimir información de depuración
        Toast.makeText(this, "Video seleccionado: " + videoSeleccionado.getId() + " - " + videoSeleccionado.getVideoUrl(), Toast.LENGTH_SHORT).show();

        // Obtener los IDs de la categoría y el video seleccionados
        int categoriaId = categoriaSeleccionada.getId();
        int videoId = videoSeleccionado.getId();

        // Actualizar el ejercicio
        Ejercicio ejercicioActualizado = new Ejercicio();
        ejercicioActualizado.setId(ejercicioId);
        ejercicioActualizado.setNombre(nombre);
        ejercicioActualizado.setDescripcion(descripcion);
        ejercicioActualizado.setCategoriaId(categoriaId);
        ejercicioActualizado.setVideoId(videoId);

        int result = ejercicioNegocio.actualizarEjercicio(ejercicioActualizado);
        if (result > 0) {
            Toast.makeText(this, "Ejercicio actualizado correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);  // Indica que se guardó correctamente
            finish();  // Finaliza la actividad y vuelve a la anterior
        } else {
            Toast.makeText(this, "Error al actualizar el ejercicio", Toast.LENGTH_SHORT).show();
        }
    }


}
