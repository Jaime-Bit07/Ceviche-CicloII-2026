package pe.edu.upeu.sysventas.components.comprob;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import pe.edu.upeu.sysventas.dto.comprobante.Comprobante;
import pe.edu.upeu.sysventas.dto.comprobante.ItemComprobante;

public class ComprobanteView {
    // Solo 3 colores principales para hacerlo adaptable a cualquier proyecto
    private static final Color COLOR_PRIMARIO = Color.web("#2c3e50"); // Headers y texto principal
    private static final Color COLOR_ACENTO   = Color.web("#c0392b"); // Detalles importantes (Totales, Tipo Doc)
    private static final Color COLOR_CLARO    = Color.web("#f4f6f7"); // Bordes y fondos secundarios

    private static final Font F_NORMAL  = Font.font("Arial", 11);
    private static final Font F_BOLD    = Font.font("Arial", FontWeight.BOLD, 12);
    private static final Font F_TITULO  = Font.font("Arial", FontWeight.BOLD, 14);

    private final Comprobante boleta;

    public ComprobanteView(Comprobante boleta) {
        this.boleta = boleta;
    }

    public VBox construir() {
        VBox pagina = new VBox(15);
        pagina.setStyle("-fx-background-color: white;");
        pagina.setPadding(new Insets(24));
        pagina.setMaxWidth(750);
        pagina.setMinWidth(750);

        pagina.getChildren().addAll(
            seccionCabecera(),
            seccionCliente(),
            seccionItems(),
            seccionTotales(),
            seccionPie()
        );
        return pagina;
    }

    private HBox seccionCabecera() {
        VBox emisor = new VBox(3,
            lbl(boleta.getRazonSocial(), F_TITULO, COLOR_PRIMARIO),
            lbl("RUC: " + boleta.getRucEmisor(), F_BOLD, COLOR_PRIMARIO),
            lbl(boleta.getDireccion() + " - " + boleta.getUbigeo(), F_NORMAL, COLOR_PRIMARIO),
            lbl("Tel: " + boleta.getTelefono(), F_NORMAL, COLOR_PRIMARIO)
        );
        emisor.setPrefWidth(420);

        VBox docBox = new VBox(4,
            lbl(boleta.getTipoDocumento(), F_TITULO, COLOR_ACENTO),
            lbl("R.U.C. " + boleta.getRucEmisor(), F_TITULO, COLOR_PRIMARIO),
            lbl(boleta.getSerie() + " - " + boleta.getNumero(), F_BOLD, COLOR_ACENTO),
            lbl("Fecha: " + boleta.getFechaEmision() + " | Moneda: " + boleta.getMoneda(), F_NORMAL, COLOR_PRIMARIO)
        );
        docBox.setAlignment(Pos.CENTER);
        docBox.setPrefWidth(280);
        docBox.setBorder(borde(COLOR_ACENTO, 2));
        docBox.setPadding(new Insets(10));

        return new HBox(16, emisor, docBox);
    }

    private VBox seccionCliente() {
        GridPane grid = new GridPane();
        grid.setBorder(borde(COLOR_CLARO, 2));
        agregarFilaGrid(grid, 0, boleta.getTipoDocCliente(), boleta.getNroDocCliente());
        agregarFilaGrid(grid, 1, "Cliente", boleta.getNombreCliente());
        agregarFilaGrid(grid, 2, "Dirección", boleta.getDireccionCliente());
        
        ColumnConstraints col1 = new ColumnConstraints(120);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);

