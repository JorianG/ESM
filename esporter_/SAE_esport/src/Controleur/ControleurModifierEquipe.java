
package Controleur;

import Modele.Equipe;
import Modele.Joueur;
import Modele.Pays;
import vue.APP;
import vue.pages.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class ControleurModifierEquipe implements ActionListener, MouseListener, ItemListener {

	/**
	 * La vue de la page
	 */
	private ModifierEquipe vue;

	/**
	 * La vue de la page ListeEquipe
	 */
	private ListeEquipe vueListeEquipe;


	/**
	 * Boolean permettant de savoir si on doit ignorer l'event de click de la combobox
	 */
	public Boolean ignoreJcomboBox = false;

	/**
	 * Constructeur de la classe {@code ControleurModifierEquipe}
	 * @param vue La vue de la page
	 * @param vueListeEquipe La vue de la page ListeEquipe
	 * @throws SQLException
	 */
	public ControleurModifierEquipe(ModifierEquipe vue, ListeEquipe vueListeEquipe) throws SQLException { // TODO gerer  le nombre de joueur
		this.vue = vue;
		this.vueListeEquipe = vueListeEquipe;

	}
	//TODO
	@Override
	public void actionPerformed(ActionEvent e) {
		Object b = e.getSource();
		if (b instanceof JButton) {
			JButton btn = (JButton) b;
			switch(btn.getText()) {
			//Valider les modifications de l'équipe en vue et fermer la feneêtre
			case ("Enregistrer"):
				System.out.println(vue.getNbPlayer());
				if (vue.getNbPlayer() == 5) {
					Equipe newEquipe = vue.getEquipe();
					newEquipe.setNom(vue.getNewNom());
					newEquipe.setCode(vue.getNewCode());
					newEquipe.setPays(vue.getNewPays());
					newEquipe.setImage(IconResized.getPath(vue.getLabelImage()));
					Equipe.update(newEquipe);
					APP.previous();
				} else {
					JOptionPane.showMessageDialog(vue, "Une équipe doit avoir 5 joueurs");
				}
				break;
			//Ouvre la page de création de joueur
			case ("+"):
                try {
                    APP.next(new AjoutJoueur().getContentPane());
                } catch (SQLException ex) {
        			System.err.println("failed to open AjoutJoueur");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page d'ajout d'un joueur");
                }
                break;
			default:
				break;
			}
		} else if (b instanceof JComboBox) {
			@SuppressWarnings("rawtypes")
			JComboBox c = (JComboBox) b;
			if (c.getSelectedItem() instanceof Pays) {
				vue.setFlag(vue.getNewPays());
			}
			
			
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

		Object o = e.getSource();
		IconResized icon = IconResized.of(IconResized.LOGOUT);
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (e.getSource() instanceof JTable) {
				JTable t = (JTable) o;
				Joueur j = Joueur.getById((Integer) t.getModel().getValueAt(t.getSelectedRow(), 3));
				switch(t.getSelectedColumn()) {
					case 0:
                    case 1:
                        try {
                            APP.next(new GestionJoueur(j, this.vueListeEquipe).getContentPane());
                        } catch (SQLException ex) {
                			System.err.println("failed to open GestionJoueur");
                        	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de gestion du joueur");
                        }
						break;
                    case 2:
						if (APP.SELECTEDYEAR != LocalDate.now().getYear()) {
							int confirme = JOptionPane.showConfirmDialog(vue ,"Etes-vous sur de vouloir retirer le joueur " + j.getNom() + " de l'équipe ?", "Confirmation", 0, 2, icon.resize(50, 50));
							if (confirme == 0) {
								j.setId_equipe(Optional.ofNullable(null));
								Joueur.update(j);
								Equipe eq = this.vue.getEquipe();
								eq.remJoueur(j);

								//this.vue.getModeleTable().removeRow(t.getSelectedRow());
								GestionEquipe.removeJoueurFromTable(j);
								Equipe.update(eq);
							}
						}
						JOptionPane.showConfirmDialog(vue ,"Vous ne pouvez pas modifier la composition d'une équipe engagée dans la saison en cours.", "Confirmation", 0, 2, icon.resize(50, 50));
						break;
					default:
						break;
				}
			} else if (o instanceof JButton) {
				JButton b = (JButton) o;
				if (b.getText() == "Supprimer l'équipe") {
					int del = JOptionPane.showConfirmDialog(vue, "Etes-vous sur de vouloir supprimer l'équipe " + vue.getEquipe().getNom() + " ?", "Confirmation", 0, 0, icon.resize(50, 50));
					if (del == 0) {
						for(Joueur j : vue.getEquipe().getJoueurs()) {
							j.setId_equipe(Optional.empty());
							Joueur.update(j);
						}

						APP.resetTo(APP.getAncestorIndex());
						Equipe.delete(vue.getEquipe().getId());
						//this.vueListeEquipe.getModeleTable().removeRow(this.vueListeEquipe.listeJoueur.getSelectedRow()); // TODO : changer la maj de la liste
					}
				} else if (b.getText() == "Annuler") {
					APP.previous();
				}
			} else if (o instanceof JLabel) {
				JLabel l = (JLabel) o;
				l.setIcon(IconResized.of(IconResized.fileSelector(), IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
			}else if (o instanceof JComboBox) {
				//this.vue.refreshComboBoxJoueur();
			}
			
		}
		}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void itemStateChanged(ItemEvent e) {
		//Select joueur a ajouter dans l'equipe en vue
		if(!this.ignoreJcomboBox) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Equipe eq = this.vue.getEquipe();
				Joueur j = this.vue.getSelectedJoueur();
				eq.addJoueur(j);
				//System.out.println(" Combo box joueur select :"+ j.getFullName());
				this.vue.setEquipe(eq);
				j.setId_equipe(Optional.of(eq.getId()));
				Joueur.update(j);
				GestionEquipe.addJoueurToTable(j);
			}
		}
	}
}
