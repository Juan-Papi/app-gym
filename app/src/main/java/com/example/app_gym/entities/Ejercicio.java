package com.example.app_gym.entities;

public class Ejercicio {
    private int id;
    private String nombre;
    private String descripcion;
    private int categoriaId; // Relación con Categoria
    private int videoId;     // Relación con Video

    // Constructor vacío
    public Ejercicio() {}

    // Constructor con todos los atributos
    public Ejercicio(int id, String nombre, String descripcion, int categoriaId, int videoId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoriaId = categoriaId;
        this.videoId = videoId;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
}