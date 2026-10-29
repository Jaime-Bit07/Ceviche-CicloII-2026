package pe.edu.upeu.sysventas.controller;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pe.edu.upeu.sysventas.components.comprob.ComprobantePdfService;
import pe.edu.upeu.sysventas.components.comprob.ComprobanteView;
import pe.edu.upeu.sysventas.dto.comprobante.Comprobante;

import java.awt.*;
import java.io.File;


public class MainComprobanteController {

    @FXML private ScrollPane       scrollVisor;
    @FXML private Button           btnExportarPdf;
    @FXML private Button           btnImprimir;

    private final ComprobantePdfService pdfService = new ComprobantePdfService();

    private VBox   vistaActual;
    private Comprobante boletaActual;

    @FXML
    public void initialize() {
        btnExportarPdf.setDisable(true);
        btnImprimir.setDisable(true);
        mostrarBoleta();
    }


    public void pasarDatos(Comprobante boleta) {
        this.boletaActual = boleta;
    }

    public void mostrarBoleta() {
        btnExportarPdf.setDisable(true);
        btnImprimir.setDisable(true);
        Task<VBox> task = new Task<>() {
            @Override
            protected VBox call() {
                return new ComprobanteView(boletaActual).construir();
            }
        };

        task.setOnSucceeded(e -> Platform.runLater(() -> {
            vistaActual = task.getValue();

            // Centrar la boleta en el ScrollPane con fondo gris
            VBox wrapper = new VBox(vistaActual);
            wrapper.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 16;");
            wrapper.setAlignment(javafx.geometry.Pos.TOP_CENTER);

            scrollVisor.setContent(wrapper);
            btnExportarPdf.setDisable(false);
            btnImprimir.setDisable(false);
        }));

        task.setOnFailed(e -> Platform.runLater(() -> {
            mostrarError("Error", "No se pudo generar la boleta",
                         task.getException().getMessage());
        }));

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    // Acciones
    @FXML
    private void onExportarPdf() {
        if (boletaActual == null) return;

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Guardar Comprobante como PDF");
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivo PDF (*.pdf)", "*.pdf"));
        chooser.setInitialFileName(
            "boleta_" + boletaActual.getSerie() + "_" + boletaActual.getNumero() + ".pdf");

        File archivo = chooser.showSaveDialog(btnExportarPdf.getScene().getWindow());
        if (archivo == null) return;
        btnExportarPdf.setDisable(true);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                pdfService.exportar(boletaActual, archivo.toPath());
                return null;
            }
        };

        task.setOnSucceeded(e -> Platform.runLater(() -> {
            btnExportarPdf.setDisable(false);
            try {
                if (Desktop.isDesktopSupported())
                    Desktop.getDesktop().open(archivo);
            } catch (Exception ignored) {}
        }));

        task.setOnFailed(e -> Platform.runLater(() -> {
            btnExportarPdf.setDisable(false);
            mostrarError("Error al exportar PDF", "No se pudo generar el PDF",
                         task.getException().getMessage());
        }));

        new Thread(task) {{ setDaemon(true); }}.start();
    }

    @FXML
    private void onImprimir() {
        if (vistaActual == null) return;

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            mostrarError("Sin impresora",
                "No se encontró ninguna impresora instalada",
                "Instala una impresora o usa 'Exportar PDF'.");
            return;
        }

        boolean imprimir = job.showPrintDialog(btnImprimir.getScene().getWindow());
        if (imprimir) {
            job.printPage(vistaActual);
            job.endJob();
        }
    }


    private void mostrarError(String titulo, String header, String detalle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(detalle);
        alert.showAndWait();
    }
}
