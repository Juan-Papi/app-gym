package com.example.app_gym.entities;

public class DetalleEjercicio {
    private int id;
    private int repeticiones;
    private int series;
    private int ejercicioId; // Relación con Ejercicio
    private int rutinaDiariaId; // Relación con RutinaDiaria

    // Constructor vacío
    public DetalleEjercicio() {}

    // Constructor con todos los atributos
    public DetalleEjercicio(int id, int repeticiones, int series, int ejercicioId, int rutinaDiariaId) {
        this.id = id;
        this.repeticiones = repeticiones;
        this.series = series;
        this.ejercicioId = ejercicioId;
        this.rutinaDiariaId = rutinaDiariaId;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getEjercicioId() {
        return ejercicioId;
    }

    public void setEjercicioId(int ejercicioId) {
        this.ejercicioId = ejercicioId;
    }

    public int getRutinaDiariaId() {
        return rutinaDiariaId;
    }

    public void setRutinaDiariaId(int rutinaDiariaId) {
        this.rutinaDiariaId = rutinaDiariaId;
    }
}
