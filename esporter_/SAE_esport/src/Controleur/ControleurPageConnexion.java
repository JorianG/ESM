package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Modele.Admin;
import Modele.Tournoi;
import vue.APP;
import vue.pages.ChoiceSaison;
import vue.pages.GestionTournoi;
import vue.pages.PageConnexion;

public class ControleurPageConnexion implements ActionListener {

	private PageConnexion vue;
	
	public ControleurPageConnexion(PageConnexion vue) {
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o instanceof JButton) {
			JButton b = (JButton) o;
			switch(b.getText()) {
			case "Connexion":
				String login = vue.getLogin();
				String password = vue.getPassword();
				boolean connected = false;
				for (Admin a : Admin.getAll()) {
					if (a.getLogin().equals(login) && a.getMdp() == (password.hashCode())) {
						APP.isArbitre = false;
						APP.next(new ChoiceSaison().getContentPane());
						connected = true;
						vue.emptyLogPass();
						break;
					}
				}
				Tournoi t = Tournoi.getTournoiByIdentification(login, password);
				if (t != null) {
					APP.isArbitre = true;
					APP.SELECTEDYEAR = t.getSaison().getAnnee();
                    APP.next(new GestionTournoi(t).getContentPane());
					connected = true;
					vue.emptyLogPass();
				}
				if (!connected) {
					vue.emptyLogPass();
					JOptionPane.showInternalMessageDialog(b.getParent(), "Identifiants incorrects");
				}
				break;
			case "Quitter":
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}
}
