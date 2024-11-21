package com.example.app_gym.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.entities.DetalleEjercicio;
import java.util.List;

public class DetalleEjercicioAdapter extends RecyclerView.Adapter<DetalleEjercicioAdapter.DetalleEjercicioViewHolder> {

    private List<DetalleEjercicio> listaDetallesEjercicio;
    private OnDetalleEjercicioClickListener listener;

    // Interface para manejar los eventos de clic en los botones
    public interface OnDetalleEjercicioClickListener {
        void onEditarClick(int position);
        void onEliminarClick(int position);
    }

    public DetalleEjercicioAdapter(List<DetalleEjercicio> listaDetallesEjercicio, OnDetalleEjercicioClickListener listener) {
        this.listaDetallesEjercicio = listaDetallesEjercicio;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DetalleEjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_ejercicio, parent, false);
        return new DetalleEjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleEjercicioViewHolder holder, int position) {
        DetalleEjercicio detalleEjercicio = listaDetallesEjercicio.get(position);

        // Setear los valores a los TextViews
        holder.tvDetalleId.setText("Id: " + detalleEjercicio.getId());
        holder.tvRepeticiones.setText("Repeticiones: " + detalleEjercicio.getRepeticiones());
        holder.tvSeries.setText("Series: " + detalleEjercicio.getSeries());

        holder.btnEditar.setOnClickListener(v -> listener.onEditarClick(position));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminarClick(position));
    }

    @Override
    public int getItemCount() {
        return listaDetallesEjercicio.size();
    }

    public static class DetalleEjercicioViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetalleId, tvRepeticiones, tvSeries;
        Button btnVer, btnEditar, btnEliminar;

        public DetalleEjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDetalleId = itemView.findViewById(R.id.tvDetalleId);
            tvRepeticiones = itemView.findViewById(R.id.tvRepeticiones);
            tvSeries = itemView.findViewById(R.id.tvSeries);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
