package com.example.app_gym.entities;

public class Membresia {
    private int id;
    private String fechaInicio;
    private String fechaVencimiento;
    private int clienteId; // Relación con Cliente

    // Constructor vacío
    public Membresia() {}

    // Constructor con todos los atributos
    public Membresia(int id, String fechaInicio, String fechaVencimiento, int clienteId) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.clienteId = clienteId;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
}
