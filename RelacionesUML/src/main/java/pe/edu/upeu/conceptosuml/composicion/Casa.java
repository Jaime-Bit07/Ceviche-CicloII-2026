package pe.edu.upeu.conceptosuml.composicion;

public class Casa {
    private Habitacion habitacion;
    Casa() {
        this.habitacion = new Habitacion(); // La habitación se crea con la casa
    }
    public static void main(String[] args) { // Uso:
        Casa miCasa = new Casa();
        System.out.println("habitaciones de "+ miCasa.habitacion.tamanho+" m2");
    }
}
