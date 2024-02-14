package vue.pages;

import Controleur.ControleurAjoutTournoi;
import Modele.Equipe;
import Modele.NiveauTournoi;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;
import vue.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class AjoutTournoi extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNomTournoi;
	private JTable tableEquipes;
	private JComboBox<NiveauTournoi> comboBoxNiveauTournoi;
	private JComboBox<String> comboBoxJourD;
	private JComboBox<String> comboBoxMoisD;
	private JComboBox<String> comboBoxJourF;
	private JComboBox<String> comboBoxMoisF;
	private JButton btnAjout;
	private JLabel lblImage;
	private DefaultTableModel modeleTable;
	private ControleurAjoutTournoi controle;

	public List<Equipe> equipes;
	public List<Equipe> equipesAdded;
	private JComboBox<Equipe> comboEquipe;
	
	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public AjoutTournoi() {
		
		this.controle = new ControleurAjoutTournoi(this);
		//Set equipe depuis les equipe de la saiosn

		this.equipes = Equipe.getAll();
		this.equipesAdded = new ArrayList<Equipe>();

		setBounds(100, 100, 1000, 548);
		contentPane = new CustomContentPane(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		ControleurAjoutTournoi controleur = new ControleurAjoutTournoi(this);

		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_titre = new JPanel();
		contentPane.add(panel_titre, BorderLayout.NORTH);
		
		JLabel lblTitrePage = new JLabel("Ajout d'un tournoi");
		lblTitrePage.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 24));
		panel_titre.add(lblTitrePage);
		
		JPanel panel_content = new JPanel();
		contentPane.add(panel_content, BorderLayout.CENTER);
		panel_content.setLayout(new MigLayout("", "[233.00,grow][220.00][grow][grow]", "[39.00][55.00,grow][59.00][grow][][grow]"));
		
		JPanel panel_image = new JPanel();
		panel_content.add(panel_image, "cell 0 1 1 2,grow");
		
		JLabel lblImageText = new JLabel("Image du tournoi :  ");
		panel_image.add(lblImageText);
		
		lblImage = new JLabel("");
		lblImage.setIcon(IconResized.of(IconResized.PLACEHOLDER, IconResized.DEFAULT_HEIGHT_POSTE, IconResized.DEFAULT_WIDTH_POSTE));
		lblImage.addMouseListener(controleur);
		panel_image.add(lblImage);
		
		JPanel panel_10 = new JPanel();
		panel_content.add(panel_10, "cell 1 1 3 1,grow");
		
		JLabel lblNomTournoi = new JLabel("Nom du tournoi : ");
		panel_10.add(lblNomTournoi);
		
		textFieldNomTournoi = new JTextField();
		textFieldNomTournoi.setColumns(10);
		panel_10.add(textFieldNomTournoi);
		
		JPanel panel = new JPanel();
		panel_content.add(panel, "cell 1 2 3 1,grow");
		
		JLabel lblNiveauTournoi = new JLabel("Niveau du tournoi : ");
		panel.add(lblNiveauTournoi);
		
		comboBoxNiveauTournoi = new JComboBox<NiveauTournoi>();
		for (NiveauTournoi nt : NiveauTournoi.values()) {
			comboBoxNiveauTournoi.addItem(nt);
		}
		panel.add(comboBoxNiveauTournoi);
		
		JPanel panel_11 = new JPanel();
		panel_content.add(panel_11, "cell 2 3,grow");
		
		JPanel panel_1 = new JPanel();
		panel_content.add(panel_1, "cell 0 5,grow");
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblDateD = new JLabel("Date de début");
		lblDateD.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 18));
		lblDateD.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDateD.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_1.add(lblDateD);
		
		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		
		JLabel lblJourD = new JLabel("Jour : ");
		panel_4.add(lblJourD);
		
		comboBoxJourD = new JComboBox<String>();
		comboBoxJourD.addItem("-- jour de début --");
		for (int i = 1; i<=31; i++) {
			comboBoxJourD.addItem(String.valueOf(i));
		}
		comboBoxJourD.addActionListener(controleur);
		panel_4.add(comboBoxJourD);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		
		JLabel lblMoisD = new JLabel("Mois : ");
		panel_5.add(lblMoisD);
		
		comboBoxMoisD = new JComboBox<String>();
		comboBoxMoisD.setModel(new DefaultComboBoxModel<String>());
		comboBoxMoisD.addItem("-- mois de début --");
		for (int i = 1; i<=12; i++) {
			comboBoxMoisD.addItem(String.valueOf(i));
		}
		comboBoxMoisD.addActionListener(controleur);
		panel_5.add(comboBoxMoisD);
		
		JPanel panel_8 = new JPanel();
		panel_1.add(panel_8);
		
		JPanel panel_3 = new JPanel();
		panel_content.add(panel_3, "cell 1 5,grow");
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JLabel lblDateF = new JLabel("Date de fin");
		lblDateF.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(lblDateF);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		
		JPanel panel_4_1 = new JPanel();
		panel_3.add(panel_4_1);
		
		JLabel lblJourF = new JLabel("Jour : ");
		panel_4_1.add(lblJourF);
		
		comboBoxJourF = new JComboBox<String>();
		comboBoxJourF.addItem("-- jour de fin --");
		for (int i = 1; i<=31; i++) {
			comboBoxJourF.addItem(String.valueOf(i));
		}
		comboBoxJourF.addActionListener(controleur);
		panel_4_1.add(comboBoxJourF);
		
		JPanel panel_5_1 = new JPanel();
		panel_3.add(panel_5_1);
		
		JLabel lblMoisF = new JLabel("Mois : ");
		panel_5_1.add(lblMoisF);
		
		comboBoxMoisF = new JComboBox<String>();
		comboBoxMoisF.setModel(new DefaultComboBoxModel<String>());
		comboBoxMoisF.addItem("-- mois de fin --");
		for (int i = 1; i<=12; i++) {
			comboBoxMoisF.addItem(String.valueOf(i));
		}
		comboBoxMoisF.addActionListener(controleur);
		panel_5_1.add(comboBoxMoisF);
		
		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9);
		
		JPanel panel_2 = new JPanel();
		panel_content.add(panel_2, "cell 3 5,grow");
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_2.add(panel_12, BorderLayout.NORTH);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JButton btnCreerEquipe = new RoundedButton("+");
		btnCreerEquipe.addActionListener(controle);
		panel_12.add(btnCreerEquipe, BorderLayout.WEST);
		
		comboEquipe = new JComboBox<Equipe>();
		comboEquipe.addItemListener(controle);
		comboEquipe.addMouseListener(controle);
		comboEquipe.setModel(new DefaultComboBoxModel<>());
		panel_12.add(comboEquipe, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new Dimension(150, 150));
		panel_2.add(scrollPane);
		
		this.modeleTable = new DefaultTableModel(new Object[] {"IMG", "NOM", "RETIRER","ID"}, 0);
		tableEquipes = new JTable(){
			
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
                return false;               
        };};
		tableEquipes.setPreferredScrollableViewportSize(new Dimension(450, 190));
        

		tableEquipes.addMouseListener(controle);
		tableEquipes.setModel(this.modeleTable);
		
			tableEquipes.setShowHorizontalLines(true);
	
			tableEquipes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableEquipes.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);
			tableEquipes.getColumnModel().getColumn(2).setCellRenderer(ImageRenderer.instance);
			tableEquipes.getColumnModel().getColumn(0).setPreferredWidth(5); // TODO : constante ?
			tableEquipes.getColumnModel().getColumn(0).setResizable(false);
			
			tableEquipes.getColumnModel().getColumn(2).setPreferredWidth(25); // TODO : constante ?
			tableEquipes.getColumnModel().getColumn(2).setResizable(false);

		tableEquipes.getColumnModel().getColumn(2).setMaxWidth(0);
		tableEquipes.getColumnModel().getColumn(2).setMinWidth(0);
		tableEquipes.getColumnModel().getColumn(2).setPreferredWidth(0);
		tableEquipes.getColumnModel().getColumn(2).setResizable(false);
			
			tableEquipes.setRowHeight(50);
			tableEquipes.getTableHeader().setReorderingAllowed(false);

			
			scrollPane.setViewportView(tableEquipes);
			
			
			
		
		JPanel panel_boutons = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_boutons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_boutons, BorderLayout.SOUTH);
		
		JButton btnAnnuler = new RoundedButton("Annuler");
		btnAnnuler.addActionListener(controleur);
		panel_boutons.add(btnAnnuler);
		
		btnAjout = new RoundedButton("Creer");
		btnAjout.addActionListener(controleur);
		btnAjout.setEnabled(false);
		panel_boutons.add(btnAjout);
	}




	public void setComboBoxJourD(int tailleMois) {
		int index = comboBoxJourD.getSelectedIndex();
		comboBoxJourD.removeAllItems();
		comboBoxJourD.addItem("-- jour de début --");
		for (int i = 1; i<=tailleMois; i++) {
			comboBoxJourD.addItem(String.valueOf(i));
		}
		if (index > tailleMois) {
			index = tailleMois;
		}
		comboBoxJourD.setSelectedIndex(index);
	}

	public void setComboBoxJourF(int tailleMois) {
		int index = comboBoxJourF.getSelectedIndex();
		comboBoxJourF.removeAllItems();
		comboBoxJourF.addItem("-- jour de fin --");
		for (int i = 1; i<=tailleMois; i++) {
			comboBoxJourF.addItem(String.valueOf(i));
		}
		if (index > tailleMois) {
			index = tailleMois;
		}
		comboBoxJourF.setSelectedIndex(index);
	}
	
	public String getNom() {
		if (textFieldNomTournoi.getText().isEmpty()) {
			return null;
		}
		return textFieldNomTournoi.getText();
	}
	
	public String getJourD() {
		return (String) comboBoxJourD.getSelectedItem();
	}
	
	public String getMoisD() {
		return (String) comboBoxMoisD.getSelectedItem();
	}
	
	public String getJourF() {
		return (String) comboBoxJourF.getSelectedItem();
	}
	
	public String getMoisF() {
		return (String) comboBoxMoisF.getSelectedItem();
	}
	
	public HashMap<Equipe, Integer> getEquipes() {
		HashMap<Equipe, Integer> res = new HashMap<Equipe, Integer>();
		for (Equipe e : equipesAdded) {
			res.put(e, 0);
		}
		return res;
	}
	
	public NiveauTournoi getNiveau() {
		return (NiveauTournoi) comboBoxNiveauTournoi.getSelectedItem();
	}
	
	public void setEnabledButtonCreate(boolean b) {
		btnAjout.setEnabled(b);
	}


	/**
	 * Permet de récupérer l'image du tournoi
	 */
	public JLabel getLabelImage() {
		return lblImage;
	}



	public DefaultTableModel getModeleTable() {
		return this.modeleTable;
	}

	

	public void refreshComboBoxJoueur() {
		comboEquipe.removeAllItems();
		comboEquipe.setEnabled(false);
		this.controle.ignoreCombo = true;
		
		for(Equipe equipe : this.equipes) {
			//System.out.println(equipe.getNom());
			comboEquipe.addItem(equipe);
		}
		comboEquipe.setSelectedIndex(-1);
		comboEquipe.setEnabled(true);
		controle.ignoreCombo=false;
		
	}



	public Equipe getSelectedEquipe() {
		return  (Equipe) this.comboEquipe.getSelectedItem();
	}

	@Override
	public void update(Observable o, Object arg) {
		refreshComboBoxJoueur();
	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Equipe"};
	}
}
