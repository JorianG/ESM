package vue.pages;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurListeEquipe;
import Modele.Equipe;
import Modele.Saison;
import vue.APP;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.*;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;
import vue.Observer;

import javax.swing.JScrollPane;
import java.awt.FlowLayout;

public class ListeEquipe extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Le panel de la page
	 */
	private JPanel contentPane;

	/**
	 * Le text field peremettant a l'utilisateur de rechercher une équipe
	 * Permet de récupérer le texte recherché par l'utilisateur
	 */
	private JTextField textFieldSearch;

	/**
	 * La table contenant les joueurs de l'équipe
	 */
	public JTable listeJoueur;

	/**
	 * Le modèle de la table utilisée
	 */
	private DefaultTableModel modeleTable;


	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 */
	public ListeEquipe() {
		ControleurListeEquipe control = new ControleurListeEquipe(this);
		setBounds(100, 100, 1081, 1081);
		contentPane = new CustomContentPane(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		Header header = new Header();
		header.addHeaderTo(contentPane);
		
		JPanel panelContent = new JPanel();
		contentPane.add(panelContent, BorderLayout.CENTER);
		panelContent.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBarreRecherche = new JPanel();
		panelContent.add(panelBarreRecherche, BorderLayout.NORTH);
		panelBarreRecherche.setLayout(new MigLayout("", "[12.00px,grow,left][603.00,grow,fill][87.00,center][31.00,grow,right]", "[28.00px]"));
		
		textFieldSearch = new JTextField();
		panelBarreRecherche.add(textFieldSearch, "cell 1 0,grow");
		textFieldSearch.setColumns(10);
		
		
		
		JButton btnSearchEquipe = new RoundedButton("Rechercher");
		btnSearchEquipe.addActionListener(control);
		panelBarreRecherche.add(btnSearchEquipe, "flowx,cell 2 0,grow");
		
		JButton btnAddEquipe = new RoundedButton("Ajouter");
		btnAddEquipe.addActionListener(control);
		panelBarreRecherche.add(btnAddEquipe, "flowx,cell 2 0,grow");
		
		JScrollPane scrollPane = new JScrollPane();
		panelContent.add(scrollPane, BorderLayout.CENTER);
		
		this.modeleTable = new DefaultTableModel(new Object[] {"LOGO", "NOM", "MODIFIER", "SUPPRIMER", ""}, 0);
		listeJoueur = new JTable() {
			private static final long serialVersionUID = 1L;				
			public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		listeJoueur.addMouseListener(new ControleurListeEquipe(this));

		listeJoueur.setModel(this.modeleTable);
		
		listeJoueur.setShowHorizontalLines(true);
		listeJoueur.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeJoueur.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);
		listeJoueur.getColumnModel().getColumn(2).setCellRenderer(ImageRenderer.instance);
		listeJoueur.getColumnModel().getColumn(3).setCellRenderer(ImageRenderer.instance);
		listeJoueur.setRowHeight(50);
		listeJoueur.getColumnModel().getColumn(0).setPreferredWidth(ImageRenderer.size);
		listeJoueur.getColumnModel().getColumn(0).setResizable(false);
		listeJoueur.getColumnModel().getColumn(1).setPreferredWidth(800);
		listeJoueur.getColumnModel().getColumn(1).setResizable(false);
		listeJoueur.getColumnModel().getColumn(2).setPreferredWidth(80);
		listeJoueur.getColumnModel().getColumn(2).setResizable(false);
		listeJoueur.getColumnModel().getColumn(3).setPreferredWidth(80);
		listeJoueur.getColumnModel().getColumn(3).setResizable(false);

		listeJoueur.getColumnModel().getColumn(4).setMaxWidth(0);
		listeJoueur.getColumnModel().getColumn(4).setMinWidth(0);
		listeJoueur.getColumnModel().getColumn(4).setPreferredWidth(0);
		listeJoueur.getColumnModel().getColumn(4).setResizable(false);
	
		listeJoueur.getTableHeader().setReorderingAllowed(false);
		
		scrollPane.setViewportView(listeJoueur);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnAnnuler = new RoundedButton("Annuler");
		btnAnnuler.addActionListener(control);
		panel.add(btnAnnuler);

		this.refreshListeEquipe();

	}
	
	/**
	 * Fonction pour récupérer le texte entré par l'utilisateur
	 * @return Le texte recherché
	 */
	public String getSearch() {
		return this.textFieldSearch.getText();
	}
	
	/**
	 * Fonction pour replir la liste des équipes
	 * @param l La liste des équipes
	 */
	public void setDataTableEquipe(List<Equipe> l) {
		this.modeleTable.setRowCount(0);
		for(Equipe e : l) {
			modeleTable.addRow(new Object[] {e.getImage(), "["+e.getCode()+"] "+ e.getNom(), "modif", "trash", e.getId()});
		}
	}
	
	/**
	 * Fonction pour récupérer le modèle de la table
	 * @return Le modèle te la table
	 */
	public DefaultTableModel getModeleTable() {
		return this.modeleTable;
	}
	
	/**
	 * Fonction permettant d'actualiser la liste des équipes
	 * @throws SQLException
	 */
	public void refreshListeEquipe() {
		this.setDataTableEquipe(new ArrayList<>(Objects.requireNonNull(Saison.getEquipeBySaison(APP.SELECTEDYEAR)).keySet()));
	}

	@Override
	public void update(Observable o, Object arg) {
        refreshListeEquipe();
    }

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Equipe"};
	}
}
