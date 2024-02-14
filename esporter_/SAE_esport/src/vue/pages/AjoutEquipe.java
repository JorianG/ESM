package vue.pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Controleur.ControleurAjoutEquipe;
import Modele.Joueur;
import Modele.Pays;
import vue.CustomContentPane;
import vue.Observer;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;

public class AjoutEquipe extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Le panel de la page
	 */
	private JPanel contentPane;

	/**
	 * La table contenant les membres de l'équipe ajoutés
	 */
	private JTable tableMembre;

	/**
	 * Le TextField contenant le nom de l'équipe
	 */
	private JTextField tfNom;

	/**
	 * Le TextField contenant le code de l'équipe
	 */
	private JTextField tfCode;

	/**
	 * La ComboBox permettant de choisir le pays de l'équipe
	 */
	private JComboBox<Pays> comboBoxPays;

	/**
	 * La ComboBox permettant de sélectionner les pays de l'équipe
	 */
	private static JComboBox<Joueur> comboBoxAjoutMembre; 

	/**
	 * L'icon du drapeau du pays de l'équipe
	 */
	private JLabel lblFlag;

	/**
	 * L'icon du choix de l'image de l'équipe
	 */
	private JLabel lblPutImage;

	/**
	 * Le spinner pour sélectionner le rang de l'équipe
	 */
	private JSpinner spinnerRang;

	private DefaultTableModel modeleTable;
	
	private ControleurAjoutEquipe controle;

	public List<Joueur> lstJoueurSansEquipe;

	private boolean isFromAjoutSaison = false;
	private boolean isFromAjoutTournoi = false;

	private JButton btnAjouter = new RoundedButton("Ajouter");

	public JPanel getContentPane() {
		return contentPane;
	}

	public AjoutEquipe(Boolean isFromAjoutSaison, Boolean isFromAjoutTournoi) throws SQLException {
		this();
		this.isFromAjoutSaison = isFromAjoutSaison;
		this.isFromAjoutTournoi = isFromAjoutTournoi;
	}
	
	/**
	 * Page qui permet d'ajouter une équipe dans la base de données. 
     */
	public AjoutEquipe() throws SQLException {
		controle = new ControleurAjoutEquipe(this);
		lstJoueurSansEquipe = Joueur.getAllSansEquipe();
		setBounds(100, 100, 1000, 500);
		contentPane = new CustomContentPane(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		/*
		 * Panel contenant le titre
		 */
		
		JPanel panelTitre = new JPanel();
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
			JLabel lblTitre = new JLabel("Nouvelle équipe");
			panelTitre.add(lblTitre);
		
			
		/*
		 * Panel contenant
		 * l'insertion des infos :
		 * 		Nom de l'équipe et son code
		 * 		
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
						
							IconResized iconPutImage = IconResized.of(IconResized.PLACEHOLDER);
							lblPutImage = new JLabel(iconPutImage.resize(IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
							lblPutImage.addMouseListener(controle);
							lblPutImage.setHorizontalAlignment(SwingConstants.CENTER);
							panelPutImage.add(lblPutImage);
					
						JPanel panelInsertName = new JPanel();
						panelIdentite.add(panelInsertName);
						panelInsertName.setLayout(new GridLayout(0, 2, 0, 0));
							
							JPanel panelLabel = new JPanel();
							panelInsertName.add(panelLabel);
							panelLabel.setLayout(new GridLayout(6, 1, 0, 0));
							
								JLabel lblNomEquipe = new JLabel("Nom de l'équipe :  ");
								lblNomEquipe.setHorizontalAlignment(SwingConstants.RIGHT);
								panelLabel.add(lblNomEquipe);
								
								JPanel panel_2 = new JPanel();
								panelLabel.add(panel_2);
								
								JLabel lblCodeEquipe = new JLabel("Code :  ");
								lblCodeEquipe.setHorizontalAlignment(SwingConstants.RIGHT);
								panelLabel.add(lblCodeEquipe);
								
								JPanel panel_3 = new JPanel();
								panelLabel.add(panel_3);
								
								JLabel lblWorldRank = new JLabel("Rang de l'équipe :  ");
								lblWorldRank.setHorizontalAlignment(SwingConstants.RIGHT);
								panelLabel.add(lblWorldRank);
								
								JPanel panel_4 = new JPanel();
								panelLabel.add(panel_4);
							
							JPanel panelTextField = new JPanel();
							panelInsertName.add(panelTextField);
							panelTextField.setLayout(new GridLayout(6, 1, 0, 0));
							
								tfNom = new JTextField();
								tfNom.setDocument(new LimitJTextField(50));
								tfNom.setColumns(10);
								panelTextField.add(tfNom);
	
								JPanel panel_1 = new JPanel();
								panelTextField.add(panel_1);
								
								tfCode = new JTextField();
								tfCode.setDocument(new LimitJTextField(3));
								tfCode.setColumns(10);
								panelTextField.add(tfCode);
								
								JPanel panel = new JPanel();
								panelTextField.add(panel);
								
								spinnerRang = new JSpinner();
								spinnerRang.setModel(new SpinnerNumberModel(Integer.valueOf(1000), null, null, Integer.valueOf(1)));
								panelTextField.add(spinnerRang);
								
								JPanel panel_ = new JPanel();
								panelTextField.add(panel_);
							
				
				JPanel panelNatPoste = new JPanel();
				panelInfo.add(panelNatPoste);
				panelNatPoste.setLayout(new GridLayout(1, 2, 0, 0));
					
					JPanel panelPays = new JPanel();
					panelNatPoste.add(panelPays);
						
						JPanel panelPaysTitre = new JPanel();
						panelPays.add(panelPaysTitre);
						panelPaysTitre.setLayout(new GridLayout(0, 2, 0, 0));
							
							JLabel lblPays = new JLabel("Pays :");
							lblPays.setHorizontalAlignment(SwingConstants.CENTER);
							panelPaysTitre.add(lblPays);

							panelPays.setLayout(new GridLayout(2, 1, 0, 0));
							lblFlag = new JLabel(IconResized.of("/vue/flag/fr.png", IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
							lblFlag.setHorizontalAlignment(SwingConstants.CENTER);
							panelPaysTitre.add(lblFlag);
						
						JPanel panelSelectPays = new JPanel();
						panelPays.add(panelSelectPays);
						panelSelectPays.setLayout(new GridLayout(3, 3, 0, 0));
						
							JLabel lblNewLabel1 = new JLabel();
							panelSelectPays.add(lblNewLabel1);
							
							JLabel lblNewLabel2 = new JLabel();
							panelSelectPays.add(lblNewLabel2);
							
							JLabel lblNewLabel3 = new JLabel();
							panelSelectPays.add(lblNewLabel3);
							
							JLabel lblNewLabel4 = new JLabel();
							panelSelectPays.add(lblNewLabel4);
							
							comboBoxPays = new JComboBox<Pays>();
							comboBoxPays.addActionListener(controle);
							panelSelectPays.add(comboBoxPays);
							remplirComboBoxPays();
							comboBoxPays.setSelectedIndex(0);
							setFlag(((Pays) comboBoxPays.getSelectedItem()));
							
							
							JLabel lblNewLabel5 = new JLabel();
							panelSelectPays.add(lblNewLabel5);
							
							JLabel lblNewLabel6 = new JLabel();
							panelSelectPays.add(lblNewLabel6);
						
						JPanel panelMembre = new JPanel();
						panelNatPoste.add(panelMembre);
							panelMembre.setLayout(new BorderLayout(0, 0));
						
							JScrollPane scrollPaneMembre = new JScrollPane();
							panelMembre.add(scrollPaneMembre);
							
							modeleTable = new DefaultTableModel(new Object[] {"IMG", "NOM", "RETIRER",""}, 0);
							tableMembre = new JTable() {
							/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

							public boolean isCellEditable(int row, int column) {
					                return false;               
					        };};
							tableMembre.addMouseListener(controle);
							tableMembre.setModel(modeleTable);
							
								tableMembre.setShowHorizontalLines(true);
						
								tableMembre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								tableMembre.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);
								tableMembre.getColumnModel().getColumn(2).setCellRenderer(ImageRenderer.instance);
								tableMembre.getColumnModel().getColumn(0).setPreferredWidth(5);
								tableMembre.getColumnModel().getColumn(0).setResizable(false);
								tableMembre.getColumnModel().getColumn(1).setPreferredWidth(25);
								tableMembre.getColumnModel().getColumn(1).setResizable(false);
								tableMembre.getColumnModel().getColumn(2).setPreferredWidth(5);
								tableMembre.setRowHeight(50);
								tableMembre.getColumnModel().getColumn(2).setResizable(false);

								tableMembre.getColumnModel().getColumn(3).setMaxWidth(0);
								tableMembre.getColumnModel().getColumn(3).setMinWidth(0);
								tableMembre.getColumnModel().getColumn(3).setPreferredWidth(0);
								tableMembre.getColumnModel().getColumn(3).setResizable(false);
								
								
								tableMembre.getTableHeader().setReorderingAllowed(false);
								
								
								scrollPaneMembre.setViewportView(tableMembre);
								
								JPanel paneControleMembr = new JPanel();
								panelMembre.add(paneControleMembr, BorderLayout.NORTH);
								paneControleMembr.setLayout(new BorderLayout(0, 0));
								
								JButton btnCreerMembre = new RoundedButton("+");
								btnCreerMembre.addActionListener(controle);
								paneControleMembr.add(btnCreerMembre, BorderLayout.WEST);
								
								comboBoxAjoutMembre = new JComboBox<Joueur>();
								comboBoxAjoutMembre.addItemListener(controle);
								comboBoxAjoutMembre.addMouseListener(controle);
								comboBoxAjoutMembre.setModel(new DefaultComboBoxModel<>());
								paneControleMembr.add(comboBoxAjoutMembre);	
								
								refreshComboBoxJoueur();
						
				
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
					btnAnnuler.addActionListener(controle);
					panelAnnuler.add(btnAnnuler);
				
				JPanel ecart = new JPanel();
				horizontalBox.add(ecart);
				
				JPanel panelAjouter = new JPanel();
				horizontalBox.add(panelAjouter);
				panelAjouter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
					btnAjouter.addActionListener(controle);
					panelAjouter.add(btnAjouter);
	}
	
	public boolean isFromAjoutSaison() {
		return this.isFromAjoutSaison;
	}

	public boolean isFromAjoutTournoi() {
		return this.isFromAjoutTournoi;
	}

	/**
	 * Fonction pour remplir la combo box de la liste des pays.
	 */
	public void remplirComboBoxPays() {
		for (Pays p: Pays.getAll()) {
			comboBoxPays.addItem(p);
		}
	}
	
	
	/**
	 * Fonction pour vider la combo box de sélection de joueurs.
	 */
	public static void viderComboBoxJoueur() {
		comboBoxAjoutMembre.removeAllItems();
	}
	
	/**
	 * Fonction qui change le drapeau affiché
	 * @param p Le pays dont on affiche le drapeau
	 */
	public void setFlag(Pays p) {
		lblFlag.setIcon(IconResized.of(p.getDrapeau()).resize(IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
    }

	/**
	 * Permet de récupérer le nom inscrit dans la page
	 * @return Le nom de l'équipe
	 */
	public String getNom() {
		return tfNom.getText();
	}
	
	/**
	 * Permet de récupérer le code inscrit dans la page
	 * @return Le code de l'équipe
	 */
	public String getCode() {
		return tfCode.getText();
	}
	
	/**
	 * Permet de récupérer le chemin de l'image choisie dans la page
	 * @return Le chemin vers le logo de l'équipe
	 */
	public JLabel getLabelImage() {
		return this.lblPutImage;
	}
	
	/**
	 * Permet de récupérer le rang choisi dans la page
	 * @return Le rang de l'équipe
	 */
	public int getRang() {
		return (int) spinnerRang.getValue();
	}
	
	/**
	 * Permet de récupérer le pays choisi dans la page
	 * @return Le pays de l'équipe
	 */
	public Pays getPays() {
		return (Pays) comboBoxPays.getSelectedItem();
	}

	public void refreshComboBoxJoueur() {
		comboBoxAjoutMembre.removeAllItems();
		comboBoxAjoutMembre.setEnabled(false);
		this.controle.ignoreJcomboBox = true;
		
			for(Joueur joueur : this.lstJoueurSansEquipe ) {
				comboBoxAjoutMembre.addItem(joueur);
			}
		
		
		comboBoxAjoutMembre.setSelectedIndex(-1);
		comboBoxAjoutMembre.setEnabled(true);
		controle.ignoreJcomboBox = false;
		
	}

	public DefaultTableModel getModeleTable() {
		return this.modeleTable;
	}



	public Joueur getSelectedJoueur() {
		return (Joueur) AjoutEquipe.comboBoxAjoutMembre.getSelectedItem();
	}

	public void setButtonAjouter(boolean enabled) {
		btnAjouter.setEnabled(enabled);
	}
    
	@Override
	public void update(Observable o, Object arg) {
		this.refreshComboBoxJoueur();
	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Joueur"};
	}
}
