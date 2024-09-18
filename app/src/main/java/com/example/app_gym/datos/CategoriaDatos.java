package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDatos {
    private SQLiteDatabase db;

    public CategoriaDatos(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarCategoria(Categoria categoria) {
        ContentValues values = new ContentValues();
        values.put("nombre", categoria.getNombre());
        return db.insert("Categoria", null, values);
    }

    public int actualizarCategoria(Categoria categoria) {
        ContentValues values = new ContentValues();
        values.put("nombre", categoria.getNombre());
        return db.update("Categoria", values, "id = ?", new String[]{String.valueOf(categoria.getId())});
    }

    public int eliminarCategoria(int categoriaId) {
        return db.delete("Categoria", "id = ?", new String[]{String.valueOf(categoriaId)});
    }

    public Categoria obtenerCategoria(int categoriaId) {
        Cursor cursor = db.query("Categoria", null, "id = ?", new String[]{String.valueOf(categoriaId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Categoria categoria = new Categoria();

            int idIndex = cursor.getColumnIndex("id");
            if (idIndex >= 0) {
                categoria.setId(cursor.getInt(idIndex));
            }

            int nombreIndex = cursor.getColumnIndex("nombre");
            if (nombreIndex >= 0) {
                categoria.setNombre(cursor.getString(nombreIndex));
            }

            cursor.close();
            return categoria;
        }
        return null;
    }

    // Método para obtener todas las categorías desde la base de datos
    public List<Categoria> obtenerTodasLasCategorias() {
        List<Categoria> listaCategorias = new ArrayList<>();
        Cursor cursor = db.query("Categoria", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Categoria categoria = new Categoria();

                // Proteger el acceso a los índices de columnas
                int idIndex = cursor.getColumnIndex("id");
                if (idIndex >= 0) {
                    categoria.setId(cursor.getInt(idIndex));
                }

                int nombreIndex = cursor.getColumnIndex("nombre");
                if (nombreIndex >= 0) {
                    categoria.setNombre(cursor.getString(nombreIndex));
                }

                listaCategorias.add(categoria);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return listaCategorias;
    }

}
