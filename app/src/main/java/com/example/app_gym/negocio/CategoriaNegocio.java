package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.repositories.CategoriaRepository;
import com.example.app_gym.entities.Categoria;

import java.util.List;

public class CategoriaNegocio {
    private CategoriaRepository categoriaRepository;

    public CategoriaNegocio(SQLiteDatabase db) {
        this.categoriaRepository = new CategoriaRepository(db);
    }

    // Método para agregar una nueva categoría
    public long agregarCategoria(Categoria categoria) {
        return categoriaRepository.insertarCategoria(categoria);
    }

    // Método para actualizar una categoría existente
    public int actualizarCategoria(Categoria categoria) {
        return categoriaRepository.actualizarCategoria(categoria);
    }

    // Método para eliminar una categoría
    public int eliminarCategoria(int categoriaId) {
        return categoriaRepository.eliminarCategoria(categoriaId);
    }

    // Método para obtener una categoría por su ID
    public Categoria obtenerCategoria(int categoriaId) {
        return categoriaRepository.obtenerCategoria(categoriaId);
    }

    // Llama a la capa de datos para obtener todas las categorías
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.obtenerTodasLasCategorias();
    }

    // Método para obtener la categoría por su ID
    public Categoria obtenerCategoriaPorId(int categoriaId) {
        return categoriaRepository.encontrarPorId(categoriaId);
    }
}
