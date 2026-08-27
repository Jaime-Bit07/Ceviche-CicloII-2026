package pe.edu.upeu.recordsx;

import java.util.ArrayList;
import java.util.List;

public class General {
    public static void main(String[] args) {
        List<ProductoDTO> pdto=new ArrayList<>();
        pdto.add(new ProductoDTO(1L, "12345678", "Laptop 1", 100000.00));
        //pdto.add(new ProductoDTO(1L, "12345678", "Laptop 2", -2));
        pdto.add(new ProductoDTO(1L, "12345678", "Laptop 3", 200));

        for (ProductoDTO p:pdto) {
            System.out.println(p.nombre());
            System.out.println(p.etiqueta());
        }

    }
}
