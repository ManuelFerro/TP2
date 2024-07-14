package aed;
import aed.SistemaSIU.conjuntoMaterias;

// clase Materia. Abstrae el concepto de una materia de la facultad, con
// su plantel docente (que define su cupo), los alumnos inscriptos a la misma y
// el diccionario de todos sus nombres y en qué carrera están

public class Materia {
    private Plantel plantel;
    private ListaEnlazada<String> estudiantesAnotados;
    private Trie<ListaEnlazada<conjuntoMaterias>> nombresMateria;
    // sus valores son listas de conjuntoMaterias, diccionarios que representan el conjunto de materias de una carrera
    // están en listas enlazadas para permitir que varias carreras se refieran a una materia con el mismo nombre,
    // agregando o quitando mediante punteros, lo que mantiene una complejidad de O(1)

    // constructor de Materia vacía
    public Materia(){
        estudiantesAnotados = new ListaEnlazada<>();
        nombresMateria = new Trie<ListaEnlazada<conjuntoMaterias>>();
        plantel = new Plantel();
    }

    public ListaEnlazada<String> estudiantesAnotados(){
        return estudiantesAnotados;
    }

    public Trie<ListaEnlazada<conjuntoMaterias>> nombresMateria(){
        return nombresMateria;
    }

    public Plantel plantel(){
        return plantel;
    }
}

