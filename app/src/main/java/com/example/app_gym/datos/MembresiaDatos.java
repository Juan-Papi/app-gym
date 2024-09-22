package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Membresia;

import java.util.ArrayList;
import java.util.List;

public class MembresiaDatos {
    private SQLiteDatabase db;

    public MembresiaDatos(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarMembresia(Membresia membresia) {
        ContentValues values = new ContentValues();
        values.put("fechaInicio", membresia.getFechaInicio());
        values.put("fechaVencimiento", membresia.getFechaVencimiento());
        values.put("clienteId", membresia.getClienteId());
        return db.insert("Membresia", null, values);
    }

    public int actualizarMembresia(Membresia membresia) {
        ContentValues values = new ContentValues();
        values.put("fechaInicio", membresia.getFechaInicio());
        values.put("fechaVencimiento", membresia.getFechaVencimiento());
        values.put("clienteId", membresia.getClienteId());
        return db.update("Membresia", values, "id = ?", new String[]{String.valueOf(membresia.getId())});
    }

    public int eliminarMembresia(int membresiaId) {
        return db.delete("Membresia", "id = ?", new String[]{String.valueOf(membresiaId)});
    }

    public Membresia obtenerMembresia(int membresiaId) {
        Cursor cursor = db.query("Membresia", null, "id = ?", new String[]{String.valueOf(membresiaId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Membresia membresia = new Membresia();

            int idIndex = cursor.getColumnIndex("id");
            if (idIndex >= 0) {
                membresia.setId(cursor.getInt(idIndex));
            }

            int fechaInicioIndex = cursor.getColumnIndex("fechaInicio");
            if (fechaInicioIndex >= 0) {
                membresia.setFechaInicio(cursor.getString(fechaInicioIndex));
            }

            int fechaVencimientoIndex = cursor.getColumnIndex("fechaVencimiento");
            if (fechaVencimientoIndex >= 0) {
                membresia.setFechaVencimiento(cursor.getString(fechaVencimientoIndex));
            }

            int clienteIdIndex = cursor.getColumnIndex("clienteId");
            if (clienteIdIndex >= 0) {
                membresia.setClienteId(cursor.getInt(clienteIdIndex));
            }

            cursor.close();
            return membresia;
        }
        return null;
    }

    // Método para obtener todas las membresías de un cliente
    public List<Membresia> obtenerMembresiasPorCliente(int clienteId) {
        List<Membresia> membresias = new ArrayList<>();

        String query = "SELECT * FROM Membresia WHERE clienteId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(clienteId)});

        if (cursor.moveToFirst()) {
            do {
                Membresia membresia = new Membresia();
                membresia.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                membresia.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow("fechaInicio")));
                membresia.setFechaVencimiento(cursor.getString(cursor.getColumnIndexOrThrow("fechaVencimiento")));
                membresia.setClienteId(cursor.getInt(cursor.getColumnIndexOrThrow("clienteId")));

                membresias.add(membresia);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return membresias;
    }

}
