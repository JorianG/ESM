package vue.pages;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Observable;
import javax.swing.JPanel;
import Modele.*;
import vue.APP;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurGestionMatch;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;
import vue.Observer;

public class GestionMatch extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableTournoiTeam1;
	private JTable tableTournoiTeam2;
	private DefaultTableModel modeleTableTeam1;
	private DefaultTableModel modeleTableTeam2;
	private JPanel panelLabelScore;
	private JLabel lblScore1;
	private JLabel lblTrait;
	private JLabel lblScore2;
	private JPanel panelSpinnerScore;
	private JSpinner spinnerScore1;
	private JLabel lblTrait2;
	private JSpinner spinnerScore2;
	private JButton btnQuitter;
	private JButton btnTerminerMatch;
	private JButton btnDelete;
	private JLabel lblNomTeam1;
	private JLabel lblIconTeam1;
	private JLabel lblIconTeam2;
	private JLabel lblNomTeam2;
	private JLabel lblTempsMatch;
	
	private Match match;
	private JLabel lblTitreTableToTe1;
	private JLabel lblTitreTablToTe2;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public GestionMatch(Match match) throws SQLException {
		this.match = match;
		ControleurGestionMatch controleur = new ControleurGestionMatch(this, match);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		contentPane = new CustomContentPane(this);
		contentPane.setLayout(new MigLayout("", "[991px,grow]", "[516px,grow][33px]"));
		
		//Contenu de la page GestionMatch
		JPanel contenu = new JPanel();
		contentPane.add(contenu, "cell 0 0,grow");
		contenu.setLayout(new MigLayout("", "[991px,grow]", "[186px][330px,grow]"));
		
		JPanel panelInfo = new JPanel();
		contenu.add(panelInfo, "cell 0 0,growx,aligny top");
		panelInfo.setLayout(new MigLayout("", "[982.00px,grow,center]", "[][center][60px]"));
		
		lblTempsMatch = new JLabel("00:00.00");
		lblTempsMatch.setText("Date du match : " + match.getDateDebutMatch().format());
		panelInfo.add(lblTempsMatch, "cell 0 0");
		lblTempsMatch.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelScore = new JPanel();
		panelInfo.add(panelScore, "cell 0 1,alignx center,aligny center");

		JLabel lblTitreTypeMatch = new JLabel("Type de match :");
		lblTitreTypeMatch.setHorizontalAlignment(SwingConstants.CENTER);
		panelScore.add(lblTitreTypeMatch);

		JLabel lblTypeMatch = new JLabel(match.getType().name());
		lblTypeMatch.setHorizontalAlignment(SwingConstants.CENTER);
		panelScore.add(lblTypeMatch);

		JLabel lblEspacement = new JLabel(" | ");
		lblEspacement.setHorizontalAlignment(SwingConstants.CENTER);
		panelScore.add(lblEspacement);

		JLabel lblTitreScore = new JLabel("Score :");
		lblTitreScore.setHorizontalAlignment(SwingConstants.CENTER);
		panelScore.add(lblTitreScore);
		
		panelLabelScore = new JPanel();
		panelScore.add(panelLabelScore);
		
		lblScore1 = new JLabel("0");
		lblScore1.setText(String.valueOf(match.getScore(match.getEquipe1())));
		panelLabelScore.add(lblScore1);
		
		lblTrait = new JLabel(" - ");
		panelLabelScore.add(lblTrait);
		
		lblScore2 = new JLabel("0");
		lblScore2.setText(String.valueOf(match.getScore(match.getEquipe2())));
		panelLabelScore.add(lblScore2);
		
		panelSpinnerScore = new JPanel();
		panelScore.add(panelSpinnerScore);
		
		spinnerScore1 = new JSpinner();
		spinnerScore1.addChangeListener(controleur);
		spinnerScore1.setModel(new SpinnerNumberModel(0,0,1,1));
		spinnerScore1.setName("spinner1");
		panelSpinnerScore.add(spinnerScore1);
		
		lblTrait2 = new JLabel(" - ");
		panelSpinnerScore.add(lblTrait2);
		
		spinnerScore2 = new JSpinner();
		spinnerScore2.setModel(new SpinnerNumberModel(0,0,1,1));
		spinnerScore2.addChangeListener(controleur);
		spinnerScore2.setName("spinner2");
		panelSpinnerScore.add(spinnerScore2);
		
		JPanel panelTeam = new JPanel();
		panelInfo.add(panelTeam, "cell 0 2,alignx center,aligny center");
		
		lblNomTeam1 = new JLabel("Team1");
		lblNomTeam1.setText(match.getEquipe1().getNom() + " " + match.getEquipe1().getCode());
		panelTeam.add(lblNomTeam1);
		
		lblIconTeam1 = new JLabel();
		lblIconTeam1.setHorizontalAlignment(SwingConstants.CENTER);
		panelTeam.add(lblIconTeam1);
		setIconEquipe1(match.getEquipe1());

		JLabel lblIconVS = new JLabel(IconResized.of(IconResized.VS,100, 100));
		panelTeam.add(lblIconVS);
		
		lblIconTeam2 = new JLabel();
		lblIconTeam2.setHorizontalAlignment(SwingConstants.CENTER);
		panelTeam.add(lblIconTeam2);
		setIconEquipe2(match.getEquipe2());
		
		lblNomTeam2 = new JLabel("Team2");
		lblNomTeam2.setText(match.getEquipe2().getCode() + " " + match.getEquipe2().getNom());
		panelTeam.add(lblNomTeam2);
		
		JPanel panelTournoi = new JPanel();
		contenu.add(panelTournoi, "cell 0 1,grow");
		panelTournoi.setLayout(new MigLayout("", "[495px,grow][495px,grow]", "[][330px,grow]"));
		
		lblTitreTableToTe1 = new JLabel("Tournois inculants l'équipe " + this.match.getEquipe1().getNom() + " : ");
		panelTournoi.add(lblTitreTableToTe1, "cell 0 0,alignx left,aligny bottom");
		
		lblTitreTablToTe2 = new JLabel("Tournois inculants l'équipe " + this.match.getEquipe2().getNom() + " : ");
		panelTournoi.add(lblTitreTablToTe2, "cell 1 0,alignx left,aligny bottom");
		
		JScrollPane scrollPaneTournoiTeam1 = new JScrollPane();
		panelTournoi.add(scrollPaneTournoiTeam1, "cell 0 1,grow");

			modeleTableTeam1 = new DefaultTableModel(
	                new Object[] {"TOURNOI", "POINT", ""}, 0);
			
			tableTournoiTeam1 = new JTable() {
				private static final long serialVersionUID = 1L;
				public boolean isCellEditable(int row, int column) {
					return false;
				};
			};
	
			tableTournoiTeam1.setName("table1");
			tableTournoiTeam1.setModel(modeleTableTeam1);
	
			tableTournoiTeam1.setShowHorizontalLines(true);
			tableTournoiTeam1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
			tableTournoiTeam1.setRowHeight(50);
			tableTournoiTeam1.getColumnModel().getColumn(0).setPreferredWidth(300);
			tableTournoiTeam1.getColumnModel().getColumn(0).setResizable(false);
			tableTournoiTeam1.getColumnModel().getColumn(1).setPreferredWidth(50);
			tableTournoiTeam1.getColumnModel().getColumn(1).setResizable(false);

			tableTournoiTeam1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

			tableTournoiTeam1.getColumnModel().getColumn(2).setMaxWidth(0);
			tableTournoiTeam1.getColumnModel().getColumn(2).setMinWidth(0);
			tableTournoiTeam1.getColumnModel().getColumn(2).setPreferredWidth(0);
			tableTournoiTeam1.getColumnModel().getColumn(2).setResizable(false);

			tableTournoiTeam1.getTableHeader().setReorderingAllowed(false);
	
			tableTournoiTeam1.addMouseListener(controleur);
	
			scrollPaneTournoiTeam1.setViewportView(tableTournoiTeam1);
			//remplissage du tableau
			for(Tournoi t : Objects.requireNonNull(Tournoi.getTournoiByEquipe(match.getEquipe1()))){
				modeleTableTeam1.addRow(new Object[] {t.toString(), Tournoi.getEquipePoints(t, match.getEquipe1()), t.getId()});
			}
		
		JScrollPane scrollPaneTournoiTeam2 = new JScrollPane();
		panelTournoi.add(scrollPaneTournoiTeam2, "cell 1 1,grow");
		
		modeleTableTeam2 = new DefaultTableModel(
                new Object[] {"TOURNOI", "POINT", ""}, 0);
		
		tableTournoiTeam2 = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		tableTournoiTeam2.setName("table2");
		tableTournoiTeam2.setModel(modeleTableTeam2);

		tableTournoiTeam2.setShowHorizontalLines(true);
		tableTournoiTeam2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableTournoiTeam2.setRowHeight(50);
		tableTournoiTeam2.getColumnModel().getColumn(0).setPreferredWidth(300);
		tableTournoiTeam2.getColumnModel().getColumn(0).setResizable(false);
		tableTournoiTeam2.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableTournoiTeam2.getColumnModel().getColumn(1).setResizable(false);
		tableTournoiTeam2.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

		tableTournoiTeam2.getColumnModel().getColumn(2).setMaxWidth(0);
		tableTournoiTeam2.getColumnModel().getColumn(2).setMinWidth(0);
		tableTournoiTeam2.getColumnModel().getColumn(2).setPreferredWidth(0);
		tableTournoiTeam2.getColumnModel().getColumn(2).setResizable(false);

		tableTournoiTeam2.getTableHeader().setReorderingAllowed(false);

		tableTournoiTeam2.addMouseListener(controleur);

		scrollPaneTournoiTeam2.setViewportView(tableTournoiTeam2);
		//remplissage du tableau
		for(Tournoi t : Objects.requireNonNull(Tournoi.getTournoiByEquipe(match.getEquipe2()))){
			modeleTableTeam2.addRow(new Object[] {t.toString(), Tournoi.getEquipePoints(t, match.getEquipe2()), t.getId()});
		}
		
		JPanel footer = new JPanel();
		contentPane.add(footer, "cell 0 1,growx,aligny top");
		footer.setLayout(new BorderLayout(0, 0));
		
		Box horizontalBox = Box.createHorizontalBox();
		footer.add(horizontalBox, BorderLayout.EAST);
		
			JPanel panelDelete = new JPanel();
			horizontalBox.add(panelDelete);
			panelDelete.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
				btnDelete = new RoundedButton("Supprimer le match");
				btnDelete.setForeground(Palette.DEL_BACKGROUND.getColor());
				btnDelete.setBackground(Palette.DEL_FOREGROUND.getColor());
				btnDelete.addActionListener(controleur);
				panelDelete.add(btnDelete);
			
			JPanel ecart2 = new JPanel();
			horizontalBox.add(ecart2);
		
			JPanel panelTerminerMatch = new JPanel();
			horizontalBox.add(panelTerminerMatch);
			panelTerminerMatch.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
				btnTerminerMatch = new RoundedButton("Terminer le match");
				btnTerminerMatch.setForeground(Palette.DEL_BACKGROUND.getColor());
				btnTerminerMatch.setBackground(Palette.DEL_FOREGROUND.getColor());
				btnTerminerMatch.addActionListener(controleur);
				panelTerminerMatch.add(btnTerminerMatch);
			
			JPanel ecart = new JPanel();
			horizontalBox.add(ecart);
			
			JPanel panelQuitter = new JPanel();
			horizontalBox.add(panelQuitter);
			panelQuitter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
				btnQuitter = new RoundedButton("Quitter");
				btnQuitter.addActionListener(controleur);
				panelQuitter.add(btnQuitter);
				
				
			if (APP.isArbitre) {
				affichageArbitre();
				setEnabledTerminer(false);
			}else {
				affichageOther();
			}
	}
	
	public boolean verif() {
		boolean isFilled = false;
		CustomDateTime dateDuJour = new CustomDateTime(LocalDateTime.now());
		if ((getScoreEquipe1() != 0 || getScoreEquipe2() != 0) && match.getDateDebutMatch().isBefore(dateDuJour)) {
			isFilled = true;
		}
		return isFilled;
	}
	
	public void setEnabledTerminer(boolean b) {
		btnTerminerMatch.setEnabled(b);
	}
	
	public void affichageArbitre() {
		panelSpinnerScore.setVisible(true);
		spinnerScore1.setVisible(true);
		lblTrait2.setVisible(true);
		spinnerScore2.setVisible(true);
		btnTerminerMatch.setVisible(true);
		
		panelLabelScore.setVisible(false);
		lblScore1.setVisible(false);
		lblTrait.setVisible(false);
		lblScore2.setVisible(false);
		btnDelete.setVisible(false);
	}
	public void affichageOther() {
		panelSpinnerScore.setVisible(false);
		spinnerScore1.setVisible(false);
		lblTrait2.setVisible(false);
		spinnerScore2.setVisible(false);
		btnTerminerMatch.setVisible(false);
		
		panelLabelScore.setVisible(true);
		lblScore1.setVisible(true);
		lblTrait.setVisible(true);
		lblScore2.setVisible(true);
		btnDelete.setVisible(true);
		
	}
	
	public int getScoreEquipe1() {
		return (int) spinnerScore1.getValue();
	}
	
	public int getScoreEquipe2() {
		return (int) spinnerScore2.getValue();
	}
	
	public void setScoreEquipe1(int score) {
		spinnerScore1.setValue(score);
	}
	
	public void setScoreEquipe2(int score) {
		spinnerScore2.setValue(score);
	}
	
	public void setIconEquipe1(Equipe equipe) {
		lblIconTeam1.setIcon(IconResized.of(equipe.getImage(),100, 100));
	}
	
	public void setIconEquipe2(Equipe equipe) {
		lblIconTeam2.setIcon(IconResized.of(equipe.getImage(), 100, 100));
	}
	
	public JTable getTableTournoiTeam1() {
		return this.tableTournoiTeam1;
	}
	
	public JTable getTableTournoiTeam2() {
		return this.tableTournoiTeam2;
	}

	public Match getMatch() {
		return match;
	}

	public void refreshTableTournoi1() {
		modeleTableTeam1.setRowCount(0);
		for(Tournoi t : Objects.requireNonNull(Tournoi.getTournoiByEquipe(match.getEquipe1()))){
			modeleTableTeam1.addRow(new Object[] {t.toString(), Tournoi.getEquipePoints(t, match.getEquipe1()), t.getId()});
		}
	}

	public void refreshTableTournoi2() {
		modeleTableTeam2.setRowCount(0);
		for(Tournoi t : Objects.requireNonNull(Tournoi.getTournoiByEquipe(match.getEquipe2()))){
			modeleTableTeam2.addRow(new Object[] {t.toString(), Tournoi.getEquipePoints(t, match.getEquipe2()), t.getId()});
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		refreshTableTournoi1();
		refreshTableTournoi2();
	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Tournoi"};
	}
}
