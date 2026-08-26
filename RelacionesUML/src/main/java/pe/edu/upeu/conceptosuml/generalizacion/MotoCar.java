package pe.edu.upeu.conceptosuml.generalizacion;

public class MotoCar extends Vehiculo{
    private boolean tieneSidecar;

    public MotoCar(String marca, int velocidadMaxima, boolean tieneSidecar) {
        super(marca, velocidadMaxima);
        this.tieneSidecar = tieneSidecar;
    }
}
