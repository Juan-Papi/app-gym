package com.example.app_gym.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_gym.R;
import com.example.app_gym.models.RutinaSemanal;

import java.util.List;

public class RutinaSemanalAdapter extends RecyclerView.Adapter<RutinaSemanalAdapter.RutinaSemanalViewHolder> {

    private List<RutinaSemanal> listaRutinasSemanales;
    private Context context;
    private int clienteId;

    public RutinaSemanalAdapter(List<RutinaSemanal> listaRutinasSemanales, Context context, int clienteId) {
        this.listaRutinasSemanales = listaRutinasSemanales;
        this.context = context;
        this.clienteId = clienteId;
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

        holder.tvNombreRutina.setText(rutinaSemanal.getNombre());
        holder.tvFechaInicio.setText("Fecha de inicio: " + rutinaSemanal.getFecha());

        // Configurar el evento de clic para el botón "Ver"
        holder.btnVer.setOnClickListener(v -> {
            Intent intent = new Intent(context, IndexDiaActivity.class);
            intent.putExtra("cliente_id", clienteId); // Pasar cliente_id a la siguiente actividad
            intent.putExtra("rutina_semanal_id", rutinaSemanal.getId()); // Pasar también el ID de la rutina semanal
            context.startActivity(intent);
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
