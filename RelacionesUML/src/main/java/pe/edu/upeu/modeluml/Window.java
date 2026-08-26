package pe.edu.upeu.modeluml;

import java.util.ArrayList;
import java.util.List;

/**
 * <<entity>>
 * The main window of the application.  (Nota del diagrama)
 *
 * - Generalización: Window --|> Frame
 * - Agregación:     Window "1" o-- "*" Shape
 * - Dependencia:    Window ..> Event (parámetro de handleEvent)
 * - Asociación:     Window -- DrawingContext
 */
public class Window extends Frame {

    /** Agregación: la ventana agrupa figuras, pero no controla su ciclo de vida. */
    private List<Shape> shapes = new ArrayList<>();

    /** Asociación con la clase de control DrawingContext. */
    private DrawingContext drawingContext;

    public void open() {
    }

    public void close() {
    }

    public void move() {
    }

    public void display() {
    }

    /** Dependencia: usa Event solo como parámetro. */
    public void handleEvent(Event e) {
    }
}
