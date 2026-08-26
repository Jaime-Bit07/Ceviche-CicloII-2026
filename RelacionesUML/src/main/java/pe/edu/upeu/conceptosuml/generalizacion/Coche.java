package pe.edu.upeu.conceptosuml.generalizacion;

public class Coche extends Vehiculo {
    private int numPuertas;

    public Coche(String marca, int velocidadMaxima, int numPuertas) {
        super(marca, velocidadMaxima);
        this.numPuertas = numPuertas;
    }

    public void abrirMaletero() {
        System.out.println(marca + " abriendo el maletero");
    }
}
