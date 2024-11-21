package com.example.app_gym.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Categoria;
import com.example.app_gym.negocio.CategoriaNegocio;

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

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ActualizarCategoriaActivity.class);
            intent.putExtra("categoria_id", categoria.getId()); // Pasar el ID de la categoría
            holder.itemView.getContext().startActivity(intent);
        });
        holder.btnEliminar.setOnClickListener(v -> {
            // Mostrar un AlertDialog para confirmar la eliminación
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Eliminar Categoría")
                    .setMessage("¿Estás seguro de que deseas eliminar esta categoría?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Eliminar la categoría de la base de datos
                        CategoriaNegocio categorianNegocio = new CategoriaNegocio(new DatabaseHelper(holder.itemView.getContext()).getWritableDatabase());
                        int result = categorianNegocio.eliminarCategoria(categoria.getId());

                        if (result > 0) {
                            // Eliminar la categoría de la lista y notificar al adaptador
                            listaCategorias.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, listaCategorias.size());
                            Toast.makeText(holder.itemView.getContext(), "Categoría eliminada correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(holder.itemView.getContext(), "Error al eliminar la categoría", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
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
        Button btnEditar, btnEliminar;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCategoria = itemView.findViewById(R.id.tvNombreCategoria);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }


}
