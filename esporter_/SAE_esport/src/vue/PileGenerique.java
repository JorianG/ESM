package vue;

public interface PileGenerique<T> {

    public void empiler(T element);

    public T depiler();

    T sommet();

    public boolean estVide();

    public int taille();

    public void vider();

}
