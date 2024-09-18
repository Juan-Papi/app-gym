package com.example.app_gym.views;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_gym.R;
import com.example.app_gym.controllers.CategoriaController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    private CategoriaController categoriaController;
    private EditText etNombreCategoria;
    private Button btnGuardarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Inicializa la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        categoriaController = new CategoriaController(db);

        // Referencias a los componentes de la vista
        etNombreCategoria = findViewById(R.id.etNombreCategoria);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);

        // Configura el botón Guardar
        btnGuardarCategoria.setOnClickListener(v -> {
            // Captura el valor del formulario
            String nombreCategoria = etNombreCategoria.getText().toString();

            if (!nombreCategoria.isEmpty()) {
                // Crea una nueva categoría
                Categoria nuevaCategoria = new Categoria();
                nuevaCategoria.setNombre(nombreCategoria);

                // Guarda la categoría en la base de datos
                categoriaController.crearNuevaCategoria(nuevaCategoria);
                Toast.makeText(CategoriaActivity.this, "Categoría guardada", Toast.LENGTH_SHORT).show();

                // Limpiar el formulario después de guardar
                etNombreCategoria.setText("");
            } else {
                Toast.makeText(CategoriaActivity.this, "Por favor, ingresa un nombre para la categoría", Toast.LENGTH_SHORT).show();
            }
        });
    }
}