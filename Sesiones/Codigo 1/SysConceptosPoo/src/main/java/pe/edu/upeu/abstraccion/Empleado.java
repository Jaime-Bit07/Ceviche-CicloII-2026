package pe.edu.upeu.abstraccion;

public class Empleado extends Persona{
    public Empleado(String nombre, String apellido, String dni, String direccion, String telefono) {
        super(nombre, apellido, dni, direccion, telefono);
    }

    @Override
    public void accion() {
        System.out.println("Empleado trabajando");
    }
}
