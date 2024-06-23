package aed;

interface Iterador<T> {
    
    /**
     * Devuelve true si hay un elemento siguiente en la colección.
     * 
     */
    public boolean haySiguiente();
    
    /**
     * Devuelve true si hay un elemento anterior en la colección.
     * 
     */
    public boolean hayAnterior();

    /**
     * Devuelve el elemento siguiente en la colección y avanza el iterador.
     * 
     */
    public T siguiente();

    /**
     * Devuelve el elemento anterior en la colección y retrocede el iterador.
     * 
     */
    public T anterior();

    /**
     * Devuelve el String asociado a la posición actual (solo usado en la clase Trie).
     * 
     */
    public String nombreActual();

}
