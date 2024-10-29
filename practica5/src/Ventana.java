import javax.swing.*;
import java.util.ArrayList;

public class Ventana {
    private JFrame ventana;
    private int altura;
    private int largo;
    private ArrayList<ArrayList<JButton>> botonesPanel;
    private int[] indiceSeleccionado;
    private int dimension;
    private double porcentajeBotones;

    public Ventana() {
        // Definir atributos
        dimension = 8;
        botonesPanel = new ArrayList<>();
        porcentajeBotones = 0.05;
        indiceSeleccionado = new int[2];

        // Crear el marco (ventana)
        ventana = new JFrame("Aplicación en pantalla completa");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hacer que la ventana se abra en pantalla completa
        //ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setSize(1920, 1080);
        ventana.setLayout(null);

        // Obtener altura y largo de la ventana
        altura = ventana.getHeight();
        largo = ventana.getWidth();


        // Crear un botón
        crearBotonesPanel();


        // Hacer visible el marco
        ventana.setVisible(true);
    }

    public ArrayList<JButton> crearFilaBotones(int fila){
        ArrayList<JButton> filaBotones = new ArrayList<>();
        int longitud;
        int espaciadoHorizontal = 50;

        for (int i = 0; i < dimension; i++) {
            JButton boton = new JButton("A");
            filaBotones.add(boton);
            ventana.add(boton);

            longitud = (int) (altura*porcentajeBotones);

            boton.setBounds(espaciadoHorizontal+(longitud*i), longitud*fila, longitud, longitud);
            boton.addActionListener(e -> {
                botonesPanel.get(indiceSeleccionado[0]).get(indiceSeleccionado[1]).setEnabled(true);
                System.out.println("Soy el boton fila: "+fila + " ;columna: "+filaBotones.indexOf(boton) );
                indiceSeleccionado[0] = fila;
                indiceSeleccionado[1] = filaBotones.indexOf(boton);
                boton.setEnabled(false);
            });
        }
        return filaBotones;
    }

    private void crearBotonesPanel(){
        for(int i = 0; i < dimension; i++){
            botonesPanel.add(crearFilaBotones(i));
        }
    }

    public int[] getIndiceSeleccionado() {
        return indiceSeleccionado;
    }
}
