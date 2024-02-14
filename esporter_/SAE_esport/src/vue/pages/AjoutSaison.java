package vue.pages;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurAjoutSaison;
import Modele.Arbitre;
import Modele.Equipe;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.util.*;
import java.util.List;

import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;
import vue.Observer;

public class AjoutSaison extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableEquipes;
	private JTable tableArbitres;
	private JTextField txtFieldNom;
	private DefaultTableModel modeleTableEq;
	private DefaultTableModel modeleTableAb;
	private static JComboBox<Equipe> comboBoxEquipes;
	private static JComboBox<Arbitre> comboBoxArbitres;
	private JLabel lblIconSaison;

	private static ControleurAjoutSaison controleur;
	private JButton btnValider = new RoundedButton("Valider");


	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public AjoutSaison() {
		setBounds(100, 100, 1200, 1000);
		contentPane = new CustomContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		controleur = new ControleurAjoutSaison(this);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_titre = new JPanel();
		contentPane.add(panel_titre, BorderLayout.NORTH);
		
		JLabel lblTitre = new JLabel("Création de la saison");
		lblTitre.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 24));
		panel_titre.add(lblTitre);
		
		JPanel panel_contenu = new JPanel();
		contentPane.add(panel_contenu, BorderLayout.CENTER);
		panel_contenu.setLayout(new MigLayout("", "[grow][30][grow]", "[100,grow][20][400,grow]"));
		
		JPanel panel_nom_logo = new JPanel();
		panel_contenu.add(panel_nom_logo, "cell 0 0 3 1,grow");
		panel_nom_logo.setLayout(new MigLayout("", "[grow][][][][grow][grow]", "[grow][][][][grow]"));
		
		JLabel lblIcon = new JLabel("Logo de la saison :");
		panel_nom_logo.add(lblIcon, "cell 3 2,alignx center");
		
		lblIconSaison = new JLabel("");
		lblIconSaison.setIcon(IconResized.of(IconResized.PLACEHOLDER));
		panel_nom_logo.add(lblIconSaison, "cell 4 2,alignx center");
		lblIconSaison.addMouseListener(controleur);
		
		JLabel lblNom = new JLabel("Nom de la saison : ");
		panel_nom_logo.add(lblNom, "cell 3 3,alignx trailing");
		
		txtFieldNom = new JTextField();
		panel_nom_logo.add(txtFieldNom, "cell 4 3,growx");
		txtFieldNom.setColumns(10);
		
		JLabel lblTitreTableEquipe = new JLabel("Equipes participant à la saison :");
		panel_contenu.add(lblTitreTableEquipe, "cell 0 1,alignx left,aligny bottom");
		
		JLabel lblTitreTableArbitre = new JLabel("Arbitres participants à la saison :");
		panel_contenu.add(lblTitreTableArbitre, "cell 2 1,alignx left,aligny bottom");
		
		JPanel panel_equipes = new JPanel();
		panel_contenu.add(panel_equipes, "cell 0 2,grow");
		panel_equipes.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_top_e = new JPanel();
		panel_equipes.add(panel_top_e, BorderLayout.NORTH);
		panel_top_e.setLayout(new BorderLayout(0, 0));
		

		//TODO REMPLIR
		//TODO CONTROLEUR
		comboBoxEquipes = new JComboBox<Equipe>();
		comboBoxEquipes.setName("Equipe");
		comboBoxEquipes.addItemListener(controleur);
		comboBoxEquipes.addMouseListener(controleur);
		panel_top_e.add(comboBoxEquipes, BorderLayout.CENTER);
		comboBoxEquipes.addActionListener(controleur);
		
		
		JButton btnCreerEquipe = new RoundedButton("+");
		btnCreerEquipe.setName("btnCreerEquipe");
		btnCreerEquipe.addActionListener(controleur);
		panel_top_e.add(btnCreerEquipe, BorderLayout.WEST);
		
		JScrollPane scrollPaneEquipes = new JScrollPane();
		panel_equipes.add(scrollPaneEquipes, BorderLayout.CENTER);

		modeleTableEq = new DefaultTableModel(new Object[] {"IMG", "NOM", "RETIRER","ID"}, 0);
		tableEquipes = new JTable(){
			
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
                return false;               
        };};
		scrollPaneEquipes.setViewportView(tableEquipes);
		tableEquipes.setModel(modeleTableEq);
		
		tableEquipes.setShowHorizontalLines(true);

		tableEquipes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEquipes.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);
		tableEquipes.getColumnModel().getColumn(2).setCellRenderer(ImageRenderer.instance);
		tableEquipes.getColumnModel().getColumn(0).setPreferredWidth(5); // TODO : constante ? cpas 20 ?
		tableEquipes.getColumnModel().getColumn(0).setResizable(false);
		
		tableEquipes.getColumnModel().getColumn(2).setPreferredWidth(25); // TODO : constante ?
		tableEquipes.getColumnModel().getColumn(2).setResizable(false);

		tableEquipes.getColumnModel().getColumn(3).setMaxWidth(0);
		tableEquipes.getColumnModel().getColumn(3).setMinWidth(0);
		tableEquipes.getColumnModel().getColumn(3).setPreferredWidth(0);
		tableEquipes.getColumnModel().getColumn(3).setResizable(false);
		
		tableEquipes.setRowHeight(50);
		tableEquipes.getTableHeader().setReorderingAllowed(false);

		tableEquipes.addMouseListener(controleur);
		tableEquipes.setName("TbleEquipe");
		
		JPanel panel_arbitres = new JPanel();
		panel_contenu.add(panel_arbitres, "cell 2 2,grow");
		panel_arbitres.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_top_a = new JPanel();
		panel_arbitres.add(panel_top_a, BorderLayout.NORTH);
		panel_top_a.setLayout(new BorderLayout(0, 0));
		
		//TODO REMPLIR 
		//TODO CONTROLEUR
		comboBoxArbitres = new JComboBox<Arbitre>();
		comboBoxArbitres.setName("Arbitre");

		comboBoxArbitres.addItemListener(controleur);
		comboBoxArbitres.addMouseListener(controleur);
		AjoutSaison.refreshComboBoxArbitre();
		panel_top_a.add(comboBoxArbitres);
		
		JButton btnCreerArbitre = new RoundedButton("+");
		btnCreerArbitre.setName("btnCreerArbitre");
		btnCreerArbitre.addActionListener(controleur);
		panel_top_a.add(btnCreerArbitre, BorderLayout.WEST);
		
		JScrollPane scrollPaneArbitres = new JScrollPane();
		panel_arbitres.add(scrollPaneArbitres, BorderLayout.CENTER);

		this.modeleTableAb = new DefaultTableModel(new Object[] {"IMG", "NOM", "RETIRER","ID"}, 0);
		tableArbitres = new JTable(){
			
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
                return false;               
        };};

		tableArbitres.setModel(modeleTableAb);
		tableArbitres.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);
		tableArbitres.getColumnModel().getColumn(0).setPreferredWidth(ImageRenderer.size); // TODO : constante ?
		tableArbitres.getColumnModel().getColumn(0).setResizable(false);
		tableArbitres.getColumnModel().getColumn(2).setCellRenderer(ImageRenderer.instance);
		tableArbitres.getColumnModel().getColumn(2).setPreferredWidth(ImageRenderer.size); // TODO : constante ?
		tableArbitres.getColumnModel().getColumn(2).setResizable(false);
		tableArbitres.getTableHeader().setReorderingAllowed(false);

		tableArbitres.getColumnModel().getColumn(3).setMaxWidth(0);
		tableArbitres.getColumnModel().getColumn(3).setMinWidth(0);
		tableArbitres.getColumnModel().getColumn(3).setPreferredWidth(0);
		tableArbitres.getColumnModel().getColumn(3).setResizable(false);

		tableArbitres.setRowHeight(50);

		tableArbitres.addMouseListener(controleur);
		tableArbitres.setShowHorizontalLines(true);
		tableArbitres.setName("TbleArbitre");
		tableArbitres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPaneArbitres.setViewportView(tableArbitres);
		
		JPanel panel_boutons = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_boutons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_boutons, BorderLayout.SOUTH);
		
		JButton btnAnnuler = new RoundedButton("Annuler");
		btnAnnuler.addActionListener(controleur);
		panel_boutons.add(btnAnnuler);
		
		btnValider.setEnabled(false);
		btnValider.addActionListener(controleur);
		panel_boutons.add(btnValider);

		AjoutSaison.refreshComboBoxArbitre();
		AjoutSaison.refreshComboBoxEquipe();
	}
	
	public String getNom() {
		return txtFieldNom.getText();
	}
	
	public JLabel getLabelImage() {
		return this.lblIconSaison;
	}

	public static void refreshComboBoxEquipe() {
		comboBoxEquipes.removeAllItems();
		controleur.ignoreCombo = true;

		List<Equipe> EquipesNotInSaison = new ArrayList<>();
		for(Equipe e : Objects.requireNonNull(Equipe.getAll())) {
			if(!controleur.SaisonContainsEquipe(e)) {
				EquipesNotInSaison.add(e);
			}
		}

		for(Equipe e : EquipesNotInSaison) {
			comboBoxEquipes.addItem(e);
		}
		comboBoxEquipes.setSelectedIndex(-1);
		comboBoxEquipes.setEnabled(true);
		controleur.ignoreCombo = false;
	}

	public static void refreshComboBoxArbitre() {
		comboBoxArbitres.removeAllItems();
		controleur.ignoreCombo = true;
		comboBoxArbitres.removeAllItems();

		List<Arbitre> ArbitresNotInSaison = new ArrayList<>();
		for(Arbitre a : Objects.requireNonNull(Arbitre.getAll())) {
			if(!controleur.SaisonContainsArbitre(a)) {
				ArbitresNotInSaison.add(a);
			}
		}

		for(Arbitre a : ArbitresNotInSaison) {
			comboBoxArbitres.addItem(a);
		}
		comboBoxArbitres.setSelectedIndex(-1);
		comboBoxArbitres.setEnabled(true);
		controleur.ignoreCombo = false;
	}

	public void verif() {
		if (modeleTableEq.getRowCount() >= 4 && modeleTableAb.getRowCount() >= 12) {
			if (!txtFieldNom.getText().isEmpty()) {
				btnValider.setEnabled(true);
			} else {
				btnValider.setEnabled(false);
			}
		} else {
			btnValider.setEnabled(false);
		}
	}




	public DefaultTableModel getModeleTableEq() {
		return this.modeleTableEq;
	}

	public void refreshTableEquipe(List<Equipe> lstEq) {
		this.getModeleTableEq().getDataVector().removeAllElements();
		for(Equipe e : lstEq) {
			//System.out.println(e.getNom());
			this.getModeleTableEq().addRow(new Object[] {e.getImage(), e.getNom(), "trash", e.getId()});
		}
		this.tableEquipes.repaint();
	}
	public DefaultTableModel getModeleTableAb() {
		return this.modeleTableAb;
	}

	public void refreshTableArbitre(List<Arbitre> lstArb) {
		this.getModeleTableAb().getDataVector().removeAllElements();

		for(Arbitre a : lstArb) {
			this.getModeleTableAb().addRow(new Object[] {a.getImage(), a.getNom(), "trash", a.getId()});
		}
		this.tableArbitres.repaint();


	}

	public JTable getTableArbitres() {
		return tableArbitres;
	}

	public JTable getTableEquipes() {
		return tableEquipes;
	}

	@Override
	public void update(Observable o, Object arg) {
		refreshComboBoxArbitre();
		refreshComboBoxEquipe();
	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Arbitre", "Equipe"};
	}
}

