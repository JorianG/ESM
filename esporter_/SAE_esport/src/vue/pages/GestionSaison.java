package vue.pages;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import Controleur.ControleurGestionSaison;
import Modele.*;

import net.miginfocom.swing.MigLayout;
import vue.APP;
import vue.CustomContentPane;
import vue.Observer;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class GestionSaison extends JPanel implements Observer {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final DefaultTableModel modeleTableScoreboard;
	private final DefaultTableModel modeleTableMatch;
	private final DefaultTableModel modeleTableTournoi;
	private JPanel contentPane;
	private JTable table_tournois;
	private JTable table_scoreboard;
	private JTable table_matchs;
	public Saison saison;
	private static int year;

	private ControleurGestionSaison control;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @param saison
	 */
	public GestionSaison(Saison saison) {
		this.saison = saison;
		GestionSaison.year = this.saison.getAnnee();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );


		control = new ControleurGestionSaison(this);
		setBounds(100, 100, 1500, 1000);
		contentPane = new CustomContentPane(this);
		contentPane.setName("Saison");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		Header header = new Header();
		header.addHeaderTo(contentPane);

		JPanel panel_content = new JPanel();
		contentPane.add(panel_content, BorderLayout.CENTER);
		panel_content.setLayout(new BorderLayout(0, 0));

		JPanel panel_titre = new JPanel();
		panel_content.add(panel_titre, BorderLayout.NORTH);

		JLabel lblTitre = new JLabel(this.saison.getNom());
		panel_titre.add(lblTitre);
		lblTitre.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 39));
		lblTitre.setIcon(IconResized.of(this.saison.getLogo(), IconResized.DEFAULT_WIDTH_IMAGE_MID, IconResized.DEFAULT_HEIGHT_IMAGE_MID)); // todo: changer la taille de l'image par 150x150 si trop grande


		JPanel panel_reste = new JPanel();
		panel_content.add(panel_reste, BorderLayout.CENTER);
		panel_reste.setLayout(new MigLayout("", "[][343.00,grow][278.00][259.00][275.00,grow][]", "[][][grow][352.00,grow][grow][][grow][320.00,grow][][]"));
		
		JLabel lblNomTableTournois = new JLabel("Tournois de la saison :");
		panel_reste.add(lblNomTableTournois, "cell 1 1");

		JScrollPane scrollPane_tournois = new JScrollPane();
		panel_reste.add(scrollPane_tournois, "cell 1 3 3 1,grow");

		this.modeleTableTournoi = new DefaultTableModel(
				new Object[] {"", "NOM","NIVEAU", "DATE DEBUT","DATE FIN", ""}, 0){
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
					case 0:
						return ImageIcon.class;
					case 1:
						return String.class;
                    case 2:
						return String.class;
                    case 3:
                        return LocalDate.class;
					case 4:
						return LocalDate.class;
					case 5:
						return Integer.class;
                    default:
						return String.class;
				}
			}
		};

		table_tournois = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		table_tournois.setName("tableTournoi");
		table_tournois.addMouseListener(this.control);
		table_tournois.setModel(this.modeleTableTournoi);
		table_tournois.setShowHorizontalLines(true);
		table_tournois.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_tournois.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);

		table_tournois.setRowHeight(50);
		table_tournois.getColumnModel().getColumn(0).setMaxWidth(ImageRenderer.columnSize);
		table_tournois.getColumnModel().getColumn(0).setResizable(false);
		table_tournois.getColumnModel().getColumn(1).setPreferredWidth(200);
		table_tournois.getColumnModel().getColumn(1).setResizable(false);
		table_tournois.getColumnModel().getColumn(2).setPreferredWidth(50);
		table_tournois.getColumnModel().getColumn(2).setResizable(false);
		table_tournois.getColumnModel().getColumn(3).setPreferredWidth(50);
		table_tournois.getColumnModel().getColumn(3).setResizable(false);

		table_tournois.getColumnModel().getColumn(4).setPreferredWidth(50);
		table_tournois.getColumnModel().getColumn(4).setResizable(false);
		table_tournois.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
		table_tournois.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );

		table_tournois.getColumnModel().getColumn(5).setMaxWidth(0);
		table_tournois.getColumnModel().getColumn(5).setMinWidth(0);
		table_tournois.getColumnModel().getColumn(5).setPreferredWidth(0);
		table_tournois.getColumnModel().getColumn(5).setResizable(false);

		table_tournois.getTableHeader().setReorderingAllowed(false);

		scrollPane_tournois.setViewportView(table_tournois);

		TableRowSorter<DefaultTableModel> rowSorterTournoi = new TableRowSorter<DefaultTableModel>(this.modeleTableTournoi);
		table_tournois.setRowSorter(rowSorterTournoi);

		List<RowSorter.SortKey> sortKeysMatch = new ArrayList<>(25);
		sortKeysMatch.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
		sortKeysMatch.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));


		rowSorterTournoi.setSortKeys(sortKeysMatch);

		refreshTournois();
		rowSorterTournoi.sort();

		scrollPane_tournois.setViewportView(table_tournois);

		JPanel panel = new JPanel();
		panel_reste.add(panel, "cell 4 3,grow");
		panel.setLayout(new MigLayout("", "[34.00][319.00,grow][53.00]", "[grow][10.00][grow][10.00][grow]"));
				
								JButton btnListeArbitres = new RoundedButton("Liste des arbitres");
								panel.add(btnListeArbitres, "cell 1 0,grow");
								btnListeArbitres.addActionListener(control);
				
								JButton btnAjoutTournoi = new RoundedButton("Ajouter un tournoi");
								panel.add(btnAjoutTournoi, "cell 1 2,grow");
								btnAjoutTournoi.addActionListener(control);
				
				JButton impressionDesResultats = new RoundedButton("Impression des résultats");
				impressionDesResultats.addActionListener(control);
				panel.add(impressionDesResultats, "cell 1 4,grow");

		JPanel panel_1 = new JPanel();
		panel_reste.add(panel_1, "cell 1 5,alignx left,growy");

		JLabel lblNomTableEquipes = new JLabel("Classement des équipes :");
		panel_1.add(lblNomTableEquipes);

		JPanel panel_2 = new JPanel();
		panel_reste.add(panel_2, "cell 2 5,alignx left,growy");
		
		JLabel lblNomTableMatchs = new JLabel("Prochains matchs :");
		panel_2.add(lblNomTableMatchs);
		JScrollPane scrollPane_equipes = new JScrollPane();
		panel_reste.add(scrollPane_equipes, "cell 1 6 1 3,grow");

		this.modeleTableScoreboard = new DefaultTableModel(
				new Object[] {"", "NOM", "PTS", ""}, 0){
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
				case 0:
					return ImageIcon.class;
				case 1:
					return String.class;
				case 2:
					return Integer.class;
				default:
					return String.class;

				}

			}
		};

		table_scoreboard = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		table_scoreboard.setName("Scoreboard");
		table_scoreboard.addMouseListener(this.control);
		table_scoreboard.setModel(this.modeleTableScoreboard);
		table_scoreboard.setShowHorizontalLines(true);
		table_scoreboard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_scoreboard.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);

		table_scoreboard.setRowHeight(50);
		table_scoreboard.getColumnModel().getColumn(0).setPreferredWidth(ImageRenderer.columnSize);
		table_scoreboard.getColumnModel().getColumn(0).setResizable(false);
		table_scoreboard.getColumnModel().getColumn(1).setPreferredWidth(600);
		table_scoreboard.getColumnModel().getColumn(1).setResizable(false);
		table_scoreboard.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_scoreboard.getColumnModel().getColumn(2).setResizable(false);

		table_scoreboard.getColumnModel().getColumn(3).setMaxWidth(0);
		table_scoreboard.getColumnModel().getColumn(3).setMinWidth(0);
		table_scoreboard.getColumnModel().getColumn(3).setPreferredWidth(0);
		table_scoreboard.getColumnModel().getColumn(3).setResizable(false);

		table_scoreboard.getTableHeader().setReorderingAllowed(false);
		scrollPane_equipes.setViewportView(table_scoreboard);


		TableRowSorter<DefaultTableModel> rowSorterScoreboard = new TableRowSorter<DefaultTableModel>(this.modeleTableScoreboard);
		table_scoreboard.setRowSorter(rowSorterScoreboard);
		List<RowSorter.SortKey> sortKeysScoreboard = new ArrayList<>(25);
		sortKeysScoreboard.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
		rowSorterScoreboard.setSortKeys(sortKeysScoreboard);

		refreshScoreboard();
		rowSorterScoreboard.sort();

		scrollPane_equipes.setViewportView(table_scoreboard);

		JScrollPane scrollPane_matchs = new JScrollPane();
		panel_reste.add(scrollPane_matchs, "cell 2 6 3 3,grow");
		modeleTableMatch = new DefaultTableModel(
				new Object[] { "", "EQUIPE 1", "DATE", "EQUIPE 2", "", ""}, 0){
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
				case 0:
					return ImageIcon.class;
				case 1:
					return String.class;
				case 2:
					return CustomDateTime.class;
				case 3:
					return String.class;
				case 4:
					return ImageIcon.class;
				case 5:
					return Integer.class;
				default:
					return String.class;
				}
			}
		};
		table_matchs = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		table_matchs.setName("tableMatch");
		table_matchs.addMouseListener(this.control);
		table_matchs.setModel(this.modeleTableMatch);
		table_matchs.setShowHorizontalLines(true);
		table_matchs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_matchs.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);
		table_matchs.getColumnModel().getColumn(4).setCellRenderer(ImageRenderer.instance);
		table_matchs.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );

		table_matchs.setRowHeight(50);
		table_matchs.getColumnModel().getColumn(0).setPreferredWidth(ImageRenderer.columnSize);
		table_matchs.getColumnModel().getColumn(0).setResizable(false);
		table_matchs.getColumnModel().getColumn(1).setPreferredWidth(600);
		table_matchs.getColumnModel().getColumn(1).setResizable(false);
		table_matchs.getColumnModel().getColumn(2).setPreferredWidth(200);
		table_matchs.getColumnModel().getColumn(2).setResizable(false);
		table_matchs.getColumnModel().getColumn(3).setPreferredWidth(600);
		table_matchs.getColumnModel().getColumn(3).setResizable(false);
		table_matchs.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		table_matchs.getColumnModel().getColumn(4).setPreferredWidth(ImageRenderer.columnSize);
		table_matchs.getColumnModel().getColumn(4).setResizable(false);
		table_matchs.getColumnModel().getColumn(5).setPreferredWidth(100);
		table_matchs.getColumnModel().getColumn(5).setResizable(false);


		table_matchs.getColumnModel().getColumn(5).setMaxWidth(0);
		table_matchs.getColumnModel().getColumn(5).setMinWidth(0);
		table_matchs.getColumnModel().getColumn(5).setPreferredWidth(0);
		table_matchs.getColumnModel().getColumn(5).setResizable(false);



		table_matchs.getTableHeader().setReorderingAllowed(false);

		TableRowSorter<DefaultTableModel> rowSorterMatch = new TableRowSorter<DefaultTableModel>(this.modeleTableMatch);
		table_matchs.setRowSorter(rowSorterMatch);
		List<RowSorter.SortKey> sortKeysMatchs = new ArrayList<>(25);
		sortKeysMatchs.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		rowSorterMatch.setSortKeys(sortKeysMatchs);


		scrollPane_matchs.setViewportView(table_matchs);
		refreshMatchs();
		rowSorterMatch.sort();

		JPanel panel_boutons = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_boutons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_boutons, BorderLayout.SOUTH);

		JButton btnSupprimer = new RoundedButton("Supprimer");
		btnSupprimer.addActionListener(control);
		panel_boutons.add(btnSupprimer);

		JButton btnQuitter = new RoundedButton("Quitter");
		btnQuitter.addActionListener(control);
		panel_boutons.add(btnQuitter);
	}

	public void refreshTournois(){
		this.modeleTableTournoi.setRowCount(0);
		//this.modeleTableTournoi.getDataVector().removeAllElements();
		Set<Tournoi> tournois = Tournoi.getBySaison(APP.SELECTEDYEAR);
		for(Tournoi t : tournois) {
			this.modeleTableTournoi.addRow(new Object[]{t.getImage(), t.getNom(), t.getNiveau(),
					new CustomDateTime(t.getDateDebut()),
					new CustomDateTime(t.getDateFin()), t.getId()});
		}
		this.table_tournois.revalidate();
	}
	public void refreshMatchs(){
		this.modeleTableMatch.setRowCount(0);
		//this.modeleTableMatch.getDataVector().removeAllElements();
		Set<Match> matchs = Saison.getById(APP.SELECTEDYEAR).getMatchsFuturs();
		for(Match m : matchs){
			this.modeleTableMatch.addRow(new Object[]{m.getEquipe1().getImage(), m.getEquipe1().getFullName(),
					m.getDateDebutMatch()
					, m.getEquipe2().getFullName(), m.getEquipe2().getImage(), m.getId()});
		}

//		for (int i = 0; match.size() < matchs.size(); i++) {
//			this.modeleTableMatch.removeRow(i);
//		}
		this.table_matchs.revalidate();
	}

	public void refreshScoreboard(){
		this.modeleTableScoreboard.setRowCount(0);
		//this.modeleTableScoreboard.getDataVector().removeAllElements();
		Map<Equipe, Integer> cl = Saison.getEquipeBySaison(APP.SELECTEDYEAR);
		for(Equipe e : cl.keySet()){
			this.modeleTableScoreboard.addRow(new Object[]{e.getImage(), e.getFullName(), cl.get(e), e.getId()});
		}
//		for (int i = 0; i < cl.size(); i++) {
//			//this.modeleTableScoreboard.removeRow(i);
//		}
		this.table_scoreboard.revalidate();
	}



	public int getYear() {
		return this.saison.getAnnee();
	}

	public static int getStaticYear() {
		return year;
	}

	public JPanel getPanel() {
		return this.contentPane;
	}

	@Override
	public void update(Observable o, Object arg) {
		refreshMatchs();
		refreshScoreboard();
		refreshTournois();
	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Saison", "Equipe", "Tournoi", "Match"};
	}
}
