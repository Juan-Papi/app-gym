package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.DetalleEjercicio;

import java.util.ArrayList;
import java.util.List;

public class DetalleEjercicioDatos {
    private SQLiteDatabase db;

    public DetalleEjercicioDatos(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarDetalleEjercicio(DetalleEjercicio detalleEjercicio) {
        ContentValues values = new ContentValues();
        values.put("repeticiones", detalleEjercicio.getRepeticiones());
        values.put("series", detalleEjercicio.getSeries());
        values.put("ejercicioId", detalleEjercicio.getEjercicioId());
        values.put("rutinaDiariaId", detalleEjercicio.getRutinaDiariaId());
        return db.insert("DetalleEjercicio", null, values);
    }

    public int actualizarDetalleEjercicio(DetalleEjercicio detalleEjercicio) {
        ContentValues values = new ContentValues();
        values.put("repeticiones", detalleEjercicio.getRepeticiones());
        values.put("series", detalleEjercicio.getSeries());
        values.put("ejercicioId", detalleEjercicio.getEjercicioId());
        values.put("rutinaDiariaId", detalleEjercicio.getRutinaDiariaId());
        return db.update("DetalleEjercicio", values, "id = ?", new String[]{String.valueOf(detalleEjercicio.getId())});
    }

    public int eliminarDetalleEjercicio(int detalleEjercicioId) {
        return db.delete("DetalleEjercicio", "id = ?", new String[]{String.valueOf(detalleEjercicioId)});
    }

    public DetalleEjercicio obtenerDetalleEjercicio(int detalleEjercicioId) {
        Cursor cursor = db.query("DetalleEjercicio", null, "id = ?", new String[]{String.valueOf(detalleEjercicioId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            DetalleEjercicio detalleEjercicio = new DetalleEjercicio();

            int idIndex = cursor.getColumnIndex("id");
            if (idIndex >= 0) {
                detalleEjercicio.setId(cursor.getInt(idIndex));
            }

            int repeticionesIndex = cursor.getColumnIndex("repeticiones");
            if (repeticionesIndex >= 0) {
                detalleEjercicio.setRepeticiones(cursor.getInt(repeticionesIndex));
            }

            int seriesIndex = cursor.getColumnIndex("series");
            if (seriesIndex >= 0) {
                detalleEjercicio.setSeries(cursor.getInt(seriesIndex));
            }

            int ejercicioIdIndex = cursor.getColumnIndex("ejercicioId");
            if (ejercicioIdIndex >= 0) {
                detalleEjercicio.setEjercicioId(cursor.getInt(ejercicioIdIndex));
            }

            int rutinaDiariaIdIndex = cursor.getColumnIndex("rutinaDiariaId");
            if (rutinaDiariaIdIndex >= 0) {
                detalleEjercicio.setRutinaDiariaId(cursor.getInt(rutinaDiariaIdIndex));
            }

            cursor.close();
            return detalleEjercicio;
        }
        return null;
    }

    // Método para obtener la lista de DetalleEjercicio según el rutinaDiariaId
    public List<DetalleEjercicio> obtenerDetallesDeEjercicio(int rutinaDiariaId) {
        List<DetalleEjercicio> detallesEjercicio = new ArrayList<>();

        String query = "SELECT id, repeticiones, series, ejercicioId, rutinaDiariaId FROM DetalleEjercicio WHERE rutinaDiariaId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(rutinaDiariaId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                DetalleEjercicio detalle = new DetalleEjercicio();
                detalle.setId(cursor.getInt(0));
                detalle.setRepeticiones(cursor.getInt(1));
                detalle.setSeries(cursor.getInt(2));
                detalle.setEjercicioId(cursor.getInt(3));
                detalle.setRutinaDiariaId(cursor.getInt(4));

                detallesEjercicio.add(detalle);
            }
            cursor.close();
        }

        return detallesEjercicio;
    }
}
