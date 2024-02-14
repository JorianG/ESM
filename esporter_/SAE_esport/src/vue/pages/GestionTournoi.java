package vue.pages;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controleur.ControleurGestionTournoi;
import Modele.*;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;
import vue.Observer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class GestionTournoi extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableMatch;
	private JTable tableClassement;
	public JButton btnAjoutMatch;
	public JButton btnCloturer;
	private Tournoi tournoi;

	private DefaultTableModel modeleTableMatch;
	private DefaultTableModel modeleTableClassement;

	private JLabel lblNomTournoi;
	private JLabel lblDates;


	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 */
	public GestionTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
		setBounds(100, 100, 1081, 1000);
		contentPane = new CustomContentPane(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		ControleurGestionTournoi controleur = new ControleurGestionTournoi(this);

		Header header = new Header();
		header.addHeaderTo(contentPane);
		
		JPanel panel_bas = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_bas.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_bas, BorderLayout.SOUTH);
		
		JButton btnQuitter = new RoundedButton("Quitter");
		btnQuitter.addActionListener(controleur);
		panel_bas.add(btnQuitter);
		
		JPanel panel_centre = new JPanel();
		contentPane.add(panel_centre, BorderLayout.CENTER);
		panel_centre.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_centre_haut = new JPanel();
		panel_centre.add(panel_centre_haut, BorderLayout.NORTH);
		panel_centre_haut.setLayout(new BorderLayout(0, 0));
		
		JButton btnLoginArbitre = new RoundedButton("Afficher login arbitre");
		btnLoginArbitre.addActionListener(controleur);
		panel_centre_haut.add(btnLoginArbitre, BorderLayout.WEST);
		
		lblNomTournoi = new JLabel(tournoi.getNom());
		lblNomTournoi.setIcon(IconResized.of(tournoi.getImage(), 100, 100));
		lblNomTournoi.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomTournoi.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 28));
		panel_centre_haut.add(lblNomTournoi, BorderLayout.CENTER);
		
		JButton btnModifier = new RoundedButton("Modifier le tournoi");
		btnModifier.addActionListener(controleur);
		panel_centre_haut.add(btnModifier, BorderLayout.EAST);
		
		JPanel panel = new JPanel();
		panel_centre_haut.add(panel, BorderLayout.SOUTH);
		
		lblDates = new JLabel("du "+ tournoi.getDateDebut().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) +" au " + tournoi.getDateFin().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		panel.add(lblDates);
		
		JPanel panel_centre_centre = new JPanel();
		panel_centre.add(panel_centre_centre, BorderLayout.CENTER);
		panel_centre_centre.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_mid_top = new JPanel();
		panel_centre_centre.add(panel_mid_top);
		panel_mid_top.setLayout(new MigLayout("", "[711.00,grow][grow]", "[][grow]"));
		
		JLabel lblTitreTableClassement = new JLabel("Classement du tournoi : ");
		panel_mid_top.add(lblTitreTableClassement, "cell 0 0,alignx left,aligny bottom");
		
		JScrollPane scrollPaneClassement = new JScrollPane();
		panel_mid_top.add(scrollPaneClassement, "cell 0 1,grow");
		

		modeleTableClassement = new DefaultTableModel(
				new Object[] {"LOGO", "NOM", "PTS", ""}, 0){
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if(columnIndex == 0) {
					return ImageIcon.class;
				}else if(columnIndex == 1) {
					return String.class;
				}else if(columnIndex == 2) {
					return Integer.class;
				}else {
					return Integer.class;
				}
			}
		};
		tableClassement = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			};


		};


		tableClassement.setModel(modeleTableClassement);

		tableClassement.setShowHorizontalLines(true);
		tableClassement.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableClassement.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);

		tableClassement.setRowHeight(50);
		tableClassement.getColumnModel().getColumn(0).setPreferredWidth(ImageRenderer.size);
		tableClassement.getColumnModel().getColumn(0).setResizable(false);
		tableClassement.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableClassement.getColumnModel().getColumn(1).setResizable(false);
		tableClassement.getColumnModel().getColumn(2).setPreferredWidth(40);
		tableClassement.getColumnModel().getColumn(2).setResizable(false);

		tableClassement.getColumnModel().getColumn(3).setMaxWidth(0);
		tableClassement.getColumnModel().getColumn(3).setMinWidth(0);
		tableClassement.getColumnModel().getColumn(3).setPreferredWidth(0);
		tableClassement.getColumnModel().getColumn(3).setResizable(false);

		tableClassement.getTableHeader().setReorderingAllowed(false);
		tableClassement.setName("tableClassement");
		tableClassement.addMouseListener(controleur);
		scrollPaneClassement.setViewportView(tableClassement);

		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modeleTableClassement);
		tableClassement.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);

		//remplir la table
		for (Equipe equipe : tournoi.getListEquipes().keySet()) {
			modeleTableClassement.addRow(new Object[] {equipe.getImage(), equipe.getNom(), tournoi.getListEquipes().get(equipe), equipe.getId()});
		}
		sorter.sort();

		JLabel lblTitreClassement = new JLabel("Classement");
		scrollPaneClassement.setColumnHeaderView(lblTitreClassement);
		
		JPanel panel_boutons = new JPanel();
		panel_mid_top.add(panel_boutons, "cell 1 1,grow");
		panel_boutons.setLayout(new MigLayout("", "[grow][58.00,grow][58.00,grow][4.00][58.00,grow][58.00,grow]", "[133.00][][grow][138.00,grow][grow][134.00][][134.00]"));
		
		btnAjoutMatch = new RoundedButton("Ajouter un match");
		btnAjoutMatch.addActionListener(controleur);
		panel_boutons.add(btnAjoutMatch, "cell 1 0 5 1,grow");
		
		JButton btnListeEquipe = new RoundedButton("Liste des équipes");
		btnListeEquipe.addActionListener(controleur);
		panel_boutons.add(btnListeEquipe, "cell 1 3 2 1,grow");
		
		JButton btnListeArbitre = new RoundedButton("Liste des arbitres");
		btnListeArbitre.addActionListener(controleur);
		panel_boutons.add(btnListeArbitre, "cell 4 3 2 1,grow");
		
		btnCloturer = new RoundedButton("Cloturer poule");
		for (Match m : Match.getByTournoi(tournoi)) {
			if (m.getType() != TypeMatch.POOL) {
				btnCloturer.setText("Terminer le tournoi");
				btnAjoutMatch.setEnabled(false);
				if (m.getType() == TypeMatch.FINALE && m.getDateFinMatch().isBefore(new CustomDateTime(LocalDate.now()))) {
					btnCloturer.setEnabled(false);
				}
				if (tournoi.isTerminer()) {
					btnCloturer.setEnabled(false);
				}
				break;
			}
		}
		btnCloturer.addActionListener(controleur);
		panel_boutons.add(btnCloturer, "cell 1 5 5 1,grow");
		
		JButton impressionDesResultats = new RoundedButton("Impression des résultats");
		impressionDesResultats.addActionListener(controleur);
		panel_boutons.add(impressionDesResultats, "cell 1 7 5 1,grow");
		
		JPanel panelTableMatchs = new JPanel();
		panel_centre_centre.add(panelTableMatchs);
		panelTableMatchs.setLayout(new MigLayout("", "[452px,grow]", "[][402px,grow]"));
		
		JLabel lblTitreTableMatch = new JLabel("Liste des matchs : ");
		panelTableMatchs.add(lblTitreTableMatch, "cell 0 0,alignx left,aligny bottom");
		
		JScrollPane scrollPaneTableMatch = new JScrollPane();
		panelTableMatchs.add(scrollPaneTableMatch, "cell 0 1,growx,aligny center");
		
		modeleTableMatch = new DefaultTableModel(
				new Object[] {"DATE", "LOGO", "EQUIPE 1", "SCORE", "", "SCORE", "EQUIPE 2", "LOGO", ""}, 0){
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if(columnIndex == 0) {
					return CustomDateTime.class;
				}else if(columnIndex == 1) {
					return ImageIcon.class;
				}else if(columnIndex == 2) {
					return String.class;
				}else if(columnIndex == 3) {
					return String.class;
				}else if(columnIndex == 4) {
					return ImageIcon.class;
				}else if(columnIndex == 5) {
					return Integer.class;
				}else if(columnIndex == 6) {
					return String.class;
				}else if(columnIndex == 7) {
					return ImageIcon.class;
				}else {
					return Integer.class;
				}
			}
		};


		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		TableRowSorter<DefaultTableModel> sorterMatch = new TableRowSorter<DefaultTableModel>(modeleTableMatch);
		List<RowSorter.SortKey> sortKeysMatch = new ArrayList<>(25);
		sortKeysMatch.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
		sorterMatch.setSortKeys(sortKeysMatch);

				tableMatch = new JTable() {
					private static final long serialVersionUID = 1L;
					public boolean isCellEditable(int row, int column) {
						return false;
					};
				};
				
						tableMatch.setModel(modeleTableMatch);
						
								tableMatch.setShowHorizontalLines(true);
								tableMatch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								
										tableMatch.getColumnModel().getColumn(1).setCellRenderer(ImageRenderer.instance);
										tableMatch.getColumnModel().getColumn(4).setCellRenderer(ImageRenderer.instance);
										tableMatch.getColumnModel().getColumn(7).setCellRenderer(ImageRenderer.instance);
										
												tableMatch.setRowHeight(50);
												tableMatch.getColumnModel().getColumn(0).setPreferredWidth(150); // date
												tableMatch.getColumnModel().getColumn(0).setResizable(false);
												tableMatch.getColumnModel().getColumn(1).setPreferredWidth(20); // image
												tableMatch.getColumnModel().getColumn(1).setResizable(false);
												tableMatch.getColumnModel().getColumn(2).setPreferredWidth(100); // nom Equipe
												tableMatch.getColumnModel().getColumn(2).setResizable(false);
												tableMatch.getColumnModel().getColumn(3).setPreferredWidth(40); // score
												tableMatch.getColumnModel().getColumn(3).setResizable(false);
												tableMatch.getColumnModel().getColumn(4).setPreferredWidth(20); // logo
												tableMatch.getColumnModel().getColumn(4).setResizable(false);
												tableMatch.getColumnModel().getColumn(5).setPreferredWidth(40); // score2
												tableMatch.getColumnModel().getColumn(5).setResizable(false);
												tableMatch.getColumnModel().getColumn(6).setPreferredWidth(100); // nom Equipe2
												tableMatch.getColumnModel().getColumn(6).setResizable(false);
												tableMatch.getColumnModel().getColumn(7).setPreferredWidth(20); // image2
												tableMatch.getColumnModel().getColumn(7).setResizable(false);
												tableMatch.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
												tableMatch.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
												
														tableMatch.getColumnModel().getColumn(8).setMaxWidth(0);
														tableMatch.getColumnModel().getColumn(8).setMinWidth(0);
														tableMatch.getColumnModel().getColumn(8).setPreferredWidth(0);
														tableMatch.getColumnModel().getColumn(8).setResizable(false);
														
																tableMatch.getTableHeader().setReorderingAllowed(false);
																
																		tableMatch.setName("tableMatch");
																		tableMatch.addMouseListener(controleur);
																		scrollPaneTableMatch.setViewportView(tableMatch);
																		tableMatch.setRowSorter(sorterMatch);
																		
																				JLabel lblTitreMatch = new JLabel("Matchs");
																				scrollPaneTableMatch.setColumnHeaderView(lblTitreMatch);


		//remplir la table

		for (Match match: Match.getByTournoi(tournoi)) {
			//System.out.println(match);
			modeleTableMatch.addRow(new Object[] {match.getDateDebutMatch(), match.getEquipe1().getImage(), match.getEquipe1().getNom(),
					match.getScore(match.getEquipe1()), "vs", match.getScore(match.getEquipe2()), match.getEquipe2().getNom(), match.getEquipe2().getImage(), match.getId()});
		}
		sorterMatch.sort();
	}

	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public JPanel getPane() {
		return this.contentPane;
	}

	public void refreshTableMatch() {
		modeleTableMatch.setRowCount(0);
		for (Match match: Tournoi.getById(tournoi.getId()).getListMatchs()) {
			modeleTableMatch.addRow(new Object[] {match.getDateDebutMatch().format(), match.getEquipe1().getImage(), match.getEquipe1().getNom(),
					match.getScore(match.getEquipe1()), "vs", match.getScore(match.getEquipe2()), match.getEquipe2().getNom(), match.getEquipe2().getImage(), match.getId()});
		}
	}

	public void refreshTableClassement() {
		modeleTableClassement.setRowCount(0);
		Tournoi t = Tournoi.getById(tournoi.getId());
		for (Equipe equipe : t.getListEquipes().keySet()) {
			modeleTableClassement.addRow(new Object[] {equipe.getImage(), equipe.getNom(), t.getListEquipes().get(equipe), equipe.getId()});
		}
	}

	public void refreshInfo() {
		Tournoi t = Tournoi.getById(tournoi.getId());
		lblNomTournoi.setText(t.getNom());
		lblNomTournoi.setIcon(IconResized.of(t.getImage(), 100, 100));
		lblDates.setText("du "+ t.getDateDebut().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) +" au " + t.getDateFin().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
	}

	@Override
	public void update(Observable o, Object arg) {
		refreshTableMatch();
		refreshTableClassement();
		refreshInfo();
	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Tournoi", "Equipe", "Match"};
	}
}
