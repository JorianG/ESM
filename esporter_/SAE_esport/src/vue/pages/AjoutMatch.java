package vue.pages;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controleur.ControleurAjoutMatch;
import Modele.Arbitre;
import Modele.Equipe;
import Modele.Match;
import Modele.Tournoi;

import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AjoutMatch extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblLogoTeam1 = new JLabel();
	private JComboBox<Equipe> comboBoxTeam1 = new JComboBox<Equipe>();
	private JComboBox<Equipe> comboBoxTeam2 = new JComboBox<Equipe>();
	private JLabel lblLogoTeam2 = new JLabel();
	private JComboBox<Arbitre> comboBoxArbitre;
	private JButton btnAjouter = new RoundedButton();
	private JComboBox<Integer> comboBoxJour;
	private JComboBox<Integer> comboBoxMois;
	private JComboBox<String> comboBoxHeure;
	private JComboBox<String> comboBoxMinute;
	private Tournoi tournoi;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public AjoutMatch(Tournoi tournoi) throws SQLException {
		this.tournoi = tournoi;
		ControleurAjoutMatch controleur = new ControleurAjoutMatch(this);
		setBounds(100, 100, 800, 400);
		
		contentPane = new CustomContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel content = new JPanel();
		contentPane.add(content, BorderLayout.CENTER);
		content.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		content.add(panel, BorderLayout.NORTH);
		
		JLabel lblNouveauMatchDu = new JLabel("Nouveau Match du Tournoi :");
		lblNouveauMatchDu.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNouveauMatchDu);
		
		JPanel panelInfo = new JPanel();
		content.add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new MigLayout("", "[479.00,grow]", "[grow][grow][bottom]"));
		
		JPanel panelDateHeure = new JPanel();
		panelInfo.add(panelDateHeure, "cell 0 0,grow");
		panelDateHeure.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panelDate = new JPanel();
		panelDateHeure.add(panelDate);
		
		JLabel lblDate = new JLabel("Date du match : ");
		panelDate.add(lblDate);
		
		comboBoxJour = new JComboBox<Integer>();
		comboBoxJour.addActionListener(controleur);
		comboBoxJour.setName("comboBoxJour");
		
		comboBoxMois = new JComboBox<Integer>();
		comboBoxMois.addActionListener(controleur);
		comboBoxMois.setName("comboBoxMois");
		remplirComboBoxJour();
		for (int i=1; i<=12; i++) {
			comboBoxMois.addItem(i);
		}
		panelDate.add(comboBoxJour);
		panelDate.add(comboBoxMois);
		
		JPanel panelHeure = new JPanel();
		panelDateHeure.add(panelHeure);
		
		JLabel lblHeure = new JLabel("Heure du match : ");
		panelHeure.add(lblHeure);
		
		comboBoxHeure = new JComboBox<String>();
		comboBoxHeure.addActionListener(controleur);
		comboBoxHeure.setName("comboBoxHeure");
		remplirComboBoxHeure();
		panelHeure.add(comboBoxHeure);
		
		JLabel lbl2Points = new JLabel(" : ");
		panelHeure.add(lbl2Points);
		
		comboBoxMinute = new JComboBox<String>();
		comboBoxMinute.addActionListener(controleur);
		comboBoxMinute.setName("comboBoxMinute");
		remplirComboBoxMinute();
		panelHeure.add(comboBoxMinute);
		
		JPanel panelTeams = new JPanel();
		panelInfo.add(panelTeams, "cell 0 1,grow");

		remplirComboBoxEquipe(Equipe.getAll());

		comboBoxArbitre = new JComboBox<Arbitre>();
		comboBoxArbitre.addActionListener(controleur);
		comboBoxArbitre.setName("comboBoxArbitre");
		remplirComboBoxArbitre(Arbitre.getAll());
		panelInfo.add(comboBoxArbitre, "cell 0 2,alignx right");


		lblLogoTeam1 = new JLabel();
		panelTeams.add(lblLogoTeam1);

		comboBoxTeam1.addActionListener(controleur);
		comboBoxTeam1.setName("comboBoxTeam1");
		panelTeams.add(comboBoxTeam1);

		JLabel lblIconVS = new JLabel(IconResized.of(IconResized.VS, 100, 100));
		panelTeams.add(lblIconVS);

		comboBoxTeam2.addActionListener(controleur);
		comboBoxTeam2.setName("comboBoxTeam2");
		panelTeams.add(comboBoxTeam2);

		lblLogoTeam2 = new JLabel();
		panelTeams.add(lblLogoTeam2);

		comboBoxTeam1.setSelectedIndex(0);
		comboBoxTeam2.setSelectedIndex(1);
		lblLogoTeam1.setIcon(IconResized.of(getEquipe1().getImage(), IconResized.DEFAULT_WIDTH_IMAGE_SMALL, IconResized.DEFAULT_HEIGHT_IMAGE_SMALL));
		lblLogoTeam2.setIcon(IconResized.of(getEquipe2().getImage(), IconResized.DEFAULT_WIDTH_IMAGE_SMALL, IconResized.DEFAULT_HEIGHT_IMAGE_SMALL));

		JPanel footer = new JPanel();
		contentPane.add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(0, 0));

		Box horizontalBox = Box.createHorizontalBox();
		footer.add(horizontalBox, BorderLayout.EAST);
		
		JPanel panelAnnuler = new JPanel();
		horizontalBox.add(panelAnnuler);
		
		JButton btnAnnuler = new RoundedButton("Annuler");
		btnAnnuler.addActionListener(controleur);
		btnAnnuler.setHorizontalAlignment(SwingConstants.RIGHT);
		panelAnnuler.add(btnAnnuler);
		
		JPanel ecart = new JPanel();
		horizontalBox.add(ecart);
		
		JPanel panelAjouter = new JPanel();
		horizontalBox.add(panelAjouter);
		
		btnAjouter = new RoundedButton("Ajouter");
		btnAjouter.addActionListener(controleur);
		setEnabledButtonAjouter(false);
		btnAjouter.setHorizontalAlignment(SwingConstants.RIGHT);
		panelAjouter.add(btnAjouter);
		
		Equipe team1 = null;
		Equipe team2 = null;
		for (Equipe equipe : tournoi.getListEquipes().keySet()){
			if(Match.getNbMatchByEquipeAndTournoi(equipe.getId(),tournoi.getId())<= tournoi.getListEquipes().size()-1) {
				for (Equipe equipe2 : tournoi.getListEquipes().keySet()) {
					if (equipe.getId() != equipe2.getId()) {
						if (!Match.isMatchBetweenEquipeAndTournoi(equipe.getId(), equipe2.getId(), tournoi.getId())) {
							team1 = equipe;
							team2 = equipe2;
							break;
						}
					}
				}
			}
		}
		try {
			comboBoxTeam1.setSelectedItem(team1);
			comboBoxTeam2.setSelectedItem(team2);
		} catch (Exception e) {
			System.out.println("Pas de match à ajouter");
		}
		
		initDateAndTime();
		
	}

	public void initDateAndTime() {
		comboBoxMois.setSelectedItem(LocalDate.now().getMonthValue());
		comboBoxJour.setSelectedItem(LocalDate.now().getDayOfMonth());
		comboBoxHeure.setSelectedIndex(LocalTime.now().getHour());
		comboBoxMinute.setSelectedIndex(LocalTime.now().getMinute());
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public void remplirComboBoxJour() {
		int jourSelected = comboBoxJour.getSelectedIndex();
		int tailleMois = 31;
        switch(comboBoxMois.getSelectedIndex()+1) {
			case 2:
				tailleMois = 28;
				int annee = LocalDate.now().getYear();
				if ((annee % 4 == 0 && annee % 100 != 0) || annee % 400 == 0) {
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
		comboBoxJour.removeAllItems();
        for (int i = 1; i<=tailleMois; i++) {
        	comboBoxJour.addItem(i);
        }
        if (comboBoxJour.getItemAt(jourSelected) != null) {
        	comboBoxJour.setSelectedIndex(jourSelected);
        }
    }
	
	public void remplirComboBoxHeure() {
		comboBoxHeure.removeAllItems();
		for(int i=0; i<24; i++) {
			String st = "";
			if (i<10) {
				st+="0";
			}
			st += i;
			comboBoxHeure.addItem(st);
		}
	}
	
	public void remplirComboBoxMinute() {
		comboBoxMinute.removeAllItems();
		for(int i=0; i<60; i++) {
			String st = "";
			if (i<10) {
				st+="0";
			}
			st += i;
			comboBoxMinute.addItem(st);
		}
	}
	
	public void remplirComboBoxEquipe(List<Equipe> equipeList) {
		comboBoxTeam1.removeAllItems();
		comboBoxTeam2.removeAllItems();
		for (Equipe e : equipeList) {
			comboBoxTeam1.addItem(e);
			comboBoxTeam2.addItem(e);
        }
    }
	
	public Equipe getEquipe1() {
		if (comboBoxTeam1.getSelectedItem() != null) {
			return (Equipe) comboBoxTeam1.getSelectedItem();
		}
		return null;
	}
	
	public Equipe getEquipe2() {
		if (comboBoxTeam2.getSelectedItem() != null) {
			return (Equipe) comboBoxTeam2.getSelectedItem();
		}
		return null;
	}
	
	public void setLogoEquipe1(Equipe equipe) {
		lblLogoTeam1.setIcon(IconResized.of(equipe.getImage(), IconResized.DEFAULT_WIDTH_IMAGE_SMALL, IconResized.DEFAULT_HEIGHT_IMAGE_SMALL));
	}
	
	public void setLogoEquipe2(Equipe equipe) {
		lblLogoTeam2.setIcon(IconResized.of(equipe.getImage(), IconResized.DEFAULT_WIDTH_IMAGE_SMALL, IconResized.DEFAULT_HEIGHT_IMAGE_SMALL));
	}
	
	public void remplirComboBoxArbitre(List<Arbitre> arbitreList) {
		comboBoxArbitre.removeAllItems();
		for (Arbitre a : arbitreList) {
			comboBoxArbitre.addItem(a);
        }
    }
	
	public Arbitre getArbitre() {
		return (Arbitre) comboBoxArbitre.getSelectedItem();
	}
	
	public int getJour() {
		return (int) comboBoxJour.getSelectedItem();
	}
	
	public int getMois() {
		return (int) comboBoxMois.getSelectedItem();
	}
	
	public int getHeure() {
		return Integer.valueOf((String) comboBoxHeure.getSelectedItem());
	}
	
	public int getMinute() {
		return Integer.valueOf((String) comboBoxMinute.getSelectedItem());
	}
	
	public void setEnabledButtonAjouter(boolean b) {
        btnAjouter.setEnabled(b);
    }

}
