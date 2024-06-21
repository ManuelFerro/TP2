package aed;

public class Plantel {

    // atributo que representa la cantidad de docentes de cada tipo
    private int[] docentes;

    // constructor del plantel con datos de entrada
    public Plantel(int cantidadAY2, int cantidadAY1, int cantidadJTP, int cantidadProf) {
        docentes = new int[4];
        docentes[0] = cantidadAY2; // ayudantes de segunda
        docentes[1] = cantidadAY1; // ayudantes de primera
        docentes[2] = cantidadJTP; // jefes de trabajos prácticos
        docentes[3] = cantidadProf; // profesores
    }

    // método para calcular el cupo
    public int cupo() {
        return (docentes[0] * 30) + (docentes[1] * 20) + (docentes[2] * 100) + (docentes[3] * 250);
    }
}
