package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.datos.ClienteDatos;
import com.example.app_gym.models.Cliente;

public class ClienteNegocio {
    private ClienteDatos clienteDatos;

    public ClienteNegocio(SQLiteDatabase db) {
        this.clienteDatos = new ClienteDatos(db);
    }

    // Método para agregar un nuevo cliente
    public long agregarCliente(Cliente cliente) {
        return clienteDatos.insertarCliente(cliente);
    }

    // Método para actualizar un cliente existente
    public int actualizarCliente(Cliente cliente) {
        return clienteDatos.actualizarCliente(cliente);
    }

    // Método para eliminar un cliente
    public int eliminarCliente(int clienteId) {
        return clienteDatos.eliminarCliente(clienteId);
    }

    // Método para obtener un cliente por su ID
    public Cliente obtenerCliente(int clienteId) {
        return clienteDatos.obtenerCliente(clienteId);
    }
}
