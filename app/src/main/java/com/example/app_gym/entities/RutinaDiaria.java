package com.example.app_gym.entities;

public class RutinaDiaria {
    private int id;
    private String nombre;
    private String fecha;
    private int rutinaSemanalId; // Relación con RutinaSemanal

    // Constructor vacío
    public RutinaDiaria() {}

    // Constructor con todos los atributos
    public RutinaDiaria(int id, String nombre, String fecha, int rutinaSemanalId) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.rutinaSemanalId = rutinaSemanalId;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getRutinaSemanalId() {
        return rutinaSemanalId;
    }

    public void setRutinaSemanalId(int rutinaSemanalId) {
        this.rutinaSemanalId = rutinaSemanalId;
    }
}

