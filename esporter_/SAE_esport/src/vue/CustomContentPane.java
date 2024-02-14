package vue;

import javax.swing.*;
import java.util.Observable;

/**
 * Classe CustomContentPane qui permet de créer un JPanel personnalisé qui hérite de JPanel et implémente Observer
 */
public class CustomContentPane extends JPanel implements Observer { // checked

	private static final long serialVersionUID = 1L;

    /**
     * instance de classe contenant le panel
     */
    private final Observer frame;

    /**
     * Constructeur de la classe
     * @param panel instance de la classe contenant le panel
     */
    public CustomContentPane(Observer panel) {
        super();
        this.frame = panel;
    }

    /**
     * Constructeur de la classe sans paramètre (quand on ne veut pas d'observer)
     */
    public CustomContentPane() {
        super();
        this.frame = null;
    }

    /**
     * Retourne l'instance de la classe contenant le panel et qui implémente Observer
     * @return frame
     */
    public Observer getFrame() {
        return frame;
    }

    /**
     * Permet de mettre à jour le contenu du panel via la méthode update de la classe implémentée dans la classe contenant le panel
     * @param o     the observable object.
     * @param arg   an argument passed to the <code>notifyObservers</code>
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        this.frame.update(o, arg);
    }

    // pas de clé d'observation dans cette classe, ce n'est pas une vue, elle contient la vue
    @Override
    @Deprecated
    public String[] getObservableKeys() {
        return null;
    }
}
