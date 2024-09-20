package com.example.app_gym.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.controllers.EjercicioController;
import com.example.app_gym.models.Categoria;
import com.example.app_gym.models.Ejercicio;
import com.example.app_gym.models.Video;

import java.util.List;

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder> {

    private List<Ejercicio> listaEjercicios;
    private EjercicioController ejercicioController;

    public EjercicioAdapter(List<Ejercicio> listaEjercicios, EjercicioController ejercicioController) {
        this.listaEjercicios = listaEjercicios;
        this.ejercicioController = ejercicioController;
    }

    @NonNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ejercicio, parent, false);
        return new EjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        Ejercicio ejercicio = listaEjercicios.get(position);

        // Establecer nombre y descripción del ejercicio
        holder.tvNombreEjercicio.setText(ejercicio.getNombre());
        holder.tvDescripcionEjercicio.setText(ejercicio.getDescripcion());

        // Obtener el nombre de la categoría y el URL del video
        Categoria categoria = ejercicioController.obtenerCategoriaPorId(ejercicio.getCategoriaId());
        Video video = ejercicioController.obtenerVideoPorId(ejercicio.getVideoId());

        if (categoria != null) {
            holder.tvCategoriaNombre.setText(categoria.getNombre());
        } else {
            holder.tvCategoriaNombre.setText("Categoría no encontrada");
        }

        if (video != null) {
            holder.tvVideoUrl.setText(video.getVideoUrl());
        } else {
            holder.tvVideoUrl.setText("Video no encontrado");
        }
    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    public static class EjercicioViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreEjercicio, tvDescripcionEjercicio, tvCategoriaNombre, tvVideoUrl;
        Button btnVer, btnEditar, btnEliminar;

        public EjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreEjercicio = itemView.findViewById(R.id.tvNombreEjercicio);
            tvDescripcionEjercicio = itemView.findViewById(R.id.tvDescripcionEjercicio);
            tvCategoriaNombre = itemView.findViewById(R.id.tvCategoriaNombre);
            tvVideoUrl = itemView.findViewById(R.id.tvVideoUrl);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
