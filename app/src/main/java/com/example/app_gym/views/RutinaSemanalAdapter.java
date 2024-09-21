package com.example.app_gym.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.controllers.RutinaSemanalController;
import com.example.app_gym.models.RutinaSemanal;
import java.util.List;

public class RutinaSemanalAdapter extends RecyclerView.Adapter<RutinaSemanalAdapter.RutinaSemanalViewHolder> {

    private List<RutinaSemanal> listaRutinasSemanales;
    private RutinaSemanalController rutinaSemanalController;

    public RutinaSemanalAdapter(List<RutinaSemanal> listaRutinasSemanales, RutinaSemanalController rutinaSemanalController) {
        this.listaRutinasSemanales = listaRutinasSemanales;
        this.rutinaSemanalController = rutinaSemanalController;
    }

    @NonNull
    @Override
    public RutinaSemanalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutina_semanal, parent, false);
        return new RutinaSemanalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinaSemanalViewHolder holder, int position) {
        RutinaSemanal rutinaSemanal = listaRutinasSemanales.get(position);

        // Obtener la rutina semanal completa usando el controlador si se necesita más información
        RutinaSemanal rutinaDetalles = rutinaSemanalController.obtenerRutinaSemanal(rutinaSemanal.getId());

        // Asignar valores a los elementos del ViewHolder
        holder.tvNombreRutina.setText(rutinaDetalles.getNombre());
        holder.tvFechaInicio.setText("Fecha inicio: " + rutinaDetalles.getFecha());

        // Configurar botones para cada acción
        holder.btnVer.setOnClickListener(v -> {
            // Acción para ver la rutina
        });

        holder.btnEditar.setOnClickListener(v -> {
            // Acción para editar la rutina
        });

        holder.btnPdf.setOnClickListener(v -> {
            // Acción para generar PDF de la rutina
        });

        holder.btnEliminar.setOnClickListener(v -> {
            // Acción para eliminar la rutina
        });
    }

    @Override
    public int getItemCount() {
        return listaRutinasSemanales.size();
    }

    public static class RutinaSemanalViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreRutina, tvFechaInicio;
        Button btnVer, btnEditar, btnPdf, btnEliminar;

        public RutinaSemanalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreRutina = itemView.findViewById(R.id.tvNombreRutina);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnPdf = itemView.findViewById(R.id.btnPdf);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
