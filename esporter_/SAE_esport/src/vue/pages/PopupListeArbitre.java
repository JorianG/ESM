package vue.pages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Modele.Arbitre;
import Modele.Tournoi;
import vue.APP;
import vue.CustomContentPane;

public class PopupListeArbitre extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_arbitres;
	private DefaultTableModel tableModel;
	
	private Tournoi tournoi;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 */
	public PopupListeArbitre(Tournoi tournoi) {
		
		this.tournoi = tournoi;
		
		this.setName("page PopupListeArbitre");

		setBounds(100, 100, 600, 500);
		contentPane = new CustomContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitre = new JLabel("Arbitres du tournoi " + tournoi.getNom() + " :");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 18));
		contentPane.add(lblTitre, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tableModel = new DefaultTableModel(new Object[] {"id", "IMG" , "arbitre"}, 0);
        for (Arbitre a : tournoi.getPoolArbitres()) {
        	tableModel.addRow(new Object[]{a.getId(),a.getImage(), a.getPrenom() + " " + a.getNom()});
        }
        
        table_arbitres = new JTable() {
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
        		return false;
        				}};
        table_arbitres.setModel(tableModel);
		table_arbitres.setRowHeight(80);
		table_arbitres.setShowHorizontalLines(true);

		table_arbitres.getColumnModel().getColumn(0).setMaxWidth(0);
		table_arbitres.getColumnModel().getColumn(0).setMinWidth(0);
		table_arbitres.getColumnModel().getColumn(0).setPreferredWidth(0);
		table_arbitres.getColumnModel().getColumn(0).setResizable(false);

		table_arbitres.getColumnModel().getColumn(1).setMaxWidth(100);
		table_arbitres.getColumnModel().getColumn(1).setCellRenderer(ImageRenderer.instance);
		table_arbitres.getColumnModel().getColumn(1).setResizable(false);

        table_arbitres.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table_arbitres);


		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnRetour = new RoundedButton("Retour");
		btnRetour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				APP.previous();
			}
		});
		panel.add(btnRetour);
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
}
