package pe.edu.upeu.enums;


enum GENERO{Masculino, Femenino}
public class Principal {

    public static void main(String[] args) {
        System.out.println(GENERO.Femenino);
        for (GENERO a:GENERO.values()){
            System.out.println(a);
        }
        for (Mes m: Mes.values()){
            System.out.println(m);
        }

        for (Estado e:Estado.values()){
            System.out.println(e.getDescripcion()+"\t"+e.name());
        }

    }
}
