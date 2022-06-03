package vueconsole;

import java.util.ArrayList;
import java.util.List;

import controleur.ControlDescripteur;
import controleur.ControlRecherche;
import modele.EnsembleResultat;
import modele.ModeRecherche;
import modele.Moteur;
import modele.ParametreRecherche;
import modele.TypeDocument;

public class BoundaryRecherche {

	private ControlRecherche controlRecherche;
	private ControlDescripteur controlDescripteur;

	public BoundaryRecherche(ControlRecherche controlRecherche, ControlDescripteur controlDescripteur) {
		this.controlRecherche = controlRecherche;
		this.controlDescripteur = controlDescripteur;
	}

	public void lancerRechercher() {
		// Parametrage recherche
		ParametreRecherche param = parametrerRecherche();

		// Choix des recherches

		// Demande des paramètres à l'utilisateur
		TypeDocument typeDocument = demanderTypeDocument();

		EnsembleResultat ensembleResultat = null;

		// 1. Recherche par document
		// 2. Recherche par crit�res
		int typeRecherche = demanderTypeRecherche();

		if (typeRecherche == 1) { // Recherche par document
			System.out.println("Veuillez saisir le chemin du fichier à rechercher :");
			String chemin = Clavier.entrerClavierString();
			System.out.println("Démarrage de la recherche par document ...");
			controlRecherche.rechercherParDocument(chemin, typeDocument, param);
		} else if (typeRecherche == 2) { // Recherche par critère
			String[] criteresTab = demanderCriteres();
			System.out.println("Démarrage de la recherche par critères ...");
			controlRecherche.rechercheParCritere(typeDocument, criteresTab, param);
		}
		System.out.println("--- Recherche terminée, enregistrement des résultats OK ---");
	}

	private ParametreRecherche parametrerRecherche() {
		List<Moteur> moteursDisponnible = controlRecherche.getMoteurs();
		List<Moteur> moteursAUtilises = new ArrayList<>();
		boolean finish = false;
		int nbMoteur = 0;

		// Paramètrage multi moteur
		while (finish == false && moteursAUtilises.size() < moteursDisponnible.size()) {

			// Selection du moteur
			Moteur moteur = demanderMoteur(moteursDisponnible, moteursAUtilises);
			moteursAUtilises.add(moteur);

			if (moteursAUtilises.size() < moteursDisponnible.size())
				finish = !demanderContinuerSelectionMoteur();
			else
				System.out.println("Il n'y a plus de moteurs disponnibles.");
		}

		// Paramètrage mode de recherche 1 : ouvert, 2 : fermé
		ModeRecherche modeRecherche = demanderModeRecherche();

		// Mode ouvert -> Vérificaiton des indexations + de nouveaux descripteurs
		// Moder fermé -> On fait directement la recherche
		if (modeRecherche.equals(ModeRecherche.OUVERT)) {
			System.out.println("-- Mode ouvert: Vérification de la présence de nouveau descripteur ... --");
			controlDescripteur.verifierNouveauDescripteur();
			System.out.println("-- Mode ouvert: Vérification de la présence de nouveau descripteur OK --");
		} // Sinon mode fermé on continue pour faire directement la recherche

		return new ParametreRecherche(modeRecherche, moteursAUtilises);
	}

	private String[] demanderCriteres() {
		boolean finish = false;
		List<String> criteres = new ArrayList<>();
		while (finish == false) {
			System.out.print("Veuillez saisir un critère : ");
			String critere = Clavier.entrerClavierString();
			criteres.add(critere);

			int choix = 0;
			while (choix != 1 && choix != 2) {
				System.out.println("Voulez vous saisir un critère supplémentaire ? 1. Oui 2. Non");
				choix = Clavier.entrerClavierInt();

				if (choix != 1 && choix != 2) {
					System.out.println("Veuillez saisir une entrée valide.");
				}
			}

			if (choix == 2) {
				finish = true;
			}

		}
		String[] criteresTab = new String[criteres.size()];
		return criteres.toArray(criteresTab);
	}

	private int demanderTypeRecherche() {
		int typeRecherche = 0;
		while (typeRecherche != 1 && typeRecherche != 2) {
			System.out.println("Quel type de recherche voulez vous effectuer? 1. Par document, 2. Critère(s)");
			typeRecherche = Clavier.entrerClavierInt();

			if (typeRecherche != 1 && typeRecherche != 2) {
				System.out.println("Veuillez saisir une entrée valide.");
			}
		}
		return typeRecherche;
	}

	private TypeDocument demanderTypeDocument() {
		int typeDocID = 0;
		while (typeDocID != 1 && typeDocID != 2 && typeDocID != 3) {
			System.out.println("Quel type de document voulez vous rechercher ? 1. Son, 2. Image, 3. Texte ");
			typeDocID = Clavier.entrerClavierInt();

			if (typeDocID != 1 && typeDocID != 2 && typeDocID != 3) {
				System.out.println("Veuillez saisir une entrée valide.");
			}
		}
		return TypeDocument.values()[typeDocID - 1];
	}

	/**
	 * 
	 * @return True si il veut continuer � selectionner
	 */
	private boolean demanderContinuerSelectionMoteur() {
		int choix = 0;
		while (choix != 1 && choix != 2) {
			System.out.println("Voulez vous sélectionner un moteur supplémentaire ? 1. Oui 2. Non");
			choix = Clavier.entrerClavierInt();

			if (choix != 1 && choix != 2) {
				System.out.println("Veuillez saisir une entrée valide.");
			}
		}

		return choix == 1;
	}

	private Moteur demanderMoteur(List<Moteur> moteursDisponnible, List<Moteur> moteursDejaSelectionnes) {
		int choix = 0;
		List<Moteur> moteursASelectionner = new ArrayList<>();
		for (Moteur moteur : moteursDisponnible) {
			if (!moteursDejaSelectionnes.contains(moteur))
				moteursASelectionner.add(moteur);
		}

		while (choix < 1 || choix > moteursASelectionner.size()) {

			// Selection du moteur
			System.out.println("Sélectionner le moteur à utiliser en saisissant le chiffre: ");
			for (int i = 0; i < moteursASelectionner.size(); i++) {
				System.out.println((i + 1) + ". " + moteursASelectionner.get(i).toString()); // Affiche si le moteur et
																								// s'il est
				// selectionne
			}
			choix = Clavier.entrerClavierInt();
			if (choix < 1 || choix > moteursASelectionner.size()) {
				System.out.println("Veuillez saisir une entrée valide.");
			}
		}

		return moteursASelectionner.get(choix - 1);
	}

	private ModeRecherche demanderModeRecherche() {
		int modeRechercheID = 0;

		while (modeRechercheID != 1 && modeRechercheID != 2) {
			System.out.println("Souhaitez rechercher en mode ouvert ou fermé ? 1. Ouvert, 2. Fermé");
			modeRechercheID = Clavier.entrerClavierInt();

			if (modeRechercheID != 1 && modeRechercheID != 2) {
				System.out.println("Veuillez saisir une entrée valide.");
			}
		}

		return ModeRecherche.values()[modeRechercheID - 1];
	}
}
