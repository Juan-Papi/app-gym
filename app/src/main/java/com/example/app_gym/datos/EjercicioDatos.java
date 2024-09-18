package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Ejercicio;

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
}
