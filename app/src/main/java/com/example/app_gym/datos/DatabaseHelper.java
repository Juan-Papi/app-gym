package com.example.app_gym.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gym_trainer2.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla Cliente
        String CREATE_TABLE_CLIENTE = "CREATE TABLE Cliente (id INTEGER PRIMARY KEY, nombre TEXT, apellido TEXT, edad INTEGER, estado TEXT, fechaEntrada TEXT, obs TEXT)";
        db.execSQL(CREATE_TABLE_CLIENTE);

        // Tabla Membresia
        String CREATE_TABLE_MEMBRESIA = "CREATE TABLE Membresia (id INTEGER PRIMARY KEY, fechaInicio TEXT, fechaVencimiento TEXT, clienteId INTEGER, FOREIGN KEY(clienteId) REFERENCES Cliente(id))";
        db.execSQL(CREATE_TABLE_MEMBRESIA);

        // Tabla RutinaSemanal
        String CREATE_TABLE_RUTINA_SEMANAL = "CREATE TABLE RutinaSemanal (id INTEGER PRIMARY KEY, nombre TEXT, fecha TEXT, clienteId INTEGER, FOREIGN KEY(clienteId) REFERENCES Cliente(id))";
        db.execSQL(CREATE_TABLE_RUTINA_SEMANAL);

        // Tabla RutinaDiaria
        String CREATE_TABLE_RUTINA_DIARIA = "CREATE TABLE RutinaDiaria (id INTEGER PRIMARY KEY, nombre TEXT, fecha TEXT, rutinaSemanalId INTEGER, FOREIGN KEY(rutinaSemanalId) REFERENCES RutinaSemanal(id))";
        db.execSQL(CREATE_TABLE_RUTINA_DIARIA);

        // Tabla Video
        String CREATE_TABLE_VIDEO = "CREATE TABLE Video (id INTEGER PRIMARY KEY, descripcion TEXT, videoUrl TEXT)";
        db.execSQL(CREATE_TABLE_VIDEO);

        // Tabla Categoria
        String CREATE_TABLE_CATEGORIA = "CREATE TABLE Categoria (id INTEGER PRIMARY KEY, nombre TEXT)";
        db.execSQL(CREATE_TABLE_CATEGORIA);

        // Tabla Ejercicio
        String CREATE_TABLE_EJERCICIO = "CREATE TABLE Ejercicio (id INTEGER PRIMARY KEY, nombre TEXT, descripcion TEXT, categoriaId INTEGER, videoId INTEGER, FOREIGN KEY(categoriaId) REFERENCES Categoria(id), FOREIGN KEY(videoId) REFERENCES Video(id))";
        db.execSQL(CREATE_TABLE_EJERCICIO);


        // Tabla DetalleEjercicio
        String CREATE_TABLE_DETALLE_EJERCICIO = "CREATE TABLE DetalleEjercicio (id INTEGER PRIMARY KEY, repeticiones INTEGER, series INTEGER, ejercicioId INTEGER, rutinaDiariaId INTEGER, FOREIGN KEY(ejercicioId) REFERENCES Ejercicio(id), FOREIGN KEY(rutinaDiariaId) REFERENCES RutinaDiaria(id))";
        db.execSQL(CREATE_TABLE_DETALLE_EJERCICIO);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas si ya existen
        db.execSQL("DROP TABLE IF EXISTS Cliente");
        db.execSQL("DROP TABLE IF EXISTS Membresia");
        db.execSQL("DROP TABLE IF EXISTS RutinaSemanal");
        db.execSQL("DROP TABLE IF EXISTS RutinaDiaria");
        db.execSQL("DROP TABLE IF EXISTS Ejercicio");
        db.execSQL("DROP TABLE IF EXISTS Categoria");
        db.execSQL("DROP TABLE IF EXISTS DetalleEjercicio");
        db.execSQL("DROP TABLE IF EXISTS Video");
        onCreate(db);
    }
}
