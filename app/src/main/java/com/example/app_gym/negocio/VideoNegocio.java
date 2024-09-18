package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.datos.VideoDatos;
import com.example.app_gym.models.Video;

import java.util.List;

public class VideoNegocio {
    private VideoDatos videoDatos;

    public VideoNegocio(SQLiteDatabase db) {
        this.videoDatos = new VideoDatos(db);
    }

    // Método para agregar un nuevo video
    public long agregarVideo(Video video) {
        return videoDatos.insertarVideo(video);
    }

    // Método para actualizar un video existente
    public int actualizarVideo(Video video) {
        return videoDatos.actualizarVideo(video);
    }

    // Método para eliminar un video
    public int eliminarVideo(int videoId) {
        return videoDatos.eliminarVideo(videoId);
    }

    // Método para obtener un video por su ID
    public Video obtenerVideo(int videoId) {
        return videoDatos.obtenerVideo(videoId);
    }

    // Llama a la capa de datos para obtener todos los videos
    public List<Video> obtenerTodosLosVideos() {
        return videoDatos.obtenerTodosLosVideos();
    }
}
