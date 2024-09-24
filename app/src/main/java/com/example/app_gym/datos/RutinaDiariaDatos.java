package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.RutinaDiaria;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class RutinaDiariaDatos {
    private SQLiteDatabase db;

    public RutinaDiariaDatos(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarRutinaDiaria(RutinaDiaria rutinaDiaria) {
        ContentValues values = new ContentValues();
        values.put("nombre", rutinaDiaria.getNombre());
        values.put("fecha", rutinaDiaria.getFecha());
        values.put("rutinaSemanalId", rutinaDiaria.getRutinaSemanalId());
        return db.insert("RutinaDiaria", null, values);
    }

    public int actualizarRutinaDiaria(RutinaDiaria rutinaDiaria) {
        ContentValues values = new ContentValues();
        values.put("nombre", rutinaDiaria.getNombre());
        values.put("fecha", rutinaDiaria.getFecha());
        values.put("rutinaSemanalId", rutinaDiaria.getRutinaSemanalId());
        return db.update("RutinaDiaria", values, "id = ?", new String[]{String.valueOf(rutinaDiaria.getId())});
    }

    public int eliminarRutinaDiaria(int rutinaDiariaId) {
        return db.delete("RutinaDiaria", "id = ?", new String[]{String.valueOf(rutinaDiariaId)});
    }

    public RutinaDiaria obtenerRutinaDiaria(int rutinaDiariaId) {
        Cursor cursor = db.query("RutinaDiaria", null, "id = ?", new String[]{String.valueOf(rutinaDiariaId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            RutinaDiaria rutinaDiaria = new RutinaDiaria();

            int idIndex = cursor.getColumnIndex("id");
            if (idIndex >= 0) {
                rutinaDiaria.setId(cursor.getInt(idIndex));
            }

            int nombreIndex = cursor.getColumnIndex("nombre");
            if (nombreIndex >= 0) {
                rutinaDiaria.setNombre(cursor.getString(nombreIndex));
            }

            int fechaIndex = cursor.getColumnIndex("fecha");
            if (fechaIndex >= 0) {
                rutinaDiaria.setFecha(cursor.getString(fechaIndex));
            }

            int rutinaSemanalIdIndex = cursor.getColumnIndex("rutinaSemanalId");
            if (rutinaSemanalIdIndex >= 0) {
                rutinaDiaria.setRutinaSemanalId(cursor.getInt(rutinaSemanalIdIndex));
            }

            cursor.close();
            return rutinaDiaria;
        }
        return null;
    }

    public List<RutinaDiaria> obtenerRutinasDiariasDeSemana(int semanaId) {
        List<RutinaDiaria> listaRutinasDiarias = new ArrayList<>();
        Cursor cursor = db.query("RutinaDiaria", null, "rutinaSemanalId = ?",
                new String[]{String.valueOf(semanaId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                RutinaDiaria rutinaDiaria = new RutinaDiaria();
                rutinaDiaria.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                rutinaDiaria.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                rutinaDiaria.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));
                rutinaDiaria.setRutinaSemanalId(cursor.getInt(cursor.getColumnIndexOrThrow("rutinaSemanalId")));

                listaRutinasDiarias.add(rutinaDiaria);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return listaRutinasDiarias;
    }

    // Método para obtener rutinas diarias con sus detalles
    public List<Map<String, Object>> obtenerRutinasDiariasConRelaciones(int semanaId) {
        List<Map<String, Object>> rutinasConDetalles = new ArrayList<>();

        // Cambia "rd.semanaId" por "rd.rutinaSemanalId"
        String query = "SELECT rd.id AS rutinaDiariaId, rd.nombre AS rutinaNombre, rd.fecha AS rutinaFecha, " +
                "de.repeticiones, de.series, e.nombre AS nombreEjercicio, e.descripcion AS descripcionEjercicio, v.videoUrl " +
                "FROM RutinaDiaria rd " +
                "LEFT JOIN DetalleEjercicio de ON rd.id = de.rutinaDiariaId " +
                "LEFT JOIN Ejercicio e ON de.ejercicioId = e.id " +
                "LEFT JOIN Video v ON e.videoId = v.id " +
                "WHERE rd.rutinaSemanalId = ?"; // Cambio realizado aquí

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(semanaId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Map<String, Object> rutinaMap = new HashMap<>();
                rutinaMap.put("rutinaDiariaId", cursor.getInt(cursor.getColumnIndexOrThrow("rutinaDiariaId")));
                rutinaMap.put("rutinaNombre", cursor.getString(cursor.getColumnIndexOrThrow("rutinaNombre")));
                rutinaMap.put("rutinaFecha", cursor.getString(cursor.getColumnIndexOrThrow("rutinaFecha")));
                rutinaMap.put("repeticiones", cursor.getInt(cursor.getColumnIndexOrThrow("repeticiones")));
                rutinaMap.put("series", cursor.getInt(cursor.getColumnIndexOrThrow("series")));
                rutinaMap.put("nombreEjercicio", cursor.getString(cursor.getColumnIndexOrThrow("nombreEjercicio")));
                rutinaMap.put("descripcionEjercicio", cursor.getString(cursor.getColumnIndexOrThrow("descripcionEjercicio")));
                rutinaMap.put("videoUrl", cursor.getString(cursor.getColumnIndexOrThrow("videoUrl")));
                rutinasConDetalles.add(rutinaMap);
            }
            cursor.close();
        }

        return rutinasConDetalles;
    }

}
