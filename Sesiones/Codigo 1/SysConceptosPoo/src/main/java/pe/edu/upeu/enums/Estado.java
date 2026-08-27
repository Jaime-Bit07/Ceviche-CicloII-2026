package pe.edu.upeu.enums;

public enum Estado {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    String descripcion;

    Estado(String descripcion){
        this.descripcion=descripcion;
    }

    public String getDescripcion(){
        return descripcion;}

}
