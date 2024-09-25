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
import com.example.app_gym.controllers.VideoController;
import com.example.app_gym.datos.DatabaseHelper;
import com.example.app_gym.models.Categoria;
import com.example.app_gym.models.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> listaVideos;

    public VideoAdapter(List<Video> listaVideos) {
        this.listaVideos = listaVideos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = listaVideos.get(position);
        holder.tvNombreVideo.setText(video.getDescripcion());
        holder.tvUrlVideo.setText(video.getVideoUrl());

        // Configuración de botones: Ver, Editar, Eliminar (Lógica a definir)

        // Configurar el botón de editar
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ActualizarVideoActivity.class);
            intent.putExtra("video_id", video.getId()); // Pasar el ID del video
            holder.itemView.getContext().startActivity(intent);
        });

        // Configurar el botón de eliminar con un AlertDialog
        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Eliminar Video")
                    .setMessage("¿Estás seguro de que deseas eliminar este video?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Eliminar el video de la base de datos
                        VideoController videoController = new VideoController(new DatabaseHelper(holder.itemView.getContext()).getWritableDatabase());
                        int resultado = videoController.eliminarVideo(video.getId());

                        if (resultado > 0) {
                            // Eliminar el video de la lista y notificar al adaptador
                            listaVideos.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, listaVideos.size());
                            Toast.makeText(holder.itemView.getContext(), "Video eliminado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(holder.itemView.getContext(), "Error al eliminar el video", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listaVideos.size();
    }

    public void actualizarVideos(List<Video> nuevosVideos) {
        listaVideos = nuevosVideos;
        notifyDataSetChanged();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreVideo, tvUrlVideo;
        Button btnVer, btnEditar, btnEliminar;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreVideo = itemView.findViewById(R.id.tvNombreVideo);
            tvUrlVideo = itemView.findViewById(R.id.tvUrlVideo);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
