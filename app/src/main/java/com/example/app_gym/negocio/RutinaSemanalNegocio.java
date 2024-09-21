package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.datos.RutinaSemanalDatos;
import com.example.app_gym.models.RutinaSemanal;

import java.util.List;

public class RutinaSemanalNegocio {
    private RutinaSemanalDatos rutinaSemanalDatos;

    public RutinaSemanalNegocio(SQLiteDatabase db) {
        this.rutinaSemanalDatos = new RutinaSemanalDatos(db);
    }

    // Método para agregar una nueva rutina semanal
    public long agregarRutinaSemanal(RutinaSemanal rutinaSemanal) {
        return rutinaSemanalDatos.insertarRutinaSemanal(rutinaSemanal);
    }

    // Método para actualizar una rutina semanal existente
    public int actualizarRutinaSemanal(RutinaSemanal rutinaSemanal) {
        return rutinaSemanalDatos.actualizarRutinaSemanal(rutinaSemanal);
    }

    // Método para eliminar una rutina semanal
    public int eliminarRutinaSemanal(int rutinaSemanalId) {
        return rutinaSemanalDatos.eliminarRutinaSemanal(rutinaSemanalId);
    }

    // Método para obtener una rutina semanal por su ID
    public RutinaSemanal obtenerRutinaSemanal(int rutinaSemanalId) {
        return rutinaSemanalDatos.obtenerRutinaSemanal(rutinaSemanalId);
    }

    public List<RutinaSemanal> obtenerRutinasSemanalesDeCliente(int clienteId) {
        return rutinaSemanalDatos.obtenerRutinasSemanalesDeCliente(clienteId);
    }
}
