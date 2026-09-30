package pe.edu.upeu.sysventas.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.sysventas.enums.TipoDocumento;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String dniruc;
    private String nombres;
    private TipoDocumento tipoDocumento;
    private String repLegal;
    private String direccion;
}
