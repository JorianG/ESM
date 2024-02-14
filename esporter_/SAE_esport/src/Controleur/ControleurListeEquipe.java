package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Modele.Equipe;
import vue.APP;
import vue.pages.ListeEquipe;
import vue.pages.AjoutEquipe;
import vue.pages.GestionEquipe;
import Modele.Joueur;

public class ControleurListeEquipe extends MouseAdapter implements ActionListener {
	
	private ListeEquipe vue;

	public ControleurListeEquipe(ListeEquipe vue) {
		this.vue = vue;

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object t = e.getSource();
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() instanceof JTable) {
			JTable j = (JTable) t;
			Equipe equipe = Equipe.getById((Integer) j.getModel().getValueAt(j.getSelectedRow(), 4));
			switch(j.getSelectedColumn()) {
			case(1):
				
				break;
			case(2):
				//modif equipe
                try {
					APP.next(new GestionEquipe(equipe, this.vue).getContentPane());
                } catch (SQLException ex) {
        			System.err.println("failed to open GestionEquipe");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de gestion de l'équipe");
                }
                break;
			case(3):
				// DELETE EQUIPE
				int verif = 1;

				verif = JOptionPane.showConfirmDialog(APP.getInstance(),
						"Etes-vous sur de vouloir supprimer l'équipe " + equipe.getNom() + " ?");

				if(verif == 0) {
						for(Joueur joueur: Objects.requireNonNull(Joueur.getByEquipe(equipe))) {
							joueur.setId_equipe(Optional.empty());
							Joueur.update(joueur);
						}
						Equipe.delete(equipe.getId());
						//this.vue.getModeleTable().removeRow(this.vue.listeJoueur.getSelectedRow());
				}
				break;
			default:
				break;
			}	
		  }
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object b = e.getSource();
		if (b instanceof JTable) {
			
		} else if (b instanceof JButton) {
			JButton btn = (JButton) b;
			switch(btn.getText()) {
			case("Rechercher"):
				this.vue.setDataTableEquipe(Objects.requireNonNull(Equipe.getByNom(this.vue.getSearch())));
				break;
			case ("Ajouter"):
                try {
                    APP.next(new AjoutEquipe().getContentPane());
                } catch (SQLException ex) {
        			System.err.println("failed to open AjoutEquipe");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page d'ajout d'équipe");
                }
				break;
			case("Annuler"):
				APP.previous();
				break;
			default:
				break;
			}
		}
	}
	
	
}
