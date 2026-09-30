package pe.edu.upeu.sysventas.components;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class ToltipCustom {
    // -----------------------------------------------------------------------
    //  VALIDACIÓN
    // -----------------------------------------------------------------------
    public static final String ESTILO_ERROR  = "-fx-border-color: #e53935; -fx-border-width: 2px; -fx-border-radius: 3px;";
    public static final String ESTILO_NORMAL = "";

    public void marcarError(Control campo, String mensaje) {
        campo.setStyle(ESTILO_ERROR);
        Tooltip tooltip = new Tooltip("⚠  " + mensaje);
        tooltip.setStyle(
                "-fx-background-color: #b71c1c;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 6 10 6 10;" +
                        "-fx-background-radius: 4;"
        );
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setHideDelay(Duration.millis(200));
        tooltip.setShowDuration(Duration.seconds(10));
        tooltip.setWrapText(true);
        tooltip.setMaxWidth(300);
        Tooltip.install(campo, tooltip);
    }

    public void limpiarCampo(Control campo) {
        campo.setStyle(ESTILO_NORMAL);
        Tooltip.install(campo, null);
    }
}