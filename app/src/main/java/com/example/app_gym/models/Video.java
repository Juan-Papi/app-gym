package com.example.app_gym.models;

public class Video {
    private int id;
    private String descripcion;
    private String videoUrl;
    private int ejercicioId; // Relación con Ejercicio

    // Constructor vacío
    public Video() {}

    // Constructor con todos los atributos
    public Video(int id, String descripcion, String videoUrl, int ejercicioId) {
        this.id = id;
        this.descripcion = descripcion;
        this.videoUrl = videoUrl;
        this.ejercicioId = ejercicioId;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return descripcion; // Esto es lo que mostrará el Spinner
    }
}
