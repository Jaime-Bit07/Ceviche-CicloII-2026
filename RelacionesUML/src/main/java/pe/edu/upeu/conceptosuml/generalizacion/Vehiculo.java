package pe.edu.upeu.conceptosuml.generalizacion;

public class Vehiculo {
    protected String marca;
    protected int velocidadMaxima;

    public Vehiculo(String marca, int velocidadMaxima) {
        this.marca = marca;
        this.velocidadMaxima = velocidadMaxima;
    }

    public void acelerar() {
        System.out.println(marca + " está acelerando");
    }

    public void frenar() {
        System.out.println(marca + " está frenando");
    }
}
