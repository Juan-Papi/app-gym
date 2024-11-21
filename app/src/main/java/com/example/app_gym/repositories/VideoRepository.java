package com.example.app_gym.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.entities.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoRepository {
    private SQLiteDatabase db;

    public VideoRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarVideo(Video video) {
        ContentValues values = new ContentValues();
        values.put("descripcion", video.getDescripcion());
        values.put("videoUrl", video.getVideoUrl());
        return db.insert("Video", null, values);
    }

    public int actualizarVideo(Video video) {
        ContentValues values = new ContentValues();
        values.put("descripcion", video.getDescripcion());
        values.put("videoUrl", video.getVideoUrl());
        return db.update("Video", values, "id = ?", new String[]{String.valueOf(video.getId())});
    }

    public int eliminarVideo(int videoId) {
        return db.delete("Video", "id = ?", new String[]{String.valueOf(videoId)});
    }

    public Video obtenerVideo(int videoId) {
        Cursor cursor = db.query("Video", null, "id = ?", new String[]{String.valueOf(videoId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Video video = new Video();

            int idIndex = cursor.getColumnIndex("id");
            if (idIndex >= 0) {
                video.setId(cursor.getInt(idIndex));
            }

            int descripcionIndex = cursor.getColumnIndex("descripcion");
            if (descripcionIndex >= 0) {
                video.setDescripcion(cursor.getString(descripcionIndex));
            }

            int videoUrlIndex = cursor.getColumnIndex("videoUrl");
            if (videoUrlIndex >= 0) {
                video.setVideoUrl(cursor.getString(videoUrlIndex));
            }

            cursor.close();
            return video;
        }
        return null;
    }

    // Método para obtener todos los videos desde la base de datos
    public List<Video> obtenerTodosLosVideos() {
        List<Video> listaVideos = new ArrayList<>();
        Cursor cursor = db.query("Video", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Video video = new Video();

                // Proteger el acceso a los índices de columnas
                int idIndex = cursor.getColumnIndex("id");
                if (idIndex >= 0) {
                    video.setId(cursor.getInt(idIndex));
                }

                int descripcionIndex = cursor.getColumnIndex("descripcion");
                if (descripcionIndex >= 0) {
                    video.setDescripcion(cursor.getString(descripcionIndex));
                }

                int urlIndex = cursor.getColumnIndex("videoUrl");
                if (urlIndex >= 0) {
                    video.setVideoUrl(cursor.getString(urlIndex));
                }

                listaVideos.add(video);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return listaVideos;
    }

    // Método para encontrar un video por su ID
    public Video encontrarPorId(int videoId) {
        Video video = null;
        Cursor cursor = db.rawQuery("SELECT * FROM Video WHERE id = ?", new String[]{String.valueOf(videoId)});

        if (cursor != null && cursor.moveToFirst()) {
            video = new Video();
            video.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            video.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
            video.setVideoUrl(cursor.getString(cursor.getColumnIndexOrThrow("videoUrl")));
            cursor.close();
        }

        return video;
    }
}
