package vue.pages;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurGestionEquipe;
import Modele.Equipe;
import Modele.Joueur;
import Modele.Saison;
import Modele.Tournoi;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.sql.SQLException;
import java.util.*;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;
import vue.Observer;

public class GestionEquipe extends JPanel implements Observer {

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
	private static JTable tableJoueur;

	/**
	 * La table contenant les tournois auxquels sont inscrit l'équipe
	 */
	private JTable tableTournoi;

	/**
	 * Le bouton qui renvoie sur la page pour modifier l'équipe
	 */
	private JButton btnModifierEquipe;

	/**
	 * Le bouton pour quitter la page
	 */
	private JButton btnQuitter;
	private JLabel lblNomEquipe;
	private JLabel lblLogoEquipe;
	private JLabel lblPays;


	/**
	 * L'équipe concernée par la page
	 */
	private Equipe equipe;

	/**
	 * La page de la liste des équipes.
	 * permet de raffraichir la liste
	 */
	private ListeEquipe vueListeEquipe;

	/**
	 * Le modèle de la table des joueurs
	 */
	private static DefaultTableModel modeleTableJoueur;

	/**
	 * Le modèle de la table des tournois
	 */
	private static DefaultTableModel modeleTableTournoi;


	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 */
	public GestionEquipe(Equipe eq, ListeEquipe vueListeEquipe) throws SQLException {
		this.equipe = eq;
		this.vueListeEquipe= vueListeEquipe;
		ControleurGestionEquipe controleur = new ControleurGestionEquipe(this);
		setBounds(100, 100, 1000, 750);
		contentPane = new CustomContentPane(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		Header header = new Header();
		header.addHeaderTo(contentPane);
		
		JPanel contenu = new JPanel();
		contentPane.add(contenu, BorderLayout.CENTER);
		contenu.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNomEquipe = new JPanel();
		contenu.add(panelNomEquipe, BorderLayout.NORTH);

		lblLogoEquipe = new JLabel(IconResized.of(eq.getImage(), IconResized.DEFAULT_WIDTH_IMAGE_SMALL, IconResized.DEFAULT_HEIGHT_IMAGE_SMALL));
		panelNomEquipe.add(lblLogoEquipe);
		
		lblNomEquipe = new JLabel();
		lblNomEquipe.setText("  ["+eq.getCode()+"] "+eq.getNom()+"  ");
		
		lblNomEquipe.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
		panelNomEquipe.add(lblNomEquipe);		
		
		lblPays = new JLabel("");
		lblPays.setIcon(IconResized.of(eq.getPays().getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
		panelNomEquipe.add(lblPays);
		
		JPanel panelInfo = new JPanel();
		contenu.add(panelInfo, BorderLayout.CENTER);
				panelInfo.setLayout(new BorderLayout(0, 0));
					
					JPanel panelBtnModifierEquipe = new JPanel();
					panelInfo.add(panelBtnModifierEquipe, BorderLayout.NORTH);
					
					btnModifierEquipe = new RoundedButton("Modifier l'équipe");
					btnModifierEquipe.addActionListener(controleur);
					btnModifierEquipe.setHorizontalAlignment(SwingConstants.RIGHT);
					panelBtnModifierEquipe.add(btnModifierEquipe);
					
					JPanel panel = new JPanel();
					panelInfo.add(panel, BorderLayout.CENTER);
					panel.setLayout(new BorderLayout(0, 0));
					
					JPanel panelTableInfo = new JPanel();
					panel.add(panelTableInfo, BorderLayout.CENTER);
						panelTableInfo.setLayout(new GridLayout(1, 2, 0, 0));
						
						
						
						JScrollPane scrollPaneJoueur = new JScrollPane();
						panelTableInfo.add(scrollPaneJoueur);
						modeleTableJoueur = new DefaultTableModel( new Object[] {"Pseudo", "Image", "Nom", "id"}, 0);
							tableJoueur = new JTable() {

								private static final long serialVersionUID = 1L;

								public boolean isCellEditable(int row, int column) {                
						                return false;               
						        };
							};
							tableJoueur.addMouseListener(controleur);
							tableJoueur.setModel(modeleTableJoueur);
							
							tableJoueur.setShowHorizontalLines(true);
							tableJoueur.setShowGrid(true);
							tableJoueur.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tableJoueur.setBorder(new EmptyBorder(0, 0, 0, 0));
							tableJoueur.getColumnModel().getColumn(1).setCellRenderer(ImageRenderer.instance);
							tableJoueur.setEnabled(true);
							tableJoueur.setRowHeight(90);
							tableJoueur.setName("tableJoueur");

							tableJoueur.getColumnModel().getColumn(3).setMaxWidth(0);
							tableJoueur.getColumnModel().getColumn(3).setMinWidth(0);
							tableJoueur.getColumnModel().getColumn(3).setPreferredWidth(0);
							tableJoueur.getColumnModel().getColumn(3).setResizable(false);
							
							scrollPaneJoueur.setViewportView(tableJoueur);
							//remplir la table joueur
							for(Joueur j : Objects.requireNonNull(Joueur.getByEquipe(equipe))) {
								System.out.println("joueur : "+j.getPseudo()+" ajouté");
								this.equipe.addJoueur(j);
								modeleTableJoueur.addRow(new Object[] {j.getPseudo(), j.getImage(), j.getPrenom()+' '+j.getNom(),j.getId()});
							
							}
							
							
								
						//TABLE TOURNOI
						JScrollPane scrollPaneTournoi = new JScrollPane();
						panelTableInfo.add(scrollPaneTournoi);
						modeleTableTournoi = new DefaultTableModel( new Object[] {"Nom", "Image", "Point", "id"}, 0);
						tableTournoi = new JTable() {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							public boolean isCellEditable(int row, int column) {
								return false;
							};
						};
						tableTournoi.addMouseListener(controleur);

						tableTournoi.setModel(modeleTableTournoi);
						tableTournoi.setShowHorizontalLines(true);
						tableTournoi.setShowGrid(true);
						tableTournoi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tableTournoi.setBorder(new EmptyBorder(0, 0, 0, 0));
						tableTournoi.getColumnModel().getColumn(1).setCellRenderer(ImageRenderer.instance);
						tableTournoi.setEnabled(true);
						tableTournoi.setRowHeight(90);
						tableTournoi.setName("tableTournoi");
						
						tableTournoi.getColumnModel().removeColumn(tableTournoi.getColumnModel().getColumn(3));
						
						scrollPaneTournoi.setViewportView(tableTournoi);
						
						JPanel panel_1 = new JPanel();
						panel.add(panel_1, BorderLayout.NORTH);
						panel_1.setLayout(new MigLayout("", "[grow][grow]", "[]"));
						
						JLabel lblTitreTableJoueurs = new JLabel("Liste des joueurs de l'équipe : ");
						panel_1.add(lblTitreTableJoueurs, "cell 0 0,alignx left,aligny center");
						
						JLabel lblTitreTableTournois = new JLabel("Liste des tournois joués : ");
						panel_1.add(lblTitreTableTournois, "cell 1 0,alignx left,aligny center");

						//remplir la table tournoi
						for (Tournoi t : Tournoi.getTournoiByEquipe(equipe)) {
							modeleTableTournoi.addRow(new Object[] { t.getNom(), t.getImage(), Tournoi.getEquipePoints(t, equipe), t.getId() });
						}



		JPanel footer = new JPanel();
		contentPane.add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(0, 0));
		
			JPanel panelQuitter = new JPanel();
			footer.add(panelQuitter, BorderLayout.EAST);
			
				btnQuitter = new RoundedButton("Quitter");
				btnQuitter.addActionListener(controleur);
				btnQuitter.setHorizontalAlignment(SwingConstants.LEFT);
				panelQuitter.add(btnQuitter);
		
		
	}
	
	/**
	 * Fonction pour récupérer l'équipe concernée dans la page
	 * @return L'équipe concernée par la vue
	 */
	public Equipe getEquipe() {
		return equipe;
	}
	
	/**
	 * Fonction pour retirer un joueur de la table
	 * Permet de modifier la vue depuis une autre page
	 * @param j Le joueur à retirer
	 */
	public static void removeJoueurFromTable(Joueur j ) {
		for(Integer i = 0; i< modeleTableJoueur.getRowCount(); i++) {
			if((int) tableJoueur.getModel().getValueAt(i,3)  == j.getId()) {
				modeleTableJoueur.removeRow(i);
			}
		}	
	}
	
	
	public static void 	addJoueurToTable(Joueur j ) {
		modeleTableJoueur.addRow(new Object[] {j.getImage(), j.getFullName(), j.getImage(),j.getId()});
	}

	
	/**
	 * Fonction qui permet de récupérer la vue de la page ListeEquipe
	 * @return La vue de la page Liste Equipe
	 */
	public ListeEquipe getVueListeEquipe() {
		return vueListeEquipe;
	}
	
	public JTable getListeJoueur() {
		return GestionEquipe.tableJoueur;
	}
	
	public JTable getListeTournoi() {
		return this.tableTournoi;
	}

	@Override
	public void update(Observable o, Object arg) {
		refrechTableTournoi();
		refrechTableJoueur();
		refreshInfosEquipe();
	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Equipe", "Joueur", "Tournoi"};
	}

	private void refrechTableTournoi() {
		modeleTableTournoi.setRowCount(0);
		for (Tournoi t : Tournoi.getTournoiByEquipe(equipe)) {
			modeleTableTournoi.addRow(new Object[] { t.getNom(), t.getImage(), Tournoi.getEquipePoints(t, equipe), t.getId()});
		}
		tableTournoi.revalidate();
	}

	public void refrechTableJoueur() {
		modeleTableJoueur.setRowCount(0);
		for(Joueur j : Objects.requireNonNull(Joueur.getByEquipe(equipe))) {
			this.equipe.addJoueur(j);
			modeleTableJoueur.addRow(new Object[] {j.getPseudo(), j.getImage(), j.getPrenom()+' '+j.getNom(),j.getId()});
		}
	}

	public void refreshInfosEquipe() {
		this.equipe = Equipe.getById(this.equipe.getId());
		lblLogoEquipe.setIcon(IconResized.of(equipe.getImage(), IconResized.DEFAULT_WIDTH_IMAGE_SMALL, IconResized.DEFAULT_HEIGHT_IMAGE_SMALL));
		lblNomEquipe.setText("  ["+equipe.getCode()+"] "+equipe.getNom()+"  ");
		lblPays.setIcon(IconResized.of(equipe.getPays().getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
	}

}
