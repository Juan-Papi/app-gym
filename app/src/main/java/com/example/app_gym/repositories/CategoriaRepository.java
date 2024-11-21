package com.example.app_gym.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.entities.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {
    private SQLiteDatabase db;

    public CategoriaRepository(SQLiteDatabase db) {
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

    // Método para encontrar una categoría por su ID
    public Categoria encontrarPorId(int categoriaId) {
        Categoria categoria = null;
        Cursor cursor = db.rawQuery("SELECT * FROM Categoria WHERE id = ?", new String[]{String.valueOf(categoriaId)});

        if (cursor != null && cursor.moveToFirst()) {
            categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            categoria.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            cursor.close();
        }

        return categoria;
    }

}
