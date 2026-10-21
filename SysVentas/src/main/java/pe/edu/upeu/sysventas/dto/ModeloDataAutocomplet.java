package pe.edu.upeu.sysventas.dto;

import lombok.Data;

/**
 * DTO para autocompletado de combos/búsquedas.
 * No requiere cambios — es un POJO simple.
 */

@Data
public class ModeloDataAutocomplet {
    private String idx;
    private String nameDysplay;
    private String otherData;

    @Override
    public String toString()
    {
        return idx+" - "+nameDysplay;
    }
}
