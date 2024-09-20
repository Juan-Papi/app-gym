package com.example.app_gym.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
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
    }

    @Override
    public int getItemCount() {
        return listaVideos.size();
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
