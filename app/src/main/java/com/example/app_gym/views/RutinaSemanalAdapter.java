package com.example.app_gym.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.models.RutinaSemanal;
import com.example.app_gym.negocio.RutinaDiariaNegocio;
import com.example.app_gym.negocio.RutinaSemanalNegocio;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RutinaSemanalAdapter extends RecyclerView.Adapter<RutinaSemanalAdapter.RutinaSemanalViewHolder> {

    private List<RutinaSemanal> listaRutinasSemanales;
    private Context context;
    private int clienteId;
    private SQLiteDatabase db; // Añade esta variable

    public RutinaSemanalAdapter(List<RutinaSemanal> listaRutinasSemanales, Context context, int clienteId, SQLiteDatabase db) {
        this.listaRutinasSemanales = listaRutinasSemanales;
        this.context = context;
        this.clienteId = clienteId;
        this.db = db; // Asigna la instancia de la base de datos
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

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActualizarSemanaActivity.class);
            intent.putExtra("cliente_id", clienteId);
            intent.putExtra("rutina_semanal_id", rutinaSemanal.getId()); // Pasar también el ID de la rutina semanal
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Rutina Semanal")
                    .setMessage("¿Estás seguro de que deseas eliminar esta rutina semanal?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        RutinaSemanalNegocio rutinaSemanalController = new RutinaSemanalNegocio(db);

                        int resultado = rutinaSemanalController.eliminarRutinaSemanal(rutinaSemanal.getId());
                        if (resultado > 0) {
                            listaRutinasSemanales.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, listaRutinasSemanales.size());
                            Toast.makeText(context, "Rutina semanal eliminada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error al eliminar la rutina semanal", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // Configurar el evento de clic para el botón "PDF"
        holder.btnPdf.setOnClickListener(v -> {
            generarPDFSemanal(rutinaSemanal);
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

    private void generarPDFSemanal(RutinaSemanal rutinaSemanal) {
        try {
            File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Rutina_Semanal_" + rutinaSemanal.getId() + ".pdf");
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Agrega el título del PDF
            Paragraph titulo = new Paragraph("Rutina Semanal: " + rutinaSemanal.getNombre())
                    .setBold()
                    .setFontSize(16);
            document.add(titulo);

            Paragraph fecha = new Paragraph("Fecha de inicio: " + rutinaSemanal.getFecha())
                    .setFontSize(14);
            document.add(fecha);

            // Obtener rutinas diarias y sus detalles
            RutinaDiariaNegocio rutinaDiariaController = new RutinaDiariaNegocio(db);
            List<Map<String, Object>> rutinasDiariasConDetalles = rutinaDiariaController.obtenerRutinasDiariasConRelaciones(rutinaSemanal.getId());

            // Agrupar los detalles de ejercicios por rutinaDiariaId
            Map<Integer, List<Map<String, Object>>> ejerciciosPorRutina = new HashMap<>();
            for (Map<String, Object> rutinaDiaria : rutinasDiariasConDetalles) {
                int rutinaDiariaId = (int) rutinaDiaria.get("rutinaDiariaId");
                if (!ejerciciosPorRutina.containsKey(rutinaDiariaId)) {
                    ejerciciosPorRutina.put(rutinaDiariaId, new ArrayList<>());
                }
                ejerciciosPorRutina.get(rutinaDiariaId).add(rutinaDiaria);
            }

            // Recorre cada RutinaDiaria agrupada y agrega la información al PDF
            for (Map.Entry<Integer, List<Map<String, Object>>> entry : ejerciciosPorRutina.entrySet()) {
                List<Map<String, Object>> ejercicios = entry.getValue();

                // Usar el primer elemento para extraer información de la RutinaDiaria
                Map<String, Object> primeraRutinaDiaria = ejercicios.get(0);

                // Agregar título de la Rutina Diaria
                Paragraph subTitulo = new Paragraph("Rutina Diaria: " + primeraRutinaDiaria.get("rutinaNombre"))
                        .setBold()
                        .setFontSize(14);
                document.add(subTitulo);

                Paragraph fechaDiaria = new Paragraph("Fecha: " + primeraRutinaDiaria.get("rutinaFecha"))
                        .setFontSize(12);
                document.add(fechaDiaria);

                // Tabla para los detalles de ejercicios
                float[] columnWidths = {100f, 150f, 150f, 50f, 50f};
                Table table = new Table(columnWidths);

                // Agrega encabezados
                table.addCell(new Paragraph("Nombre Ejercicio").setBold());
                table.addCell(new Paragraph("Descripción").setBold());
                table.addCell(new Paragraph("Video URL").setBold());
                table.addCell(new Paragraph("Series").setBold());
                table.addCell(new Paragraph("Repeticiones").setBold());

                // Añadir los detalles de ejercicios a la tabla
                for (Map<String, Object> detalle : ejercicios) {
                    table.addCell((String) detalle.get("nombreEjercicio"));
                    table.addCell((String) detalle.get("descripcionEjercicio"));

                    // Crea un hipervínculo para la URL del video
                    String videoUrl = (String) detalle.get("videoUrl");
                    if (videoUrl != null && !videoUrl.isEmpty()) {
                        Link link = new Link("Ver Video", PdfAction.createURI(videoUrl));
                        Paragraph urlParagraph = new Paragraph(link)
                                .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE)
                                .setUnderline();
                        table.addCell(urlParagraph);
                    } else {
                        table.addCell("N/A");
                    }

                    table.addCell(String.valueOf(detalle.get("series")));
                    table.addCell(String.valueOf(detalle.get("repeticiones")));
                }

                document.add(table);
            }

            document.close();

            Toast.makeText(context, "PDF generado en: " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

            // Compartir el archivo PDF utilizando FileProvider
            Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", pdfFile);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Compartir PDF"));

        } catch (Exception e) {
            Toast.makeText(context, "Error al generar el PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
