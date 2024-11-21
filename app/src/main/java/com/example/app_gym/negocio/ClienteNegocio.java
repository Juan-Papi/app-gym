package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.repositories.ClienteRepository;
import com.example.app_gym.entities.Cliente;

import java.util.List;

public class ClienteNegocio {
    private ClienteRepository clienteRepository;

    public ClienteNegocio(SQLiteDatabase db) {
        this.clienteRepository = new ClienteRepository(db);
    }

    // Método para agregar un nuevo cliente
    public long agregarCliente(Cliente cliente) {
        return clienteRepository.insertarCliente(cliente);
    }

    // Método para actualizar un cliente existente
    public int actualizarCliente(Cliente cliente) {
        return clienteRepository.actualizarCliente(cliente);
    }

    // Método para eliminar un cliente
    public int eliminarCliente(int clienteId) {
        return clienteRepository.eliminarCliente(clienteId);
    }

    // Método para obtener un cliente por su ID
    public Cliente obtenerCliente(int clienteId) {
        return clienteRepository.obtenerCliente(clienteId);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.obtenerTodosLosClientes();
    }
}