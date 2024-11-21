package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.repositories.DetalleEjercicioRepository;
import com.example.app_gym.entities.DetalleEjercicio;

import java.util.List;
import java.util.Map;

public class DetalleEjercicioNegocio {
    private DetalleEjercicioRepository detalleEjercicioRepository;

    public DetalleEjercicioNegocio(SQLiteDatabase db) {
        this.detalleEjercicioRepository = new DetalleEjercicioRepository(db);
    }

    // Método para agregar un nuevo detalle de ejercicio
    public long agregarDetalleEjercicio(DetalleEjercicio detalleEjercicio) {
        return detalleEjercicioRepository.insertarDetalleEjercicio(detalleEjercicio);
    }

    // Método para actualizar un detalle de ejercicio existente
    public int actualizarDetalleEjercicio(DetalleEjercicio detalleEjercicio) {
        return detalleEjercicioRepository.actualizarDetalleEjercicio(detalleEjercicio);
    }

    // Método para eliminar un detalle de ejercicio
    public int eliminarDetalleEjercicio(int detalleEjercicioId) {
        return detalleEjercicioRepository.eliminarDetalleEjercicio(detalleEjercicioId);
    }

    // Método para obtener un detalle de ejercicio por su ID
    public DetalleEjercicio obtenerDetalleEjercicio(int detalleEjercicioId) {
        return detalleEjercicioRepository.obtenerDetalleEjercicio(detalleEjercicioId);
    }

    // Método para obtener los detalles de ejercicio
    public List<DetalleEjercicio> obtenerDetallesDeEjercicio(int rutinaDiariaId) {
        return detalleEjercicioRepository.obtenerDetallesDeEjercicio(rutinaDiariaId);
    }

    public List<Map<String, Object>> obtenerDetallesConRelaciones(int rutinaDiariaId) {
        return detalleEjercicioRepository.obtenerDetallesConRelaciones(rutinaDiariaId);
    }

}
