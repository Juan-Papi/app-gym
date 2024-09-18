package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Ejercicio;
import com.example.app_gym.negocio.EjercicioNegocio;

public class EjercicioController {
    private EjercicioNegocio ejercicioNegocio;

    public EjercicioController(SQLiteDatabase db) {
        ejercicioNegocio = new EjercicioNegocio(db);
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
}
