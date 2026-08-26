package pe.edu.upeu.modeluml;

import java.util.ArrayList;
import java.util.List;

/**
 * <<entity>>
 * Composición: Circle "1" *-- "*" Point
 * (el círculo crea y destruye sus puntos: ciclo de vida dependiente)
 */
public class Circle extends Shape {

    private float radius;

    /** unsigned int en el diagrama; Java no tiene unsigned, se usa int. */
    private int center;

    /** Composición: los Point pertenecen exclusivamente a este Circle. */
    private final List<Point> points = new ArrayList<>();

    public double area(float radius) {
        return Math.PI * radius * radius;
    }

    public void circum() {
    }

    public void setCenter() {
    }

    public void setRadius() {
    }

    @Override
    public void draw() {
    }

    @Override
    public void erase() {
    }

    @Override
    public void move() {
    }

    @Override
    public void resize() {
    }
}
