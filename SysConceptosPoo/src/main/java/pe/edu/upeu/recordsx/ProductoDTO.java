package pe.edu.upeu.recordsx;


public record ProductoDTO(Long id, String codigo, String nombre, double precio) {
        public ProductoDTO { // constructor compacto: sin parámetros ni asignaciones
                if (codigo == null || codigo.isBlank()) {
                    throw new IllegalArgumentException("El código es obligatorio");
                }
                if (precio < 0) {
                    throw new IllegalArgumentException("El precio no puede ser negativo");
                } // no hace falta escribir this.codigo = codigo; el compilador lo añade
            }

    public String etiqueta() {
        return nombre + " (" + codigo + ")";
    }

}
