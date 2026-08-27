package pe.edu.upeu.saeledx;

public class General {

   static String resumen(FormaPago fp) {
        return switch (fp) {
            case PagoEfectivo e -> "Pago en efectivo";
            case PagoTarjeta t -> "Tarjeta " + t.numeroEnmascarado();
            case PagoYape y -> "Yape " + y.celular();
            case PagoTransferencia tr -> "Transferencia a " + tr.numeroCuenta();
        };
    }

    public static void main(String[] args) {
        FormaPago dd=new PagoTransferencia("123456789");
        System.out.println(resumen(dd));
        dd.procesar(100000);
    }
}