        return new VBox(lbl("DATOS DEL CLIENTE", F_BOLD, COLOR_PRIMARIO), grid);
    }

    private VBox seccionItems() {
        GridPane header = new GridPane();
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setPadding(new Insets(5, 8, 5, 8));
        header.addRow(0, 
            celda(header, "CANT.", Color.WHITE, F_BOLD, 50, Pos.CENTER),
            celda(header, "DESCRIPCIÓN", Color.WHITE, F_BOLD, 380, Pos.CENTER_LEFT),
            celda(header, "P. UNIT", Color.WHITE, F_BOLD, 100, Pos.CENTER_RIGHT),
            celda(header, "SUBTOTAL", Color.WHITE, F_BOLD, 100, Pos.CENTER_RIGHT)
        );

        VBox filas = new VBox(0);
        filas.setBorder(borde(COLOR_CLARO, 1));
        for (int i = 0; i < boleta.getItems().size(); i++) {
            ItemComprobante item = boleta.getItems().get(i);
            GridPane fila = new GridPane();
            fila.setPadding(new Insets(5, 8, 5, 8));
            fila.setStyle("-fx-background-color: " + (i % 2 == 0 ? "white" : "#f4f6f7") + ";");
            fila.addRow(0,
                celda(fila, item.getCantidad() + "", COLOR_PRIMARIO, F_NORMAL, 50, Pos.CENTER),
                celda(fila, item.getDescripcion(), COLOR_PRIMARIO, F_NORMAL, 380, Pos.CENTER_LEFT),
                celda(fila, "S/ " + item.getPrecioUnitario(), COLOR_PRIMARIO, F_NORMAL, 100, Pos.CENTER_RIGHT),
                celda(fila, "S/ " + item.getSubtotal(), COLOR_PRIMARIO, F_NORMAL, 100, Pos.CENTER_RIGHT)
            );
            filas.getChildren().add(fila);
        }
        return new VBox(lbl("DETALLE", F_BOLD, COLOR_PRIMARIO), header, filas);
    }

    private HBox seccionTotales() {
        VBox izq = new VBox(lbl("SON:", F_BOLD, COLOR_PRIMARIO), 
            lbl("TOTAL: S/ " + boleta.getTotal(), F_BOLD, COLOR_PRIMARIO));
        izq.setPrefWidth(380);

        VBox der = new VBox(
            filaTotal("Op. Gravadas", boleta.getOpGravadas().toString(), false),
            filaTotal("IGV (18%)", boleta.getIgv().toString(), false),
            filaTotal("TOTAL", boleta.getTotal().toString(), true)
        );
        der.setPrefWidth(300);
        der.setAlignment(Pos.TOP_RIGHT);

        return new HBox(16, izq, der);
    }

    private VBox seccionPie() {
        VBox pie = new VBox(4,
            lbl("Representación impresa de la " + boleta.getTipoDocumento(), F_NORMAL, COLOR_PRIMARIO),
            lbl("Generado el " + boleta.getFechaEmision(), F_NORMAL, COLOR_PRIMARIO)
        );
        pie.setAlignment(Pos.CENTER);
        pie.setBorder(new Border(new BorderStroke(COLOR_CLARO, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(2, 0, 0, 0))));
        pie.setPadding(new Insets(10, 0, 0, 0));
        return pie;
    }

    // -- Componentes UI genéricos reutilizables --

    private Label lbl(String texto, Font font, Color color) {
        Label l = new Label(texto);
        l.setFont(font); l.setTextFill(color); l.setWrapText(true);
        l.setTextAlignment(TextAlignment.CENTER);
        return l;
    }

    private Label celda(GridPane grid, String texto, Color color, Font font, double width, Pos pos) {
        Label l = lbl(texto, font, color);
        l.setPrefWidth(width); l.setAlignment(pos);
        return l;
    }

    private void agregarFilaGrid(GridPane grid, int row, String etiqueta, String valor) {
        Label lEtiqueta = lbl(etiqueta, F_BOLD, COLOR_PRIMARIO);
        lEtiqueta.setPadding(new Insets(4, 8, 4, 8));
        Label lValor = lbl(valor, F_NORMAL, COLOR_PRIMARIO);
        lValor.setPadding(new Insets(4, 8, 4, 8));
        grid.addRow(row, lEtiqueta, lValor);
    }

    private HBox filaTotal(String etiqueta, String valor, boolean esTotal) {
        Label lEtiq = lbl(etiqueta, esTotal ? F_TITULO : F_BOLD, esTotal ? Color.WHITE : COLOR_PRIMARIO);
        lEtiq.setPrefWidth(160); lEtiq.setPadding(new Insets(4, 8, 4, 8));
        Label lVal = lbl("S/ " + valor, esTotal ? F_TITULO : F_NORMAL, esTotal ? Color.WHITE : COLOR_PRIMARIO);
        lVal.setPrefWidth(130); lVal.setAlignment(Pos.CENTER_RIGHT); lVal.setPadding(new Insets(4, 8, 4, 8));
        
        HBox fila = new HBox(lEtiq, lVal);
        fila.setStyle("-fx-background-color: " + (esTotal ? "#c0392b" : "#f4f6f7") + ";");
        return fila;
    }

    private Border borde(Color color, double ancho) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(ancho)));
    }
}
