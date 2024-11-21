package com.example.app_gym.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.entities.RutinaSemanal;

import java.util.ArrayList;
import java.util.List;

public class RutinaSemanalRepository {
    private SQLiteDatabase db;

    public RutinaSemanalRepository(SQLiteDatabase db) {
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

    public List<RutinaSemanal> obtenerRutinasSemanalesDeCliente(int clienteId) {
        List<RutinaSemanal> rutinasSemanales = new ArrayList<>();

        String query = "SELECT * FROM RutinaSemanal WHERE clienteId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(clienteId)});

        if (cursor.moveToFirst()) {
            do {
                RutinaSemanal rutina = new RutinaSemanal();
                rutina.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                rutina.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                rutina.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));
                rutina.setClienteId(cursor.getInt(cursor.getColumnIndexOrThrow("clienteId")));

                rutinasSemanales.add(rutina);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return rutinasSemanales;
    }
}
