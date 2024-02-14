package vue.pages;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class IconResized extends ImageIcon{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final URL LOGO_ESPORTER = IconResized.class.getResource("/vue/images/LOGO_esporter.png");
	public static final URL PLACEHOLDER = IconResized.class.getResource("/vue/images/placeholder.png");
	public static final URL LOGOUT = IconResized.class.getResource("/vue/images/iconLogout.png");
	public static final URL VS = IconResized.class.getResource("/vue/images/vs.png");
	public static final URL DEFAULT_DRAPEAU = IconResized.class.getResource("/vue/flag/fr.png");
	public static final URL TOURNOI = IconResized.class.getResource("/vue/images/tournoi.png");
	public static final URL JOUEUR = IconResized.class.getResource("/vue/images/joueur.png");
	public static final URL ARBITRE = IconResized.class.getResource("/vue/images/arbitre.png");

	public static final int DEFAULT_WIDTH_IMAGE = 180;
	public static final int DEFAULT_HEIGHT_IMAGE = 180;
	public static final int DEFAULT_WIDTH_IMAGE_SMALL = 80;
	public static final int DEFAULT_HEIGHT_IMAGE_SMALL = 80;
	public static final int DEFAULT_WIDTH_IMAGE_MID = 110;
	public static final int DEFAULT_HEIGHT_IMAGE_MID = 110;
	public static final int DEFAULT_WIDTH_DRAPEAU = 100;
	public static final int DEFAULT_HEIGHT_DRAPEAU = 70;
	public static final int DEFAULT_WIDTH_POSTE = 70;
	public static final int DEFAULT_HEIGHT_POSTE = 70;

	/**
	 *  Chemin de l'image
	 */
	String image;

	/**
	 * Constructeur de l'objet IconResized
	 * @param icon URL de l'image
	 */
	private IconResized(URL icon) {
		super(icon);
		this.image = icon.getPath();
	}

	/**
	 * Constructeur de l'objet IconResized
	 * @param icon Image
	 * @param path Chemin de l'image
	 */
	private IconResized(Image icon, String path) {
		super(icon);
		if (path.charAt(0) == '/' && path.charAt(1) == 'C') {
			this.image = path.substring(1);
		} else {
			this.image = path;
		}
	}

	/**
	 * Redimensionne l'image
	 * @param longueur longueur de l'image (en pixel)
	 * @param hauteur hauteur de l'image (en pixel)
	 * @return IconResized image redimensionnée
	 */
	public IconResized resize(int longueur, int hauteur) {
		Image res = this.getImage().getScaledInstance(longueur, hauteur, Image.SCALE_SMOOTH);
		return new IconResized(res, this.image);
	}

	/**
	 * builder de l'objet IconResized
	 * @param path Chemin de l'image
	 * @param longueur longueur de l'image (en pixel)
	 * @param hauteur hauteur de l'image (en pixel)
	 * @return IconResized image redimensionnée
	 */
	public static IconResized of(String path, int longueur, int hauteur) {
		try {
			return new IconResized(ImageIO.read(new File(path)), path).resize(longueur, hauteur);
		} catch (IOException e) {
			URL imageUrl = IconResized.class.getResource("/vue/images/" + path + ".png");
			if (imageUrl != null) {
				return new IconResized(imageUrl).resize(longueur, hauteur);
			}
			imageUrl = IconResized.class.getResource("/vue/flag/" + path + ".png");
			if (imageUrl != null) {
				return new IconResized(imageUrl).resize(longueur, hauteur);
			}
			imageUrl = IconResized.class.getResource("/vue/imgPoste/" + path + ".png");
			if (imageUrl != null) {
				return new IconResized(imageUrl).resize(longueur, hauteur);
			}
			return new IconResized(PLACEHOLDER).resize(longueur, hauteur);
		}
    }

	/**
	 * builder de l'objet IconResized
	 * @param path Chemin de l'image
	 * @return IconResized image
	 */
	public static IconResized of(String path) {
		try {
			return new IconResized(ImageIO.read(new File(path)), path);
		} catch (IOException e) {
			URL imageUrl = IconResized.class.getResource("/vue/images/" + path + ".png");
			if (imageUrl != null) {
				return new IconResized(imageUrl);
			}
			imageUrl = IconResized.class.getResource("/vue/flag/" + path + ".png");
			if (imageUrl != null) {
				return new IconResized(imageUrl);
			}
			imageUrl = IconResized.class.getResource("/vue/imgPoste/" + path + ".png");
			if (imageUrl != null) {
				return new IconResized(imageUrl);
			}
			return new IconResized(PLACEHOLDER);
		}
	}

	/**
	 * builder de l'objet IconResized
	 * @param icon URL de l'image
	 * @return IconResized image
	 */
	public static IconResized of(URL icon) {
		return new IconResized(icon);
	}

	/**
	 * builder de l'objet IconResized
	 * @param icon URL de l'image
	 * @param longueur longueur de l'image (en pixel)
	 * @param hauteur hauteur de l'image (en pixel)
	 * @return IconResized image redimensionnée
	 */
	public static IconResized of(URL icon, int longueur, int hauteur) {
		return new IconResized(icon).resize(longueur, hauteur);
	}

	/**
	 * Retourne le chemin de l'image contenue dans un JLabel
	 * @param label JLabel contenant l'image
	 * @return String chemin de l'image
	 */
	public static String getPath(JLabel label) {
		if (label.getIcon() != null)
			return ((IconResized) label.getIcon()).image;
		else {
			return "/vue/images/placeholder.png";
		}
	}

	/**
	 * Ouvre une fenêtre de sélection de fichier jpg ou png
	 * @return String chemin complet absolu de l'image
	 */
	public static String fileSelector() {
		JFileChooser choose = new JFileChooser(
				System.getProperty("user.dir")
		);
		choose.setDialogTitle("Selectionnez une image");
		choose.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images JPG et PNG", "jpg", "png");
		choose.addChoosableFileFilter(filter);
		int res = choose.showOpenDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			return choose.getSelectedFile().getPath();
		} else {
			return "placeholder";
		}
	}

	/**
	 * Retourne le chemin de l'image
	 * @return String image
	 */
	@Override
	public String toString() {
		return this.image;
	}
}
