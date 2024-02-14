package vue.pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controleur.ControleurModifierEquipe;
import Modele.Equipe;
import Modele.Joueur;
import Modele.Pays;
import vue.APP;
import vue.CustomContentPane;
import vue.Observer;

import java.awt.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import javax.swing.table.DefaultTableModel;

public class ModifierEquipe extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Le panel de la page
	 */
	private JPanel contentPane;

	/**
	 * La table contenant les joueurs de l'équipe
	 */
	private JTable tableMembre;

	/**
	 * Le TextField contenant une entrée pour le nom de l'équipe.
	 * Permet de récupérer le nouveau nom entré par l'utilisateur.
	 */
	private JTextField textField_Nom;

	/**
	 * Le TextField contenant une entrée pour le code de l'équipe.
	 * Permet de récupérer le nouveau nom entré par l'utilisateur.
	 */
	private JTextField txtCode;

	/**
	 * La ComboBox des pays possible.
	 * Permet de sélectionner le pays de l'équipe
	 */
	private JComboBox<Pays> comboBoxPays;

	/**
	 * L'icon du drapeau sélectionné
	 */
	private JLabel lblFlag;

	/**
	 * L'icon permettant a l'utilisateur de sélectionner un logo pour l'équipe depuis son ordinateur
	 */
	private JLabel lblPutImage;

	/**
	 * La comboBox permetant d'ajouter des joueurs à l'équipe
	 */
	private JComboBox<Joueur> comboBoxNvJr = new JComboBox<Joueur>();

	/**
	 * L'équipe concernée par la page
	 */
	private Equipe equipe;

	/**
	 * Le modèle de la table
	 */
	private DefaultTableModel modeleTable;

	/**
	 * Le controleur de la page
	 */
	private ControleurModifierEquipe controle;
	private JButton btnCreerMembre;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException
	 */
	public ModifierEquipe(Equipe eq,  ListeEquipe vueListeEquipe) throws SQLException {
		
		this.equipe = eq;
		this.controle = new ControleurModifierEquipe(this, vueListeEquipe);
		setBounds(100, 100, 1000, 688);
		contentPane = new CustomContentPane(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		/*
		 * Panel contenant le titre
		 */
		
		JPanel panelTitre = new JPanel();
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
			JLabel lblTitre = new JLabel("Modifier l'équipe");
			lblTitre.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
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

							lblPutImage = new JLabel(IconResized.of(equipe.getImage(), IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
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
								
								JLabel lblWrldRank = new JLabel("World Ranking :  ");
								lblWrldRank.setHorizontalAlignment(SwingConstants.RIGHT);
								panelLabel.add(lblWrldRank);
								
								JPanel panel_3_1 = new JPanel();
								panelLabel.add(panel_3_1);
							
							JPanel panelTextField = new JPanel();
							panelInsertName.add(panelTextField);
							panelTextField.setLayout(new GridLayout(0, 1, 0, 0));
							
								textField_Nom = new JTextField();
								textField_Nom.setDocument(new LimitJTextField((50)));
								textField_Nom.setColumns(10);
								textField_Nom.setText(this.equipe.getNom());
								panelTextField.add(textField_Nom);
	
								JPanel panel_1 = new JPanel();
								panelTextField.add(panel_1);
								
								txtCode = new JTextField();
								txtCode.setDocument(new LimitJTextField(3));
								txtCode.setText(this.equipe.getCode());
								txtCode.setColumns(10);
								panelTextField.add(txtCode);
								
								JPanel panel = new JPanel();
								panelTextField.add(panel);
								
								JSpinner spinner = new JSpinner();
								spinner.setValue(this.equipe.getWorldRank());
								panelTextField.add(spinner);
								
								JPanel panel_4 = new JPanel();
								panelTextField.add(panel_4);
							
				
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
							lblFlag = new JLabel(IconResized.of(this.equipe.getPays().getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
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
							comboBoxPays.setForeground(new Color(50, 151, 27));
							comboBoxPays.setBackground(new Color(38, 60, 51));
							comboBoxPays.addActionListener(controle);
							remplirComboBoxPays(Pays.getAll());
							comboBoxPays.setSelectedItem(eq.getPays());

							panelSelectPays.add(comboBoxPays);

							JLabel lblNewLabel5 = new JLabel();
							panelSelectPays.add(lblNewLabel5);
							
							JLabel lblNewLabel6 = new JLabel();
							panelSelectPays.add(lblNewLabel6);
						
						JPanel panelMembre = new JPanel();
						panelNatPoste.add(panelMembre);
							panelMembre.setLayout(new BorderLayout(0, 0));
							
							JPanel panel_5 = new JPanel();
							panelMembre.add(panel_5, BorderLayout.NORTH);
							panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
							
							
							
							btnCreerMembre = new RoundedButton("+");
							btnCreerMembre.addActionListener(controle);
							panel_5.setLayout(new BorderLayout(0, 0));
							panel_5.add(btnCreerMembre, BorderLayout.WEST);
							
							
							comboBoxNvJr.addItemListener(controle);
							comboBoxNvJr.addMouseListener(controle);
							comboBoxNvJr.setModel(new DefaultComboBoxModel<>());
							panel_5.add(comboBoxNvJr);
							
							
							
							
							this.refreshComboBoxJoueur();

						
							JScrollPane scrollPaneMembre = new JScrollPane();
							panelMembre.add(scrollPaneMembre);
							
							this.modeleTable = new DefaultTableModel(new Object[] {"IMG", "NOM", "RETIRER","ID"}, 0);
							tableMembre = new JTable() {
							
								private static final long serialVersionUID = 1L;

							public boolean isCellEditable(int row, int column) {
					                return false;               
					        };};
							tableMembre.addMouseListener(controle);
							tableMembre.setModel(this.modeleTable);
							
								tableMembre.setShowHorizontalLines(true);
						
								tableMembre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								tableMembre.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);
								tableMembre.getColumnModel().getColumn(2).setCellRenderer(ImageRenderer.instance);
								tableMembre.getColumnModel().getColumn(0).setPreferredWidth(5);
								tableMembre.getColumnModel().getColumn(0).setResizable(false);
								
								tableMembre.getColumnModel().getColumn(2).setPreferredWidth(25);
								tableMembre.getColumnModel().getColumn(2).setResizable(false);
								
								
								tableMembre.setRowHeight(50);
								tableMembre.getTableHeader().setReorderingAllowed(false);

								tableMembre.getColumnModel().getColumn(3).setMaxWidth(0);
								tableMembre.getColumnModel().getColumn(3).setMinWidth(0);
								tableMembre.getColumnModel().getColumn(3).setPreferredWidth(0);
								tableMembre.getColumnModel().getColumn(3).setResizable(false);

								scrollPaneMembre.setViewportView(tableMembre);
								
								this.fillTableJoueur();

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
				
					JButton btnDelete = new RoundedButton("Supprimer l'équipe");
					btnDelete.setForeground(Palette.DEL_BACKGROUND.getColor());
					btnDelete.setBackground(Palette.DEL_FOREGROUND.getColor());
					btnDelete.addMouseListener(controle);
					panelDelete.add(btnDelete);
				
				JPanel ecart2 = new JPanel();
				horizontalBox.add(ecart2);
			
				JPanel panelAjouter = new JPanel();
				horizontalBox.add(panelAjouter);
				panelAjouter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
					JButton btnEnregistrer = new RoundedButton("Enregistrer");
					btnEnregistrer.addActionListener(controle);
					panelAjouter.add(btnEnregistrer);
				
				JPanel ecart = new JPanel();
				horizontalBox.add(ecart);
				
				JPanel panelAnnuler = new JPanel();
				horizontalBox.add(panelAnnuler);
				panelAnnuler.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
				JButton btnAnnuler = new RoundedButton("Annuler");
				btnAnnuler.addMouseListener(controle);
				panelAnnuler.add(btnAnnuler);
					
				
	}
	
	
	/**
	 * Getter de l'équipe concernée par la page
	 * @return L'équipe concernée
	 */
	public Equipe getEquipe() {
		return equipe;
	}
	
	/**
	 * Setter de l'équipe
	 * @param equipe L'équipe
	 */
	public void setEquipe(Equipe equipe) {
		this.equipe  = equipe;
	}
	
	/**
	 * Fonction pour récupérer le nom entré par l'utilisateur
	 * @return Le string du nom
	 */
	public String getNewNom() {
		String nom = textField_Nom.getText();
		if (nom.length() < 3) {
			nom = equipe.getNom();
		}
		return textField_Nom.getText();
	}

	/**
	 * Fonction pour récupérer le code entré par l'utilisateur
	 * @return Le code
	 */
	public String getNewCode() {
		String code = txtCode.getText();
		if (code.length() != 3) {
			code = equipe.getCode();
		}
		return code;
	}
	
	/**
	 * Fonction pour récupérer le Joueur sélectionné par l'équipe
	 * @return le Joueur sélectionné dans la combo Box
	 */
	public Joueur getSelectedJoueur() {
		return (Joueur) comboBoxNvJr.getSelectedItem();
	}
	
	
	/**
	 * Fonction mettant a jour la comboBox des joueurs disponibles avec les joueurs sans equipes
	 *
	 */
	public void refreshComboBoxJoueur() {
		comboBoxNvJr.removeAllItems();
		comboBoxNvJr.setEnabled(false);
		this.controle.ignoreJcomboBox = true;
		for(Joueur joueur : Joueur.getAllSansEquipe()) {
			System.out.println("joueur sans equipe" + joueur.getFullName());
			comboBoxNvJr.addItem(joueur);
		}
		comboBoxNvJr.setSelectedIndex(-1);
		comboBoxNvJr.setEnabled(true);
		this.controle.ignoreJcomboBox = false;
		
	}
	
	/**
	 * Fonction pour remplir la ComboBox des pays
	 * @param paysList La liste des pays
	 */
	public void remplirComboBoxPays(List<Pays> paysList) {
        for (Pays p: paysList) {
            comboBoxPays.addItem(p);
        }
    }
		
	/**
	 * Fonction pour changer l'image du drapeau affiché
	 * @param p Le pays du drapeau
	 */
	public void setFlag(Pays p) {
        lblFlag.setIcon(IconResized.of(p.getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
	}

	/**
	 * Permet de récupérer le chemin de l'image du joueur
	 */
	public JLabel getLabelImage() {
		return lblPutImage;
	}

	/**
	 * Fonction pour récupérer le nouveau pays de l'équipe.
	 * @return Le pays sélectionné dans la combobox
	 */
	public Pays getNewPays() {
		return (Pays) comboBoxPays.getSelectedItem();
	}
	
	/**
	 * Fonction qui récupère le modèle de la table
	 * @return Le modèle de la table
	 */
	public DefaultTableModel getModeleTable() {
		return this.modeleTable;
	}
	
	public int getNbPlayer() {
		return this.modeleTable.getRowCount();
	}
	
	/**
	 * Fonction pour remplir la table des joueurs de l'équipe
	 * @throws SQLException
	 */
	public void fillTableJoueur() throws SQLException {
		this.equipe.getJoueurs().removeAll(this.equipe.getJoueurs());
		this.modeleTable.setRowCount(0);
		for (Joueur j : Joueur.getByEquipe(equipe)) {
			this.equipe.addJoueur(j);
			this.modeleTable.addRow(new Object[] {j.getImage(), j.getFullName(), "trash", j.getId()});
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		refreshComboBoxJoueur();
        try {
            fillTableJoueur();
        } catch (SQLException e) {
			System.err.println("failed to refresh liste joueurs");
        	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de la mise à jour de la liste des joueurs");
        	//TODO on fait quoi après ?
        	APP.exit();
        }
    }

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Joueur"};
	}
}
