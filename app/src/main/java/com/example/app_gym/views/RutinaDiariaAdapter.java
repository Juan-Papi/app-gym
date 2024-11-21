package com.example.app_gym.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.entities.RutinaDiaria;
import java.util.List;

public class RutinaDiariaAdapter extends RecyclerView.Adapter<RutinaDiariaAdapter.RutinaDiariaViewHolder> {

    private List<RutinaDiaria> listaRutinasDiarias;
    private OnRutinaDiariaClickListener listener;

    // Interface para manejar los eventos de clic en los botones
    public interface OnRutinaDiariaClickListener {
        void onVerClick(int position);
        void onEditarClick(int position);
        void onEliminarClick(int position);
        void onPdfClick(int position);
    }

    public RutinaDiariaAdapter(List<RutinaDiaria> listaRutinasDiarias, OnRutinaDiariaClickListener listener) {
        this.listaRutinasDiarias = listaRutinasDiarias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RutinaDiariaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dia, parent, false);
        return new RutinaDiariaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinaDiariaViewHolder holder, int position) {
        RutinaDiaria rutinaDiaria = listaRutinasDiarias.get(position);
        holder.tvNombreRutina.setText(rutinaDiaria.getNombre());
        holder.tvFechaInicio.setText("Fecha: " + rutinaDiaria.getFecha());

        // Asignar eventos a los botones
        holder.btnVer.setOnClickListener(v -> listener.onVerClick(position));
        holder.btnEditar.setOnClickListener(v -> listener.onEditarClick(position));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminarClick(position));
        holder.btnPdf.setOnClickListener(v -> listener.onPdfClick(position));
    }

    @Override
    public int getItemCount() {
        return listaRutinasDiarias.size();
    }

    public static class RutinaDiariaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreRutina, tvFechaInicio;
        Button btnVer, btnEditar, btnEliminar, btnPdf;

        public RutinaDiariaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreRutina = itemView.findViewById(R.id.tvNombreDia);
            tvFechaInicio = itemView.findViewById(R.id.tvFecha);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnPdf = itemView.findViewById(R.id.btnPdf);
        }
    }
}
