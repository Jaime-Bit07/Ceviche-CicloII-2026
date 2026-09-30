package pe.edu.upeu.sysventas.cine.candycbody;

import pe.edu.upeu.sysventas.cine.Candy;

import java.util.List;

public class CandyDelete {

    public void eliminarCandy(
            List<Candy> productos,
            Candy candy
    ) {
        productos.remove(candy);
    }
}
