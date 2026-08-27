package pe.edu.upeu.abstracpolimorf;

public class Loro extends Animal {
    //Polimorfismo
    @Override
    public void sonidoAnimal() {
        System.out.println("Hey como estas...estas entendiendo?");
    }

    @Override
    public void dormir() {
        System.out.println("Zzzz..Zz...zz");
    }
}
