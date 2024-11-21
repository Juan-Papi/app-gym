package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.repositories.EjercicioRepository;
import com.example.app_gym.entities.Ejercicio;

import java.util.List;

public class EjercicioNegocio {
    private EjercicioRepository ejercicioRepository;

    public EjercicioNegocio(SQLiteDatabase db) {
        this.ejercicioRepository = new EjercicioRepository(db);
    }

    // Método para agregar un nuevo ejercicio
    public long agregarEjercicio(Ejercicio ejercicio) {
        return ejercicioRepository.insertarEjercicio(ejercicio);
    }

    // Método para actualizar un ejercicio existente
    public int actualizarEjercicio(Ejercicio ejercicio) {
        return ejercicioRepository.actualizarEjercicio(ejercicio);
    }

    // Método para eliminar un ejercicio
    public int eliminarEjercicio(int ejercicioId) {
        return ejercicioRepository.eliminarEjercicio(ejercicioId);
    }

    // Método para obtener un ejercicio por su ID
    public Ejercicio obtenerEjercicio(int ejercicioId) {
        return ejercicioRepository.obtenerEjercicio(ejercicioId);
    }

    // Obtener ejercicios con sus relaciones
    public List<Ejercicio> obtenerTodosLosEjerciciosConRelaciones() {
        return ejercicioRepository.obtenerTodosLosEjerciciosConRelaciones();
    }
}
