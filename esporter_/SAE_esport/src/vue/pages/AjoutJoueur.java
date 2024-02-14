package vue.pages;

import Controleur.ControleurAjoutJoueur;
import Modele.Pays;
import Modele.Poste;
import connect.DBConnect;
import vue.CustomContentPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AjoutJoueur extends JPanel {

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
	 * Labels pour l'insertion d'une image qui représente le joueur
	 * et pour l'affichage de la nationalité et du poste
	 */
	private JLabel lblPutImage;
	private JLabel lblIconPoste;
	private JLabel lblFlag;

	/**
	 * ComboBox pour la sélection de la nationalité et du poste
	 */
	private JComboBox<Pays> comboBoxPays;
	private JComboBox<Poste> comboBoxPoste;

	/**
	 * Controleur de la page
	 */
	private ControleurAjoutJoueur controleurAjoutJoueur; 
	
	public AjoutEquipe vueAjoutEquipe ;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException
     */
	public AjoutJoueur() throws SQLException {
		controleurAjoutJoueur = new ControleurAjoutJoueur(DBConnect.getDbInstance(), this);
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
		
			JLabel lblTitre = new JLabel("Nouveau Joueur");
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

							// initialisation de l'image par défaut
							lblPutImage = new JLabel(IconResized.of(IconResized.PLACEHOLDER).resize(IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
							lblPutImage.addMouseListener(controleurAjoutJoueur);
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
							panelInsertName.add(textFieldNom);
				
				JPanel panelNatPoste = new JPanel();
				panelInfo.add(panelNatPoste);
				panelNatPoste.setLayout(new GridLayout(2, 2, 0, 0));
					
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
					
						lblFlag = new JLabel(IconResized.of("/vue/flag/fr.png").resize(IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
						comboBoxPays = new JComboBox<Pays>();
						comboBoxPays.addActionListener(controleurAjoutJoueur);
						remplirComboBoxPays(Pays.getAll());
						panelSelectPays.add(comboBoxPays);
						comboBoxPays.setSelectedItem(comboBoxPays.getItemAt(0));
						
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
						
						lblIconPoste = new JLabel(IconResized.of(Poste.TOPLANE.getImage()).resize(IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
						comboBoxPoste = new JComboBox<Poste>();
						comboBoxPoste.addActionListener(controleurAjoutJoueur);
						remplirComboBoxPoste(Poste.values());
						panelSelectPoste.add(comboBoxPoste);
						comboBoxPoste.setSelectedItem(comboBoxPoste.getItemAt(0));
						
						JLabel lblNewLabel5 = new JLabel();
						panelSelectPoste.add(lblNewLabel5);
						
						JLabel lblNewLabel6 = new JLabel();
						panelSelectPoste.add(lblNewLabel6);
					
			
						JPanel panelNat = new JPanel();
						panelNatPoste.add(panelNat);
							panelNat.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						
							JLabel lblNationalite = new JLabel("Nationalité :    ");
							lblNationalite.setHorizontalAlignment(SwingConstants.CENTER);
							panelNat.add(lblNationalite);

							lblFlag = new JLabel(IconResized.of(((Pays) this.comboBoxPays.getSelectedItem()).getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
							lblFlag.setHorizontalAlignment(SwingConstants.CENTER);
							panelNat.add(lblFlag);
						
						JPanel panelPoste = new JPanel();
						panelNatPoste.add(panelPoste);
							panelPoste.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						
							JLabel lblNomPoste = new JLabel("Poste :    ");
							panelPoste.add(lblNomPoste);

							lblIconPoste = new JLabel(IconResized.of(((Poste) this.comboBoxPoste.getSelectedItem()).getImage(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
							lblIconPoste.setHorizontalAlignment(SwingConstants.CENTER);
						panelPoste.add(lblIconPoste);
		/*
		 * Footer contenant
		 * 2 boutons : quitter et modifier
		 */
			JPanel footer = new JPanel();
			contentPane.add(footer, BorderLayout.SOUTH);
			footer.setLayout(new BorderLayout(0, 0));
			
			Box horizontalBox = Box.createHorizontalBox();
			footer.add(horizontalBox, BorderLayout.EAST);
			
				JPanel panelAnnuler = new JPanel();
				horizontalBox.add(panelAnnuler);
				panelAnnuler.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
					JButton btnAnnuler = new RoundedButton("Annuler");
					btnAnnuler.addMouseListener(controleurAjoutJoueur);
					panelAnnuler.add(btnAnnuler);
				
				JPanel ecart = new JPanel();
				horizontalBox.add(ecart);
				
				JPanel panelAjouter = new JPanel();
				horizontalBox.add(panelAjouter);
				panelAjouter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
					JButton btnAjouter = new RoundedButton("Ajouter");
					btnAjouter.addMouseListener(controleurAjoutJoueur);
					panelAjouter.add(btnAjouter);
	}

	/**
	 * Remplir la comboBox des pays
	 * @param paysList
	 */
	public void remplirComboBoxPays(List<Pays> paysList) {
		for (Pays p: paysList) {
			comboBoxPays.addItem(p);
		}
	}

	/**
	 * Remplir la comboBox des postes
	 * @param postes
	 */
	public void remplirComboBoxPoste(Poste[] postes) {
		for (Poste p: postes) {
			comboBoxPoste.addItem(p);
		}
	}

	/**
	 * Récupére le nom du joueur
	 * @return le nom du joueur
	 */
	public String getNom() {
		return this.textFieldNom.getText();
	}

	/**
	 * Récupére le prénom du joueur
	 * @return le prénom du joueur
	 */
	public String getPrenom() {
		return this.textFieldPrenom.getText();
	}

	/**
	 * Récupére le pseudo du joueur
	 * @return le pseudo du joueur
	 */
	public String getPseudo() {
		return this.textFieldPseudo.getText();
	}


	/**
	 * Permet de mettre à jour l'image du joueur
	 * @param path
	 */
	public void setImageNationalité(String path) {
		this.lblFlag.setIcon(IconResized.of(path,IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
	}

	/**
	 * Permet de mettre à jour l'image du poste
	 * @param path
	 */
	public void setImagePoste(String path) {
		this.lblIconPoste.setIcon(IconResized.of(path,IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
	}

	/**
	 * Permet de récupérer le chemin de l'image du joueur
	 */
	public JLabel getLabelImage() {
		return this.lblPutImage;
	}

	/**
	 * Permet de récupérer la nationalité du joueur dans la comboBox Pays
	 * @return la nationalité du joueur
	 */
	public Pays getNationalite() {
		Object p = this.comboBoxPays.getSelectedItem();
		if (p instanceof Pays) {
			return (Pays) p;
		}
		return null;
	}

	/**
	 * Permet de récupérer le poste du joueur dans la comboBox Poste
	 * @return le poste du joueur
	 */
	public Poste getPoste() {
		Object p = this.comboBoxPoste.getSelectedItem();
		if (p instanceof Poste) {
			return (Poste) p;
		}
		return null;
	}

	/**
	 * Permet de savoir si tous les champs sont remplis
	 * @return true si tous les champs sont remplis, false sinon
	 */
	public boolean allFieldFill() {
		return !this.textFieldNom.getText().isEmpty() &&
				!this.textFieldPrenom.getText().isEmpty() &&
				!this.textFieldPseudo.getText().isEmpty() &&
				this.comboBoxPays.getSelectedItem() != null &&
				this.comboBoxPoste.getSelectedItem() != null;

	}

}
