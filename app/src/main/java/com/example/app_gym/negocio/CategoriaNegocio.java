package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.datos.CategoriaDatos;
import com.example.app_gym.models.Categoria;

public class CategoriaNegocio {
    private CategoriaDatos categoriaDatos;

    public CategoriaNegocio(SQLiteDatabase db) {
        this.categoriaDatos = new CategoriaDatos(db);
    }

    // Método para agregar una nueva categoría
    public long agregarCategoria(Categoria categoria) {
        return categoriaDatos.insertarCategoria(categoria);
    }

    // Método para actualizar una categoría existente
    public int actualizarCategoria(Categoria categoria) {
        return categoriaDatos.actualizarCategoria(categoria);
    }

    // Método para eliminar una categoría
    public int eliminarCategoria(int categoriaId) {
        return categoriaDatos.eliminarCategoria(categoriaId);
    }

    // Método para obtener una categoría por su ID
    public Categoria obtenerCategoria(int categoriaId) {
        return categoriaDatos.obtenerCategoria(categoriaId);
    }
}
