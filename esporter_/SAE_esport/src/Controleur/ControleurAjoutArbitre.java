package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import Modele.Arbitre;
import vue.APP;
import vue.pages.AjoutArbitre;
import vue.pages.AjoutSaison;
import vue.pages.IconResized;

public class ControleurAjoutArbitre extends MouseAdapter implements ActionListener{
	
	private AjoutArbitre vue;

	public ControleurAjoutArbitre (AjoutArbitre vue) {
		this.vue = vue;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o instanceof JButton) {
			JButton j = (JButton) e.getSource();
			switch(j.getText()) {
			case "Ajouter":
				if(vue.isTextFieldFill()) new Arbitre(vue.getNomTextField(), vue.getPrenomTextField(), IconResized.getPath(vue.getLabelImage()));
				if (vue.isFromAjoutSaison()) AjoutSaison.refreshComboBoxArbitre(); // todo observer pattern
				APP.previous();
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
