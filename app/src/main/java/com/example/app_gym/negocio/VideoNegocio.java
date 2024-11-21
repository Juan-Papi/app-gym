package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.repositories.VideoRepository;
import com.example.app_gym.entities.Video;

import java.util.List;

public class VideoNegocio {
    private VideoRepository videoRepository;

    public VideoNegocio(SQLiteDatabase db) {
        this.videoRepository = new VideoRepository(db);
    }

    // Método para agregar un nuevo video
    public long agregarVideo(Video video) {
        return videoRepository.insertarVideo(video);
    }

    // Método para actualizar un video existente
    public int actualizarVideo(Video video) {
        return videoRepository.actualizarVideo(video);
    }

    // Método para eliminar un video
    public int eliminarVideo(int videoId) {
        return videoRepository.eliminarVideo(videoId);
    }

    // Método para obtener un video por su ID
    public Video obtenerVideo(int videoId) {
        return videoRepository.obtenerVideo(videoId);
    }

    // Llama a la capa de datos para obtener todos los videos
    public List<Video> obtenerTodosLosVideos() {
        return videoRepository.obtenerTodosLosVideos();
    }

    // Método para obtener el video por su ID
    public Video obtenerVideoPorId(int videoId) {
        return videoRepository.encontrarPorId(videoId);
    }
}
