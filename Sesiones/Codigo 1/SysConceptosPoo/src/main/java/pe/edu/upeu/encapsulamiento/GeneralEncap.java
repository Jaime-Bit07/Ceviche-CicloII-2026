package pe.edu.upeu.encapsulamiento;

public class GeneralEncap {
    public static void main(String[] args) {
        Persona pObj=new Persona(); //pObj es un Objeto
        /*Aplicar encapsulamiento; para acceder a las vatibales de la clase instanciada */
        pObj.setNombre("Dario");
        pObj.setEdad(48);
        System.out.println(pObj.getNombre()+"\t"+pObj.getEdad());

        PersonaX pObjX=new PersonaX();
        pObjX.setNombre("David");
        pObjX.setEdad(30);
        System.out.println(pObjX.getNombre()+"\t"+pObjX.getEdad());

    }
}
