package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Modele.Equipe;
import Modele.Match;
import Modele.Saison;
import Modele.Tournoi;
import vue.APP;
import vue.pages.*;

public class ControleurGestionSaison implements ActionListener, MouseListener, ItemListener{

	private GestionSaison vue;

	public boolean ignoreCombo;
	
	public ControleurGestionSaison(GestionSaison vue){
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JButton) {
			JButton b = (JButton) o;
			switch(b.getText()) {
				case "Liste des arbitres":
					APP.next(new ListeArbitre().getContentPane());
					break;
				case "Ajouter un tournoi":
					APP.next(new AjoutTournoi().getContentPane());
					break;
				case "Impression des résultats":
					PrintRectangle.impression(vue.saison);
					break;
				case "Supprimer":
					int verif = JOptionPane.showConfirmDialog(vue, "Etes-vous sur de vouloir supprimer la saison " + vue.saison.getNom() + " (" + vue.saison.getAnnee() + ") ?\nToutes les données importantes seront archivées et les autres supprimées.");
					if(verif == 0) {
						Saison.delete(vue.saison.getAnnee());
						APP.resetTo(APP.INDEX_USER_ROOT);
					}
					break;
				case "Quitter":
					APP.resetTo(APP.INDEX_USER_ROOT);
					break;
				default:
					break;
				}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		 if (o instanceof JTable) {
				JTable t = (JTable) o;
				switch (t.getName()) {
				case "tableTournoi":
					if (e.getClickCount() == 2) {
						Integer idTournoi = (Integer) t.getValueAt(t.getSelectedRow(), 5);
						APP.next(new GestionTournoi(Tournoi.getById(idTournoi)).getContentPane());
					}
					break;
				case "tableMatch":
					if (e.getClickCount() == 2) {
						Integer idMatch = (Integer) t.getValueAt(t.getSelectedRow(), 5);
						try {
							APP.next(new GestionMatch(Match.getById(idMatch)).getContentPane());
						} catch (SQLException ex) {
		        			System.err.println("failed to open GestionMatch");
		                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de gestion du match");
						}
					}
					break;
				case "Scoreboard":
					if (e.getClickCount() == 2) {
						Integer idEquipe = (Integer) t.getValueAt(t.getSelectedRow(), 3);
						ListeEquipe pageListe = new ListeEquipe(); // TODO : voir pour tej page Liste
						try {
							APP.next(new GestionEquipe(Equipe.getById(idEquipe), pageListe).getContentPane());
						} catch (SQLException ex) {
		        			System.err.println("failed to open GestionEquipe");
		                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de gestion de l'équipe");
						}
					}
					break;
				}
			}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	
}
