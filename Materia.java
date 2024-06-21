package aed;
import aed.SistemaSIU.materias_c;

public class Materia {
    private ListaEnlazada<String> estudiantesAnotados;
    private Trie<materias_c> nombresMateria;
    private Plantel plantel;

    // constructor de Materia vacía
    public Materia(){
        estudiantesAnotados = new ListaEnlazada<>();
        nombresMateria = new Trie<materias_c>();
        plantel = new Plantel(0, 0, 0, 0);
    }
}

