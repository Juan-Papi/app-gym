package com.example.app_gym.views;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.app_gym.controllers.EjercicioController;
import com.example.app_gym.models.Categoria;
import com.example.app_gym.models.Ejercicio;
import com.example.app_gym.models.Video;

import java.util.List;

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder> {

    private List<Ejercicio> listaEjercicios;
    private EjercicioController ejercicioController;
    private Context context; // Añadir una variable de instancia para el contexto

    public EjercicioAdapter(List<Ejercicio> listaEjercicios, EjercicioController ejercicioController, Context context) {
        this.listaEjercicios = listaEjercicios;
        this.ejercicioController = ejercicioController;
        this.context = context; // Inicializar la variable de contexto
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

        // Configurar el botón "Editar" para abrir el ActualizarEjercicioActivity
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ActualizarEjercicioActivity.class);
            intent.putExtra("ejercicio_id", ejercicio.getId()); // Pasar el ID del ejercicio
            v.getContext().startActivity(intent); // Utilizar el contexto de la vista
        });

        // Configurar el botón de eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Ejercicio")
                    .setMessage("¿Estás seguro de que deseas eliminar este ejercicio?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        int result = ejercicioController.eliminarEjercicio(ejercicio.getId());

                        if (result > 0) {
                            listaEjercicios.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, listaEjercicios.size());
                            Toast.makeText(context, "Ejercicio eliminado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error al eliminar el ejercicio", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }


    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    public static class EjercicioViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreEjercicio, tvDescripcionEjercicio, tvCategoriaNombre, tvVideoUrl;
        Button btnEditar, btnEliminar;

        public EjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreEjercicio = itemView.findViewById(R.id.tvNombreEjercicio);
            tvDescripcionEjercicio = itemView.findViewById(R.id.tvDescripcionEjercicio);
            tvCategoriaNombre = itemView.findViewById(R.id.tvCategoriaNombre);
            tvVideoUrl = itemView.findViewById(R.id.tvVideoUrl);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
