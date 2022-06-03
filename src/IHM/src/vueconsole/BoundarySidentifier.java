package vueconsole;

import controleur.ControlAdmin;

public class BoundarySidentifier {
	private ControlAdmin controleurAdmin;

	public BoundarySidentifier(ControlAdmin cAdmin) {
		this.controleurAdmin = cAdmin;
	}

	public boolean lancerIdentification() {
		if (!controleurAdmin.compteCree()) {
			this.creerCompte();
		}
		System.out.println("Veuillez vous identifier avant de rentrer dans le mode administrateur.");
		boolean valide = false;
		int giveUp = 0;
		while (!valide && giveUp == 0) {
			System.out.print("Saisissez votre identifiant : ");
			String id = Clavier.entrerClavierString();
			System.out.print("Saisissez votre mot de passe : ");
			String mdp = Clavier.entrerClavierString();

			valide = controleurAdmin.connexion(id, mdp);

			if (!valide) {
				System.out.println("Identifiants incorrects, veuillez ressayer");
				System.out.print("Voulez vous essayer de nouveau ? (0=yes ou 1=non)");
				giveUp = Clavier.entrerClavierInt();
			}
		}

		if (!valide) {
			System.out.println("Retour au menu principal");
		} else {
			System.out.println("Connexion...");
			System.out.println("Connecté !");
		}

		return valide;
	}

	public void creerCompte() {
		System.out.println("Première connexion");
		System.out.println("Vous devez créer votre compte");
		System.out.print("Saisissez votre identifiant : ");
		String id = Clavier.entrerClavierString();
		System.out.print("Saisissez votre mot de passe : ");
		String mdp = Clavier.entrerClavierString();
		controleurAdmin.creerCompte(id, mdp);
		System.out.print("Votre compte a bien été crée");
	}
}
