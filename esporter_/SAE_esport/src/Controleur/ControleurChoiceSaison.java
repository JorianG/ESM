package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

import javax.swing.*;

import Modele.Saison;
import vue.APP;
import vue.pages.AjoutSaison;
import vue.pages.GestionSaison;

public class ControleurChoiceSaison implements ActionListener, MouseListener{

	public ControleurChoiceSaison() {}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JButton) {
			JButton b = (JButton) o;
			switch (b.getName()){
			case "courante":
				APP.SELECTEDYEAR = LocalDate.now().getYear();
				try {
	                Saison s = Saison.getById(LocalDate.now().getYear());
					if (s.isArchived()) {
						int i = JOptionPane.showOptionDialog(APP.getInstance(), "La saison " + s.getNom() + " (" + s.getAnnee() + ") est archivée.\nVous ne pouvez plus la modifier, souhaitez vous la détruire pour la recréer ?", "Saison archivée", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (i == 0) {
							Saison.destroy(s.getAnnee());
							APP.next(new AjoutSaison().getContentPane());
						}
					} else {
						APP.next(new GestionSaison(s).getContentPane());
					}
	            } catch (NullPointerException e1) {
	                APP.next(new AjoutSaison().getContentPane());
	            }
				break;
			case "suivante":
				APP.SELECTEDYEAR = LocalDate.now().getYear()+1;
				try {
					Saison s = Saison.getById(APP.SELECTEDYEAR);
					APP.next(new GestionSaison(s).getContentPane());
				} catch (NullPointerException e1) {
					APP.next(new AjoutSaison().getContentPane());
				}
                break;
            default:
                break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof JLabel) {
            JLabel l = (JLabel) o;
            switch (l.getName()) {
                case "deconnect":
    				APP.root();
                    break;
                default:
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
