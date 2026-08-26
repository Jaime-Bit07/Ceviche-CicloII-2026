package pe.edu.upeu.conceptosuml.agregacion;

import java.util.ArrayList;

public class Departamento {
    private ArrayList<Profesor> profesores = new ArrayList<>();


    void agregarProfesor(Profesor profesor){
        profesores.add(profesor);
    }

    void listarProfesores(){
        for (Profesor profesor : profesores) {
            System.out.println(profesor.nombre);
        }
    }

    public static void main(String[] args) {
        Departamento departamento = new Departamento();
        Profesor profesor = new Profesor("Juan");
        departamento.agregarProfesor(profesor);
        departamento.agregarProfesor(new Profesor("Pedro"));
        departamento.agregarProfesor(new Profesor("Raul"));
        departamento.listarProfesores();

    }
}
