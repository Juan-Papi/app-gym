package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Categoria;
import com.example.app_gym.models.Ejercicio;
import com.example.app_gym.models.Video;
import com.example.app_gym.negocio.CategoriaNegocio;
import com.example.app_gym.negocio.EjercicioNegocio;
import com.example.app_gym.negocio.VideoNegocio;


import java.util.List;

public class EjercicioController {
    private EjercicioNegocio ejercicioNegocio;
    private CategoriaNegocio categoriaNegocio;
    private VideoNegocio videoNegocio;

    public EjercicioController(SQLiteDatabase db) {
        ejercicioNegocio = new EjercicioNegocio(db);
        this.categoriaNegocio = new CategoriaNegocio(db);
        this.videoNegocio = new VideoNegocio(db);
    }

    // Método para crear un nuevo ejercicio
    public long crearNuevoEjercicio(Ejercicio ejercicio) {
        return ejercicioNegocio.agregarEjercicio(ejercicio);
    }

    // Método para actualizar un ejercicio existente
    public int actualizarEjercicio(Ejercicio ejercicio) {
        return ejercicioNegocio.actualizarEjercicio(ejercicio);
    }

    // Método para eliminar un ejercicio
    public int eliminarEjercicio(int ejercicioId) {
        return ejercicioNegocio.eliminarEjercicio(ejercicioId);
    }

    // Método para obtener un ejercicio por su ID
    public Ejercicio obtenerEjercicio(int ejercicioId) {
        return ejercicioNegocio.obtenerEjercicio(ejercicioId);
    }

    // Obtener todos los ejercicios con las relaciones
    public List<Ejercicio> obtenerTodosLosEjerciciosConRelaciones() {
        return ejercicioNegocio.obtenerTodosLosEjerciciosConRelaciones();
    }

    // Método para encontrar la categoría por su ID
    public Categoria obtenerCategoriaPorId(int categoriaId) {
        return categoriaNegocio.obtenerCategoriaPorId(categoriaId);
    }

    // Método para encontrar el video por su ID
    public Video obtenerVideoPorId(int videoId) {
        return videoNegocio.obtenerVideoPorId(videoId);
    }

}
