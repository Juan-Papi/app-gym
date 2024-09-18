package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Categoria;
import com.example.app_gym.negocio.CategoriaNegocio;

public class CategoriaController {
    private CategoriaNegocio categoriaNegocio;

    public CategoriaController(SQLiteDatabase db) {
        categoriaNegocio = new CategoriaNegocio(db);
    }

    // Método para crear una nueva categoría
    public long crearNuevaCategoria(Categoria categoria) {
        return categoriaNegocio.agregarCategoria(categoria);
    }

    // Método para actualizar una categoría existente
    public int actualizarCategoria(Categoria categoria) {
        return categoriaNegocio.actualizarCategoria(categoria);
    }

    // Método para eliminar una categoría
    public int eliminarCategoria(int categoriaId) {
        return categoriaNegocio.eliminarCategoria(categoriaId);
    }

    // Método para obtener una categoría por su ID
    public Categoria obtenerCategoria(int categoriaId) {
        return categoriaNegocio.obtenerCategoria(categoriaId);
    }
}
