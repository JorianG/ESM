package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.*;

import Modele.Joueur;
import Modele.Match;
import connect.DBConnect;
import vue.APP;
import vue.pages.*;

public class ControleurGestionJoueur implements ActionListener, MouseListener, ItemListener{
	
	GestionJoueur gestionJoueur;
	ListeEquipe listeEquipe;
	Joueur joueur;
	
	public ControleurGestionJoueur(DBConnect db, GestionJoueur gestionJoueur, Joueur joueur, ListeEquipe listeEquipe) throws SQLException {
		this.gestionJoueur = gestionJoueur;
		this.listeEquipe = listeEquipe;
		this.joueur = joueur;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b.getText() == "Quitter") {
			//gestionJoueur.dispose();
			if (joueur.getId_equipe() != null) {
                APP.previous();
            } else {
				APP.resetTo(APP.getAncestorIndex()-1); // TODO : à tester
			}
		} else if (b.getText() == "Modifier") {
            try {
                APP.next(new ModifierJoueur(joueur).getContentPane());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

	@Override
	public void itemStateChanged(ItemEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getSource() instanceof JTable) {
			JTable table = (JTable) e.getSource();
			int id = (int) table.getValueAt(table.getSelectedRow(), 8);
			Match match = Match.getById(id);
			if (match != null) {
				try {
					APP.next(new GestionMatch(match).getContentPane());
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
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
