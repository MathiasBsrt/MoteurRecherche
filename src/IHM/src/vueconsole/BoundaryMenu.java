package vueconsole;

public class BoundaryMenu {

	private BoundaryModeAdmin boundaryModeAdmin;
	private BoundaryRecherche boundaryRecherche;
	private BoundaryVisualiserResultat boundVisualiserResultat;

	public BoundaryMenu(BoundaryModeAdmin boundaryModeAdmin, BoundaryRecherche boundaryRecherche,
			BoundaryVisualiserResultat boundVisualiserResultat) {
		this.boundaryModeAdmin = boundaryModeAdmin;
		this.boundaryRecherche = boundaryRecherche;
		this.boundVisualiserResultat = boundVisualiserResultat;
	}

	public void lancerMenu() {
		System.out.println("Bonjour, bienvenue dans SEEK by Unimate");

		boolean sortir = false;
		while (!sortir) {
			int choix = 0;
			while (choix < 1 || choix > 4) {
				System.out.println(
						"Veuillez selectionner le mode d'utilisation : 1. Recherche 2. Mode administrateur 3. Visualiser les résultats 4. Quitter");
				choix = Clavier.entrerClavierInt();

				if (choix < 1 || choix > 4) {
					System.out.println("Veuillez saisir une entrée valide.");
				}
			}

			switch (choix) {
			case 1: {
				boundaryRecherche.lancerRechercher(); // Mode recherche
				break;
			}
			case 2: {
				boundaryModeAdmin.lancerModeAdmin(); // Mode admin
				break;
			}
			case 3: {
				boundVisualiserResultat.visualiserResultat(); // Visualiser les résultats
				break;
			}
			default: {
				sortir = true; // Quitter l'application
				break;
			}
			}
		}

	}
}
