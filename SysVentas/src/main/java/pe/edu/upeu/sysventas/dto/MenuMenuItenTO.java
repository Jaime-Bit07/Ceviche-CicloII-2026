package pe.edu.upeu.sysventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuMenuItenTO {
    String idNombreObj, rutaFile, menunombre, menuitemnombre;
    String nombreTab, tipoTab;
}
