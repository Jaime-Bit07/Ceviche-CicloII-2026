package pe.edu.upeu.ventaautos.model;

public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private double precio;
    private int stock;

    public Vehiculo() { }
    public Vehiculo(String placa, String marca, String modelo, int anio, double precio, int stock) {
        this.placa = placa; this.marca = marca; this.modelo = modelo; this.anio = anio; this.precio = precio; this.stock = stock;
    }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getVehiculo() { return marca + " " + modelo; }
}