package pe.edu.upeu.saeledx;

public record PagoYape(String celular) implements FormaPago {
    @Override
    public boolean procesar(double monto) {
        System.out.println("Solicitando S/ " + monto + " vía Yape a " + celular);
        return true;
    }
}
