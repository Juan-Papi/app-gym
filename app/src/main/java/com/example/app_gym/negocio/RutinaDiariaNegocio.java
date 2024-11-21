package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.repositories.RutinaDiariaMembresia;
import com.example.app_gym.entities.RutinaDiaria;

import java.util.List;
import java.util.Map;

public class RutinaDiariaNegocio {
    private RutinaDiariaMembresia rutinaDiariaRepository;

    public RutinaDiariaNegocio(SQLiteDatabase db) {
        this.rutinaDiariaRepository = new RutinaDiariaMembresia(db);
    }

    // Método para agregar una nueva rutina diaria
    public long agregarRutinaDiaria(RutinaDiaria rutinaDiaria) {
        return rutinaDiariaRepository.insertarRutinaDiaria(rutinaDiaria);
    }

    // Método para actualizar una rutina diaria existente
    public int actualizarRutinaDiaria(RutinaDiaria rutinaDiaria) {
        return rutinaDiariaRepository.actualizarRutinaDiaria(rutinaDiaria);
    }

    // Método para eliminar una rutina diaria
    public int eliminarRutinaDiaria(int rutinaDiariaId) {
        return rutinaDiariaRepository.eliminarRutinaDiaria(rutinaDiariaId);
    }

    // Método para obtener una rutina diaria por su ID
    public RutinaDiaria obtenerRutinaDiaria(int rutinaDiariaId) {
        return rutinaDiariaRepository.obtenerRutinaDiaria(rutinaDiariaId);
    }

    public List<RutinaDiaria> obtenerRutinasDiariasDeSemana(int semanaId) {
        return rutinaDiariaRepository.obtenerRutinasDiariasDeSemana(semanaId);
    }

    // Nuevo método para obtener rutinas diarias con sus detalles
    public List<Map<String, Object>> obtenerRutinasDiariasConRelaciones(int semanaId) {
        return rutinaDiariaRepository.obtenerRutinasDiariasConRelaciones(semanaId);
    }
}
