package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Modele.CustomDateTime;
import Modele.Match;
import Modele.Tournoi;
import vue.APP;
import vue.pages.GestionMatch;
import vue.pages.GestionTournoi;

public class ControleurGestionMatch implements ActionListener, ChangeListener, MouseListener{
	
	GestionMatch vue;

	Boolean isArbitre;
	Match match;
	
	public ControleurGestionMatch(GestionMatch gestionMatch, Match match) throws SQLException {
		this.vue = gestionMatch;
		this.match = match;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (APP.isArbitre) {
			vue.affichageArbitre();
		}else {
			vue.affichageOther();
		}
		Object o = e.getSource();
		if (vue.verif()) {
			vue.setEnabledTerminer(true);
		}
		if (o instanceof JButton) {
			JButton b = (JButton) e.getSource();
			switch (b.getText()) {
			case "Supprimer le match":
				int confirme = JOptionPane.showConfirmDialog(vue, "Etes-vous sur de vouloir supprimer le match entre " +
						match.getEquipe1().getNom() + " et " + match.getEquipe2().getNom() + "?");
				if(confirme==0) {
					Match.delete(match.getId());
					APP.previous();
				}
				break;
			case "Terminer le match":
				CustomDateTime dateCourante = new CustomDateTime(LocalDateTime.now());
				if ((match.getDateFinMatch().minus(dateCourante)).isBefore(new CustomDateTime(0,0,0,0,55))) {
					match.setDateFinMatch(dateCourante);
				}
				if (vue.getScoreEquipe1() == 1) {
					match.setVictorious(match.getEquipe1());
				}else if (vue.getScoreEquipe2() == 1) {
					match.setVictorious(match.getEquipe2());
				}
                //match.modClassementTournoi(); // todo verif avec observer
				APP.previous();
				break;
			case "Quitter":
				APP.previous();
				break;
			}
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object o = e.getSource();
		if (vue.verif()) {
			vue.setEnabledTerminer(true);
		}
		if (o instanceof JSpinner) {
			JSpinner s = (JSpinner) e.getSource();
			switch(s.getName()) {
			case "spinner1":
				if (vue.getScoreEquipe1() == 0) {
					vue.setScoreEquipe2(1);
				}else {
					vue.setScoreEquipe2(0);
				}
				break;
			case "spinner2":
				if (vue.getScoreEquipe2() == 0) {
					vue.setScoreEquipe1(1);
				}else {
					vue.setScoreEquipe1(0);
				}
				break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (vue.verif()) {
			vue.setEnabledTerminer(true);
		}
		if (e.getSource() instanceof JTable) {
			if (e.getClickCount() == 2) {
				JTable table = (JTable) e.getSource();
				if (table.getName() == "table1") {
					int row = vue.getTableTournoiTeam1().getSelectedRow();
					//id = id tournoi select
					int id = (int) vue.getTableTournoiTeam1().getModel().getValueAt(row, 2);
                    APP.next(new GestionTournoi(Tournoi.getById(id)).getContentPane());
				}else if (table.getName() == "table2") {
					int row = vue.getTableTournoiTeam2().getSelectedRow();
					//id = id tournoi select
					int id = (int) vue.getTableTournoiTeam2().getModel().getValueAt(row, 2);
					APP.next(new GestionTournoi(Tournoi.getById(id)).getContentPane());
				}
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
