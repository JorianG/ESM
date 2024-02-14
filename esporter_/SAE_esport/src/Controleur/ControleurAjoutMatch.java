package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import Modele.CustomDateTime;
import Modele.Match;
import Modele.TypeMatch;
import vue.APP;
import vue.pages.AjoutMatch;

public class ControleurAjoutMatch implements ActionListener{
	
	AjoutMatch vue;

	
	public ControleurAjoutMatch(AjoutMatch ajoutMatch) throws SQLException {
		this.vue = ajoutMatch;

	}
	
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JComboBox) {
			@SuppressWarnings("rawtypes")
			JComboBox c = (JComboBox) e.getSource();
			switch(c.getName()) {
			case "comboBoxMois":
				vue.remplirComboBoxJour();
				break;
			case "comboBoxTeam1":
				if (vue.getEquipe1() != null) {
					vue.setLogoEquipe1(vue.getEquipe1());
				}
				break;
			case "comboBoxTeam2":
				if (vue.getEquipe2() != null) {
					vue.setLogoEquipe2(vue.getEquipe2());
				}
				break;
			}
		}else if(o instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if (b.getText() == "Ajouter") {
				CustomDateTime dateDebutMatch = new CustomDateTime(vue.getTournoi().getDateDebut().getYear(),
						vue.getMois(), 
						vue.getJour(), 
						vue.getHeure(), 
						vue.getMinute());
				CustomDateTime dateDebutTournoi = new CustomDateTime(vue.getTournoi().getDateDebut());
				CustomDateTime dateFinTournoi = new CustomDateTime(vue.getTournoi().getDateFin());
				if (!dateDebutMatch.isBetween(dateDebutTournoi, dateFinTournoi)) {
					JOptionPane.showMessageDialog(vue, "La date du match n'est pas comprise dans la période du tournoi");
				}else {
					Boolean haveAlreadyAMatch = false;
					for (Match m : vue.getTournoi().getListMatchs()) {
						if (m.getDateDebutMatch().equals(dateDebutMatch)) {
							haveAlreadyAMatch = true;
						}
					}
					if(haveAlreadyAMatch) {
						JOptionPane.showMessageDialog(vue, "Il y a déjà un match à cette date");
					}
				}
				Match match = new Match( dateDebutMatch,
						vue.getEquipe1(), vue.getEquipe2(), vue.getArbitre(), 
						vue.getTournoi(), TypeMatch.POOL);
				APP.previous();
			}else if (b.getText() == "Annuler") {
				APP.previous();
			}
		}
		if (vue.getEquipe1() != null && vue.getEquipe2() != null 
				&& vue.getEquipe1() != vue.getEquipe2() && vue.getArbitre() != null) {
			vue.setEnabledButtonAjouter(true);
		} else {
			vue.setEnabledButtonAjouter(false);
		}
	}
}
