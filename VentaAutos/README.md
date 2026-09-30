# AutoNova | Sistema de venta de autos

Aplicación de escritorio académica basada en Java 21, Maven y JavaFX. La organización del código sigue el estilo de SysVentas con paquetes separados para `controller`, `model` y `service`, y vistas FXML dentro de recursos.

## Funciones

- Inventario inicial de muestra y registro, edición, búsqueda y eliminación de vehículos.
- Registro de ventas por unidad con validación de stock y nombre del cliente.
- Descuento automático de una unidad al registrar la venta.
- Historial de operaciones y resumen del catálogo y ventas de la sesión.

## Ejecución

Requiere JDK 21 y Maven. Desde esta carpeta ejecuta:

```bash
mvn clean javafx:run
```

Los datos son de demostración y viven en memoria; se reinician cuando se cierra la aplicación. El proyecto no se conecta a SysVentas ni modifica sus archivos.