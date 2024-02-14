package Modele;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import DAO.DAOAdmin;

/**
 * Classe représentant un admin
 * @author Theo Lugagne
 */
public class Admin {

    /**
     * Identifiant de l'admin
     */
    int id;

    /**
     * Login de l'admin
     */
    String login;

    /**
     * Mot de passe de l'admin (hashé)
     */
    int mdp;

    /**
     * DAO permettant de gerer les admins
     */
    private static DAOAdmin dao = DAOAdmin.getDAO();

    /**
     * Constructeur de la classe Admin
     * @param login login de l'admin
     * @param mdp mot de passe de l'admin
     */
    public Admin(String login, String mdp) {
        try {
            this.setLogin(login);
            this.setMdp(mdp);
			dao.add(this);
            this.setIdSQL();
		} catch (SQLException e) {
			System.err.println("Failed DAOAdmin creation/adding in Admin constructor");
		}
    }

    /**
     * Constructeur de la classe Admin
     * @param id identifiant de l'admin
     * @param login login de l'admin
     * @param mdp mot de passe de l'admin (hashé)
     */
    public Admin(int id, String login, int mdp) {
        this.setLogin(login);
        this.setMdp(mdp);
        this.setId(id);
    }

    /**
     * Constructeur de la classe Admin
     * @param id identifiant de l'admin
     * @param login login de l'admin
     * @param mdp mot de passe de l'admin
     */
    public Admin(int id, String login, String mdp) {
        this.setLogin(login);
        this.setMdp(mdp);
        this.setId(id);
    }

    /**
     * Getter de l'identifiant de l'admin
     * @return l'identifiant de l'admin
     */
    public int getId() {
        return id;
    }

    /**
     * Getter du login de l'admin
     * @return le login de l'admin
     */
    public String getLogin() {
        return login;
    }

    /**
     * Getter du mot de passe de l'admin (hashé)
     *
     * @return le mot de passe de l'admin (hashé)
     */
    public int getMdp() {
        return mdp;
    }

    /**
     * Setter de l'identifiant de l'admin
     */
    private void setIdSQL() throws SQLException {
    	this.id = dao.getLastId();
    }

    private void setId(int id) {
        this.id = id;
    }

    /**
     * Setter du login de l'admin
     * @param login login de l'admin
     */
    public void setLogin(String login) {
    	this.login = login;
    }

    /**
     * Setter du mot de passe de l'admin (hashé)
     * @param mdp mot de passe de l'admin (hashé)
     */
    public void setMdp(String mdp) {
    	this.mdp = mdp.hashCode();
    }

    public void setMdp(int mdp) {
    	this.mdp = mdp;
    }

    /**
     * Methode permettant de comparer deux admins
     * @param o admin à comparer
     * @return true si les deux admins sont identiques, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(login, admin.login) && Objects.equals(mdp, admin.mdp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, mdp);
    }

    /**
     * Methode permettant d'afficher un admin
     * @return l'affichage de l'admin
     */
    @Override
    public String toString() {
        return "Admin " + id + " (" + login + " " + mdp + ")";
    }

    /**
     * Methode permettant d'ajouter un admin dans la base de données
     * @param admin admin à ajouter
     * @return true si l'admin a été ajouté, false sinon
     */
	public static boolean update(Admin admin) {
		try {
			return dao.update(admin);
		} catch (SQLException e) {
			System.err.println("Failed to update admin in update " + Admin.class);
			return false;
		}
	}

    /**
     * Methode permettant de supprimer un admin de la base de données
     * @param id identifiant de l'admin à supprimer
     * @return true si l'admin a été supprimé, false sinon
     */
	public static boolean delete(int id) {
		try {
			return dao.delete(id);
		} catch (SQLException e) {
			System.err.println("Failed to delete admin in delete " + Admin.class);
			return false;
		}
	}

    /**
     * Methode permettant de récupérer un admin de la base de données
     * @param id identifiant de l'admin à récupérer
     * @return l'admin récupéré
     */
	public static Admin getById(int id) {
		try {
			return dao.getById(id);
		} catch (SQLException e) {
			System.err.println("Failed to get admin in getById " + Admin.class);
			return null;
		}
	}

    /**
     * Methode permettant de récupérer tous les admins de la base de données
     * @return la liste des admins récupérés
     */
	public static List<Admin> getAll() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			System.err.println("Failed to get admin in getAll " + Admin.class);
			return null;
		}
	}
}
