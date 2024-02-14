package vue.pages;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Controleur.ControleurModifierJoueur;
import Modele.Joueur;
import Modele.Pays;
import Modele.Poste;
import vue.CustomContentPane;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

	public class ModifierJoueur extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 *  TextFields pour l'insertion des infos :
	 *  	Prenom, Pseudo, Nom
	 */
	private JTextField textFieldPrenom;
	private JTextField textFieldPseudo;
	private JTextField textFieldNom;

	/**
	 * ComboBox pour l'insertion de la nationalité et du poste
	 */
	private JComboBox<Pays> comboBoxPays;
	private JComboBox<Poste> comboBoxPoste;

	/**
	 * Labels pour l'insertion d'une image qui représente le joueur
	 * et pour l'affichage de la nationalité et du poste
	 */
	private JLabel lblFlag;
	private JLabel lblIconPoste;
	private JLabel lblPutImage;

	/**
	 * Boutons pour Annuler, Modifier et Supprimer
	 */
	private JButton btnAnnuler;
	private JButton btnEnregistrer;
	private JButton btnDelete;

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws MalformedURLException 
	 */
	public ModifierJoueur(Joueur joueur) throws SQLException {
		ControleurModifierJoueur controle = new ControleurModifierJoueur(this, joueur);
		setBounds(100, 100, 1000, 500);
		contentPane = new CustomContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
			/*
			 * Panel contenant le titre
			 */
		
			JPanel panelTitre = new JPanel();
			contentPane.add(panelTitre, BorderLayout.NORTH);
			panelTitre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
				JLabel lblTitre = new JLabel("Modifer le Joueur : " + joueur.getFullName());
				lblTitre.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
				panelTitre.add(lblTitre);
			
				
			/*
			 * Panel contenant
			 * l'insertion des infos :
			 * 		Prenom, Pseudo, Nom
			 * 		ainsi que la nationalité et le poste
			 * et l'ajout de la date de naissance
			 */
			JPanel panelCentre = new JPanel();
			contentPane.add(panelCentre, BorderLayout.CENTER);
			panelCentre.setLayout(new BorderLayout(0, 0));
			
				JPanel panelInfo = new JPanel();
				panelCentre.add(panelInfo, BorderLayout.CENTER);
				panelInfo.setLayout(new GridLayout(2, 1, 0, 0));
				
				JPanel panelIdentite = new JPanel();
				panelInfo.add(panelIdentite);
				panelIdentite.setLayout(new GridLayout(0, 2, 0, 0));
			
					JPanel panelPutImage = new JPanel();
					panelIdentite.add(panelPutImage);
					panelPutImage.setLayout(new BorderLayout(0, 0));
	
						lblPutImage = new JLabel(IconResized.of(joueur.getImage(), IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
						lblPutImage.addMouseListener(controle);
						lblPutImage.setHorizontalAlignment(SwingConstants.CENTER);
						panelPutImage.add(lblPutImage);
		
					JPanel panelInsertName = new JPanel();
					panelIdentite.add(panelInsertName);
					panelInsertName.setLayout(new GridLayout(0, 3, 0, 0));
				
						JLabel lblPrenom = new JLabel("Prenom :  ");
						lblPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
						panelInsertName.add(lblPrenom);
			
						textFieldPrenom = new JTextField();
						textFieldPrenom.setHorizontalAlignment(SwingConstants.CENTER);
						textFieldPrenom.setColumns(10);
						textFieldPrenom.setDocument(new LimitJTextField(50));
						textFieldPrenom.setText(joueur.getPrenom());
						panelInsertName.add(textFieldPrenom);
			
						JLabel lblEcart1 = new JLabel();
						panelInsertName.add(lblEcart1);
			
						JLabel lblPseudo = new JLabel("Pseudonyme :  ");
						lblPseudo.setHorizontalAlignment(SwingConstants.RIGHT);
						panelInsertName.add(lblPseudo);
						
						textFieldPseudo = new JTextField();
						textFieldPseudo.setHorizontalAlignment(SwingConstants.CENTER);
						textFieldPseudo.setColumns(10);
						textFieldPseudo.setDocument(new LimitJTextField(50));
						textFieldPseudo.setText(joueur.getPseudo());
						panelInsertName.add(textFieldPseudo);
						
						JLabel lblEcart2 = new JLabel();
						panelInsertName.add(lblEcart2);
						
						JLabel lblNom = new JLabel("Nom :  ");
						lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
						panelInsertName.add(lblNom);
						
						textFieldNom = new JTextField();
						textFieldNom.setHorizontalAlignment(SwingConstants.CENTER);
						textFieldNom.setColumns(10);
						textFieldNom.setDocument(new LimitJTextField(50));
						textFieldNom.setText(joueur.getNom());
						panelInsertName.add(textFieldNom);
				
					JPanel panelNatPoste = new JPanel();
					panelInfo.add(panelNatPoste);
					panelNatPoste.setLayout(new GridLayout(2, 2, 0, 0));
				
						JPanel panelNat = new JPanel();
						panelNatPoste.add(panelNat);
						panelNat.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					
							JLabel lblNationalite = new JLabel("Nationalité :    ");
							lblNationalite.setHorizontalAlignment(SwingConstants.CENTER);
							panelNat.add(lblNationalite);
					
							lblFlag = new JLabel(IconResized.of(joueur.getPays().getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
							lblFlag.setHorizontalAlignment(SwingConstants.CENTER);
							panelNat.add(lblFlag);
						
						JPanel panelPoste = new JPanel();
						panelNatPoste.add(panelPoste);
						panelPoste.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					
							JLabel lblNomPoste = new JLabel("Poste :    ");
							panelPoste.add(lblNomPoste);
					
							lblIconPoste = new JLabel(IconResized.of(joueur.getPoste().getImage(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
							lblIconPoste.setHorizontalAlignment(SwingConstants.CENTER);
							panelPoste.add(lblIconPoste);
								
							JPanel panelSelectPays = new JPanel();
							panelNatPoste.add(panelSelectPays);
							panelSelectPays.setLayout(new GridLayout(3, 3, 0, 0));
						
								JLabel lblNewLabel = new JLabel();
								panelSelectPays.add(lblNewLabel);
								
								JLabel lblNewLabel_1 = new JLabel();
								panelSelectPays.add(lblNewLabel_1);
								
								JLabel lblNewLabel_2 = new JLabel();
								panelSelectPays.add(lblNewLabel_2);
								
								JLabel lblNewLabel_3 = new JLabel();
								panelSelectPays.add(lblNewLabel_3);
								
								comboBoxPays = new JComboBox<Pays>();
								remplirComboBoxPays(Pays.getAll());
								comboBoxPays.setSelectedItem(joueur.getPays());
								comboBoxPays.addActionListener(controle);
								panelSelectPays.add(comboBoxPays);
								
								JLabel lblNewLabel_4 = new JLabel();
								panelSelectPays.add(lblNewLabel_4);
								
								JLabel lblNewLabel_5 = new JLabel();
								panelSelectPays.add(lblNewLabel_5);
							
							JPanel panelSelectPoste = new JPanel();
							panelNatPoste.add(panelSelectPoste);
							panelSelectPoste.setLayout(new GridLayout(3, 3, 0, 0));
						
								JLabel lblNewLabel1 = new JLabel();
								panelSelectPoste.add(lblNewLabel1);
								
								JLabel lblNewLabel2 = new JLabel();
								panelSelectPoste.add(lblNewLabel2);
								
								JLabel lblNewLabel3 = new JLabel();
								panelSelectPoste.add(lblNewLabel3);
								
								JLabel lblNewLabel4 = new JLabel();
								panelSelectPoste.add(lblNewLabel4);
								
								comboBoxPoste = new JComboBox<Poste>();
								remplirComboBoxPoste(Poste.values());
								comboBoxPoste.setSelectedItem(joueur.getPoste());
								comboBoxPoste.addActionListener(controle);
								panelSelectPoste.add(comboBoxPoste);
								
								JLabel lblNewLabel5 = new JLabel();
								panelSelectPoste.add(lblNewLabel5);
								
								JLabel lblNewLabel6 = new JLabel();
								panelSelectPoste.add(lblNewLabel6);
				
			/*
			 * Footer contenant
			 * 2 boutons : quitter et modifier
			 */
			JPanel footer = new JPanel();
			contentPane.add(footer, BorderLayout.SOUTH);
			footer.setLayout(new BorderLayout(0, 0));
			
				Box horizontalBox = Box.createHorizontalBox();
				footer.add(horizontalBox, BorderLayout.EAST);
				
					JPanel panelDelete = new JPanel();
					horizontalBox.add(panelDelete);
					panelDelete.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					
						btnDelete = new RoundedButton("Supprimer le joueur");
						btnDelete.setForeground(Palette.DEL_BACKGROUND.getColor());
						btnDelete.setBackground(Palette.DEL_FOREGROUND.getColor());
						btnDelete.addActionListener(controle);
						panelDelete.add(btnDelete);
					
					JPanel ecart2 = new JPanel();
					horizontalBox.add(ecart2);
					
					JPanel panelAnnuler = new JPanel();
					horizontalBox.add(panelAnnuler);
					panelAnnuler.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					
						btnAnnuler = new RoundedButton("Annuler");
						btnAnnuler.addActionListener(controle);
						panelAnnuler.add(btnAnnuler);
				
					JPanel ecart = new JPanel();
					horizontalBox.add(ecart);
					
					JPanel panelAjouter = new JPanel();
					horizontalBox.add(panelAjouter);
					panelAjouter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					
						btnEnregistrer = new RoundedButton("Enregistrer");
						btnEnregistrer.addActionListener(controle);
						panelAjouter.add(btnEnregistrer);
					
	}

	/**
	 * Remplir la comboBox Pays
	 * @param paysList
	 */
	public void remplirComboBoxPays(List<Pays> paysList) {
        for (Pays p: paysList) {
            comboBoxPays.addItem(p);
        }
    }

	/**
	 * Remplir la comboBox Poste
	 * @param postes
	 */
	public void remplirComboBoxPoste(Poste[] postes) {
        for (Poste p: postes) {
            comboBoxPoste.addItem(p);
        }
    }

	/**
	 * Récupere le prénom du textField
	 * @return
	 */
	public String getThePrenom() {
    	return textFieldPrenom.getText();
    }

	/**
	 * Récupere le pseudo du textField
	 * @return
	 */
    public String getThePseudo() {
    	return textFieldPseudo.getText();
    }

	/**
	 * Récupere le nom du textField
	 * @return
	 */
    public String getTheNom() {
    	return textFieldNom.getText();
    }

	/**
	 * Récupere le poste de la comboBox
	 * @return
	 */
	public Poste getThePoste() {
    	return (Poste) comboBoxPoste.getSelectedItem();
    }

	/**
	 * Récupere la nationalité de la comboBox
	 * @return
	 */
	public Pays getThePays() {
    	return (Pays) comboBoxPays.getSelectedItem();
    }

	/**
	 * Récupere le chemin de l'image du joueur
	 * @return
	 */
	public JLabel getLabelImage() {
    	return lblPutImage;
    }

	/**
	 * Met à jour le drapeau
	 * @param p pays
	 */
	public void setFlag(Pays p) {
		lblFlag.setIcon(IconResized.of(p.getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
    }

	/**
	 * Met à jour l'image du poste
	 * @param p
	 */
	public void setIconPoste(Poste p) {
		lblIconPoste.setIcon(IconResized.of(p.getImage(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
    }

		public JPanel getContentPane() {
			return contentPane;
		}
}
