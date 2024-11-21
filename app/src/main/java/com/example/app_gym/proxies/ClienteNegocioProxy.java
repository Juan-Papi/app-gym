package com.example.app_gym.proxies;

import com.example.app_gym.entities.Cliente;
import com.example.app_gym.interfaces.IClienteNegocio;
import com.example.app_gym.negocio.ClienteNegocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteNegocioProxy implements IClienteNegocio {
    private ClienteNegocio clienteNegocio;
    private Map<Integer, Cliente> clienteCache = new HashMap<>();

    public ClienteNegocioProxy(ClienteNegocio clienteNegocio) {
        this.clienteNegocio = clienteNegocio;
    }

    @Override
    public long agregarCliente(Cliente cliente) {
        // Delegar al objeto real
        return clienteNegocio.agregarCliente(cliente);
    }

    @Override
    public int actualizarCliente(Cliente cliente) {
        // Eliminar del cache si está presente
        clienteCache.remove(cliente.getId());
        return clienteNegocio.actualizarCliente(cliente);
    }

    @Override
    public int eliminarCliente(int clienteId) {
        // Eliminar del cache si está presente
        clienteCache.remove(clienteId);
        return clienteNegocio.eliminarCliente(clienteId);
    }

    @Override
    public Cliente obtenerCliente(int clienteId) {
        // Verificar el cache primero
        if (clienteCache.containsKey(clienteId)) {
            return clienteCache.get(clienteId);
        }

        // Si no está en cache, obtener del objeto real y almacenar en cache
        Cliente cliente = clienteNegocio.obtenerCliente(clienteId);
        if (cliente != null) {
            clienteCache.put(clienteId, cliente);
        }
        return cliente;
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteNegocio.obtenerTodosLosClientes();
    }
}