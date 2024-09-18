package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.DetalleEjercicio;
import com.example.app_gym.negocio.DetalleEjercicioNegocio;

public class DetalleEjercicioController {
    private DetalleEjercicioNegocio detalleEjercicioNegocio;

    public DetalleEjercicioController(SQLiteDatabase db) {
        detalleEjercicioNegocio = new DetalleEjercicioNegocio(db);
    }

    // Método para crear un nuevo detalle de ejercicio
    public long crearNuevoDetalleEjercicio(DetalleEjercicio detalleEjercicio) {
        return detalleEjercicioNegocio.agregarDetalleEjercicio(detalleEjercicio);
    }

    // Método para actualizar un detalle de ejercicio existente
    public int actualizarDetalleEjercicio(DetalleEjercicio detalleEjercicio) {
        return detalleEjercicioNegocio.actualizarDetalleEjercicio(detalleEjercicio);
    }

    // Método para eliminar un detalle de ejercicio
    public int eliminarDetalleEjercicio(int detalleEjercicioId) {
        return detalleEjercicioNegocio.eliminarDetalleEjercicio(detalleEjercicioId);
    }

    // Método para obtener un detalle de ejercicio por su ID
    public DetalleEjercicio obtenerDetalleEjercicio(int detalleEjercicioId) {
        return detalleEjercicioNegocio.obtenerDetalleEjercicio(detalleEjercicioId);
    }
}
