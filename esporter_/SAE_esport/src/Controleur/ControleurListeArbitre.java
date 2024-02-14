package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Modele.Arbitre;
import vue.APP;
import vue.pages.AjoutArbitre;
import vue.pages.GestionArbitre;
import vue.pages.ListeArbitre;

public class ControleurListeArbitre extends MouseAdapter implements ActionListener {
	
	private ListeArbitre vue;

	public ControleurListeArbitre(ListeArbitre vue) {
		this.vue = vue;

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object t = e.getSource();
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() instanceof JTable) {
			JTable table = (JTable) t;
			int idArbitreSelected = (Integer) table.getValueAt(table.getSelectedRow(), 4);
			Arbitre arbitreSelected = Arbitre.getById(idArbitreSelected);
			switch(table.getSelectedColumn()) {
			case(1):
				
				break;
			case(2):
				//modif arbitre
				APP.next(new GestionArbitre(arbitreSelected).getContentPane());
				break;
			case(3):
				// DELETE ARBITRE
				int verif = 1;
				verif = JOptionPane.showConfirmDialog(vue,
						"Etes-vous sur de vouloir supprimer l'arbitre " + arbitreSelected.getPrenom() + " " + arbitreSelected.getNom() + " ?");

				if(verif == 0) {
					Arbitre.delete(idArbitreSelected);
					this.vue.getModeleTable().removeRow(this.vue.listeArbitre.getSelectedRow());
				}
				break;
				
			default:
				break;
			}	
		  }
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object b = e.getSource();
		if (b instanceof JButton) {
			JButton btn = (JButton) b;
			switch(btn.getText()) {
			case("Rechercher"):
				this.vue.remplirTableListeArbitre(Objects.requireNonNull(Arbitre.getByNom(this.vue.getSearch())));
				break;
			case ("Ajouter"):
                try {
                    APP.next(new AjoutArbitre().getContentPane());
                } catch (SQLException ex) {
        			System.err.println("failed to open AjoutArbitre");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page d'ajout d'arbitre");
                }
				break;
			case("Annuler"):
				APP.previous();
				break;
			default:
				break;
			}
		}
	}
	
	
}
