package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

import javax.swing.*;

import Modele.Tournoi;
import vue.APP;
import vue.pages.IconResized;
import vue.pages.ModifierTournoi;

public class ControleurModifierTournoi implements ActionListener, MouseListener {
	
	ModifierTournoi vue;

	Tournoi tournoi;
	
	
	public ControleurModifierTournoi(ModifierTournoi modifierTournoi, Tournoi tournoi) {
		this.vue = modifierTournoi;
		this.tournoi = tournoi;

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JButton) {
			JButton b = (JButton) e.getSource();
			switch(b.getText()) {
			case "Enregistrer":
				if (!(LocalDate.of(tournoi.getSaison().getAnnee(), vue.getMoisFin(), vue.getJourFin()).isBefore(LocalDate.of(tournoi.getSaison().getAnnee(), vue.getMoisDebut(), vue.getJourDebut())))){
					tournoi.setNom(vue.getTextFieldNom());
					tournoi.setDateDebut(LocalDate.of(tournoi.getSaison().getAnnee(), vue.getMoisDebut(), vue.getJourDebut()));
					tournoi.setDateFin(LocalDate.of(tournoi.getSaison().getAnnee(), vue.getMoisFin(), vue.getJourFin()));
					tournoi.setImage(IconResized.getPath(vue.getLabelImage()));
					boolean superposees = false;
					Tournoi.update(tournoi);
					if (!superposees) {
						APP.previous();
					}
				}else {
					JOptionPane.showMessageDialog(vue, "La date de fin ne peut pas être avant la date de début");
				}
				break;
			case "Annuler le tournoi":
				int confirme = JOptionPane.showConfirmDialog(vue, "Etes-vous sur de vouloir annuler le tournoi " + tournoi.getNom());
				if(confirme==0) {
					APP.resetTo(APP.getAncestorIndex());
					Tournoi.delete(tournoi.getId());
				}
				break;
			case "Annuler":
				APP.previous();
				break;
			}
		}else if(o instanceof JComboBox) {
			@SuppressWarnings("unchecked")
			JComboBox<Integer> c = (JComboBox<Integer>) e.getSource();
			if (c.getItemCount() == 12) {
				if (c.getName() == "moisDeb") {
					vue.remplirComboBoxJourDebut();
				}else if (c.getName() == "moisFin"){
					vue.remplirComboBoxJourFin();
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o instanceof JLabel) {
			JLabel l = (JLabel) o;
			if (l.getName() == "Logo") {
				l.setIcon(IconResized.of(IconResized.fileSelector(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
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
