package pe.edu.upeu.abstraccion;

import lombok.Data;

@Data
public abstract class Persona { //Una clase abstracta no puede ser instanciada por mas que tenga constructores
    String nombre;
    String apellido;
    String dni;
    String direccion;
    String telefono;

    public Persona(String nombre, String apellido, String dni, String direccion, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    protected Persona() {
    }
    //Metodo abstracto cada persona tiene accion diferente
    public abstract void accion();
    //Metodo para mostrar datos...todos los objetos deben tener un metodo mostrar datos
    public String mostrarDatos(){
        return "Nombre: "+nombre+" Apellido: "+apellido+" DNI: "+dni+" Direccion: "+direccion+" Telefono: "+telefono;
    }
}
