package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.RutinaSemanal;

public class RutinaSemanalDatos {
    private SQLiteDatabase db;

    public RutinaSemanalDatos(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarRutinaSemanal(RutinaSemanal rutinaSemanal) {
        ContentValues values = new ContentValues();
        values.put("nombre", rutinaSemanal.getNombre());
        values.put("fecha", rutinaSemanal.getFecha());
        values.put("clienteId", rutinaSemanal.getClienteId());
        return db.insert("RutinaSemanal", null, values);
    }

    public int actualizarRutinaSemanal(RutinaSemanal rutinaSemanal) {
        ContentValues values = new ContentValues();
        values.put("nombre", rutinaSemanal.getNombre());
        values.put("fecha", rutinaSemanal.getFecha());
        values.put("clienteId", rutinaSemanal.getClienteId());
        return db.update("RutinaSemanal", values, "id = ?", new String[]{String.valueOf(rutinaSemanal.getId())});
    }

    public int eliminarRutinaSemanal(int rutinaSemanalId) {
        return db.delete("RutinaSemanal", "id = ?", new String[]{String.valueOf(rutinaSemanalId)});
    }

    public RutinaSemanal obtenerRutinaSemanal(int rutinaSemanalId) {
        Cursor cursor = db.query("RutinaSemanal", null, "id = ?", new String[]{String.valueOf(rutinaSemanalId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            RutinaSemanal rutinaSemanal = new RutinaSemanal();

            int idIndex = cursor.getColumnIndex("id");
            if (idIndex >= 0) {
                rutinaSemanal.setId(cursor.getInt(idIndex));
            }

            int nombreIndex = cursor.getColumnIndex("nombre");
            if (nombreIndex >= 0) {
                rutinaSemanal.setNombre(cursor.getString(nombreIndex));
            }

            int fechaIndex = cursor.getColumnIndex("fecha");
            if (fechaIndex >= 0) {
                rutinaSemanal.setFecha(cursor.getString(fechaIndex));
            }

            int clienteIdIndex = cursor.getColumnIndex("clienteId");
            if (clienteIdIndex >= 0) {
                rutinaSemanal.setClienteId(cursor.getInt(clienteIdIndex));
            }

            cursor.close();
            return rutinaSemanal;
        }
        return null;
    }
}
