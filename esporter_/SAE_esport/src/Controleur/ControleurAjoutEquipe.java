package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import Modele.Equipe;
import Modele.Joueur;
import Modele.Pays;
import Modele.Poste;
import vue.APP;
import vue.pages.*;

public class ControleurAjoutEquipe implements ActionListener, MouseListener, ItemListener {
	
	AjoutEquipe vue;
	public List<Joueur> lstJoueur = new ArrayList<Joueur> ();
	
	public Boolean ignoreJcomboBox = false;
	

	public ControleurAjoutEquipe(AjoutEquipe ajoutEquipe) throws SQLException {
		this.vue = ajoutEquipe;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		verif();
		Object b = e.getSource();
		if (b instanceof JComboBox) {
			@SuppressWarnings("rawtypes")
			JComboBox combo = (JComboBox) b;
			if (combo.getSelectedItem() instanceof Pays) {
				vue.setFlag(((Pays) combo.getSelectedItem()));
			}
		} else if (b instanceof JButton) { 
			JButton button = (JButton) b;
			if (button.getText() == "+") {
                try {
                    APP.next(new AjoutJoueur().getContentPane());
                } catch (SQLException ex) {
        			System.err.println("failed to open AjoutJoueur");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page d'ajout de joueur");
                }
            } else if (button.getText() == "Ajouter") {
				new Equipe(vue.getNom(), vue.getCode(), IconResized.getPath(vue.getLabelImage()), vue.getPays(), vue.getRang());
				for(Joueur j : this.lstJoueur) {
					j.setId_equipe(Optional.of(Equipe.getLastId()));
					Joueur.update(j);
				}
				if (vue.isFromAjoutSaison()) AjoutSaison.refreshComboBoxEquipe();
				APP.previous();
			} else if (Objects.equals(button.getText(), "Annuler")) {
				APP.previous();
            }else if (button.getText() == "Refresh") {
				System.out.println("test");
			}
		}
		verif();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		verif();
		if (e.getSource() instanceof JLabel) {
			JLabel l = (JLabel) e.getSource();
			l.setIcon(IconResized.of(IconResized.fileSelector()).resize(IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
		}else if (e.getSource() instanceof JTable) {
			if(e.getClickCount()>=2) {
				//RETIRER JOUEUR DE LA TABLE -> DANS LA COMBO
				JTable j= (JTable) e.getSource();
				if (j.getSelectedColumn()==2) {
					int idJoueur = (int) j.getValueAt(j.getSelectedRow(), 3);
					Joueur remJ = null;
					for(Joueur joueur : this.lstJoueur) {
						if(joueur.getId() == idJoueur) {
							remJ = joueur;
						}
					}
					this.lstJoueur.remove(remJ);
					this.vue.lstJoueurSansEquipe.add(remJ);

					this.vue.getModeleTable().removeRow(j.getSelectedRow());
					this.vue.refreshComboBoxJoueur();
				}
			}
		}
		verif();
	}
	

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		verif();
		Object o = e.getSource();
		if(o instanceof JComboBox) {
			//get les joueurs sans equipe n'etant ni dans lstJoueur ni dans lstJoueurSansEquipe
			for(Joueur j : Objects.requireNonNull(Joueur.getAllSansEquipe())) {
				if(!this.lstJoueur.contains(j) && !this.vue.lstJoueurSansEquipe.contains(j)) {
					System.out.println(this.vue.lstJoueurSansEquipe.contains(j));
					this.vue.lstJoueurSansEquipe.add(j);
				}
			}
			this.vue.refreshComboBoxJoueur();
		}
		verif();
	}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	public void itemStateChanged(ItemEvent e) {
		verif();
		//Select joueur a ajouter dans l'equipe en vue
		if(!this.ignoreJcomboBox) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				//ajouter un joueur depuis le comboBox
				Joueur j = this.vue.getSelectedJoueur();
				//enlever de j sans equipe
				this.vue.lstJoueurSansEquipe.remove(j);
				//ajouter dans l'equipe
				this.lstJoueur.add(j);
				this.vue.refreshComboBoxJoueur();
				this.vue.getModeleTable().addRow(new Object[] {j.getImage(), j.getFullName(), "trash", j.getId()});
				
			}
		}
		verif();
	}
	
	private void verif() {
		if (this.vue.getNom().length() > 3 && this.vue.getCode().length() > 0 && this.vue.getRang() >= 1000 && this.vue.getPays() != null && this.lstJoueur.size() >= 5 && this.isAllPosteOK()) {
			this.vue.setButtonAjouter(true);
		} else {
			this.vue.setButtonAjouter(false);
		}
	}

	private boolean isAllPosteOK() {
		for (Poste p : Poste.values()) {
			boolean isPoste = false;
			for (Joueur j : this.lstJoueur) {
				if (j.getPoste() == p) {
					isPoste = true;
				}
			}
			if (!isPoste) {
				return false;
			}
		}
		return true;
	}

}
