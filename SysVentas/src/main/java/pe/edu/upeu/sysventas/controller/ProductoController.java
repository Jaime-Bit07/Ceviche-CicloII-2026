package pe.edu.upeu.sysventas.controller;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sysventas.service.ICategoriaService;
import pe.edu.upeu.sysventas.service.IMarcaService;
import pe.edu.upeu.sysventas.service.IProductoService;
import pe.edu.upeu.sysventas.service.IUnidadMedidaService;

@RequiredArgsConstructor
public class ProductoController {

    private final IMarcaService ms;
    private final ICategoriaService cs;

    private final IProductoService ps;
    private  final IUnidadMedidaService us;

}
