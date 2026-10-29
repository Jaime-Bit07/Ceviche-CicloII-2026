package pe.edu.upeu.sysventas.dto.comprobante;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class Comprobante {

    // Emisor
    private String rucEmisor;
    private String razonSocial;
    private String direccion;
    private String ubigeo;          // ej: "Lima, Lima, Lima"
    private String telefono;

    // Documento
    private String tipoDocumento;   // "BOLETA DE VENTA" o "FACTURA"
    private String serie;           // B001
    private String numero;          // 00000123
    private String fechaEmision;    // dd/MM/yyyy
    private String moneda = "PEN";  // PEN = Soles

    // Cliente
    private String tipoDocCliente;  // DNI, RUC, CE
    private String nroDocCliente;
    private String nombreCliente;
    private String direccionCliente;

    // Items
    private List<ItemComprobante> items;

    // Totales
    private BigDecimal opGravadas;   // base imponible
    private BigDecimal igv;          // 18%
    private BigDecimal total;

    // QR (opcional)
    private String qrBase64;         // imagen QR en base64 para incrustar en HTML




}
