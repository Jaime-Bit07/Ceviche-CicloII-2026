package pe.edu.upeu.sysventas.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emisor {
    private Long idEmisor;

    private String ruc;

    private String nombreComercial;

    private String ubigeo;

    private String domicilioFiscal;

    private String urbanizacion;

    private String departamento;

    private String provincia;
    private String distrito;
}