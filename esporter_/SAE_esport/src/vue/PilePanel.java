package vue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Classe PilePanel
 * Implemente l'interface PileGenerique
 * Represente une pile de CustomContentPane
 * @see PileGenerique
 */
public class PilePanel implements PileGenerique<CustomContentPane>{

    /**
     * Pile de CustomContentPane
     */
    private ArrayList<CustomContentPane> pile;

    /**
     * Constructeur de la classe PilePanel
     */
    public PilePanel() {
        this.pile = new ArrayList<CustomContentPane>();
    }

    /**
     * Iterateur de la pile
     */
    protected class IteratorPile implements Iterator<CustomContentPane> {
        private int index = -1;

        /**
         * Teste si la pile a un CustomContentPane suivant
         * @return vrai si la pile a un CustomContentPane suivant, faux sinon
         */
        public boolean hasNext() {
            return index < pile.size()-1;
        }

        /**
         * Retourne le CustomContentPane suivant de la pile
         * @return CustomContentPane suivant de la pile
         */
        public CustomContentPane next() {
            index++;
            return pile.get(index);
        }
    }

    /**
     * Empile un CustomContentPane dans la pile
     * @param element CustomContentPane a empiler
     */
    @Override
    public void empiler(CustomContentPane element) {
        this.pile.add(this.taille(), element);
    }

    /**
     * Cache le CustomContentPane au sommet de la pile et le retire de la pile
     * @return CustomContentPane depile
     */
    @Override
    public CustomContentPane depiler() {
        CustomContentPane sommet = this.sommet();
        this.pile.remove(sommet);
        return sommet;
    }

    /**
     * Retourne le CustomContentPane au sommet de la pile
     * @return CustomContentPane au sommet de la pile
     */
    @Override
    public CustomContentPane sommet() {
        return this.pile.get(this.taille() - 1);
    }

    /**
     * Teste si la pile est vide
     * @return vrai si la pile est vide, faux sinon
     */
    @Override
    public boolean estVide() {
        return pile.isEmpty();
    }

    /**
     * Retourne la taille de la pile
     * @return taille de la pile
     */
    @Override
    public int taille() {
        return this.pile.size();
    }

    /**
     * Vide la pile
     */
    @Override
    public void vider() {
        this.pile.clear();
    }

    public int getAncestorIndex() {
        return this.taille() - 2;
    }

    public void resetToRoot() {
        while (this.taille() > 1) {
            this.depiler();
        }
    }

    public void resetTo(int index) {
        while (this.taille() > index) {
            this.depiler();
        }
    }

    public IteratorPile iterator() {
        return new IteratorPile();
    }
}
