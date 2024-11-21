package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.repositories.MembresiaRepository;
import com.example.app_gym.entities.Membresia;

import java.util.List;

public class MembresiaNegocio {
    private MembresiaRepository membresiaRepository;

    public MembresiaNegocio(SQLiteDatabase db) {
        this.membresiaRepository = new MembresiaRepository(db);
    }

    // Método para agregar una nueva membresía
    public long agregarMembresia(Membresia membresia) {
        return membresiaRepository.insertarMembresia(membresia);
    }

    // Método para actualizar una membresía existente
    public int actualizarMembresia(Membresia membresia) {
        return membresiaRepository.actualizarMembresia(membresia);
    }

    // Método para eliminar una membresía
    public int eliminarMembresia(int membresiaId) {
        return membresiaRepository.eliminarMembresia(membresiaId);
    }

    // Método para obtener una membresía por su ID
    public Membresia obtenerMembresia(int membresiaId) {
        return membresiaRepository.obtenerMembresia(membresiaId);
    }

    // Método para obtener todas las membresías de un cliente
    public List<Membresia> obtenerMembresiasPorCliente(int clienteId) {
        return membresiaRepository.obtenerMembresiasPorCliente(clienteId);
    }
}
