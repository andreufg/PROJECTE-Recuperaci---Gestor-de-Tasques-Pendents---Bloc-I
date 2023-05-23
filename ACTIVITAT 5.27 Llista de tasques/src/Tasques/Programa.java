package Tasques;

import Tasques.Tasca.Enum.Prioridad;
import Tasques.Tasca.Enum.Tipos;
import Tasques.Tasca.Tasca;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Programa {
    private Scanner scanner = new Scanner(System.in);
    private final int MAX_TASQUES = 20;
    private Tasca[] lisaTarea;
    private boolean finalizar;
    public static final String ANSI_ROJO = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private static final List<String> TIPOS_VALIDOS = Arrays.asList("hardware", "xarxa", "programació", "base de dades", "altres");
    private static final List<String> PRIORIDADES_VALIDAS = Arrays.asList("baixa", "mitja", "alta");


    public Programa() {
        this.lisaTarea = new Tasca[MAX_TASQUES];
        this.finalizar = false;
    }

    public void setFinalizar(boolean finalizar) {
        this.finalizar = finalizar;
    }

    public void menu() {
        do {
            System.out.println("Benvingut a Batoi To-Do List");
            System.out.println("---------------------------");
            System.out.println("1. Afegir una tasca");
            System.out.println("2. Mostrar tasques");
            System.out.println("3. Mostrar tasques ordenades per temps");
            System.out.println("4. Afegir temps");
            System.out.println("5. Marcar tasca com a finalitzada");
            System.out.println("6. Eixir del programa");
            elegir();
        } while (!finalizar);


    }

    private void elegir() {
        int opcion;
        boolean salir = false;
        do {
            System.out.println("Introdueix l'opció[1-6]:");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion > 0 && opcion < 7) {
                    if (opcion == 1) {
                        crearTasca();
                        salir = true;
                    } else if (opcion == 2) {
                        mostrarTareas(this.lisaTarea);
                        salir = true;
                    } else if (opcion == 3) {
                        mostrarTareas(crearListaOrdenada());
                        salir = true;
                    } else if (opcion == 4) {
                        afegirTemps();
                        salir = true;
                    } else if (opcion == 5) {
                        finalizarTarea();
                        salir = true;
                    } else {
                        System.out.println("Adéu");
                        salir = true;
                        setFinalizar(true);
                    }
                } else {
                    System.out.println(ANSI_ROJO +  "Error! Has de introduïr un valor entre 1 i 6" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_ROJO + "Error! Has d'introduir un valor numèric" + ANSI_RESET);
                scanner.nextLine();
            }

        } while (!salir);
    }


    private Tasca[] crearListaOrdenada() {
        Tasca[] listaOrnada = new Tasca[MAX_TASQUES];
        if (lisaTarea[0] == null) {
        } else {
            for (int i = 0; i < lisaTarea.length; i++) {
                if (lisaTarea[i] == null) {
                } else {
                    Tasca tasca = lisaTarea[i];
                    int j = i;
                    while (j > -0 && listaOrnada[j - 1].getTiempo() > tasca.getTiempo()) {
                        listaOrnada[j] = listaOrnada[j - 1];
                        j--;

                    }
                    listaOrnada[j] = tasca;
                }
            }
        }
        return listaOrnada;
    }

    private void crearTasca() {
        System.out.println("Descripció de la tasca:");
        String descripcion = scanner.next();
        String tipus = comprobarTexto("Tipus[Hardware, Xarxa, Programació, Base de dades, Altres]:", TIPOS_VALIDOS);
        int temps = comprobarTiempo("Temps estimat[5-1000]");
        String prioridad = comprobarTexto("Prioritat[Baixa, Mitja, Alta]:", PRIORIDADES_VALIDAS);
        Tasca tasca = new Tasca(descripcion, pasarTipos(tipus), temps, pasarPrioridad(prioridad));
        anyadirTarea(tasca);
    }

    private String comprobarTexto(String texto, List<String> opcionesValidas) {
        String input;
        do {
            System.out.println(texto);
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
                if (opcionesValidas.contains(input.toLowerCase())) {
                    return input;
                } else {
                    System.out.println(ANSI_ROJO + "Error! El valor introduït no és vàlid" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_ROJO + "Error! El valor introduït no és vàlid" + ANSI_RESET);
                scanner.nextLine(); // Limpiar el búfer del escáner
            }
        } while (true);
    }

    private int comprobarTiempo(String texto) {
        int tiempo;
        do {
            System.out.println(texto);
            if (scanner.hasNextInt()) {
                tiempo = scanner.nextInt();
                if (tiempo >= 5 && tiempo <= 1000) {
                    return tiempo;
                } else {
                    System.out.println(ANSI_ROJO + "Error! El valor introduït no és vàlid" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_ROJO + "Error! El valor introduït no és vàlid" + ANSI_RESET);
                scanner.nextLine();
            }
        } while (true);
    }


    private void anyadirTarea(Tasca tasca) {
        if (!listaLLena()) {
            for (int i = 0; i < lisaTarea.length; i++) {
                if (lisaTarea[i] == null) {
                    lisaTarea[i] = tasca;
                    System.out.println(ANSI_GREEN + "S'ha afegit la tasca satisfactòriament. La tasca ocupa la posició " + (i + 1) + ANSI_RESET);
                    return;
                }
            }
        }
        System.out.println("Ya no caben mas tareas");
    }

    private void finalizarTarea() {
        mostrarTareas(this.lisaTarea);
        if (lisaTarea[0] != null) {
            System.out.println("Introdueix el número de la tasca:");
            int numeroTasca = scanner.nextInt();
            numeroTasca -= 1;
            if (numeroTasca < 0 || numeroTasca > 20 || lisaTarea[numeroTasca] == null) {
                System.out.println(ANSI_ROJO + "La tasca seleccionada no existeix" + ANSI_RESET);
                return;
            }
            if (lisaTarea[numeroTasca].isFinalizada()) {
                System.out.println(ANSI_ROJO + "No pots finalitzar una tasca ja finalitzada" + ANSI_RESET);
            } else {
                lisaTarea[numeroTasca].setFinalizada(true);
                if (lisaTarea[numeroTasca].isFinalizada()) {
                    System.out.println(ANSI_GREEN + "Tasca " + (numeroTasca + 1) + " finalitzada satisfactòriament" + ANSI_RESET);
                }
            }
        }
    }

    private void afegirTemps() {
        mostrarTareas(this.lisaTarea);
        if (lisaTarea[0] != null) {
            System.out.println("Introdueix el número de la tasca:");
            int numeroTasca = scanner.nextInt();
            numeroTasca -= 1;
            if (numeroTasca < 0 || numeroTasca > 20 || lisaTarea[numeroTasca] == null) {
                System.out.println(ANSI_ROJO + "La tasca seleccionada no existeix" + ANSI_RESET);
                return;
            }
            if (lisaTarea[numeroTasca].isFinalizada()) {
                System.out.println(ANSI_ROJO + "No pots afegir temps a una tasca ja finalitzada" + ANSI_RESET);
            } else {
                int temps = comprobarTiempo("Quant de temps vols afegir?:");
                lisaTarea[numeroTasca].sumarTiempo(temps);
            }
        }
    }

    private boolean listaLLena() {
        int num = 0;
        for (int i = 0; i < lisaTarea.length; i++) {
            if (lisaTarea[i] == null) {
                num++;
            }
        }
        if (num == 0) {
            return true;
        }
        return false;
    }

    private Prioridad pasarPrioridad(String text) {
        if (text.equals("Baixa")) {
            return Prioridad.BAIXA;
        } else if (text.equals("Mitja")) {
            return Prioridad.MITJA;
        }
        return Prioridad.ALTA;
    }

    private Tipos pasarTipos(String text) {
        if (text.equals("Hardware")) {
            return Tipos.HARDWARE;
        } else if (text.equals("Xarxa")) {
            return Tipos.XARXA;
        } else if (text.equals("Programació")) {
            return Tipos.PROGRAMACIO;
        } else if (text.equals("Base de dades")) {
            return Tipos.BASE_DE_DADES;
        }
        return Tipos.ALTRE;
    }



    public void mostrarTareas(Tasca[] lisaTarea) {
        if (lisaTarea[0] == null) {
            System.out.println(ANSI_ROJO + "No hi han tasques afegides" + ANSI_RESET);
        } else {
            for (int i = 0; i < lisaTarea.length; i++) {
                if (lisaTarea[i] == null) {
                    return;
                }
                System.out.println(ANSI_PURPLE + "Tasca " + (i + 1) + " => " + lisaTarea[i].toString() + ANSI_RESET);
            }
        }

    }
}
