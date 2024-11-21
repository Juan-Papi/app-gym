package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.repositories.RutinaSemanalRepository;
import com.example.app_gym.entities.RutinaSemanal;

import java.util.List;

public class RutinaSemanalNegocio {
    private RutinaSemanalRepository rutinaSemanalRepository;

    public RutinaSemanalNegocio(SQLiteDatabase db) {
        this.rutinaSemanalRepository = new RutinaSemanalRepository(db);
    }

    // Método para agregar una nueva rutina semanal
    public long agregarRutinaSemanal(RutinaSemanal rutinaSemanal) {
        return rutinaSemanalRepository.insertarRutinaSemanal(rutinaSemanal);
    }

    // Método para actualizar una rutina semanal existente
    public int actualizarRutinaSemanal(RutinaSemanal rutinaSemanal) {
        return rutinaSemanalRepository.actualizarRutinaSemanal(rutinaSemanal);
    }

    // Método para eliminar una rutina semanal
    public int eliminarRutinaSemanal(int rutinaSemanalId) {
        return rutinaSemanalRepository.eliminarRutinaSemanal(rutinaSemanalId);
    }

    // Método para obtener una rutina semanal por su ID
    public RutinaSemanal obtenerRutinaSemanal(int rutinaSemanalId) {
        return rutinaSemanalRepository.obtenerRutinaSemanal(rutinaSemanalId);
    }

    public List<RutinaSemanal> obtenerRutinasSemanalesDeCliente(int clienteId) {
        return rutinaSemanalRepository.obtenerRutinasSemanalesDeCliente(clienteId);
    }
}
