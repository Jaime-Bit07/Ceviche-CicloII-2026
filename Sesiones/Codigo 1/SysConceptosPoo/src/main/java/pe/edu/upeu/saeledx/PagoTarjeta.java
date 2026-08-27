package pe.edu.upeu.saeledx;

public record PagoTarjeta(String numeroEnmascarado) implements FormaPago{

    @Override
    public boolean procesar(double monto) {
        System.out.println("Autorizando S/ " + monto + " en tarjeta " +
                numeroEnmascarado);
        return true;
    }

}
