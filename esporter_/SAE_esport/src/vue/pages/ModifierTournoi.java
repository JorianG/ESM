package vue.pages;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controleur.ControleurModifierTournoi;
import Modele.Tournoi;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JComboBox;
import java.time.LocalDate;
import java.sql.SQLException;

public class ModifierTournoi extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Tournoi tournoi;
	private JPanel contentPane;
	private JTextField textField_nom;
	private JComboBox<Integer> comboBox_jour_debut;
	private JComboBox<Integer> comboBox_mois_debut;
	private JComboBox<Integer> comboBox_jour_fin;
	private JComboBox<Integer> comboBox_mois_fin;
	private JLabel lblNewLogo;


	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ModifierTournoi(Tournoi tournoi) throws SQLException {
		this.tournoi = tournoi;
		ControleurModifierTournoi controleur = new ControleurModifierTournoi(this, this.tournoi);

		setBounds(100, 100, 700, 360);
		contentPane = new CustomContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_top = new JPanel();
		contentPane.add(panel_top, BorderLayout.NORTH);
		
		JLabel lblTitrePage = new JLabel("Modifier le tournoi");
		lblTitrePage.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 28));
		panel_top.add(lblTitrePage);
		
		JPanel panel_centre = new JPanel();
		contentPane.add(panel_centre, BorderLayout.CENTER);
		panel_centre.setLayout(new MigLayout("", "[grow][grow]", "[4.00][20.00][grow][grow]"));
		
		JPanel panel = new JPanel();
		panel_centre.add(panel, "cell 0 0,grow");
		
		JPanel panel_5 = new JPanel();
		panel_centre.add(panel_5, "cell 0 1 2 1,grow");
		
		JLabel lblLogo = new JLabel("Logo du tournoi : ");
		lblLogo.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 17));
		panel_5.add(lblLogo);
		
		lblNewLogo = new JLabel("");
		lblNewLogo.setName("Logo");
		lblNewLogo.setIcon(IconResized.of(tournoi.getImage(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_WIDTH_POSTE));
		lblNewLogo.addMouseListener(controleur);
		panel_5.add(lblNewLogo);
		
		JPanel panel_saisie_nom = new JPanel();
		panel_centre.add(panel_saisie_nom, "cell 0 2 2 1,grow");
		
		Box horizontalBox_saisie_nom = Box.createHorizontalBox();
		panel_saisie_nom.add(horizontalBox_saisie_nom);
		
		JLabel lblNom = new JLabel("Nom du tournoi : ");
		lblNom.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 17));
		horizontalBox_saisie_nom.add(lblNom);
		
		textField_nom = new JTextField();
		textField_nom.addActionListener(controleur);
		panel_saisie_nom.add(textField_nom);
		textField_nom.setColumns(10);
		if (tournoi != null) {
			textField_nom.setText(tournoi.getNom());
		}
		
		JPanel panel_date_debut = new JPanel();
		panel_centre.add(panel_date_debut, "cell 0 3,grow");
		
		Box horizontalBox_date_debut = Box.createHorizontalBox();
		panel_date_debut.add(horizontalBox_date_debut);
		
		JLabel lbl_date_debut = new JLabel("Date de début : ");
		lbl_date_debut.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 17));
		horizontalBox_date_debut.add(lbl_date_debut);
		
		JPanel panel_3 = new JPanel();
		horizontalBox_date_debut.add(panel_3);
		
		comboBox_jour_debut = new JComboBox<Integer>();
		comboBox_jour_debut.addActionListener(controleur);
		comboBox_jour_debut.setName("jourDeb");
		horizontalBox_date_debut.add(comboBox_jour_debut);
		
		JPanel panel_1 = new JPanel();
		horizontalBox_date_debut.add(panel_1);
		
		comboBox_mois_debut = new JComboBox<Integer>();
		comboBox_mois_debut.addActionListener(controleur);
		comboBox_mois_debut.setName("moisDeb");
		horizontalBox_date_debut.add(comboBox_mois_debut);
		
		JPanel panel_date_fin = new JPanel();
		panel_centre.add(panel_date_fin, "cell 1 3,grow");
		
		Box horizontalBox_date_fin = Box.createHorizontalBox();
		panel_date_fin.add(horizontalBox_date_fin);
		
		JLabel lbl_date_fin = new JLabel("Date de fin : ");
		lbl_date_fin.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 17));
		horizontalBox_date_fin.add(lbl_date_fin);
		
		JPanel panel_4 = new JPanel();
		horizontalBox_date_fin.add(panel_4);
		
		comboBox_jour_fin = new JComboBox<Integer>();
		comboBox_jour_fin.addActionListener(controleur);
		comboBox_jour_fin.setName("jourFin");
		horizontalBox_date_fin.add(comboBox_jour_fin);
		
		JPanel panel_2 = new JPanel();
		horizontalBox_date_fin.add(panel_2);
		
		comboBox_mois_fin = new JComboBox<Integer>();
		comboBox_mois_fin.addActionListener(controleur);
		comboBox_mois_fin.setName("moisFin");
		horizontalBox_date_fin.add(comboBox_mois_fin);
		
		JPanel panel_bas = new JPanel();
		FlowLayout fl_panel_bas = (FlowLayout) panel_bas.getLayout();
		fl_panel_bas.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_bas, BorderLayout.SOUTH);
		
		JButton btnEnregistrer = new RoundedButton("Enregistrer");
		btnEnregistrer.addActionListener(controleur);
		panel_bas.add(btnEnregistrer);
		
		JButton btnSupprimer = new RoundedButton("Annuler le tournoi");
		btnSupprimer.addActionListener(controleur);
		btnSupprimer.setForeground(Palette.DEL_BACKGROUND.getColor());
		btnSupprimer.setBackground(Palette.DEL_FOREGROUND.getColor());
		panel_bas.add(btnSupprimer);
		
		JButton btnAnnuler = new RoundedButton("Annuler");
		btnAnnuler.addActionListener(controleur);
		panel_bas.add(btnAnnuler);
		
		
		//remplissage des comboBox
		remplirComboBoxMois();
		remplirComboBoxJourDebut();
		remplirComboBoxJourFin();
		
		initDate();
	}
	
	public int daysInMonth(JComboBox<Integer> j) {
		int tailleMois = 31;
        switch(j.getSelectedIndex()+1) {
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
        return tailleMois;
	}
	
	
	public void remplirComboBoxJourDebut() {
		int jourSelected = comboBox_jour_debut.getSelectedIndex();
		comboBox_jour_debut.removeAllItems();
        for (int i = 1; i<=daysInMonth(comboBox_mois_debut); i++) {
        	comboBox_jour_debut.addItem(i);
        }
        if (comboBox_jour_debut.getItemAt(jourSelected) != null) {
        	comboBox_jour_debut.setSelectedIndex(jourSelected);
        }
	}
	
	public void remplirComboBoxJourFin() {
		int jourSelected = comboBox_jour_fin.getSelectedIndex();
		comboBox_jour_fin.removeAllItems();
        for (int i = 1; i<=daysInMonth(comboBox_mois_fin); i++) {
        	comboBox_jour_fin.addItem(i);
        }
        if (comboBox_jour_fin.getItemAt(jourSelected) != null) {
        	comboBox_jour_fin.setSelectedIndex(jourSelected);
        }
	}
	
	public void remplirComboBoxMois() {
		comboBox_mois_debut.removeAllItems();
		comboBox_mois_fin.removeAllItems();
		for (int i=1; i<=12; i++) {
			comboBox_mois_debut.addItem(i);
			comboBox_mois_fin.addItem(i);
		}
	}
	
	public void initDate() {
		comboBox_jour_debut.setSelectedItem(tournoi.getDateDebut().getDayOfMonth());
		comboBox_mois_debut.setSelectedItem(tournoi.getDateDebut().getMonthValue());
		comboBox_jour_fin.setSelectedItem(tournoi.getDateFin().getDayOfMonth());
		comboBox_mois_fin.setSelectedItem(tournoi.getDateFin().getMonthValue());
	}
	
	public String getTextFieldNom() {
		return textField_nom.getText();
	}
	
	public int getJourDebut() {
		return (int) comboBox_jour_debut.getSelectedItem();
	}
	
	public int getMoisDebut() {
		return (int) comboBox_mois_debut.getSelectedItem();
	}
	
	public int getJourFin() {
		return (int) comboBox_jour_fin.getSelectedItem();
	}
	
	public int getMoisFin() {
		return (int) comboBox_mois_fin.getSelectedItem();
	}
	
	public JLabel getLabelImage() {
		return this.lblNewLogo;
	}

}
