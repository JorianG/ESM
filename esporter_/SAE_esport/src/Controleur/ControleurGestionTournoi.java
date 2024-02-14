package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Modele.*;
import vue.APP;
import vue.pages.*;

public class ControleurGestionTournoi implements MouseListener, ActionListener {

	private GestionTournoi vue;
	
	public ControleurGestionTournoi(GestionTournoi vue) {
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JButton){
			JButton b = (JButton) o;
			switch(b.getText()) {
			case ("Afficher login arbitre"):
				JOptionPane.showMessageDialog(APP.getInstance(),
						"\nLogin : " + vue.getTournoi().getLogin() + "\nMot de passe : " + vue.getTournoi().getPassword(), "login arbitre",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			case ("Modifier le tournoi"):
				try {
					APP.next(new ModifierTournoi(vue.getTournoi()).getContentPane());
				} catch (SQLException e1) {
        			System.err.println("failed to open ModifierTournoi");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de modification du tournoi");
				}
				break;
			case ("Ajouter un match"):
				Tournoi t = vue.getTournoi();
				boolean allMatch = true;
			    for(Equipe equipe : t.getListEquipes().keySet()) {
					try {
						if(Match.getNbMatchByEquipeAndTournoi(equipe.getId(), t.getId()) < t.getListEquipes().size() - 1) {
							APP.next(new AjoutMatch(t).getContentPane());
							allMatch = false;
						}
					}catch (SQLException e1) {
	        			System.err.println("failed to open AjoutMatch");
	                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page d'ajout de match");
					}
				}
				if(allMatch) {
					JOptionPane.showMessageDialog(APP.getInstance(), "Toutes les équipes ont déjà leurs nombre de matchs maximum");
				}
				break;
			case ("Liste des équipes"):
				System.out.println("bouton cliqué : liste des équipes");
				APP.next(new PopupListeEquipe(vue.getTournoi()).getContentPane());
				break;
			case ("Liste des arbitres"):
				APP.next(new PopupListeArbitre(vue.getTournoi()).getContentPane());
				break;
			case ("Cloturer poule"):
				Tournoi tournoi = vue.getTournoi();
				boolean closable = true;
				for (Match m: tournoi.getListMatchs()) {
					if (!m.getDateFinMatch().isBefore(new CustomDateTime(LocalDateTime.now()))) {
						JOptionPane.showMessageDialog(APP.getInstance(), "Vous ne pouvez pas cloturer la pool du tournoi " + tournoi.getNom() + "\nIl reste des matchs à jouer");
						closable = false;
						break;
					}
				} 
				if (closable) {
					int confirme = JOptionPane.showConfirmDialog(APP.getInstance(), "Etes-vous sur de vouloir cloturer la pool du tournoi " + tournoi.getNom() +
							"\nCette action est irreversible");
					if(confirme==0) {
						boolean clot = tournoi.cloturerPool();
						if (clot) {
							vue.btnAjoutMatch.setEnabled(false);
							vue.btnCloturer.setEnabled(false);
							vue.btnCloturer.setText("Terminer le tournoi");
						} else {
							JOptionPane.showMessageDialog(APP.getInstance(),"La pool du tournoi " + tournoi.getNom() + " n'a pas pu être cloturée");
						}
					}
				}
				break;
			case ("Terminer le tournoi"):
				//TODO verifier que finales terminées
				int confirme2 = JOptionPane.showConfirmDialog(APP.getInstance(), "Etes-vous sur de vouloir terminer le tournoi "
						+ vue.getTournoi().getNom() + "\nCette action est irreversible");
			    if(confirme2==0) {
					boolean termine = vue.getTournoi().cloturerTournoi();
					if (termine) {
						vue.btnAjoutMatch.setEnabled(false);
						vue.btnCloturer.setEnabled(false);
						vue.getTournoi().setTerminer(true);
						Tournoi.update(vue.getTournoi());
					} else {
						JOptionPane.showMessageDialog(APP.getInstance(),
								"Le tournoi " + vue.getTournoi().getNom() + " n'a pas pu être terminé");
					}
			    }
				break;
			case ("Impression des résultats"):
				PrintRectangle.impression(vue.getTournoi());
				break;
			case ("Quitter"):
				if (APP.isArbitre) {
					APP.root();
				} else {
					APP.previous();
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
		if (o instanceof JTable){
			JTable table = (JTable) o;
			if(e.getClickCount() == 2 ){
				switch (table.getName()) {
				case "tableClassement":
					Equipe equipe = Equipe.getById((Integer) table.getValueAt(table.getSelectedRow(), 3));
					GestionEquipe page = null;
					try {
                        page = new GestionEquipe(equipe, new ListeEquipe());
					} catch (SQLException ex) {
	        			System.err.println("failed to open GestionEquipe");
	                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de gestion d'équipe");
					}
					APP.next(page.getContentPane());
					break;
				case "tableMatch":
					Match match = Match.getById((Integer) table.getValueAt(table.getSelectedRow(), 8));
					if (!match.isFinished()) {
						try {
							APP.next(new GestionMatch(match).getContentPane());
						} catch (SQLException ex) {
		        			System.err.println("failed to open GestionMatch");
		                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de gestion du match");
						}	
					} else {
						JOptionPane.showMessageDialog(vue, "Le match est déjà terminé");
					}
					break;
				}
			}
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
