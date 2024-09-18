package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.RutinaDiaria;

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
}
