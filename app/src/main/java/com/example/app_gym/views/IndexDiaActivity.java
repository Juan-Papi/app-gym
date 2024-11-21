package com.example.app_gym.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_gym.R;
import com.example.app_gym.repositories.DatabaseHelper;
import com.example.app_gym.entities.Cliente;
import com.example.app_gym.entities.RutinaDiaria;
import java.util.List;

import com.example.app_gym.negocio.ClienteNegocio;
import com.example.app_gym.negocio.DetalleEjercicioNegocio;
import com.example.app_gym.negocio.RutinaDiariaNegocio;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import com.itextpdf.layout.element.Link;


import android.os.Environment;
import com.itextpdf.kernel.pdf.action.PdfAction;

public class IndexDiaActivity extends AppCompatActivity {

    private ClienteNegocio clienteNegocio;
    private RutinaDiariaNegocio rutinaDiariaNegocio;
    private DetalleEjercicioNegocio detalleEjercicioNegocio;
    private TextView tvInformacionCliente;
    private RecyclerView recyclerDias;
    private RutinaDiariaAdapter rutinaDiariaAdapter;
    private List<RutinaDiaria> listaRutinasDiarias;
    private static final int REQUEST_STORAGE_PERMISSION = 100;

    private int clienteId;
    private int semanaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_dia);

        // Solicitar permisos si no están concedidos
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }

        // Inicializar la base de datos y controladores
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        clienteNegocio = new ClienteNegocio(dbHelper.getWritableDatabase());
        rutinaDiariaNegocio = new RutinaDiariaNegocio(dbHelper.getWritableDatabase());
        detalleEjercicioNegocio = new DetalleEjercicioNegocio(dbHelper.getWritableDatabase());

        // Obtener los IDs del cliente y de la semana desde el Intent
        clienteId = getIntent().getIntExtra("cliente_id", -1);
        semanaId = getIntent().getIntExtra("rutina_semanal_id", -1);

        // Mostrar la información del cliente
        tvInformacionCliente = findViewById(R.id.tvInformacionCliente);
        Cliente cliente = clienteNegocio.obtenerCliente(clienteId);
        if (cliente != null) {
            mostrarInformacionCliente(cliente);
        }

        // Configurar el RecyclerView
        recyclerDias = findViewById(R.id.recyclerDias);
        recyclerDias.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la lista de rutinas diarias de la semana y configurar el adaptador
        listaRutinasDiarias = rutinaDiariaNegocio.obtenerRutinasDiariasDeSemana(semanaId);

        rutinaDiariaAdapter = new RutinaDiariaAdapter(listaRutinasDiarias, new RutinaDiariaAdapter.OnRutinaDiariaClickListener() {
            @Override
            public void onVerClick(int position) {
                // Acción al hacer clic en "Ver"
                RutinaDiaria rutinaDiaria = listaRutinasDiarias.get(position);
                Intent intent = new Intent(IndexDiaActivity.this, IndexDetalleEjercicioActivity.class);
                intent.putExtra("cliente_id", clienteId); // Pasar el clienteId a la nueva actividad
                intent.putExtra("rutina_diaria_id", rutinaDiaria.getId()); // Pasar el rutinaDiariaId a la nueva actividad
                startActivity(intent);
            }

            @Override
            public void onEditarClick(int position) {
                // Acción al hacer clic en "Editar"
                RutinaDiaria rutinaDiaria = listaRutinasDiarias.get(position);
                Intent intent = new Intent(IndexDiaActivity.this, ActualizarDiaActivity.class);
                intent.putExtra("cliente_id", clienteId); // Pasar el clienteId a la nueva actividad
                intent.putExtra("rutina_diaria_id", rutinaDiaria.getId()); // Pasar el rutinaDiariaId a la nueva actividad
                intent.putExtra("semana_id", semanaId); // Pasar el semanaId
                startActivityForResult(intent, 2); // Usar startActivityForResult para obtener el resultado al editar
            }


            @Override
            public void onEliminarClick(int position) {
                // Mostrar un AlertDialog para confirmar la eliminación
                new AlertDialog.Builder(IndexDiaActivity.this)
                        .setTitle("Eliminar Rutina Diaria")
                        .setMessage("¿Estás seguro de que deseas eliminar esta rutina diaria?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            RutinaDiaria rutinaDiaria = listaRutinasDiarias.get(position);

                            // Eliminar la rutina diaria a través del controlador
                            int resultado = rutinaDiariaNegocio.eliminarRutinaDiaria(rutinaDiaria.getId());

                            if (resultado > 0) {
                                listaRutinasDiarias.remove(position);
                                rutinaDiariaAdapter.notifyItemRemoved(position);
                                rutinaDiariaAdapter.notifyItemRangeChanged(position, listaRutinasDiarias.size());
                                Toast.makeText(IndexDiaActivity.this, "Rutina diaria eliminada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(IndexDiaActivity.this, "Error al eliminar la rutina diaria", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null) // No hace nada si se elige "No"
                        .show();
            }

            @Override
            public void onPdfClick(int position) {
                RutinaDiaria rutinaDiaria = listaRutinasDiarias.get(position);
                generarPDF(rutinaDiaria);
            }
        });
        recyclerDias.setAdapter(rutinaDiariaAdapter);

        Button btnNuevoDia = findViewById(R.id.btnNuevoDia);
        btnNuevoDia.setOnClickListener(v -> {
            // Intent para abrir DiaActivity
            Intent intent = new Intent(IndexDiaActivity.this, DiaActivity.class);
            intent.putExtra("semana_id", semanaId); // Pasar también el ID de la rutina semanal
            startActivityForResult(intent, 1); // Usar startActivityForResult para obtener el resultado
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
            } else {
              //  Toast.makeText(this, "Los permisos son necesarios para guardar el PDF", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Método para mostrar la información del cliente
    private void mostrarInformacionCliente(Cliente cliente) {
        String informacion = cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                cliente.getEdad() + " años\n" +
                "Plan: " + cliente.getEstado() + "\n" +
                "Se unió: " + cliente.getFechaEntrada();
        tvInformacionCliente.setText(informacion);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1 || requestCode == 2) && resultCode == RESULT_OK) {
            // Volver a cargar la lista de rutinas diarias
            actualizarListaRutinasDiarias();
        }
    }

    // Método para actualizar la lista de rutinas diarias
    private void actualizarListaRutinasDiarias() {
        listaRutinasDiarias.clear(); // Limpiar la lista actual
        listaRutinasDiarias.addAll(rutinaDiariaNegocio.obtenerRutinasDiariasDeSemana(semanaId)); // Agregar los elementos actualizados
        rutinaDiariaAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }

    private void generarPDF(RutinaDiaria rutinaDiaria) {
        try {
            // Crear archivo PDF en el directorio de documentos de la aplicación
            File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Rutina_" + rutinaDiaria.getId() + ".pdf");
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));

            // Crear un objeto PdfDocument a partir del PdfWriter
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Crear el documento usando PdfDocument
            Document document = new Document(pdfDocument);

            // Agregar contenido al PDF con texto en negrita
            Paragraph titulo = new Paragraph("Rutina Diaria: " + rutinaDiaria.getNombre())
                    .setBold() // Poner en negrita
                    .setFontSize(14);
            document.add(titulo);

            Paragraph fecha = new Paragraph("Fecha: " + rutinaDiaria.getFecha())
                    .setBold() // Poner en negrita
                    .setFontSize(12);
            document.add(fecha);

            // Obtener detalles de ejercicios
            List<Map<String, Object>> detallesConRelaciones = detalleEjercicioNegocio.obtenerDetallesConRelaciones(rutinaDiaria.getId());

            // Crear tabla y agregar encabezados con formato en negrita
            float[] columnWidths = {100f, 150f, 150f, 50f, 50f};
            Table table = new Table(columnWidths);

            table.addCell(new Paragraph("Nombre Ejercicio").setBold());
            table.addCell(new Paragraph("Descripción").setBold());
            table.addCell(new Paragraph("Video URL").setBold());
            table.addCell(new Paragraph("Series").setBold());
            table.addCell(new Paragraph("Repeticiones").setBold());

            // Llenar la tabla con los datos
            for (Map<String, Object> detalle : detallesConRelaciones) {
                table.addCell((String) detalle.get("nombreEjercicio"));
                table.addCell((String) detalle.get("descripcionEjercicio"));

                // Crear un hipervínculo para la URL del video
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
            document.close();

            Toast.makeText(this, "PDF generado en: " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

            // Compartir el archivo PDF utilizando FileProvider
            Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", pdfFile);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Compartir PDF"));

        } catch (Exception e) {
            Toast.makeText(this, "Error al generar el PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
