package com.example.app_gym.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.entities.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private SQLiteDatabase db;

    public ClienteRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertarCliente(Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put("nombre", cliente.getNombre());
        values.put("apellido", cliente.getApellido());
        values.put("edad", cliente.getEdad());
        values.put("estado", cliente.getEstado());
        values.put("fechaEntrada", cliente.getFechaEntrada());
        values.put("obs", cliente.getObs());
        return db.insert("Cliente", null, values);
    }

    public int actualizarCliente(Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put("nombre", cliente.getNombre());
        values.put("apellido", cliente.getApellido());
        values.put("edad", cliente.getEdad());
        values.put("estado", cliente.getEstado());
        values.put("fechaEntrada", cliente.getFechaEntrada());
        values.put("obs", cliente.getObs());
        return db.update("Cliente", values, "id = ?", new String[]{String.valueOf(cliente.getId())});
    }

    public int eliminarCliente(int clienteId) {
        return db.delete("Cliente", "id = ?", new String[]{String.valueOf(clienteId)});
    }

    public Cliente obtenerCliente(int clienteId) {
        Cursor cursor = db.query("Cliente", null, "id = ?", new String[]{String.valueOf(clienteId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Cliente cliente = new Cliente();

            int idIndex = cursor.getColumnIndex("id");
            if (idIndex >= 0) {
                cliente.setId(cursor.getInt(idIndex));
            }

            int nombreIndex = cursor.getColumnIndex("nombre");
            if (nombreIndex >= 0) {
                cliente.setNombre(cursor.getString(nombreIndex));
            }

            int apellidoIndex = cursor.getColumnIndex("apellido");
            if (apellidoIndex >= 0) {
                cliente.setApellido(cursor.getString(apellidoIndex));
            }

            int edadIndex = cursor.getColumnIndex("edad");
            if (edadIndex >= 0) {
                cliente.setEdad(cursor.getInt(edadIndex));
            }

            int estadoIndex = cursor.getColumnIndex("estado");
            if (estadoIndex >= 0) {
                cliente.setEstado(cursor.getString(estadoIndex));
            }

            int fechaEntradaIndex = cursor.getColumnIndex("fechaEntrada");
            if (fechaEntradaIndex >= 0) {
                cliente.setFechaEntrada(cursor.getString(fechaEntradaIndex));
            }

            int obsIndex = cursor.getColumnIndex("obs");
            if (obsIndex >= 0) {
                cliente.setObs(cursor.getString(obsIndex));
            }

            cursor.close();
            return cliente;
        }
        return null;
    }

    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        Cursor cursor = db.query("Cliente", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();

                int idIndex = cursor.getColumnIndex("id");
                if (idIndex != -1) {
                    cliente.setId(cursor.getInt(idIndex));
                }

                int nombreIndex = cursor.getColumnIndex("nombre");
                if (nombreIndex != -1) {
                    cliente.setNombre(cursor.getString(nombreIndex));
                }

                int apellidoIndex = cursor.getColumnIndex("apellido");
                if (apellidoIndex != -1) {
                    cliente.setApellido(cursor.getString(apellidoIndex));
                }

                int edadIndex = cursor.getColumnIndex("edad");
                if (edadIndex != -1) {
                    cliente.setEdad(cursor.getInt(edadIndex));
                }

                int estadoIndex = cursor.getColumnIndex("estado");
                if (estadoIndex != -1) {
                    cliente.setEstado(cursor.getString(estadoIndex));
                }

                int fechaEntradaIndex = cursor.getColumnIndex("fechaEntrada");
                if (fechaEntradaIndex != -1) {
                    cliente.setFechaEntrada(cursor.getString(fechaEntradaIndex));
                }

                int obsIndex = cursor.getColumnIndex("obs");
                if (obsIndex != -1) {
                    cliente.setObs(cursor.getString(obsIndex));
                }

                listaClientes.add(cliente);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listaClientes;
    }


}
