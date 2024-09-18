package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Membresia;
import com.example.app_gym.negocio.MembresiaNegocio;

public class MembresiaController {
    private MembresiaNegocio membresiaNegocio;

    public MembresiaController(SQLiteDatabase db) {
        membresiaNegocio = new MembresiaNegocio(db);
    }

    // Método para crear una nueva membresía
    public long crearNuevaMembresia(Membresia membresia) {
        return membresiaNegocio.agregarMembresia(membresia);
    }

    // Método para actualizar una membresía existente
    public int actualizarMembresia(Membresia membresia) {
        return membresiaNegocio.actualizarMembresia(membresia);
    }

    // Método para eliminar una membresía
    public int eliminarMembresia(int membresiaId) {
        return membresiaNegocio.eliminarMembresia(membresiaId);
    }

    // Método para obtener una membresía por su ID
    public Membresia obtenerMembresia(int membresiaId) {
        return membresiaNegocio.obtenerMembresia(membresiaId);
    }
}
