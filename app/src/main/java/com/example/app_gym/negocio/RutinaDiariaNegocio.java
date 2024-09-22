package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.datos.RutinaDiariaDatos;
import com.example.app_gym.models.RutinaDiaria;

import java.util.List;

public class RutinaDiariaNegocio {
    private RutinaDiariaDatos rutinaDiariaDatos;

    public RutinaDiariaNegocio(SQLiteDatabase db) {
        this.rutinaDiariaDatos = new RutinaDiariaDatos(db);
    }

    // Método para agregar una nueva rutina diaria
    public long agregarRutinaDiaria(RutinaDiaria rutinaDiaria) {
        return rutinaDiariaDatos.insertarRutinaDiaria(rutinaDiaria);
    }

    // Método para actualizar una rutina diaria existente
    public int actualizarRutinaDiaria(RutinaDiaria rutinaDiaria) {
        return rutinaDiariaDatos.actualizarRutinaDiaria(rutinaDiaria);
    }

    // Método para eliminar una rutina diaria
    public int eliminarRutinaDiaria(int rutinaDiariaId) {
        return rutinaDiariaDatos.eliminarRutinaDiaria(rutinaDiariaId);
    }

    // Método para obtener una rutina diaria por su ID
    public RutinaDiaria obtenerRutinaDiaria(int rutinaDiariaId) {
        return rutinaDiariaDatos.obtenerRutinaDiaria(rutinaDiariaId);
    }

    public List<RutinaDiaria> obtenerRutinasDiariasDeSemana(int semanaId) {
        return rutinaDiariaDatos.obtenerRutinasDiariasDeSemana(semanaId);
    }
}
