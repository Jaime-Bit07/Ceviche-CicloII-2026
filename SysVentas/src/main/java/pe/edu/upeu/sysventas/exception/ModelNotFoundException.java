package pe.edu.upeu.sysventas.exception;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(String message) {
        super(message);
    }
}
