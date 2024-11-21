package com.example.app_gym.negocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.app_gym.interfaces.IClienteNegocio;
import com.example.app_gym.repositories.ClienteRepository;
import com.example.app_gym.entities.Cliente;

import java.util.List;

public class ClienteNegocio implements IClienteNegocio {
    private ClienteRepository clienteRepository;

    public ClienteNegocio(SQLiteDatabase db) {
        this.clienteRepository = new ClienteRepository(db);
    }

    @Override
    public long agregarCliente(Cliente cliente) {
        return clienteRepository.insertarCliente(cliente);
    }

    @Override
    public int actualizarCliente(Cliente cliente) {
        return clienteRepository.actualizarCliente(cliente);
    }

    @Override
    public int eliminarCliente(int clienteId) {
        return clienteRepository.eliminarCliente(clienteId);
    }

    @Override
    public Cliente obtenerCliente(int clienteId) {
        return clienteRepository.obtenerCliente(clienteId);
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.obtenerTodosLosClientes();
    }
}