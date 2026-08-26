package pe.edu.upeu.modeluml;

/**
 * <<entity>>
 * Clase abstracta (nombre en cursiva en el diagrama).
 */
public abstract class Shape {

    public abstract void draw();

    public abstract void erase();

    public abstract void move();

    public abstract void resize();
}
