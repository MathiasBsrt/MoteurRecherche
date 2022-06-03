package vueconsole;

import controleur.ControlAdmin;

public class BoundaryModeAdmin {
	private ControlAdmin controleurAdmin;
	private BoundaryIndexer boundaryIndex;
	private BoundarySidentifier boundarySidentifier;
	private BoundaryParamSeuil boundaryParamSeuil;

	public BoundaryModeAdmin(ControlAdmin controleur, BoundaryIndexer bindex, BoundarySidentifier bSidentifier,
			BoundaryParamSeuil bParamSeuil) {
		this.controleurAdmin = controleur;
		this.boundaryIndex = bindex;
		this.boundarySidentifier = bSidentifier;
		this.boundaryParamSeuil = bParamSeuil;

	}

	public void lancerModeAdmin() {
		System.out.println("Bienvenue dans le mode administrateur");

		// Ici, on laisse deux chances � l'utilisateur pour se connecter,
		// avant de retourner au menu principale.
		if (!controleurAdmin.isConnect()) {
			System.out.println(
					"Vous devez �tre connect� pour pouvoir entrer dans le mode administration. D�but de l'identification:");
			this.boundarySidentifier.lancerIdentification();
		}
		if (controleurAdmin.isConnect()) {
			// S'il est connecté
			int choix = 0;
			while (choix != 1 && choix != 2 && choix != 3) {
				System.out.println(
						"Choisissez l'action � effectuer : 1. Indexer des documents 2.Parametrer les seuils de recherche 3. Retour");
				choix = Clavier.entrerClavierInt();

				if (choix != 1 && choix != 2 && choix != 3) {
					System.out.println("Veuillez saisir une entr�e valide.");
				}
			}
			if (choix == 1) {
				// Indexation
				this.boundaryIndex.lancerIndexation();
			} else if (choix == 2) {
				// Parametrage seuil
				this.boundaryParamSeuil.lancerParamSeuil();
			}

		} else {
			System.out.println("Retour au menu principale.");
		}
	}
}
