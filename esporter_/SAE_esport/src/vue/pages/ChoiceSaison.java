package vue.pages;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controleur.ControleurChoiceSaison;
import javax.swing.JButton;
import java.time.LocalDate;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import vue.CustomContentPane;

import javax.swing.JLabel;
import java.awt.Font;

public class ChoiceSaison extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ControleurChoiceSaison controleur;

	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Create the frame.
	 */
	public ChoiceSaison() {
		controleur = new ControleurChoiceSaison();
		setBackground(Palette.BACKGROUND.getColor());
		setBounds(100, 100, 290*2, 145*2);
		contentPane = new CustomContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitre = new JPanel();
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new MigLayout("", "[][241px,grow][]", "[26px]"));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(IconResized.of(IconResized.LOGO_ESPORTER, IconResized.DEFAULT_WIDTH_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
		panelTitre.add(lblLogo, "cell 0 0,alignx left,aligny center");
		
		JLabel lblTitre = new JLabel("Sélection de la saison :");
		lblTitre.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 22));
		panelTitre.add(lblTitre, "cell 1 0,alignx center,aligny center");
		
		JLabel lblDeconnect = new JLabel("");
		lblDeconnect.setIcon(IconResized.of(IconResized.LOGOUT, IconResized.DEFAULT_HEIGHT_DRAPEAU, IconResized.DEFAULT_HEIGHT_DRAPEAU));
		lblDeconnect.addMouseListener(controleur);
		lblDeconnect.setName("deconnect");
		panelTitre.add(lblDeconnect, "cell 2 0,alignx right,aligny center");
		
		JPanel panelChoix = new JPanel();
		contentPane.add(panelChoix, BorderLayout.CENTER);
		panelChoix.setLayout(new MigLayout("", "[grow][132.00][30][132.00][grow]", "[grow][132.00][grow]"));
		
		JButton btnSaisonC = new RoundedButton("Saison courante \n" + LocalDate.now().getYear());
		panelChoix.add(btnSaisonC, "cell 1 1,grow");
		btnSaisonC.addActionListener(controleur);
		btnSaisonC.setName("courante");
		
		JButton btnSaisonS = new RoundedButton("Saison suivante \n" + (LocalDate.now().getYear() + 1));
		panelChoix.add(btnSaisonS, "cell 3 1,grow");
		btnSaisonS.addActionListener(controleur);
		btnSaisonS.setName("suivante");
		
	}
}
