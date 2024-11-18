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

public class ActualizarCategoriaActivity extends AppCompatActivity {

    private CategoriaNegocio categoriaNegocio;
    private EditText etNombreCategoria;
    private Button btnGuardarCategoria, btnVolver;
    private int categoriaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_categoria);

        // Inicializa la base de datos y el controlador
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        categoriaNegocio = new CategoriaNegocio(db);

        // Referenciar los componentes de la vista
        etNombreCategoria = findViewById(R.id.etNombreCategoria);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);
        btnVolver = findViewById(R.id.btnVolver);

        // Obtener el ID de la categoría a actualizar
        categoriaId = getIntent().getIntExtra("categoria_id", -1);

        // Cargar los datos de la categoría
        Categoria categoria = categoriaNegocio.obtenerCategoria(categoriaId);
        if (categoria != null) {
            etNombreCategoria.setText(categoria.getNombre());
        }

        // Configurar el botón Guardar
        btnGuardarCategoria.setOnClickListener(v -> actualizarCategoria());

        // Configurar el botón Volver
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para actualizar la categoría
    private void actualizarCategoria() {
        String nombreCategoria = etNombreCategoria.getText().toString().trim();

        if (nombreCategoria.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un nombre para la categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNombre(nombreCategoria);

        int resultado = categoriaNegocio.actualizarCategoria(categoria);
        if (resultado > 0) {
            Toast.makeText(this, "Categoría actualizada correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);  // Indicar que la operación fue exitosa
            finish();  // Cierra la actividad y regresa a la anterior
        } else {
            Toast.makeText(this, "Error al actualizar la categoría", Toast.LENGTH_SHORT).show();
        }
    }
}
