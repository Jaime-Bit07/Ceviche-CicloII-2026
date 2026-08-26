package pe.edu.upeu.modeluml;

/**
 * <<boundary>>
 * Generalización: DialogBox --|> Window
 * Asociación:     DialogBox -- DataController
 */
public class DialogBox extends Window {

    /** Asociación con la clase de control DataController. */
    private DataController dataController;
}
