package vue.pages;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import Controleur.ControleurPageConnexion;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;

import javax.swing.JButton;

public class PageConnexion extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Font BASIC_FONT = new Font("Trebuchet MS", Font.BOLD, 16);

	private JPanel ContentPane;
	private JLabel lblIcon;
	private JLabel lblLogin;
	private JTextField textFieldLogin;
	private JLabel lblPassword;
	private JTextField textFieldPassword;
	private JButton btnConnexion;
	private JButton btnAnnuler;

	public JPanel getContentPane() {
		return ContentPane;
	}

	/**
	 * Create the frame.
	 */
	public PageConnexion() {
		
		ControleurPageConnexion controleur = new ControleurPageConnexion(this);
		setBackground(Palette.BACKGROUND.getColor());
		setBounds(100, 100, 717, 536);
		ContentPane = new CustomContentPane();
		ContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		ContentPane.setLayout(new MigLayout("", "[148.00,grow][grow][148.00,grow]", "[grow][][grow][][][][][][grow][][grow][grow][]"));
		
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(PageConnexion.class.getResource("/vue/images/LOGO_esporter.png")));
		ContentPane.add(lblIcon, "cell 1 1,alignx center");
		
		lblLogin = new JLabel("Identifiant : ");
		ContentPane.add(lblLogin, "cell 1 3");
		
		textFieldLogin = new JTextField();
		ContentPane.add(textFieldLogin, "cell 1 4,growx");
		textFieldLogin.putClientProperty( "JComponent.roundRect", true);
		textFieldLogin.setColumns(10);
		
		lblPassword = new JLabel("Mot de passe : ");
		ContentPane.add(lblPassword, "cell 1 6");
		
		textFieldPassword = new JPasswordField();
		ContentPane.add(textFieldPassword, "cell 1 7,growx");
		textFieldPassword.putClientProperty( "JComponent.roundRect", true);
		textFieldPassword.setColumns(10);
		
		btnConnexion = new RoundedButton("Connexion");
		btnConnexion.addActionListener(controleur);
		ContentPane.add(btnConnexion, "cell 1 9,alignx right");
		
		btnAnnuler = new RoundedButton("Quitter");
		btnAnnuler.addActionListener(controleur);
		ContentPane.add(btnAnnuler, "cell 2 12,alignx right");
	}
	
	public String getLogin() {
		return textFieldLogin.getText();
	}
	
	public String getPassword() {
		return textFieldPassword.getText();
	}
	
	public void emptyLogPass() {
		textFieldLogin.setText("");
		textFieldPassword.setText("");
	}
}
