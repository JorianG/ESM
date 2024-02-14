package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

import Modele.Equipe;
import Modele.Tournoi;
import vue.APP;
import vue.pages.*;

public class ControleurAjoutTournoi implements ActionListener, MouseListener, ItemListener{

	private AjoutTournoi vue;

	private ArrayList<Equipe> lstEquipeDuTournoi;
	public boolean ignoreCombo;

	
	public ControleurAjoutTournoi(AjoutTournoi vue) {
		this.vue = vue;

		this.lstEquipeDuTournoi = new ArrayList<>();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JComboBox) {
			@SuppressWarnings("rawtypes")
			JComboBox j = (JComboBox) o;
			if(j.getSelectedItem() instanceof String) {
				@SuppressWarnings("unchecked")
				JComboBox<String> js = (JComboBox<String>) j;
				if (js.getItemCount() == 13) {
		            int tailleMois = 31;
		            switch(j.getSelectedIndex()) {
		            case 2:
		                tailleMois = 28;
		                if ((APP.SELECTEDYEAR % 4 == 0 && APP.SELECTEDYEAR % 100 != 0) || APP.SELECTEDYEAR % 400 == 0) {
		                    tailleMois++;
		                }
		                break;
		            case 4:
					case 6:
					case 9:
					case 11:
						tailleMois = 30;
						break;
		            }
					switch (js.getItemAt(0)) {
					case ("-- mois de début --"):
						vue.setComboBoxJourD(tailleMois);
						break;
					case ("-- mois de fin --"):
						vue.setComboBoxJourF(tailleMois);
						break;
					default:
						break;
					}
				}
			}
		} else if (o instanceof JButton) {
			JButton b = (JButton) o;
			switch (b.getText()) {
			case ("Annuler"):
				APP.previous();
				break;
			case ("Creer"):
				LocalDate debut = LocalDate.of(APP.SELECTEDYEAR, Integer.parseInt(vue.getMoisD()), Integer.parseInt(vue.getJourD()));
				LocalDate fin = LocalDate.of(APP.SELECTEDYEAR, Integer.parseInt(vue.getMoisF()), Integer.parseInt(vue.getJourF()));
                boolean superposees = false;
                try {
					new Tournoi( vue.getNom(), debut, fin, IconResized.getPath(vue.getLabelImage()), vue.getNiveau(), vue.getEquipes(), false);
                } catch (SQLException ex) { // TODO Observer patter
                    superposees = true;
					JFrame frame = new PopupTournoiSuperposees();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
                }
				if (!superposees) {
					APP.previous();
				}
				break;
			case("+"):
                try {
					APP.next(new AjoutEquipe(false, true).getContentPane()); // TODO : changer la liaison
                } catch (SQLException ex) {
        			System.err.println("failed to open AjoutEquipe");
                	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de l'ouverture de la page d'ajout d'équipe");
                }
				break;
			default:
				break;
			}
		}
		this.verify();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if  (o instanceof JLabel) {
			JLabel l = (JLabel) o;
			l.setIcon(IconResized.of(IconResized.fileSelector(), 75, 75)); // TODO : constant poste ?
		}else if(o instanceof JTable) {
			if(e.getClickCount()>=2) {
				//RETIRER JOUEUR DE LA TABLE -> DANS LA COMBO
				JTable j= (JTable) e.getSource();
				if (j.getSelectedColumn()==2) {
					int idEquipe = (int) j.getValueAt(j.getSelectedRow(), 3);
					Equipe remEquipe = null;				
					for(Equipe equipe : this.lstEquipeDuTournoi) {
						if(equipe.getId() == idEquipe) {
							remEquipe = equipe;
							
						}
					}
					this.lstEquipeDuTournoi.remove(remEquipe);
					this.vue.equipes.add(remEquipe);
					this.vue.equipesAdded.remove(remEquipe);
					
					this.vue.getModeleTable().removeRow(j.getSelectedRow());
					this.vue.refreshComboBoxJoueur();
				}
			}	
		}
		this.verify();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object o = e.getSource();
		if (o instanceof JComboBox) {
				for(Equipe eq : Equipe.getAll()) {
					if(!this.lstEquipeDuTournoi.contains(eq) && !this.vue.equipes.contains(eq)) {
						this.vue.equipes.add(eq);
					}
				}

		}
		this.vue.refreshComboBoxJoueur();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	private void verify() {
		if (vue.getNom() != null
		&& vue.getJourD() != "-- jour de début --" && vue.getMoisD() != "-- mois de début --"
		&& vue.getJourF() != "-- jour de fin --" && vue.getMoisF() != "-- mois de fin --"
		&& vue.getJourD() != null && vue.getMoisD() != null
		&& vue.getJourF() != null && vue.getMoisF() != null 
		&& (vue.equipesAdded.size() >= 4 && vue.equipesAdded.size() <= 8)) {
			System.out.println(vue.equipesAdded.size());
			int jourD = Integer.valueOf(vue.getJourD());
			int moisD = Integer.valueOf(vue.getMoisD());
			int jourF = Integer.valueOf(vue.getJourF());
			int moisF = Integer.valueOf(vue.getMoisF());
			if ((moisF == moisD && jourF >= jourD) || moisF > moisD) {
				vue.setEnabledButtonCreate(true);
			} else {
				vue.setEnabledButtonCreate(false);
			}
		} else {
			vue.setEnabledButtonCreate(false);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(!this.ignoreCombo) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				Equipe eq = vue.getSelectedEquipe();
				System.out.println("combo select"+ eq.getNom() );
				vue.equipes.remove(eq);
				vue.equipesAdded.add(eq);
				this.lstEquipeDuTournoi.add(eq);
				vue.refreshComboBoxJoueur();
				vue.getModeleTable().addRow(new Object[] {eq.getImage(), eq.getNom(), "trash", eq.getId()});
				
			}
		}
		this.verify();
	}
}
