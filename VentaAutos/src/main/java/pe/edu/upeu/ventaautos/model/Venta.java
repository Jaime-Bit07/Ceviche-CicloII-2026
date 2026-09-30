package pe.edu.upeu.ventaautos.model;

import java.time.LocalDate;

public class Venta {
    private String codigo;
    private LocalDate fecha;
    private String cliente;
    private String vehiculo;
    private int cantidad;
    private double total;

    public Venta() { }
    public Venta(String codigo, LocalDate fecha, String cliente, String vehiculo, int cantidad, double total) {
        this.codigo = codigo; this.fecha = fecha; this.cliente = cliente; this.vehiculo = vehiculo; this.cantidad = cantidad; this.total = total;
    }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}