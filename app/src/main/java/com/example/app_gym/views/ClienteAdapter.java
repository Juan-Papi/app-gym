package com.example.app_gym.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.models.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    private List<Cliente> listaClientes;
    private OnClienteClickListener listener;

    // Interfaz para manejar clics en los botones
    public interface OnClienteClickListener {
        void onVerClick(int position);
        void onEditarClick(int position);
        void onEliminarClick(int position);
    }

    public ClienteAdapter(List<Cliente> listaClientes, OnClienteClickListener listener) {
        this.listaClientes = listaClientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);
        holder.tvNombreCliente.setText(cliente.getNombre());
        holder.tvApellidoCliente.setText(cliente.getApellido());

        // Configurar los botones de ver, editar y eliminar
        holder.btnVer.setOnClickListener(v -> listener.onVerClick(position));
        holder.btnEditar.setOnClickListener(v -> listener.onEditarClick(position));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminarClick(position));
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreCliente, tvApellidoCliente;
        Button btnVer, btnEditar, btnEliminar;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvApellidoCliente = itemView.findViewById(R.id.tvApellidoCliente);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
