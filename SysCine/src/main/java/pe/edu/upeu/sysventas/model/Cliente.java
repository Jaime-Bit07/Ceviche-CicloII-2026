package pe.edu.upeu.sysventas.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String dniruc;
    private String nombres;

    private String repLegal;
    private String direccion;
}
