package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.RutinaSemanal;
import com.example.app_gym.negocio.RutinaSemanalNegocio;

import java.util.List;

public class RutinaSemanalController {
    private RutinaSemanalNegocio rutinaSemanalNegocio;

    public RutinaSemanalController(SQLiteDatabase db) {
        rutinaSemanalNegocio = new RutinaSemanalNegocio(db);
    }

    // Método para crear una nueva rutina semanal
    public long crearNuevaRutinaSemanal(RutinaSemanal rutinaSemanal) {
        return rutinaSemanalNegocio.agregarRutinaSemanal(rutinaSemanal);
    }

    // Método para actualizar una rutina semanal existente
    public int actualizarRutinaSemanal(RutinaSemanal rutinaSemanal) {
        return rutinaSemanalNegocio.actualizarRutinaSemanal(rutinaSemanal);
    }

    // Método para eliminar una rutina semanal
    public int eliminarRutinaSemanal(int rutinaSemanalId) {
        return rutinaSemanalNegocio.eliminarRutinaSemanal(rutinaSemanalId);
    }

    // Método para obtener una rutina semanal por su ID
    public RutinaSemanal obtenerRutinaSemanal(int rutinaSemanalId) {
        return rutinaSemanalNegocio.obtenerRutinaSemanal(rutinaSemanalId);
    }

    public List<RutinaSemanal> obtenerRutinasSemanalesDeCliente(int clienteId) {
        return rutinaSemanalNegocio.obtenerRutinasSemanalesDeCliente(clienteId);
    }
}
