package Tasques.Tasca;

import Tasques.Tasca.Enum.Prioridad;
import Tasques.Tasca.Enum.Tipos;

public class Tasca {
    private String descripcio;
    private Tipos tipos;
    private Prioridad prioridad;
    private int tiempo;
    private boolean finalizada;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Tasca(String descripcio, Tipos tipos, int tiempo, Prioridad prioridad) {
        this.descripcio = descripcio;
        this.tipos = tipos;
        this.prioridad = prioridad;
        this.tiempo = tiempo;
        this.finalizada = false;
    }
    private String actualizarTarea(){
        if (finalizada){
            return "Finalizada";
        }
        return "Pendent";
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public void sumarTiempo(int tiempo) {
        this.tiempo += tiempo;
        System.out.println(ANSI_GREEN + "Temps afegit amb èxit" + ANSI_RESET);
    }

    @Override
    public String toString() {
        return descripcio + " a realitzar en " + tiempo + " minuts" +
                " [Prioritat " + prioridad + " (" + actualizarTarea() + ")";
    }
}
