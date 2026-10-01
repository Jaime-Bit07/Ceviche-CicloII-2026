package pe.edu.upeu.sysventas.cine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candy {
    
    private Long idCandy;
    private String nombre;
    private String tipo;
    private Double precio;
    private Integer stock;

    @Override
    public String toString() {
        return nombre + " - S/ " + String.format("%.2f", precio);
    }
}
