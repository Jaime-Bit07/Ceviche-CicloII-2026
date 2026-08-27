package pe.edu.upeu.saeledx;

public sealed interface FormaPago permits PagoEfectivo, PagoTarjeta, PagoTransferencia, PagoYape {
    boolean procesar(double monto);
}
