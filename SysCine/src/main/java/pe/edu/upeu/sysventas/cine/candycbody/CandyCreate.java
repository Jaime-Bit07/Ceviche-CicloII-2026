package pe.edu.upeu.sysventas.cine.candycbody;

import pe.edu.upeu.sysventas.cine.Candy;

public class CandyCreate {

    public Candy crearCandy(
            Long idCandy,
            String nombre,
            String tipo,
            Double precio,
            Integer stock
    ) {
        return new Candy(
                idCandy,
                nombre,
                tipo,
                precio,
                stock
        );
    }
}
