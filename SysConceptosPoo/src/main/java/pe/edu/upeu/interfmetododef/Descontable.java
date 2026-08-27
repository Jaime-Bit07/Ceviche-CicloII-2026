package pe.edu.upeu.interfmetododef;

public interface Descontable {
    double DESCUENTO_MAXIMO = 50.0;
    double getPrecio();
    void aplicarDescuento(double porcentaje);

    default double precioConDescuento(double porcentaje) {
        validarPorcentaje(porcentaje);
        return getPrecio() * (1 - porcentaje / 100);
    }

    static void validarPorcentaje(double porcentaje) {
        if (porcentaje < 0 || porcentaje > DESCUENTO_MAXIMO) {
            throw new IllegalArgumentException(
                    "Descuento fuera de rango (0-" + DESCUENTO_MAXIMO + ")");
        }
    }
}
