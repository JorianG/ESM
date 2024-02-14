package vue.pages;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.util.*;

import Controleur.ControleurGestionJoueur;
import Modele.*;
import connect.DBConnect;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import javax.swing.JTable;
import java.sql.SQLException;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;
import vue.Observer;


public class GestionJoueur extends JPanel implements Observer {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	private DefaultTableModel modeleTable;

	private ControleurGestionJoueur controle;
	private JTable tableMatch;
	private List<Match> lstMatch;

	private JLabel lblPhoto;
	private JLabel lblNom;
	private JLabel lblPrenom;
	private JLabel lblPseudo;
	private JLabel lblPoste;
	private JLabel lblequipeLogo;
	private JLabel lblPays;

	public Joueur joueur;


	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException
	 */
	public GestionJoueur(Joueur joueur, ListeEquipe listeEquipe) throws SQLException {
		this.joueur = joueur;
		this.controle= new ControleurGestionJoueur(DBConnect.getDbInstance(), this, joueur, listeEquipe);
		setBounds(100, 100, 1000, 500);
		contentPane = new CustomContentPane(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new MigLayout("", "[grow][][grow]", "[][][][][grow][]"));

		lblPhoto = new JLabel(IconResized.of(joueur.getImage(), IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));
		contentPane.add(lblPhoto, "cell 0 0 1 3,alignx center,aligny center");

		lblNom = new JLabel(joueur.getNom());
		contentPane.add(lblNom, "cell 1 0,aligny center");

		lblPrenom = new JLabel(joueur.getPrenom());
		contentPane.add(lblPrenom, "cell 2 0,alignx left,aligny center");

		lblPseudo = new JLabel(joueur.getPseudo());
		contentPane.add(lblPseudo, "cell 1 1,aligny center");

		lblPoste = new JLabel("   "+joueur.getPoste().name());
		lblPoste.setIcon(IconResized.of(joueur.getPoste().getImage(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
		contentPane.add(lblPoste, "cell 2 1,alignx left,aligny center");

		lblequipeLogo = new JLabel(IconResized.of(Equipe.getById(joueur.getId_equipe()).getImage(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
		contentPane.add(lblequipeLogo, "cell 1 2,aligny center");

		lblPays = new JLabel(IconResized.of(joueur.getPays().getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
		contentPane.add(lblPays, "cell 2 2,alignx left,aligny center");

		JLabel lblTitreMatchs = new JLabel("Matchs joués : ");
		contentPane.add(lblTitreMatchs, "cell 0 3");

		JScrollPane scrollPane = new JScrollPane();


		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		lstMatch = Match.getByJoueur(Joueur.getById(joueur.getId()));

		this.modeleTable = new DefaultTableModel(
				new Object[] {"Equipe 1","","VS","","Equipe 2","Resultat", "Date", "VOIR", ""}, 0);
		tableMatch = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		tableMatch.setModel(modeleTable);
		tableMatch.setShowHorizontalLines(true);
		tableMatch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableMatch.setRowHeight(40);
		tableMatch.setDefaultEditor(Object.class, null);
		tableMatch.setRowHeight(50);

		tableMatch.getColumnModel().getColumn(1).setCellRenderer(ImageRenderer.instance);
		tableMatch.getColumnModel().getColumn(2).setCellRenderer(ImageRenderer.instance);
		tableMatch.getColumnModel().getColumn(3).setCellRenderer(ImageRenderer.instance);
		tableMatch.getColumnModel().getColumn(7).setCellRenderer(ImageRenderer.instance);

		tableMatch.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableMatch.getColumnModel().getColumn(0).setResizable(false);
		tableMatch.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);


		tableMatch.getColumnModel().getColumn(1).setPreferredWidth(40);
		tableMatch.getColumnModel().getColumn(1).setMaxWidth(40);
		tableMatch.getColumnModel().getColumn(1).setResizable(false);

		tableMatch.getColumnModel().getColumn(2).setPreferredWidth(40);
		tableMatch.getColumnModel().getColumn(2).setMaxWidth(40);
		tableMatch.getColumnModel().getColumn(2).setResizable(false);

		tableMatch.getColumnModel().getColumn(3).setPreferredWidth(40);
		tableMatch.getColumnModel().getColumn(3).setMaxWidth(40);
		tableMatch.getColumnModel().getColumn(3).setResizable(false);

		tableMatch.getColumnModel().getColumn(4).setPreferredWidth(50);
		tableMatch.getColumnModel().getColumn(4).setResizable(false);
		tableMatch.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

		tableMatch.getColumnModel().getColumn(5).setPreferredWidth(100);
		tableMatch.getColumnModel().getColumn(5).setResizable(false);
		tableMatch.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		tableMatch.getColumnModel().getColumn(6).setResizable(false);
		tableMatch.getColumnModel().getColumn(6).setPreferredWidth(100);

		tableMatch.getColumnModel().getColumn(7).setPreferredWidth(ImageRenderer.size);
		tableMatch.getColumnModel().getColumn(7).setResizable(false);

		tableMatch.getColumnModel().getColumn(8).setMinWidth(0);
		tableMatch.getColumnModel().getColumn(8).setMaxWidth(0);
		tableMatch.getColumnModel().getColumn(8).setPreferredWidth(0);
		tableMatch.getColumnModel().getColumn(8).setWidth(0);
		tableMatch.getColumnModel().getColumn(8).setResizable(false);

		tableMatch.addMouseListener(controle);
		scrollPane.setViewportView(tableMatch);
		tableMatch(lstMatch);
		contentPane.add(scrollPane, "cell 0 4 3 1,grow");

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, "cell 0 5 3 1,grow");

		JButton btnModifier = new RoundedButton("Modifier");
		btnModifier.addActionListener(controle);
		panel.add(btnModifier);

		JButton btnQuitter = new RoundedButton("Quitter");
		btnQuitter.addActionListener(controle);
		panel.add(btnQuitter);

	}

	public void tableMatch(List<Match>lst){
		this.modeleTable.setRowCount(0);
		for(Match m : lst){
			this.modeleTable.addRow(new Object[]{m.getEquipe1().getNom(),m.getEquipe1().getImage(), "vs", m.getEquipe2().getImage(),
					m.getEquipe2().getNom(),
					m.getScore(m.getEquipe1()) + " - " + m.getScore(m.getEquipe2()), m.getDateDebutMatch().format(), "modif", m.getId()});
		}
	}

	public void refreshInfoJoueur() {
		Joueur j = Joueur.getById(joueur.getId());
		lblNom.setText(j.getNom());
		lblPrenom.setText(j.getPrenom());
		lblPseudo.setText(j.getPseudo());
		lblPoste.setIcon(IconResized.of(j.getPoste().getImage(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
		lblequipeLogo.setIcon(IconResized.of(Equipe.getById(j.getId_equipe()).getImage(), IconResized.DEFAULT_WIDTH_POSTE, IconResized.DEFAULT_HEIGHT_POSTE));
		lblPays.setIcon(IconResized.of(j.getPays().getDrapeau(), IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
		lblPhoto.setIcon(IconResized.of(j.getImage(), IconResized.DEFAULT_WIDTH_IMAGE, IconResized.DEFAULT_HEIGHT_IMAGE));

	}

	@Override
	public void update(Observable o, Object arg) {
		tableMatch(lstMatch);
		refreshInfoJoueur();

	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Joueur", "Match"};
	}
}
