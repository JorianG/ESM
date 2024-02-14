package Controleur;

import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import Modele.Arbitre;
import vue.APP;
import vue.pages.*;

public class ControleurModifierArbitre extends MouseAdapter implements ActionListener {
	
	private ModifierArbitre vue;
	
	private Arbitre arbitre;
	

	
	
	public ControleurModifierArbitre(ModifierArbitre vue, Arbitre arbitre) throws SQLException {
		this.vue = vue;
		this.arbitre = arbitre;

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o instanceof JButton) {
			JButton jButton = (JButton) e.getSource();
			switch(jButton.getText()) {
				case "Enregistrer":
					if(vue.isTextFieldFill()) {
						arbitre.setNom(vue.getNomTextField());
						arbitre.setPrenom(vue.getPrenomTextField());
						arbitre.setImage(vue.getImage());
						Arbitre.update(arbitre);
						APP.previous();
					} else {
						JOptionPane.showMessageDialog(vue, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					break;
				case "Supprimer":
					APP.resetTo(APP.getAncestorIndex());
					Arbitre.delete(arbitre.getId());
					break;
				case "Annuler":
					APP.previous();
					break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o instanceof JLabel) {
			JLabel j = (JLabel) e.getSource();
			j.setIcon(IconResized.of(IconResized.fileSelector(), IconResized.DEFAULT_WIDTH_IMAGE_SMALL, IconResized.DEFAULT_HEIGHT_IMAGE_SMALL));
		}
	}
}
