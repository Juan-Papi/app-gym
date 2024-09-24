package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.RutinaDiaria;
import com.example.app_gym.negocio.RutinaDiariaNegocio;

import java.util.List;
import java.util.Map;

public class RutinaDiariaController {
    private RutinaDiariaNegocio rutinaDiariaNegocio;

    public RutinaDiariaController(SQLiteDatabase db) {
        rutinaDiariaNegocio = new RutinaDiariaNegocio(db);
    }

    // Método para crear una nueva rutina diaria
    public long crearNuevaRutinaDiaria(RutinaDiaria rutinaDiaria) {
        return rutinaDiariaNegocio.agregarRutinaDiaria(rutinaDiaria);
    }

    // Método para actualizar una rutina diaria existente
    public int actualizarRutinaDiaria(RutinaDiaria rutinaDiaria) {
        return rutinaDiariaNegocio.actualizarRutinaDiaria(rutinaDiaria);
    }

    // Método para eliminar una rutina diaria
    public int eliminarRutinaDiaria(int rutinaDiariaId) {
        return rutinaDiariaNegocio.eliminarRutinaDiaria(rutinaDiariaId);
    }

    // Método para obtener una rutina diaria por su ID
    public RutinaDiaria obtenerRutinaDiaria(int rutinaDiariaId) {
        return rutinaDiariaNegocio.obtenerRutinaDiaria(rutinaDiariaId);
    }

    public List<RutinaDiaria> obtenerRutinasDiariasDeSemana(int semanaId) {
        return rutinaDiariaNegocio.obtenerRutinasDiariasDeSemana(semanaId);
    }

    // Nuevo método para obtener rutinas diarias con sus detalles
    public List<Map<String, Object>> obtenerRutinasDiariasConRelaciones(int semanaId) {
        return rutinaDiariaNegocio.obtenerRutinasDiariasConRelaciones(semanaId);
    }
}
