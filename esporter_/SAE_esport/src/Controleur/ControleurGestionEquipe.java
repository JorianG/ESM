package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Modele.Joueur;
import Modele.Tournoi;
import vue.APP;
import vue.pages.GestionEquipe;
import vue.pages.GestionJoueur;
import vue.pages.GestionTournoi;
import vue.pages.ListeEquipe;
import vue.pages.ModifierEquipe;

public class ControleurGestionEquipe implements ActionListener, MouseListener {
	
	private GestionEquipe vue;
	
	public ControleurGestionEquipe(GestionEquipe vue) {
		this.vue = vue;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object b = e.getSource();
		 if (b instanceof JButton) {
			JButton btn = (JButton) b;
			switch(btn.getText()) {
			case("Modifier l'équipe"):
				try {
					APP.next(new ModifierEquipe(vue.getEquipe(), this.vue.getVueListeEquipe()).getContentPane());
				} catch (SQLException e1) {
        			System.err.println("failed to open ModifierEquipe");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de modification de l'équipe");
				}
				break;
			case("Quitter"):
				APP.previous();
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// if table clicked
		if (e.getSource() instanceof JTable) {
			JTable t = (JTable) e.getSource();
			switch(t.getName()) {
				case "tableJoueur":
					if (e.getClickCount() == 2) {
						int row = vue.getListeJoueur().getSelectedRow();
						// id = id joueur select
						int id = (int) vue.getListeJoueur().getModel().getValueAt(row, 3);
						try {
							APP.next(new GestionJoueur(Objects.requireNonNull(Joueur.getById(id)), new ListeEquipe()).getContentPane());
						} catch (SQLException e1) {
		        			System.err.println("failed to open GestionJoueur");
		                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de gestion du joueur");
						}
				}
				break;
				case "tableTournoi":
					if (e.getClickCount() == 2) {
						int row = vue.getListeTournoi().getSelectedRow();
						//id = id tournoi select
						int id = (int) vue.getListeTournoi().getModel().getValueAt(row, 3);
						APP.next(new GestionTournoi(Tournoi.getById(id)).getContentPane());
					}
					break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
