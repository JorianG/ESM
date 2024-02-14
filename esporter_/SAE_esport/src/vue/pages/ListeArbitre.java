package vue.pages;

import Controleur.ControleurListeArbitre;
import Modele.*;
import net.miginfocom.swing.MigLayout;
import vue.APP;
import vue.CustomContentPane;
import vue.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ListeArbitre extends JPanel implements Observer {

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
	public JTable listeArbitre;

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
	public ListeArbitre() {
		ControleurListeArbitre control = new ControleurListeArbitre(this);
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
		
		this.modeleTable = new DefaultTableModel(
                new Object[] {"PRENOM", "NOM", "MODIFIER", "SUPPRIMER", ""}, 0);
			listeArbitre = new JTable() {
				private static final long serialVersionUID = 1L;				
				public boolean isCellEditable(int row, int column) {                
		                return false;               
		        };
		        

			};
			listeArbitre.addMouseListener(control);

			listeArbitre.setModel(this.modeleTable);
		
		listeArbitre.setShowHorizontalLines(true);
		listeArbitre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeArbitre.getColumnModel().getColumn(2).setCellRenderer(ImageRenderer.instance);
		listeArbitre.getColumnModel().getColumn(3).setCellRenderer(ImageRenderer.instance);
		listeArbitre.setRowHeight(50);
		listeArbitre.getColumnModel().getColumn(0).setPreferredWidth(400);
		listeArbitre.getColumnModel().getColumn(0).setResizable(false);
		listeArbitre.getColumnModel().getColumn(1).setPreferredWidth(400);
		listeArbitre.getColumnModel().getColumn(1).setResizable(false);
		listeArbitre.getColumnModel().getColumn(2).setPreferredWidth(80);
		listeArbitre.getColumnModel().getColumn(2).setResizable(false);
		listeArbitre.getColumnModel().getColumn(3).setPreferredWidth(80);
		listeArbitre.getColumnModel().getColumn(3).setResizable(false);

		listeArbitre.getColumnModel().getColumn(4).setMaxWidth(0);
		listeArbitre.getColumnModel().getColumn(4).setMinWidth(0);
		listeArbitre.getColumnModel().getColumn(4).setPreferredWidth(0);
		listeArbitre.getColumnModel().getColumn(4).setResizable(false);
	
		listeArbitre.getTableHeader().setReorderingAllowed(false);
		
		scrollPane.setViewportView(listeArbitre);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnAnnuler = new RoundedButton("Annuler");
		btnAnnuler.addActionListener(control);
		panel.add(btnAnnuler);

		this.remplirTableListeArbitre(Arbitre.getAll());
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
	public void remplirTableListeArbitre(List<Arbitre> l) {
		this.modeleTable.setRowCount(0);
		for(Arbitre e : l) {
			modeleTable.addRow(new Object[] {e.getPrenom(), e.getNom(), "modif", "trash", e.getId()});
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
	public void refreshTableListeArbitre() throws SQLException {
		this.modeleTable.setRowCount(0);
		List<Arbitre> l = Saison.getArbitreInSaison(APP.SELECTEDYEAR).stream().collect(Collectors.toList());
		for (Arbitre e : l) {
			modeleTable.addRow(new Object[] {e.getPrenom(), e.getNom(), "modif", "trash", e.getId()});
		}
		this.listeArbitre.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
        try {
            refreshTableListeArbitre();
        } catch (SQLException e) {
			System.err.println("failed to refresh liste arbitre");
        	JOptionPane.showInternalMessageDialog(APP.getInstance(), "Erreur lors de la mise à jour de la liste des joueurs");
        	//TODO on fait quoi après ?
        	APP.exit();
        }
    }

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Arbitre"};
	}
}
