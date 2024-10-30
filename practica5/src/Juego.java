import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Juego {
    private Ventana ventana;
    private JButton botonConfirmar;
    private ArrayList<int[]> eliminadosJugador;
    private ArrayList<int[]> eliminadosRival;
    private ArrayList<int[]> barcosJugador;
    private ArrayList<int[]> barcosRival;
    private boolean turnoJugador;
    private int cantidadBarcos;


    public Juego() {

        ventana = new Ventana();
        eliminadosRival = new ArrayList<>();
        eliminadosJugador = new ArrayList<>();
        barcosJugador = new ArrayList<>();
        barcosRival = new ArrayList<>();
        turnoJugador = true;
        cantidadBarcos = 4;
        botonConfirmar = ventana.getBotonConfirmar();

        agregarFuncionamientoBotonConfirmar();

    }

    private void agregarFuncionamientoBotonConfirmar(){
        botonConfirmar.addActionListener(e -> {
            int fila = ventana.getIndiceSeleccionado()[0];
            int columna = ventana.getIndiceSeleccionado()[1];

            botonConfirmar.setEnabled(false);
            System.out.println("El boton seleccionado es: "+fila+","+columna);
            eliminarCasilla(fila, columna);
            eliminadosRival.forEach(i -> System.out.println(i[0]+","+i[1]));
        });
    }

    private void eliminarCasilla(int fila, int columna){
        ventana.setEstadoIndiceSeleccionado(1);
        ventana.getBotonPanel(turnoJugador, fila, columna).setBackground(Color.DARK_GRAY);
        eliminadosRival.add(new int[]{fila, columna});
    }

    private void crearBarcos(){
        Random rand = new Random();
        int longitudBarco;

        for(int i = 0; i < cantidadBarcos; i++){
            longitudBarco = rand.nextInt(3)+2; // Barcos de longitud del 2 al 4
        }

    }

    private void agregarFuncionamientoBotonesPanel(){

    }
}
