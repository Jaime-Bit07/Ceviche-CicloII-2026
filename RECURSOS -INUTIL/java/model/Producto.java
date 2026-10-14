package pe.edu.upeu.sysventas.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.sysventas.enums.TipoProducto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private Long idProducto;
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private TipoProducto tipoProducto;
    @NotNull(message = "El precio del producto es obligatorio")
    @Positive(message = "El precio del producto debe ser positivo")
    private Double pu;
    @NotNull(message = "El precio anterior del producto es obligatorio")
    @PositiveOrZero(message = "El precio anterior del producto debe ser positivo o cero")
    private Double puold;
    @NotNull(message = "La utilidad es obligatoria")
    @PositiveOrZero(message = "La utilidad debe ser positiva o cero")
    private Double utilidad;
    @NotNull(message = "El stock del producto es obligatorio")
    @PositiveOrZero(message = "El stock del producto debe ser positivo")
    private Double stock;
    @NotNull(message = "El stock anterior del producto es obligatorio")
    @PositiveOrZero(message = "El stock anterior del producto debe ser positivo")
    private Double stockold;
    //@NotNull(message = "La categoria del producto es obligatoria")
    private Categoria idCategoria;
    //@NotNull(message = "La marca del producto es obligatoria")
    private Marca idMarca;
    //@NotNull(message = "La unidad de medida del producto es obligatoria")
    private UnidMedida idUnidad;
}