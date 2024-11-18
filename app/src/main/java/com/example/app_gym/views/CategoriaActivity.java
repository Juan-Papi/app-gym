package com.example.app_gym.views;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_gym.R;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Categoria;
import com.example.app_gym.negocio.CategoriaNegocio;

public class CategoriaActivity extends AppCompatActivity {

    private CategoriaNegocio categoriaNegocio;
    private EditText etNombreCategoria;
    private Button btnGuardarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Inicializa la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        categoriaNegocio = new CategoriaNegocio(db);

        // Referencias a los componentes de la vista
        etNombreCategoria = findViewById(R.id.etNombreCategoria);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);

        Button btnVolver = findViewById(R.id.btnVolverToIndexc);
        btnVolver.setOnClickListener(v -> {
            // Cerrar esta actividad
            finish();
        });

        // Configura el botón Guardar
        btnGuardarCategoria.setOnClickListener(v -> {
            String nombreCategoria = etNombreCategoria.getText().toString();

            if (!nombreCategoria.isEmpty()) {
                Categoria nuevaCategoria = new Categoria();
                nuevaCategoria.setNombre(nombreCategoria);

                categoriaNegocio.agregarCategoria(nuevaCategoria);
                Toast.makeText(CategoriaActivity.this, "Categoría guardada", Toast.LENGTH_SHORT).show();

                // Finalizar la actividad y volver a IndexCategoriaActivity
                finish();
            } else {
                Toast.makeText(CategoriaActivity.this, "Por favor, ingresa un nombre para la categoría", Toast.LENGTH_SHORT).show();
            }
        });
    }
}