package pe.edu.upeu.sysventas.model;

import lombok.Data;
import pe.edu.upeu.sysventas.enums.TipoDocumento;
@Data
public class Cliente {
    private String dniruc;
    private String nombres;
    private TipoDocumento tipoDocumento;
    private String repLegal;
    private String direccion;
}
