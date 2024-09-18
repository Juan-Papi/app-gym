package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.datos.MembresiaDatos;
import com.example.app_gym.models.Membresia;

public class MembresiaNegocio {
    private MembresiaDatos membresiaDatos;

    public MembresiaNegocio(SQLiteDatabase db) {
        this.membresiaDatos = new MembresiaDatos(db);
    }

    // Método para agregar una nueva membresía
    public long agregarMembresia(Membresia membresia) {
        return membresiaDatos.insertarMembresia(membresia);
    }

    // Método para actualizar una membresía existente
    public int actualizarMembresia(Membresia membresia) {
        return membresiaDatos.actualizarMembresia(membresia);
    }

    // Método para eliminar una membresía
    public int eliminarMembresia(int membresiaId) {
        return membresiaDatos.eliminarMembresia(membresiaId);
    }

    // Método para obtener una membresía por su ID
    public Membresia obtenerMembresia(int membresiaId) {
        return membresiaDatos.obtenerMembresia(membresiaId);
    }
}
