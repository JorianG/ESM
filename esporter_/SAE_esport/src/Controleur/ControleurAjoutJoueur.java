package Controleur;


import Modele.Joueur;
import Modele.Pays;
import Modele.Poste;
import connect.DBConnect;
import vue.APP;
import vue.pages.AjoutJoueur;
import vue.pages.IconResized;
import vue.pages.PopupChampsRequis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class ControleurAjoutJoueur implements MouseListener, ActionListener {
    AjoutJoueur ajoutJoueur;

    public ControleurAjoutJoueur(DBConnect db, AjoutJoueur ajoutJoueur) throws SQLException {
        this.ajoutJoueur = ajoutJoueur;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if (Objects.equals(b.getText(), "Ajouter")) {
				if (ajoutJoueur.allFieldFill()) {
					new Joueur(
					        ajoutJoueur.getNom(),
					        ajoutJoueur.getPrenom(),
					        ajoutJoueur.getPseudo(),
					        IconResized.getPath(ajoutJoueur.getLabelImage()),
					        ajoutJoueur.getPoste(),
					        ajoutJoueur.getNationalite(),
							Optional.empty());
					System.out.println("ajout joueur ok");
					APP.previous();
				} else {
					System.out.println("Erreur all field required");
					PopupChampsRequis popup = new PopupChampsRequis();
					popup.setVisible(true);
					popup.setLocationRelativeTo(null);
					
				}
	        } else if (Objects.equals(b.getText(), "Annuler")) {
	        	APP.previous();
	        }
		}  else {
			JLabel l = (JLabel) e.getSource();
			l.setIcon(IconResized.of(IconResized.fileSelector(), IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			@SuppressWarnings("rawtypes")
			JComboBox cb = (JComboBox) e.getSource();
			if (cb.getSelectedItem() instanceof Pays) {
				ajoutJoueur.setImageNationalité(ajoutJoueur.getNationalite().getDrapeau());
			} else if (cb.getSelectedItem() instanceof Poste) {
				ajoutJoueur.setImagePoste(ajoutJoueur.getPoste().getImage());
			}
		}	
	}


}
