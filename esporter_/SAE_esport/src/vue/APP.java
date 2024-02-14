package vue;

import vue.pages.IconResized;
import vue.pages.PageConnexion;
import vue.theme.EsporterManagementTheme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class APP extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Pile des panels affichés dans l'application
     */
	private static final PilePanel pilePanel = new PilePanel();
    public static APP instance;
    public static boolean isArbitre = false;
    public static int SELECTEDYEAR = 0;
    public static final int INDEX_USER_ROOT = 2;

    // Initialisation de l'instance de l'application (Singleton)
    static {
        if (instance == null) {
            instance = new APP();
        }
    }

    /**
     * Constructeur de l'objet APP
     */
    private APP() {
        super("Esporter Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1200, 800);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(IconResized.LOGO_ESPORTER));
        this.setTitle("Esporter Management System");
        this.setLocationRelativeTo(null);
        this.setResizable(true);
    }

    /**
     * Retourne l'instance de l'application
     * @return instance
     */
    public static APP getInstance() {
        return instance;
    }

    /**
     * Permet de changer le contenu de la fenêtre
     * @param panel Panel à afficher
     */
    private void setContent(JPanel panel) {
        this.setContentPane(panel);
        this.revalidate();
    }

    /**
     * Permet d'empiler le prochain panel à afficher et de l'afficher
     * @param panel Panel à afficher
     */
    public static void next(JPanel panel) {
        pilePanel.empiler((CustomContentPane) panel);
        instance.setContent(panel);
    }

    /**
     * Permet de dépiler le panel actuel et d'afficher le précédent
     */
    public static void previous() {
        Observable.detach(pilePanel.sommet().getFrame());
        pilePanel.depiler();
        instance.setContent(pilePanel.sommet());
    }

    /**
     * Permet de revenir au panel racine (Page connexion)
     */
    public static void root() {
        pilePanel.resetToRoot();
        instance.setContent(pilePanel.sommet());
    }

    /**
     * Permet de revenir à un panel précis
     * @param index Index du panel à afficher
     */
    public static void resetTo(int index) {
        pilePanel.resetTo(index);
        instance.setContent(pilePanel.sommet());
    }

    /**
     * Permet de quitter l'application
     */
    public static void exit() {
        System.exit(0);
    }

    /**
     * Permet de récupérer un panel ou plusieurs panels dans la pile en fonction des clés d'observation affectés à chaque panel
     * et de la ou les clés passées en paramètre
     * @param nom Clés d'observation (Arbitre, Equipe, etc.)
     * @return ArrayList<Observer> Liste des panels correspondants aux clés passées en paramètre
     * @see Observer#getObservableKeys()
     * @see PilePanel
     * @see PilePanel.IteratorPile
     */
    public static ArrayList<Observer> getPanel(String... nom) { // checked
        ArrayList<Observer> panelList = new ArrayList<>();
        PilePanel.IteratorPile it = pilePanel.iterator();
        while (it.hasNext()) {
            Observer panel = it.next().getFrame();
            if (panel != null && Arrays.asList(panel.getObservableKeys()).stream().anyMatch(e -> {
                for (int i = 0; i < nom.length; i++) {
                    if (e.equals(nom[i])) {
                        return true;
                    }
                }
                return false;
            }) && !panelList.contains(panel)) {
                panelList.add(panel);
            }
        }
        return panelList;
    }

    /**
     * Permet de récupérer le panel actuel
     * @return le panel à l'index courant - 2
     */
    public static int getAncestorIndex() {
        return pilePanel.getAncestorIndex();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new EsporterManagementTheme());
                    APP frame = getInstance();
                    APP.next(new PageConnexion().getContentPane());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
