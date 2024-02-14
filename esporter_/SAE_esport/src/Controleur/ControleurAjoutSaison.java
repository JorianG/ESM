package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Modele.Arbitre;
import Modele.Equipe;
import Modele.Saison;
import vue.APP;
import vue.pages.*;

public class ControleurAjoutSaison implements ActionListener, MouseListener, ItemListener{

	private final AjoutSaison vue;

	private final HashMap<Equipe, Integer> lstEquipeSaison;

	private final Set<Arbitre> lstArbitreSaison;

	public boolean ignoreCombo;
	
	public ControleurAjoutSaison(AjoutSaison vue) {
		this.vue = vue;
		this.lstEquipeSaison = new HashMap<Equipe, Integer>();
		this.lstArbitreSaison = new HashSet<Arbitre>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JButton) {
			JButton b = (JButton) o;
			switch(b.getText()) {
				case "Annuler":
					APP.previous();
					break;
				case "Valider":
					Saison s = new Saison(APP.SELECTEDYEAR, vue.getNom(), IconResized.getPath(vue.getLabelImage()), this.lstArbitreSaison, this.lstEquipeSaison, false, true);
					APP.next(new GestionSaison(s).getContentPane());
					break;
				case "+":
					System.out.println(b.getName());
					if (Objects.equals(b.getName(), "btnCreerEquipe")) {
                        try {
							// le rafraichissement se fait dans le contrôleur de AjoutEquipe à l'aide du booléen isFromAjoutSaison
							// et de la méthode static refreshComboBoxEquipe
							APP.next(new AjoutEquipe(true, false).getContentPane()); // TODO changer par des test avec getAncestor
                        } catch (SQLException ex) {
                			System.err.println("failed to open AjoutEquipe");
                        	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page d'ajout d'équipe");
                        }
                    } else if (Objects.equals(b.getName(), "btnCreerArbitre")) {
                        try {
							// le rafraichissement se fait dans le contrôleur de AjoutArbitre à l'aide du booléen isFromAjoutSaison
							// et de la méthode static refreshComboBoxArbitre
							APP.next(new AjoutArbitre(true).getContentPane());
                        } catch (SQLException ex) {
                			System.err.println("failed to open AjoutArbitre");
                        	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page d'ajout d'arbitre");
                        }
                    }
					break;
				default:
					break;
			}
		}
		vue.verif();
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) { // checked
		if(!this.ignoreCombo){
			if(e.getStateChange() == ItemEvent.SELECTED){
				@SuppressWarnings("rawtypes")
				JComboBox j = (JComboBox) e.getSource();
				if (Objects.equals(j.getName(), "Arbitre")){
					Arbitre a = (Arbitre) j.getSelectedItem();
					this.lstArbitreSaison.add(a);
					this.vue.refreshTableArbitre(this.getArbitres());
					AjoutSaison.refreshComboBoxArbitre();
				} else if (Objects.equals(j.getName(), "Equipe")) {
					Equipe eq = (Equipe) j.getSelectedItem();
					this.lstEquipeSaison.put(eq, 0);
					this.vue.refreshTableEquipe(this.getEquipes());
					AjoutSaison.refreshComboBoxEquipe();
				}
			}
		}
		vue.verif();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		 if (o instanceof JTable) {
			 JTable t = (JTable) o;
			 if (t.getName() == "TbleArbitre") {
				 if (t.getSelectedColumn() == 2 && e.getClickCount() == 2) {
					 //remove arbitre
					Arbitre a = Arbitre.getById((Integer) t.getValueAt(t.getSelectedRow(), 3));
					this.lstArbitreSaison.remove(a);

					AjoutSaison.refreshComboBoxArbitre();
					this.vue.refreshTableArbitre(this.getArbitres());
				}

			 } else if (t.getName() == "TbleEquipe") {

				if (t.getSelectedColumn() == 2 && e.getClickCount() == 2) {
					Equipe eq = Equipe.getById((Integer) t.getValueAt(t.getSelectedRow(), 3));
					this.lstEquipeSaison.remove(eq);

					AjoutSaison.refreshComboBoxEquipe();
					this.vue.refreshTableEquipe(this.getEquipes());

				}
			 }
		 } else if  (o instanceof JLabel) {
			 JLabel l = (JLabel) o;
			 l.setIcon(IconResized.of(IconResized.fileSelector(), IconResized.DEFAULT_WIDTH_IMAGE_MID, IconResized.DEFAULT_HEIGHT_IMAGE_MID));
		 }
		vue.verif();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object o = e.getSource();
		if (o instanceof JComboBox) {
			@SuppressWarnings("rawtypes")
			JComboBox j = (JComboBox) o;
			if (j.getName() == "Arbitre") {
				AjoutSaison.refreshComboBoxArbitre();
			} else if (j.getName() == "Equipe") {
				AjoutSaison.refreshComboBoxEquipe();
			}
		}
		vue.verif();
	}

	@Override
	public void mouseExited(MouseEvent e) {}

	public boolean SaisonContainsEquipe (Equipe e) {
		return this.lstEquipeSaison.containsKey(e);
	}

	public boolean SaisonContainsArbitre (Arbitre a) {
		return this.lstArbitreSaison.contains(a);
	}

	public List<Equipe> getEquipes() {
		return new ArrayList<>(this.lstEquipeSaison.keySet());
	}

	public List<Arbitre> getArbitres() {
		return new ArrayList<>(this.lstArbitreSaison);
	}

	public void refreshTableArbitre() {
		this.vue.getModeleTableAb().getDataVector().removeAllElements();
		for(Arbitre a : this.getArbitres()) {
			this.vue.getModeleTableAb().addRow(new Object[]{a.getImage(), a.getNom(), "trash", a.getId()});
		}
		this.vue.getTableArbitres().repaint();
	}

	public void refreshTableEquipe() {
		this.vue.getModeleTableEq().getDataVector().removeAllElements();
		for(Equipe e : this.getEquipes()) {
			this.vue.getModeleTableEq().addRow(new Object[] {e.getImage(), e.getNom(), "trash", e.getId()});
		}
		this.vue.getTableEquipes().repaint();
	}
}
