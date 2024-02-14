package vue.pages;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Classe LimitJTextField qui permet de limiter la taille d'un champ
 */
class LimitJTextField extends PlainDocument
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * taille maximale du champ
     */
    private int max;

    /**
     * Constructeur de la classe
     * @param max
     */
    LimitJTextField(int max) {
        super();
        this.max = max;
    }

    /**
     * Permet de limiter la taille du champ
     */
    public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text == null)
            return;
        if ((getLength() + text.length()) <= max) {
            super.insertString(offset, text, attr);
        }
    }
}
