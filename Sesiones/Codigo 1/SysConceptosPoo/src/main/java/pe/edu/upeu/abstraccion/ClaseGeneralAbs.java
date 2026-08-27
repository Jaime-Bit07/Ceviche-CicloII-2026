package pe.edu.upeu.abstraccion;

public class ClaseGeneralAbs {
    public static void main(String[] args) {
        Persona p=new Docente();
        System.out.println(p.mostrarDatos());
        p.accion();
        p=new Estudiante("Dario", "Perez", "12345678",
                "Calle 123", "951782520");
        System.out.println(p.mostrarDatos());
        p.accion();
        p=new Empleado("Pedro", "Gonzales", "12345679",
                "Calle 123", "951782521");
        System.out.println(p.mostrarDatos());
        p.accion();
    }
}
