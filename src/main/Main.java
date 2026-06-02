package main;

import javax.swing.SwingUtilities;
import ui.PanelSwing;

//Lanza la interfaz grafica Swing
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PanelSwing ventana = new PanelSwing();
            ventana.setVisible(true);
        });
    }
}
