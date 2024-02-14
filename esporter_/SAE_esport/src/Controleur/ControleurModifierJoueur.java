package Controleur;

import Modele.Joueur;
import Modele.Pays;
import Modele.Poste;
import vue.APP;
import vue.pages.IconResized;
import vue.pages.ModifierJoueur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

public class ControleurModifierJoueur implements ActionListener, MouseListener {
	
	ModifierJoueur vue;

	Joueur joueur;
	
	public ControleurModifierJoueur(ModifierJoueur modifierJoueur, Joueur joueur) {
		this.vue = modifierJoueur;
		this.joueur = joueur;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o instanceof JButton) {
			JButton b = (JButton) e.getSource();
			switch(b.getText()) {
			case "Supprimer le joueur":
				int confirme = JOptionPane.showConfirmDialog(vue, "Etes-vous sur de vouloir supprimer " + joueur.getPseudo() + "?");
				if(confirme==0) {
					APP.resetTo(APP.getAncestorIndex());
					Joueur.delete(joueur.getId());
				}
				break;
			case "Annuler":
				APP.previous();
				break;
			case "Enregistrer":
				Joueur joueurUpdate = new Joueur(joueur.getId(),
						vue.getTheNom(),
						vue.getThePrenom(),
						vue.getThePseudo(),
						IconResized.getPath(vue.getLabelImage()),
						vue.getThePoste(),
						vue.getThePays(),
						Optional.of(joueur.getId_equipe()));
				Joueur.update(joueurUpdate);
				APP.previous();
				break;
			}
		}else if(o instanceof JComboBox) {
			@SuppressWarnings("rawtypes")
			JComboBox j = (JComboBox) e.getSource();
			if(j.getSelectedItem() instanceof Pays){
				vue.setFlag(vue.getThePays());
			}else if(j.getSelectedItem() instanceof Poste) {
				vue.setIconPoste((Poste)j.getSelectedItem());
			}
		}
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			JLabel l = (JLabel) e.getSource();
			l.setIcon(IconResized.of(IconResized.fileSelector(), IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
