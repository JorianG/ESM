package vue.pages;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controleur.ControleurGestionArbitre;
import Modele.Arbitre;
import Modele.Tournoi;
import vue.CustomContentPane;
import vue.Observer;

import javax.swing.table.DefaultTableModel;
import java.util.*;
import net.miginfocom.swing.MigLayout;

public class GestionArbitre extends JPanel implements Observer {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static JLabel lblPhoto;
	private JPanel contentPane;
	private JTable listeTournoi;

	private Arbitre arbitre;
	private JTextField textFieldRecherche;
	private static JLabel lblNom;
	private static JLabel lblPrenom;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 */
	public GestionArbitre(Arbitre arbitre) {

		ControleurGestionArbitre controleur = new ControleurGestionArbitre(this);
		this.arbitre = arbitre;
		setBounds(100, 100, 800, 500);
		contentPane = new CustomContentPane(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		Header header = new Header();
		header.addHeaderTo(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelInfo = new JPanel();
		contentPane.add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new MigLayout("", "[140.00,grow][grow]", "[][][][][][grow]"));

		lblPhoto = new JLabel("");
		lblPhoto.setIcon(IconResized.of(arbitre.getImage(), IconResized.DEFAULT_HEIGHT_IMAGE_SMALL, IconResized.DEFAULT_WIDTH_IMAGE_SMALL));
		panelInfo.add(lblPhoto, "cell 0 1 1 2,alignx center,aligny center");
		
		lblNom = new JLabel("nom");
		panelInfo.add(lblNom, "cell 1 1,alignx left,aligny center");
		
		lblPrenom = new JLabel("prenom");
		panelInfo.add(lblPrenom, "cell 1 2,alignx left,aligny center");
		
		JLabel lblNewLabel = new JLabel("Tournois auxquels l'arbitre participe : ");
		panelInfo.add(lblNewLabel, "cell 0 4,alignx left,aligny bottom");
		
		JScrollPane scrollPaneTableau = new JScrollPane();
		panelInfo.add(scrollPaneTableau, "cell 0 5 2 1,grow");
		
		
		textFieldRecherche = new JTextField();
		scrollPaneTableau.setColumnHeaderView(textFieldRecherche);
		textFieldRecherche.setColumns(10);

		listeTournoi = new JTable();
		DefaultTableModel modeleTable = new DefaultTableModel(
				new Object[] {"ICON", "NOM", "DATE", ""}, 0);
		listeTournoi = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		listeTournoi.setModel(modeleTable);

		listeTournoi.setShowHorizontalLines(true);
		listeTournoi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeTournoi.getColumnModel().getColumn(0).setCellRenderer(ImageRenderer.instance);

		listeTournoi.setRowHeight(50);
		listeTournoi.getColumnModel().getColumn(0).setPreferredWidth(50);
		listeTournoi.getColumnModel().getColumn(0).setResizable(false);
		listeTournoi.getColumnModel().getColumn(1).setPreferredWidth(300);
		listeTournoi.getColumnModel().getColumn(1).setResizable(false);
		listeTournoi.getColumnModel().getColumn(2).setPreferredWidth(80);
		listeTournoi.getColumnModel().getColumn(2).setResizable(false);

		listeTournoi.getColumnModel().getColumn(3).setMaxWidth(0);
		listeTournoi.getColumnModel().getColumn(3).setMinWidth(0);
		listeTournoi.getColumnModel().getColumn(3).setPreferredWidth(0);
		listeTournoi.getColumnModel().getColumn(3).setResizable(false);

		listeTournoi.getTableHeader().setReorderingAllowed(false);

		listeTournoi.addMouseListener(controleur);

		scrollPaneTableau.setViewportView(listeTournoi);
		//remplissage du tableau, pas modifiable donc pas de fonction refresh
		for(Tournoi t : Objects.requireNonNull(Tournoi.getTournoiByArbitre(arbitre))){
			modeleTable.addRow(new Object[] {t.getImage(), t.getNom(), t.getDateDebut(), t.getId()});
		}

		JPanel footer = new JPanel();
		FlowLayout flowLayout = (FlowLayout) footer.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(footer, BorderLayout.SOUTH);

		JButton btnQuitter = new RoundedButton("Quitter");
		btnQuitter.addActionListener(controleur);
		btnQuitter.setHorizontalAlignment(SwingConstants.RIGHT);
		btnQuitter.setName("Quitter");
		footer.add(btnQuitter);

		JButton btnModifier = new RoundedButton("Modifier");
		btnModifier.addActionListener(controleur);
		btnModifier.setHorizontalAlignment(SwingConstants.RIGHT);
		btnModifier.setName("Modifier");
		footer.add(btnModifier);
		
		lblPhoto.setIcon(IconResized.of(arbitre.getImage(), IconResized.DEFAULT_HEIGHT_IMAGE_SMALL, IconResized.DEFAULT_WIDTH_IMAGE_SMALL));
		lblPrenom.setText(arbitre.getPrenom());
		lblNom.setText(arbitre.getNom());
	}

	public static void refreshInfo(Arbitre arbitre) {
		arbitre = Arbitre.getById(arbitre.getId());
		lblNom.setText(arbitre.getNom());
		lblPrenom.setText(arbitre.getPrenom());
		lblPhoto.setIcon(IconResized.of(arbitre.getImage(), IconResized.DEFAULT_HEIGHT_IMAGE_SMALL, IconResized.DEFAULT_WIDTH_IMAGE_SMALL));
	}
	public Arbitre getArbitre() {
		return this.arbitre;
	}

	public JTable getListeTournoi() {
		return this.listeTournoi;
	}

	@Override
	public void update(Observable o, Object arg) { // TODO DEBUG
		arbitre = Arbitre.getById(arbitre.getId());
		refreshInfo(arbitre);

	}

	@Override
	public String[] getObservableKeys() {
		return new String[] {"Arbitre", "Tournoi"};
	}
}
