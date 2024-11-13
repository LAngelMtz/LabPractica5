import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Ventana {
    private JFrame ventana;
    private int altura;
    private int largo;
    private ArrayList<ArrayList<JButton>> botonesPanelRival;
    private  ArrayList<ArrayList<JButton>> botonesPanelJugador;
    private int[] indiceSeleccionado;
    private int dimension;
    private double porcentajeBotones;
    private JButton botonConfirmar;

    public Ventana() {
        // Definir atributos
        dimension = 8;
        botonesPanelRival = new ArrayList<>();
        botonesPanelJugador = new ArrayList<>();
        porcentajeBotones = 0.05;
        indiceSeleccionado = new int[3];

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

        // Crear paneles
        crearPanel(true);
        crearPanel(false);

        // Hacer visible el marco
        ventana.setVisible(true);
    }

    public ArrayList<JButton> crearFilaBotones(int fila, int longitud, int espaciadoHorizontal, int espaciadoVertical, boolean jugador) {
        ArrayList<JButton> filaBotones = new ArrayList<>();
        Color colorMar = Color.cyan;


        for (int i = 0; i < dimension; i++) { // Ciclo que va añadendo botones al array de fila
            JButton boton = new JButton();
            filaBotones.add(boton);
            ventana.add(boton);

            // definir dimensiones del boton
            boton.setBounds(espaciadoHorizontal+(longitud*i), espaciadoVertical+(longitud*fila), longitud, longitud);

            boton.setBackground(colorMar); // Cambiar el color del botón
            //boton.setOpaque(true);          // Hace que el color de fondo sea visible
            //boton.setBorderPainted(false);  // Quitar el borde del botón
            boton.setFocusPainted(false);   // Quitar el borde de enfoque cuando se selecciona
            //boton.setContentAreaFilled(false); // Evita que el fondo se vea con el efecto predeterminado
            //boton.setOpaque(true); // Hace que el color de fondo sea visible

            // Si son los botones del tablero del jugador, desactivar los botones
            if(jugador){boton.setEnabled(false);}

            // añadir accion a los botones
            boton.addActionListener(e -> {
                // Si no ha sido eliminado
                if(!(indiceSeleccionado[2] == 1)){
                    botonesPanelRival.get(indiceSeleccionado[0]).get(indiceSeleccionado[1]).setEnabled(true);
                    botonesPanelRival.get(indiceSeleccionado[0]).get(indiceSeleccionado[1]).setBackground(colorMar);
                }
                System.out.println("Soy el boton fila: "+(fila+1) + " ;columna: "+(filaBotones.indexOf(boton)+1) );
                // Registra el indice del boton presionado
                indiceSeleccionado[0] = fila;
                indiceSeleccionado[1] = filaBotones.indexOf(boton);
                indiceSeleccionado[2] = 0;
                // Al presionar el boton, marcarlo como seleccionado
                boton.setEnabled(false);
                boton.setBackground(Color.GRAY);
                botonConfirmar.setEnabled(true);
            });
        }
        return filaBotones;
    }

    private void crearBotonesPanel(int longitud, int espaciadoHorizontal, int espaciadoVertical, boolean jugador) {
        // Ciclo que va añadiendo las filas de botones al array/panel indicado
        for(int i = 0; i < dimension; i++){
            if(!jugador){botonesPanelRival.add(crearFilaBotones(i, longitud, espaciadoHorizontal, espaciadoVertical,jugador));}
            else{botonesPanelJugador.add(crearFilaBotones(i,longitud,espaciadoHorizontal,espaciadoVertical,jugador));}
        }
    }

    private void crearIndicesPanel(int longitud, int espaciadoHorizontal, int espaciadoVertical){
       // Ciclos que añaden los label de los indices del panel (numero de fila/columna)
        for(int i = 0; i < dimension; i++){
            JLabel mensaje = new JLabel(""+(i+1),SwingConstants.CENTER);
            mensaje.setBounds(espaciadoHorizontal*2+(longitud*i), espaciadoVertical, longitud, longitud);
            mensaje.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Coloca un borde al label
            ventana.add(mensaje);
        }
        for(int i = 0; i < dimension; i++){
            JLabel mensaje = new JLabel(""+(i+1),SwingConstants.CENTER);
            mensaje.setBounds(espaciadoHorizontal*2-longitud, espaciadoVertical+longitud+(longitud*i), longitud, longitud);
            mensaje.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Coloca un borde al label
            ventana.add(mensaje);
        }
    }

    private void crearPanel(boolean panelJugador){
        int longitud = (int) (altura*porcentajeBotones);
        int espaciadoHorizontal;
        int espaciadoVertical;
        String titulo;

        // Se define las coordenadas iniciales del panel, dependiendo si es jugador o no
        if(panelJugador){
            espaciadoHorizontal = 50;
            espaciadoVertical = 60;
            titulo = "Campo del jugador";
        }
        else{
            espaciadoHorizontal = 400;
            espaciadoVertical = 60;
            titulo = "Campo del rival";
            crearBotonConfirmar(espaciadoHorizontal,espaciadoVertical, longitud); // El boton confimar se crea debajo del panel de rival
        }

        // Label del titulo del palenel, indica si es el panel del jugador o pc
        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER); // SwingConstans.Center centra texto horizontalmente
        tituloLabel.setBounds(espaciadoHorizontal*2+(longitud*3), espaciadoVertical-longitud, longitud*2, longitud);
        tituloLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Coloca un borde al label
        ventana.add(tituloLabel);

        // Se crean y añaden los botones e indices (numero fila/columna) del panel
        crearIndicesPanel(longitud, espaciadoHorizontal, espaciadoVertical);
        crearBotonesPanel(longitud, espaciadoHorizontal*2, espaciadoVertical+longitud, panelJugador);
    }

    private void crearBotonConfirmar(int horizontal, int vertical, int longitud){
        botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setBounds(horizontal*2+(longitud*3),vertical+(longitud*(dimension+2)), longitud*2, longitud);
        botonConfirmar.setEnabled(false);
        ventana.add(botonConfirmar);
    }

    public JButton getBotonConfirmar(){return botonConfirmar;}

    public int[] getIndiceSeleccionado() {return indiceSeleccionado;}

    public int getDimension(){return dimension;}

    public JButton getBotonPanel(boolean esJugador, int i, int j){
        if(esJugador){return botonesPanelRival.get(i).get(j);}
        else{return botonesPanelJugador.get(i).get(j);}
    }

    public void setEstadoIndiceSeleccionado(int estado){indiceSeleccionado[2] = estado;}

    public void desactivarTodosLosBotones(){
        botonesPanelRival.forEach(f -> f.forEach(b -> b.setEnabled(false)));
    }

}
