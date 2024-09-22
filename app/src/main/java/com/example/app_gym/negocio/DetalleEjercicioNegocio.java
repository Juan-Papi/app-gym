package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.datos.DetalleEjercicioDatos;
import com.example.app_gym.models.DetalleEjercicio;

import java.util.List;

public class DetalleEjercicioNegocio {
    private DetalleEjercicioDatos detalleEjercicioDatos;

    public DetalleEjercicioNegocio(SQLiteDatabase db) {
        this.detalleEjercicioDatos = new DetalleEjercicioDatos(db);
    }

    // Método para agregar un nuevo detalle de ejercicio
    public long agregarDetalleEjercicio(DetalleEjercicio detalleEjercicio) {
        return detalleEjercicioDatos.insertarDetalleEjercicio(detalleEjercicio);
    }

    // Método para actualizar un detalle de ejercicio existente
    public int actualizarDetalleEjercicio(DetalleEjercicio detalleEjercicio) {
        return detalleEjercicioDatos.actualizarDetalleEjercicio(detalleEjercicio);
    }

    // Método para eliminar un detalle de ejercicio
    public int eliminarDetalleEjercicio(int detalleEjercicioId) {
        return detalleEjercicioDatos.eliminarDetalleEjercicio(detalleEjercicioId);
    }

    // Método para obtener un detalle de ejercicio por su ID
    public DetalleEjercicio obtenerDetalleEjercicio(int detalleEjercicioId) {
        return detalleEjercicioDatos.obtenerDetalleEjercicio(detalleEjercicioId);
    }

    // Método para obtener los detalles de ejercicio
    public List<DetalleEjercicio> obtenerDetallesDeEjercicio(int rutinaDiariaId) {
        return detalleEjercicioDatos.obtenerDetallesDeEjercicio(rutinaDiariaId);
    }
}
