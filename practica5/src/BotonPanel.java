import javax.swing.*;

public class BotonPanel {
    private JButton boton;

    public BotonPanel() {
        boton = new JButton("A");

        // Establecer el tamaño y posición del botón: posX,posY,largo,altura
        boton.setBounds(100, 100, 100, 100);
    }
}
