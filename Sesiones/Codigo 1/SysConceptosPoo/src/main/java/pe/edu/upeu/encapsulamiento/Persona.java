package pe.edu.upeu.encapsulamiento;

public class Persona {
    String nombre;
    int edad;
    /*Aplicando concepto encapsulamiento*/
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
