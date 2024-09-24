package com.example.app_gym.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.models.Membresia;
import java.util.List;

public class MembresiaAdapter extends RecyclerView.Adapter<MembresiaAdapter.MembresiaViewHolder> {

    private List<Membresia> listaMembresias;
    private OnMembresiaClickListener listener;

    public interface OnMembresiaClickListener {
        void onEditarClick(int position);
        void onEliminarClick(int position);
    }

    public MembresiaAdapter(List<Membresia> listaMembresias, OnMembresiaClickListener listener) {
        this.listaMembresias = listaMembresias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MembresiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_membresia, parent, false);
        return new MembresiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembresiaViewHolder holder, int position) {
        Membresia membresia = listaMembresias.get(position);
        holder.tvFechaInicio.setText("Fecha inicio: " + membresia.getFechaInicio());
        holder.tvFechaFin.setText("Fecha fin: " + membresia.getFechaVencimiento());

        holder.btnEditar.setOnClickListener(v -> listener.onEditarClick(position));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminarClick(position));

    }

    @Override
    public int getItemCount() {
        return listaMembresias.size();
    }

    public static class MembresiaViewHolder extends RecyclerView.ViewHolder {
        TextView tvFechaInicio, tvFechaFin;
        Button btnVer, btnEditar, btnEliminar, btnPdf;

        public MembresiaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
