package modele;

public class Admin {

	private boolean connecte = false;
	private String id = null;
	private String mdp = null;

	private boolean isCompteCree = false;

	private static class AdminHolder {
		public static Admin INSTANCE = new Admin();
	}

	public static Admin getInstance() {
		return AdminHolder.INSTANCE;
	}

	public boolean isConnect() {
		return connecte;
	}

	public boolean connexion(String id, String mdp) {
		if (this.id.equals(id) && this.mdp.equals(mdp)) {
			connecte = true;
		}
		return connecte;
	}

	public void deconnexion() {
		connecte = false;
	}

	public boolean compteCree() {
		return isCompteCree;
	}

	public boolean creerCompte(String id, String mdp) {
		this.id = id;
		this.mdp = mdp;
		connecte = true;
		isCompteCree = true;
		return isCompteCree;
	}

}
