package com.example.app_gym.models;

public class Ejercicio {
    private int id;
    private String nombre;
    private String descripcion;
    private int categoriaId; // Relación con Categoria

    // Constructor vacío
    public Ejercicio() {}

    // Constructor con todos los atributos
    public Ejercicio(int id, String nombre, String descripcion, int categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoriaId = categoriaId;
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
}

