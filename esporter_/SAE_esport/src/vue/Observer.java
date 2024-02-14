package vue;

import java.util.Observable;

/**
 * Classe Observer
 */
public interface Observer extends java.util.Observer { // checked

    /**
     * Permet de mettre à jour le contenu du panel via la méthode update de la classe implémentée dans la classe contenant le panel
     * @param o     the observable object.
     * @param arg   an argument passed to the <code>notifyObservers</code>
     *                 method.
     */
    void update(Observable o, Object arg);

    /**
     * Retourne les clés d'observation de la classe
     * @return String[] Clés d'observation
     */
    String[] getObservableKeys();
}
