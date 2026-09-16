package pe.edu.upeu.sysventas.components;

public class ColumnInfo {
    private String field;  // El nombre del campo en el modelo
    private Double width;  // El ancho deseado para la columna

    public ColumnInfo(String field, Double width) {
        this.field = field;
        this.width = width;
    }

    public String getField() {
        return field;
    }

    public Double getWidth() {
        return width;
    }
}
