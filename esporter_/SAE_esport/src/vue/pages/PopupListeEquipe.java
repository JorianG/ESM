package vue.pages;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Modele.Equipe;
import Modele.Tournoi;
import vue.APP;
import vue.CustomContentPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.SwingConstants;

public class PopupListeEquipe extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_equipes;
	private DefaultTableModel tableModel;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 */
	public PopupListeEquipe(Tournoi tournoi) {

		setBounds(100, 100, 600, 500);
		contentPane = new CustomContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitre = new JLabel("Equipes du tournoi " + tournoi.getNom() + " :");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 18));
		contentPane.add(lblTitre, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Equipes");
        for (Equipe e : tournoi.getListEquipes().keySet()) {
        	tableModel.addRow(new Object[]{e.getId(), e.getCode() + " - " + e.getNom()});
        }
        
		table_equipes = new JTable(tableModel);
        table_equipes.removeColumn(table_equipes.getColumnModel().getColumn(0));

		table_equipes.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table_equipes);
		
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
}
