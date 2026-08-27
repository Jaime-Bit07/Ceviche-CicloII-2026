package pe.edu.upeu.abstraccion;

public class Estudiante extends Persona {
    public Estudiante(String nombre, String apellido, String dni, String direccion, String telefono) {
        super(nombre, apellido, dni, direccion, telefono);
    }

    @Override
    public void accion() {
        System.out.println("Estudiante estudiando");
    }
}
