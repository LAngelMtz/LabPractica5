import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
        crearBarcos();


        //System.out.println("-------------------------------------------------");
        //barcosJugador.forEach(c -> System.out.println(c[0] + "," + c[1]));
    }

    private void agregarFuncionamientoBotonConfirmar(){
        botonConfirmar.addActionListener(e -> {
            int fila = ventana.getIndiceSeleccionado()[0];
            int columna = ventana.getIndiceSeleccionado()[1];


            System.out.println("El boton seleccionado es: "+fila+","+columna);
            eliminarCasilla(fila, columna);
            botonConfirmar.setEnabled(false);

            if(!barcosRival.isEmpty()){
                do{
                    jugarPc();
                }while(!turnoJugador);
            }else{
                ventana.desactivarTodosLosBotones();
                mostrarMensajeFinalizacion("Gana el jugador!");
            }

            barcosRival.forEach(i -> System.out.println(i[0]+","+i[1]));
        });
    }

    private void eliminarCasilla(int fila, int columna){
        ventana.setEstadoIndiceSeleccionado(1);

        if(turnoJugador){ // Jugador dispara
            if(existeBarco(fila,columna,!turnoJugador)){ // Caso de impacto a barco de la PC
                ventana.getBotonPanel(turnoJugador, fila, columna).setBackground(Color.RED);
                for(int i = 0; i < barcosRival.size(); i++){
                    if(barcosRival.get(i)[0] == fila && barcosRival.get(i)[1] == columna){
                        barcosRival.remove(i);
                        break;
                    }
                }
            }else{ // Caso de no impacto al barco de la PC
                ventana.getBotonPanel(turnoJugador, fila, columna).setBackground(Color.DARK_GRAY);
                eliminadosRival.add(new int[]{fila, columna});
                turnoJugador = !turnoJugador;
            }
        }
        else{ // PC dispara
            if(existeBarco(fila,columna,!turnoJugador)){ // Caso de impacto a barco del jugador
                ventana.getBotonPanel(turnoJugador, fila, columna).setBackground(Color.RED);
                for(int i = 0; i < barcosJugador.size(); i++){
                    if(barcosJugador.get(i)[0] == fila && barcosJugador.get(i)[1] == columna){
                        eliminadosJugador.add(barcosJugador.remove(i));
                        break;
                    }
                }
            }else{ // Caso de no impacto al barco del jugador
                ventana.getBotonPanel(turnoJugador, fila, columna).setBackground(Color.DARK_GRAY);
                eliminadosJugador.add(new int[]{fila, columna});
                turnoJugador = !turnoJugador;
            }
        }


    }

    private void crearBarcos(){
        Random rand = new Random();
        int longitudBarco;
        int[] posInicial = new int[2];
        int dimension = ventana.getDimension();
        boolean exito = false;

        // Ciclo que crea los barcos indicados
        for(int i = 0; i < cantidadBarcos; i++){
            longitudBarco = rand.nextInt(3)+2;
            System.out.println("Barco = "+(i+1));

            // Ciclo barcos jugador
            do{
                posInicial[0] = rand.nextInt(dimension);
                posInicial[1] = rand.nextInt(dimension);

                if(!barcosJugador.isEmpty()){ // Si hay barcos
                    if(!existeBarco(posInicial[0],posInicial[1],true)){ // Si no esta ocupada la posicion inicial propuesta
                        exito = definirDireccionBarco(posInicial, longitudBarco, true, i+1);
                    }else{exito = false;}
                }else{
                    //Si no hay barcos
                    exito = definirDireccionBarco(posInicial, longitudBarco, true, i+1);
                }
            }while(!exito);

            for(int j = 1; j <= longitudBarco; j++){
                System.out.println(barcosJugador.get(barcosJugador.size()-j)[0] + "," + barcosJugador.get(barcosJugador.size()-j)[1]);
            }


            // Ciclo barcos rival/PC
            do{
                posInicial[0] = rand.nextInt(dimension);
                posInicial[1] = rand.nextInt(dimension);

                if(!barcosJugador.isEmpty()){ // Si hay barcos
                    if(!existeBarco(posInicial[0],posInicial[1],false)){ // Si no esta ocupada la posicion inicial propuesta
                        exito = definirDireccionBarco(posInicial, longitudBarco, false, i+1);
                    }
                }else{
                    //Si no hay barcos
                    exito = definirDireccionBarco(posInicial, longitudBarco, false, i+1);
                }
            }while(!exito);
        }
    }

    private boolean definirDireccionBarco(int[] inicial, int longitudBarco, boolean esJugador, int numBarco){
        Random rand = new Random();
        int direccion = rand.nextInt(4)*90;
        int fila = inicial[0];
        int columna = inicial[1];
        ArrayList<Integer> probados = new ArrayList<>();
        Color color = Color.ORANGE;


        do{ // Ciclo que añade los barcos a sus array
            if(!probados.isEmpty()){
                do{
                    direccion = rand.nextInt(4)*90;
                }while (probados.contains(direccion));
            }

            if(validarDireccion(direccion,fila,columna,longitudBarco,esJugador)){ // Si las posiciones que quiere ocupar el barco son validas
                JButton boton;
                switch (direccion){
                    case 0:
                        if(esJugador){
                            for(int i = 0; i < longitudBarco;i++){
                                barcosJugador.add(new int[]{fila, columna+i});
                                boton = ventana.getBotonPanel(false,fila,columna+i);
                                boton.setBackground(color);
                                boton.setText("B"+numBarco);
                            }
                        }else{
                            for(int i = 0; i < longitudBarco;i++){
                                barcosRival.add(new int[]{fila, columna+i});
                            }
                        }
                        break;
                    case 90:
                        if(esJugador){
                            for(int i = 0; i < longitudBarco;i++){
                                barcosJugador.add(new int[]{fila-i, columna});
                                boton = ventana.getBotonPanel(false,fila-i,columna);
                                boton.setBackground(color);
                                boton.setText("B"+numBarco);
                            }
                        }else{
                            for(int i = 0; i < longitudBarco;i++){
                                barcosRival.add(new int[]{fila-i, columna});
                            }
                        }
                        break;
                    case 180:
                        if(esJugador){
                            for(int i = 0; i < longitudBarco;i++){
                                barcosJugador.add(new int[]{fila, columna-i});
                                boton = ventana.getBotonPanel(false,fila,columna-i);
                                boton.setBackground(color);
                                boton.setText("B"+numBarco);
                            }
                        }else{
                            for(int i = 0; i < longitudBarco;i++){
                                barcosRival.add(new int[]{fila, columna-i});
                            }
                        }
                        break;
                    case 270:
                        if(esJugador){
                            for(int i = 0; i < longitudBarco;i++){
                                barcosJugador.add(new int[]{fila+i, columna});
                                boton = ventana.getBotonPanel(false,fila+i,columna);
                                boton.setBackground(color);
                                boton.setText("B"+numBarco);
                            }
                        }else{
                            for(int i = 0; i < longitudBarco;i++){
                                barcosRival.add(new int[]{fila+i, columna});
                            }
                        }

                }
                return true;
            }else{probados.add(direccion);}
        }while(probados.size() < 4);
        return false;
    }

    private boolean validarDireccion(int direccion, int fila, int columna, int longitudBarco, boolean esJugador){
       int dimensionTablero = ventana.getDimension();
        switch (direccion){
            case 0:
                if(columna+(longitudBarco-1) < dimensionTablero){
                    for(int i = 1; i < longitudBarco; i++){
                        if(existeBarco(fila,columna+i,esJugador)){return false;}
                    }
                }else{return false;}
                break;
            case 90:
                if(fila-(longitudBarco-1) >= 0){
                    for(int i = 1; i < longitudBarco; i++){
                        if(existeBarco(fila-i,columna,esJugador)){return false;}
                    }
                }else{return false;}
                break;
            case 180:
                if(columna-(longitudBarco-1) >= 0){
                    for(int i = 1; i < longitudBarco; i++){
                        if(existeBarco(fila,columna-i,esJugador)){return false;}
                    }
                }else{return false;}
                break;
            case 270:
                if(fila+(longitudBarco-1) < dimensionTablero){
                    for(int i = 1; i < longitudBarco; i++){
                        if(existeBarco(fila+i,columna,esJugador)){return false;}
                    }
                }else{return false;}
                break;
        }
        return true;
    }

    private boolean existeBarco(int fila, int columna, boolean esPanelJugador){
        if(esPanelJugador){
            return barcosJugador.stream().anyMatch(i -> i[0] == fila && i[1] == columna);
        }
        else{
            return barcosRival.stream().anyMatch( i -> i[0] == fila && i[1] == columna);
        }
    }

    private void jugarPc(){
        Random rand = new Random();
        int dimension = ventana.getDimension();
        int fila = rand.nextInt(dimension);
        int columna = rand.nextInt(dimension);
        int contador = 1;

        if(!turnoJugador){
            System.out.println("Entro turno pc");

            if(!eliminadosJugador.isEmpty()){
                do {
                    int f = fila;
                    int c = columna;
                    if (!eliminadosJugador.stream().anyMatch(i -> i[0] == f && i[1] == c)) {
                        break; // Si no está atrapado, salir del ciclo
                    }

                    // Si está atrapado, generar nuevas coordenadas
                    fila = rand.nextInt(dimension);
                    columna = rand.nextInt(dimension);
                    contador++;
                    System.out.println("Estoy atrapado en el ciclo :( numero: " + contador);
                } while (true);
            }

            eliminarCasilla(fila, columna);
            System.out.println("Termina turno PC1");
            if(!barcosJugador.isEmpty()){
                System.out.println("Termina turno PC2");
            }else{
                ventana.desactivarTodosLosBotones();
                mostrarMensajeFinalizacion("Gana la PC!");
            }
        }

    }

    private static void mostrarMensajeFinalizacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Juego finalizado", JOptionPane.INFORMATION_MESSAGE);
    }
}
