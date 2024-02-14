package vue.pages;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.ControleurHeader;
import net.miginfocom.swing.MigLayout;

public class Header extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construit un header pour l'application
	 */
	public Header() {
		//Creation du header
		
		super();
		ControleurHeader controleur = new ControleurHeader(this);
			setLayout(new MigLayout("", "[][grow][grow][][grow][][grow][][grow][grow][]", "[56px]"));
			JLabel lblLogo = new JLabel(IconResized.of(IconResized.LOGO_ESPORTER, 97, 34));
			add(lblLogo, "cell 0 0,alignx center,aligny center");
			lblLogo.setName("Accueil");
			lblLogo.addMouseListener(controleur);
			
			JButton btnAllerVersEquipes = new RoundedButton("Equipes");
			add(btnAllerVersEquipes, "cell 3 0,grow");
			btnAllerVersEquipes.addActionListener(controleur);
			
			JButton btnAllerVersSaison = new RoundedButton("Saison");
			add(btnAllerVersSaison, "cell 5 0,grow");
			btnAllerVersSaison.addActionListener(controleur);
			
			JButton btnAllerVersTournoi = new RoundedButton("Tournoi");
			add(btnAllerVersTournoi, "cell 7 0,grow");
			btnAllerVersTournoi.addActionListener(controleur);
			
			
			//Affiche Bouton de deconnexion
			JLabel lblLogout = new JLabel(IconResized.of(IconResized.LOGOUT, 50, 50));
			add(lblLogout, "cell 10 0,alignx center,aligny center");
			lblLogout.setName("Logout");
			lblLogout.addMouseListener(controleur);
	}
	
	/**
	 * Fonction pour ajouter le header a une page
	 * @param page La page dans laquelle on ajoute le header
	 */
	public void addHeaderTo(JPanel page) {
		page.add(this, BorderLayout.NORTH);
	}

}
