package com.example.app_gym.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.models.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> listaCategorias;

    public CategoriaAdapter(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);
        holder.tvNombreCategoria.setText(categoria.getNombre());

        holder.btnVer.setOnClickListener(v -> {
            // Acción para ver categoría
        });
        holder.btnEditar.setOnClickListener(v -> {
            // Acción para editar categoría
        });
        holder.btnEliminar.setOnClickListener(v -> {
            // Acción para eliminar categoría
        });
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public void actualizarCategorias(List<Categoria> nuevasCategorias) {
        listaCategorias = nuevasCategorias;
        notifyDataSetChanged();
    }

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreCategoria;
        Button btnVer, btnEditar, btnEliminar;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCategoria = itemView.findViewById(R.id.tvNombreCategoria);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
