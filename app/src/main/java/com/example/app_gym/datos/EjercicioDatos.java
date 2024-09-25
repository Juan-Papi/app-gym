package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Ejercicio;

import java.util.ArrayList;
import java.util.List;

public class EjercicioDatos {
    private SQLiteDatabase db;

    public EjercicioDatos(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarEjercicio(Ejercicio ejercicio) {
        ContentValues values = new ContentValues();
        values.put("nombre", ejercicio.getNombre());
        values.put("descripcion", ejercicio.getDescripcion());
        values.put("categoriaId", ejercicio.getCategoriaId());
        values.put("videoId", ejercicio.getVideoId());
        return db.insert("Ejercicio", null, values);
    }

    public int actualizarEjercicio(Ejercicio ejercicio) {
        ContentValues values = new ContentValues();
        values.put("nombre", ejercicio.getNombre());
        values.put("descripcion", ejercicio.getDescripcion());
        values.put("categoriaId", ejercicio.getCategoriaId());
        values.put("videoId", ejercicio.getVideoId());
        return db.update("Ejercicio", values, "id = ?", new String[]{String.valueOf(ejercicio.getId())});
    }

    public int eliminarEjercicio(int ejercicioId) {
        return db.delete("Ejercicio", "id = ?", new String[]{String.valueOf(ejercicioId)});
    }

    public Ejercicio obtenerEjercicio(int ejercicioId) {
        Cursor cursor = db.query("Ejercicio", null, "id = ?", new String[]{String.valueOf(ejercicioId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Ejercicio ejercicio = new Ejercicio();

            int idIndex = cursor.getColumnIndex("id");
            if (idIndex >= 0) {
                ejercicio.setId(cursor.getInt(idIndex));
            }

            int nombreIndex = cursor.getColumnIndex("nombre");
            if (nombreIndex >= 0) {
                ejercicio.setNombre(cursor.getString(nombreIndex));
            }

            int descripcionIndex = cursor.getColumnIndex("descripcion");
            if (descripcionIndex >= 0) {
                ejercicio.setDescripcion(cursor.getString(descripcionIndex));
            }

            int categoriaIdIndex = cursor.getColumnIndex("categoriaId");
            if (categoriaIdIndex >= 0) {
                ejercicio.setCategoriaId(cursor.getInt(categoriaIdIndex));
            }

            cursor.close();
            return ejercicio;
        }
        return null;
    }

    // Obtener todos los ejercicios junto con su categoría y video
    public List<Ejercicio> obtenerTodosLosEjerciciosConRelaciones() {
        List<Ejercicio> listaEjercicios = new ArrayList<>();

        String query = "SELECT e.id, e.nombre, e.descripcion, e.categoriaId, e.videoId " +
                "FROM Ejercicio e " +
                "JOIN Categoria c ON e.categoriaId = c.id " +
                "JOIN Video v ON e.videoId = v.id";



        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Ejercicio ejercicio = new Ejercicio();

                // Verifica si los índices de las columnas son válidos antes de acceder
                int idIndex = cursor.getColumnIndex("id");
                int nombreIndex = cursor.getColumnIndex("nombre");
                int descripcionIndex = cursor.getColumnIndex("descripcion");
                int categoriaIdIndex = cursor.getColumnIndex("categoriaId");
                int videoIdIndex = cursor.getColumnIndex("videoId");

                if (idIndex >= 0) {
                    ejercicio.setId(cursor.getInt(idIndex));  // Obtener el id del ejercicio
                }
                if (nombreIndex >= 0) {
                    ejercicio.setNombre(cursor.getString(nombreIndex));  // Obtener el nombre del ejercicio
                }
                if (descripcionIndex >= 0) {
                    ejercicio.setDescripcion(cursor.getString(descripcionIndex));  // Obtener la descripción del ejercicio
                }
                if (categoriaIdIndex >= 0) {
                    ejercicio.setCategoriaId(cursor.getInt(categoriaIdIndex));  // Obtener el id de la categoría
                }
                if (videoIdIndex >= 0) {
                    ejercicio.setVideoId(cursor.getInt(videoIdIndex));  // Obtener el id del video
                }

                listaEjercicios.add(ejercicio);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return listaEjercicios;
    }
}
