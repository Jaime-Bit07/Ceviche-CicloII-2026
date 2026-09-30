package pe.edu.upeu.sysventas.cine.candycbody;

import pe.edu.upeu.sysventas.cine.Candy;

public class CandyUpdate {

    public Candy actualizarCandy(
            Candy candy,
            String nombre,
            String tipo,
            Double precio,
            Integer stock
    ) {
        candy.setNombre(nombre);
        candy.setTipo(tipo);
        candy.setPrecio(precio);
        candy.setStock(stock);

        return candy;
    }
}
