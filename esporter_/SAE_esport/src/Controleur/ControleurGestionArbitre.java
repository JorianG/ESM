package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Objects;

import javax.swing.*;

import Modele.Tournoi;
import vue.APP;
import vue.pages.GestionArbitre;
import vue.pages.GestionTournoi;
import vue.pages.ModifierArbitre;

public class ControleurGestionArbitre implements ActionListener, MouseListener {

	private GestionArbitre vue;

	public ControleurGestionArbitre(GestionArbitre vue) {
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if (b.getName() == "Quitter") {
				APP.previous();
			} else if (b.getName() == "Modifier") {
				try {
					APP.next(new ModifierArbitre(vue.getArbitre()).getContentPane());
				} catch (SQLException e1) {
        			System.err.println("failed to open ModifierArbitre");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page de modification de l'arbitre");
				}
			}	
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// if table clicked
		if (e.getSource() instanceof JTable) {
			if (e.getClickCount() == 2) {
				int row = vue.getListeTournoi().getSelectedRow();
				//id = id tournoi select
				int id = (int) vue.getListeTournoi().getModel().getValueAt(row, 3);
				APP.next(new GestionTournoi(Objects.requireNonNull(Tournoi.getById(id))).getContentPane());
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
