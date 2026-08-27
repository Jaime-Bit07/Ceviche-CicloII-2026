package pe.edu.upeu.saeledx;

public record PagoTransferencia(String numeroCuenta) implements FormaPago{

    @Override
    public boolean procesar(double monto) {
        System.out.println("Registrando transferencia de S/ " + monto + " a " +
                numeroCuenta);
        return true;
    }


}
