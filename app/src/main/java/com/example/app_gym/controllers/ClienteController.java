package com.example.app_gym.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.models.Cliente;
import com.example.app_gym.negocio.ClienteNegocio;

import java.util.List;

public class ClienteController {
    private ClienteNegocio clienteNegocio;

    public ClienteController(SQLiteDatabase db) {
        clienteNegocio = new ClienteNegocio(db);
    }

    // Método para crear un nuevo cliente
    public long crearNuevoCliente(Cliente cliente) {
        return clienteNegocio.agregarCliente(cliente);
    }

    // Método para actualizar un cliente existente
    public int actualizarCliente(Cliente cliente) {
        return clienteNegocio.actualizarCliente(cliente);
    }

    // Método para eliminar un cliente
    public int eliminarCliente(int clienteId) {
        return clienteNegocio.eliminarCliente(clienteId);
    }

    // Método para obtener un cliente por su ID
    public Cliente obtenerCliente(int clienteId) {
        return clienteNegocio.obtenerCliente(clienteId);
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteNegocio.obtenerTodosLosClientes();
    }
}
