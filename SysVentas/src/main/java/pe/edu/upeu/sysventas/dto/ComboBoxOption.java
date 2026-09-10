package pe.edu.upeu.sysventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ComboBoxOption {
    String key;
    String value;

    @Override
    public String toString(){
        return value;
    }

}
