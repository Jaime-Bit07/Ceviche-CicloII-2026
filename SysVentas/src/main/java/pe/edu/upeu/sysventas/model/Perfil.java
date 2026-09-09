package pe.edu.upeu.sysventas.model;

import lombok.Data;

import java.util.List;
@Data
public class Perfil {
    private Long idPerfil;
    private String nombre;
    private String codigo;
    private List<Acceso> accesos;

}
