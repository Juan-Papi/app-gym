package com.example.app_gym.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Video;

public class VideoDatos {
    private SQLiteDatabase db;

    public VideoDatos(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarVideo(Video video) {
        ContentValues values = new ContentValues();
        values.put("descripcion", video.getDescripcion());
        values.put("videoUrl", video.getVideoUrl());
        values.put("ejercicioId", video.getEjercicioId());
        return db.insert("Video", null, values);
    }

    public int actualizarVideo(Video video) {
        ContentValues values = new ContentValues();
        values.put("descripcion", video.getDescripcion());
        values.put("videoUrl", video.getVideoUrl());
        values.put("ejercicioId", video.getEjercicioId());
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

            int ejercicioIdIndex = cursor.getColumnIndex("ejercicioId");
            if (ejercicioIdIndex >= 0) {
                video.setEjercicioId(cursor.getInt(ejercicioIdIndex));
            }

            cursor.close();
            return video;
        }
        return null;
    }
}
