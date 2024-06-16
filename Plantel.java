package aed;
public class Plantel {

    // Atributo que representa la cantidad de docentes de cada tipo
    private int[] docentes;

    // Constructor
    public Plantel(int AY2, int AY1, int JTP, int Prof) {
        this.docentes = new int[4];
        this.docentes[0] = AY2; // Ayudantes de segunda
        this.docentes[1] = AY1; // Ayudantes de primera
        this.docentes[2] = JTP; // Jefes de trabajos prácticos
        this.docentes[3] = Prof; // Profesores
    }

    // Método para calcular el cupo
    public int cupo() {
        return (docentes[0] * 30) + (docentes[1] * 20) + (docentes[2] * 100) + (docentes[3] * 250);
    }
}
