package pe.edu.upeu.sysventas.utils;

import com.github.anastaciocintra.output.PrinterOutputStream;

import javax.print.PrintService;
import java.io.IOException;

public class PrinterManager {
    private static PrinterManager instance;
    private PrintService printService;
    private String printerNameR="pos-80-series";
    private PrinterManager() throws IOException {
        initializePrinter();
    }

    public static synchronized PrinterManager getInstance() throws IOException {
        if (instance == null) {
            instance = new PrinterManager();
        }
        return instance;
    }

    private void initializePrinter() throws IOException {
        String[] printerNames = PrinterOutputStream.getListPrintServicesNames();
        System.out.println("Impresoras disponibles:");
        for (String printerName : printerNames) {
            System.out.println(" - " + printerName);
        }
        String targetPrinter = null;
        for (String printerName : printerNames) {
            if (printerName.toLowerCase().contains(printerNameR.toLowerCase())) {
                targetPrinter = printerName;
                break;
            }
        }
        if (targetPrinter == null) {
            throw new IOException("No se encontro ninguna impresora 'POS-80-Series'.");
        }
        System.out.println("Impresora seleccionada: " + targetPrinter);
        this.printService = PrinterOutputStream.getPrintServiceByName(targetPrinter);
    }
    public PrintService getPrintService() {
        return printService;
    }
}