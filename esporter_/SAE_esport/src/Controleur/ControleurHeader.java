package Controleur;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Modele.Saison;
import vue.APP;
import vue.pages.GestionSaison;
import vue.pages.GestionTournoi;
import vue.pages.Header;
import vue.pages.ListeEquipe;

public class ControleurHeader implements ActionListener, MouseListener{
	private Header head;
	
	public ControleurHeader(Header head) {
		this.head = head;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JButton) {
			if (!APP.isArbitre) {
				JButton b = (JButton) o;
				switch (b.getText()) {
				case "Equipes":
					APP.next(new ListeEquipe().getContentPane());
					break;
				case "Saison":
					APP.next(new GestionSaison(Saison.getById(APP.SELECTEDYEAR)).getContentPane());
					break;
				case "Tournoi":
					if (Saison.getCurrentTournoi() == null) {
						JOptionPane.showInternalMessageDialog(head.getParent(), "Aucun Tournoi en cours");
					} else {
						APP.next(new GestionTournoi(Saison.getCurrentTournoi()).getContentPane());
					}
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o instanceof JLabel) {
			JLabel l = (JLabel) o;
			switch (l.getName()) {
			case "Accueil":
				APP.resetTo(APP.INDEX_USER_ROOT);
				break;
			case "Logout":
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
	
	public static JFrame getFrame(Component component) {
        while (component != null) {
            if (component instanceof JFrame) {
                return (JFrame) component;
            }
            component = component.getParent();
        }
        return null;
    }
}
