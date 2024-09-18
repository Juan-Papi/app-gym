package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Video;
import com.example.app_gym.negocio.VideoNegocio;

public class VideoController {
    private VideoNegocio videoNegocio;

    public VideoController(SQLiteDatabase db) {
        videoNegocio = new VideoNegocio(db);
    }

    // Método para crear un nuevo video
    public long crearNuevoVideo(Video video) {
        return videoNegocio.agregarVideo(video);
    }

    // Método para actualizar un video existente
    public int actualizarVideo(Video video) {
        return videoNegocio.actualizarVideo(video);
    }

    // Método para eliminar un video
    public int eliminarVideo(int videoId) {
        return videoNegocio.eliminarVideo(videoId);
    }

    // Método para obtener un video por su ID
    public Video obtenerVideo(int videoId) {
        return videoNegocio.obtenerVideo(videoId);
    }
}
