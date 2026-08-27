package pe.edu.upeu.interfmetododef;

public abstract class Producto implements Descontable, Auditable {
    private final String codigo;
    private String nombre;
    private double precio;

    protected Producto(String codigo, String nombre, double precio) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    @Override
    public double getPrecio() {
        return precio;
    } @
            Override
    public void aplicarDescuento(double porcentaje) {
        Descontable.validarPorcentaje(porcentaje);
        this.precio = precio * (1 - porcentaje / 100);
    } @
            Override
    public String registrarCambio(String usuario, String detalle) {
        return "[" + codigo + "] modificado por " + usuario + ": " + detalle;
    }

    public abstract double costoEnvio();
}
