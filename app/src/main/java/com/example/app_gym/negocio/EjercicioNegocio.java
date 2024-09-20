package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.datos.EjercicioDatos;
import com.example.app_gym.models.Ejercicio;

import java.util.List;

public class EjercicioNegocio {
    private EjercicioDatos ejercicioDatos;

    public EjercicioNegocio(SQLiteDatabase db) {
        this.ejercicioDatos = new EjercicioDatos(db);
    }

    // Método para agregar un nuevo ejercicio
    public long agregarEjercicio(Ejercicio ejercicio) {
        return ejercicioDatos.insertarEjercicio(ejercicio);
    }

    // Método para actualizar un ejercicio existente
    public int actualizarEjercicio(Ejercicio ejercicio) {
        return ejercicioDatos.actualizarEjercicio(ejercicio);
    }

    // Método para eliminar un ejercicio
    public int eliminarEjercicio(int ejercicioId) {
        return ejercicioDatos.eliminarEjercicio(ejercicioId);
    }

    // Método para obtener un ejercicio por su ID
    public Ejercicio obtenerEjercicio(int ejercicioId) {
        return ejercicioDatos.obtenerEjercicio(ejercicioId);
    }

    // Obtener ejercicios con sus relaciones
    public List<Ejercicio> obtenerTodosLosEjerciciosConRelaciones() {
        return ejercicioDatos.obtenerTodosLosEjerciciosConRelaciones();
    }
}
