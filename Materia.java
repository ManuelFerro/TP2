package aed;
import aed.SistemaSIU.conjuntoMaterias;

// clase Materia. Abstrae el concepto d euna materia de la facultad, con
// su plantel docente (que define su cupo), los alumnos inscriptos a la misma y
// el diccionario de todos sus nombres y en qué carrera están

public class Materia {
    private ListaEnlazada<String> estudiantesAnotados;
    private Trie<conjuntoMaterias> nombresMateria; // sus valores son conjuntoMaterias, un diccionario que representa el conjunto de materias de una carrera
    private Plantel plantel;

    // constructor de Materia vacía
    public Materia(){
        estudiantesAnotados = new ListaEnlazada<>();
        nombresMateria = new Trie<conjuntoMaterias>();
        plantel = new Plantel();
    }

    public ListaEnlazada<String> estudiantesAnotados(){
        return estudiantesAnotados;
    }

    public Trie<conjuntoMaterias> nombresMateria(){
        return nombresMateria;
    }

    public Plantel plantel(){
        return plantel;
    }
}

