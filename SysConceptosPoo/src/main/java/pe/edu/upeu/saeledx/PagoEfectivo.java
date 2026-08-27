package pe.edu.upeu.saeledx;

public record PagoEfectivo() implements FormaPago{
    @Override
    public boolean procesar(double monto) {
        System.out.println("Cobrando S/ " + monto + " en efectivo");
        return true;
    }
}
