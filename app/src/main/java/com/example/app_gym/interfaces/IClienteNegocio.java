package com.example.app_gym.interfaces;

import com.example.app_gym.entities.Cliente;
import java.util.List;

public interface IClienteNegocio {
    long agregarCliente(Cliente cliente);
    int actualizarCliente(Cliente cliente);
    int eliminarCliente(int clienteId);
    Cliente obtenerCliente(int clienteId);
    List<Cliente> obtenerTodosLosClientes();
}
