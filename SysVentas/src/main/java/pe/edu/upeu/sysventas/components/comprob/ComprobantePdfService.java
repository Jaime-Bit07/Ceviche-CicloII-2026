package pe.edu.upeu.sysventas.components.comprob;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pe.edu.upeu.sysventas.dto.comprobante.Comprobante;
import pe.edu.upeu.sysventas.dto.comprobante.ItemComprobante;

import java.awt.*;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ComprobantePdfService {
    private static final Color C_PRIMARIO = new Color(0x2c, 0x3e, 0x50); // Headers y textos
    private static final Color C_ACENTO   = new Color(0xc0, 0x39, 0x2b); // Resaltes (Total, Tipo)
    private static final Color C_CLARO    = new Color(0xf4, 0xf6, 0xf7); // Fondos alternos y bordes

    private static final Font F_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10, C_PRIMARIO);
    private static final Font F_BOLD   = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, C_PRIMARIO);
    private static final Font F_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, C_PRIMARIO);
    private static final Font F_ACENTO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, C_ACENTO);
    private static final Font F_BLANCO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);

    public void exportar(Comprobante boleta, Path destino) throws Exception {
        try (OutputStream os = Files.newOutputStream(destino)) {
            exportar(boleta, os);
        }
    }

    public void exportar(Comprobante boleta, OutputStream os) throws Exception {
        Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(doc, os);
        doc.open();

        doc.add(tablaCabecera(boleta));
        doc.add(new Paragraph(" "));
        doc.add(tablaCliente(boleta));
        doc.add(Chunk.NEWLINE);
        doc.add(tablaItems(boleta));
        doc.add(Chunk.NEWLINE);
        doc.add(tablaTotales(boleta));
        doc.add(Chunk.NEWLINE);
        doc.add(pie(boleta));

        doc.close();
    }

    private PdfPTable tablaCabecera(Comprobante b) {
        PdfPTable tabla = new PdfPTable(new float[]{60, 40});
        tabla.setWidthPercentage(100);

        PdfPCell emisor = new PdfPCell();
        emisor.setBorder(Rectangle.NO_BORDER);
        emisor.addElement(new Paragraph(b.getRazonSocial(), F_TITULO));
        emisor.addElement(new Paragraph("RUC: " + b.getRucEmisor(), F_BOLD));
        emisor.addElement(new Paragraph(b.getDireccion() + " - " + b.getUbigeo(), F_NORMAL));
        emisor.addElement(new Paragraph("Tel: " + b.getTelefono(), F_NORMAL));

        PdfPCell docCelda = new PdfPCell();
        docCelda.setBorderColor(C_ACENTO); docCelda.setBorderWidth(2);
        docCelda.setPadding(10); docCelda.setHorizontalAlignment(Element.ALIGN_CENTER);

        agregarCentrado(docCelda, b.getTipoDocumento(), F_ACENTO);
        agregarCentrado(docCelda, "R.U.C. " + b.getRucEmisor(), F_TITULO);
        agregarCentrado(docCelda, b.getSerie() + " - " + b.getNumero(), F_ACENTO);
        agregarCentrado(docCelda, "Fecha: " + b.getFechaEmision() + " | Moneda: " + b.getMoneda(), F_NORMAL);

        tabla.addCell(emisor); tabla.addCell(docCelda);
        return tabla;
    }

    private PdfPTable tablaCliente(Comprobante b) {
        PdfPTable tabla = new PdfPTable(new float[]{25, 75});
        tabla.setWidthPercentage(100);
        tabla.addCell(celdaBanda("DATOS DEL CLIENTE", 2));
        agregarFila(tabla, b.getTipoDocCliente(), b.getNroDocCliente());
        agregarFila(tabla, "Cliente", b.getNombreCliente());
        agregarFila(tabla, "Dirección", b.getDireccionCliente());
        return tabla;
    }

    private PdfPTable tablaItems(Comprobante b) {
        PdfPTable tabla = new PdfPTable(new float[]{10, 52, 19, 19});
        tabla.setWidthPercentage(100);
        tabla.addCell(celdaBanda("DETALLE", 4));
        tabla.addCell(celdaHeader("CANT.", Element.ALIGN_CENTER));
        tabla.addCell(celdaHeader("DESCRIPCIÓN", Element.ALIGN_LEFT));
        tabla.addCell(celdaHeader("P. UNIT", Element.ALIGN_RIGHT));
        tabla.addCell(celdaHeader("SUBTOTAL", Element.ALIGN_RIGHT));

        for (int i = 0; i < b.getItems().size(); i++) {
            ItemComprobante item = b.getItems().get(i);
            Color bg = (i % 2 == 0) ? Color.WHITE : C_CLARO;
            tabla.addCell(celdaDato("" + item.getCantidad(), Element.ALIGN_CENTER, bg));
            tabla.addCell(celdaDato(item.getDescripcion(), Element.ALIGN_LEFT, bg));
            tabla.addCell(celdaDato("S/ " + item.getPrecioUnitario(), Element.ALIGN_RIGHT, bg));
            tabla.addCell(celdaDato("S/ " + item.getSubtotal(), Element.ALIGN_RIGHT, bg));
        }
        return tabla;
    }

    private PdfPTable tablaTotales(Comprobante b) {
        PdfPTable tabla = new PdfPTable(new float[]{55, 45});
        tabla.setWidthPercentage(100);

        PdfPCell izq = new PdfPCell();
        izq.setBorder(Rectangle.NO_BORDER); izq.setPaddingRight(10);
        PdfPTable letrasBox = new PdfPTable(1); letrasBox.setWidthPercentage(100);
        letrasBox.addCell(celdaBanda("SON:", 1));
        PdfPCell letrasCell = new PdfPCell(new Phrase("TOTAL: S/ " + b.getTotal(), F_BOLD));
        letrasCell.setBackgroundColor(Color.WHITE); letrasCell.setPadding(8); letrasCell.setBorderColor(C_CLARO);
        letrasBox.addCell(letrasCell);
        izq.addElement(letrasBox);

        PdfPCell der = new PdfPCell(); der.setBorder(Rectangle.NO_BORDER);
        PdfPTable montos = new PdfPTable(new float[]{60, 40}); montos.setWidthPercentage(100);
        agregarMonto(montos, "Op. Gravadas", "S/ " + b.getOpGravadas(), false);
        agregarMonto(montos, "IGV (18%)", "S/ " + b.getIgv(), false);
        agregarMonto(montos, "TOTAL", "S/ " + b.getTotal(), true);
        der.addElement(montos);

        tabla.addCell(izq); tabla.addCell(der);
        return tabla;
    }

    private Paragraph pie(Comprobante b) {
        Paragraph p = new Paragraph();
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(new Chunk("Representación impresa de la " + b.getTipoDocumento() + "\n", F_NORMAL));
        p.add(new Chunk("Generado el " + b.getFechaEmision(), F_NORMAL));
        return p;
    }

    // -- Helpers Genéricos --

    private void agregarCentrado(PdfPCell celda, String texto, Font font) {
        Paragraph p = new Paragraph(texto, font);
        p.setAlignment(Element.ALIGN_CENTER);
        celda.addElement(p);
    }

    private PdfPCell celdaBanda(String texto, int colspan) {
        PdfPCell c = new PdfPCell(new Phrase(texto, F_BLANCO));
        c.setColspan(colspan); c.setBackgroundColor(C_PRIMARIO);
        c.setPadding(4); c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    private PdfPCell celdaHeader(String texto, int align) {
        PdfPCell c = new PdfPCell(new Phrase(texto, F_BLANCO));
        c.setBackgroundColor(C_PRIMARIO); c.setPadding(5);
        c.setHorizontalAlignment(align); c.setBorderColor(C_CLARO);
        return c;
    }

    private PdfPCell celdaDato(String texto, int align, Color bg) {
        PdfPCell c = new PdfPCell(new Phrase(texto, F_NORMAL));
        c.setBackgroundColor(bg); c.setPadding(4);
        c.setHorizontalAlignment(align); c.setBorderColor(C_CLARO);
        return c;
    }

    private void agregarFila(PdfPTable tabla, String etiqueta, String valor) {
        tabla.addCell(celdaDato(etiqueta, Element.ALIGN_LEFT, C_CLARO));
        tabla.addCell(celdaDato(valor, Element.ALIGN_LEFT, Color.WHITE));
    }

    private void agregarMonto(PdfPTable tabla, String etiqueta, String valor, boolean esTotal) {
        Font f = esTotal ? F_BLANCO : F_BOLD;
        Color bg = esTotal ? C_ACENTO : C_CLARO;
        
        PdfPCell lEtiq = new PdfPCell(new Phrase(etiqueta, f));
        lEtiq.setBackgroundColor(bg); lEtiq.setPadding(4); lEtiq.setBorderColor(C_CLARO);
        
        PdfPCell lVal = new PdfPCell(new Phrase(valor, f));
        lVal.setBackgroundColor(bg); lVal.setPadding(4); lVal.setBorderColor(C_CLARO);
        lVal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        tabla.addCell(lEtiq); tabla.addCell(lVal);
    }
}
