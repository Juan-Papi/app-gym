package com.example.app_gym.entities;

public class RutinaSemanal {
    private int id;
    private String nombre;
    private String fecha;
    private int clienteId; // Relación con Cliente

    // Constructor vacío
    public RutinaSemanal() {}

    // Constructor con todos los atributos
    public RutinaSemanal(int id, String nombre, String fecha, int clienteId) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.clienteId = clienteId;
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

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
}

