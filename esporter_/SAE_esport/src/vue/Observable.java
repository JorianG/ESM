package vue;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe Observable
 * @see java.util.Observable
 */
public class Observable extends java.util.Observable { // checked

    /**
     * set d'observers de la classe
     */
    private static Set<Observer> obs;
    private static Observable obv;

    /**
     * Constructeur de la classe
     */
    public Observable() {
        obs = new HashSet<>();
        obv = this;
    }

    /**
     * Retourne l'instance du set d'observers de la classe ou en crée une si elle n'existe pas (singleton)
     * @return obs
     */
    public static synchronized Set<Observer> getObsInstance() {
        if (obs == null) {
            obs = new HashSet<>();
        }
        return obs;
    }

    /**
     * Permet d'attacher un ou plusieurs observers à la classe observable, on attache uniquement les observers qui sont nécessaires
     * @param keys Clés d'observation concerné par l'observable (Arbitre, Equipe, etc.)
     */
    public static void attach(String... keys) {
        for (Observer o: APP.getPanel(keys)) {
            attach(o);
        }
    }

    /**
     * Permet d'attacher un observer à la classe observable
     * @param o Observer à attacher
     */
    public static void attach(Observer o) {
        obs.add(o);
    }

    /**
     * Permet de détacher un observer de la classe observable
     * @param o Observer à détacher
     */
    public static void detach(Observer o) {
        obs.remove(o);
    }

    /**
     * Permet de notifier les observers de la classe observable
     */
    public static void notifyStaticObservers() {
        for (Observer o: obs) {
            o.update(obv, null);
        }
    }
}
