package controleur;

import modele.Admin;

public class ControlAdmin {
	private Admin admin = Admin.getInstance();

	public boolean isConnect() {
		return admin.isConnect();
	}

	public boolean compteCree() {
		return admin.compteCree();
	}

	public boolean creerCompte(String id, String mdp) {
		return admin.creerCompte(id, mdp);
	}

	public boolean connexion(String id, String mdp) {
		return admin.connexion(id, mdp);
	}

	public void deconnexion() {
		admin.deconnexion();
	}

}
