package vue.pages;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controleur.ControleurModifierArbitre;
import Modele.Arbitre;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.sql.SQLException;

import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;

public class ModifierArbitre extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldPrenom;
	private JTextField textFieldNom;
	private JLabel lblImage;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ModifierArbitre(Arbitre arbitre) throws SQLException {
		ControleurModifierArbitre controleur = new ControleurModifierArbitre(this, arbitre);
		setBounds(100, 100, 800, 300);
		contentPane = new CustomContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitre = new JLabel("Modifier arbitre");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitre, BorderLayout.NORTH);
		
		JPanel panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panelImage = new JPanel();
		panelCenter.add(panelImage);
		panelImage.setLayout(new MigLayout("", "[80px,grow]", "[80px,grow]"));

		lblImage = new JLabel(IconResized.of(arbitre.getImage(), IconResized.DEFAULT_WIDTH_IMAGE_SMALL, IconResized.DEFAULT_HEIGHT_IMAGE_SMALL));
		panelImage.add(lblImage, "cell 0 0,alignx center,aligny center");
		lblImage.addMouseListener(controleur);
		
		JPanel panelPrenom = new JPanel();
		panelCenter.add(panelPrenom);
		panelPrenom.setLayout(new MigLayout("", "[46px,grow][86px,grow]", "[20px,grow]"));
		
		JLabel lblPrenom = new JLabel("Prenom : ");
		panelPrenom.add(lblPrenom, "cell 0 0,alignx right,aligny center");
		
		textFieldPrenom = new JTextField();
		textFieldPrenom.setColumns(10);
		panelPrenom.add(textFieldPrenom, "cell 1 0,growx,aligny center");
		
		JPanel panelNom = new JPanel();
		panelCenter.add(panelNom);
		panelNom.setLayout(new MigLayout("", "[31px,grow][86px,grow]", "[20px,grow]"));
		
		JLabel lblNom = new JLabel("Nom : ");
		panelNom.add(lblNom, "cell 0 0,alignx right,aligny center");
		
		textFieldNom = new JTextField();
		textFieldNom.setColumns(10);
		panelNom.add(textFieldNom, "cell 1 0,growx,aligny center");
		
		JPanel footer = new JPanel();
		FlowLayout flFooter = (FlowLayout) footer.getLayout();
		flFooter.setAlignment(FlowLayout.RIGHT);
		contentPane.add(footer, BorderLayout.SOUTH);
		
		JButton btnEnregistrer = new RoundedButton("Enregistrer");
		btnEnregistrer.addActionListener(controleur);
		footer.add(btnEnregistrer);
		
		JButton btnSupprimer = new RoundedButton("Supprimer");
		btnSupprimer.setForeground(Palette.DEL_BACKGROUND.getColor());
		btnSupprimer.setBackground(Palette.DEL_FOREGROUND.getColor());
		btnSupprimer.addActionListener(controleur);
		footer.add(btnSupprimer);
		
		JButton btnAnnuler = new RoundedButton("Annuler");
		btnAnnuler.addActionListener(controleur);
		footer.add(btnAnnuler);
		
		
		textFieldNom.setText(arbitre.getNom());
		textFieldPrenom.setText(arbitre.getPrenom());
	}
	
	public String getNomTextField() {
		return textFieldNom.getText();
	}
	
	public String getPrenomTextField() {
		return textFieldPrenom.getText();
	}
	
	public Boolean isTextFieldFill() {
		return !(textFieldPrenom.getText().isEmpty() || textFieldNom.getText().isEmpty());
	}

	public String getImage() {
		return ((IconResized) lblImage.getIcon()).image;
	}
}
