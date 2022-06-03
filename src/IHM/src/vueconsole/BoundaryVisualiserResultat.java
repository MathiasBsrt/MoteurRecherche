package vueconsole;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import controleur.ControlVisualiserResultat;
import modele.EnsembleResultat;
import modele.Moteur;
import modele.Resultat;

public class BoundaryVisualiserResultat {

	private ControlVisualiserResultat controlVisualiserResultat;

	public BoundaryVisualiserResultat(ControlVisualiserResultat controlVisualiserResultat) {
		this.controlVisualiserResultat = controlVisualiserResultat;
	}

	public void visualiserResultat() {
		System.out.println("Voulez-vous:");
		System.out.println("\t 1 - Visualiser les resultats");
		System.out.println("\t 2 - Visualiser le nouveau resultat");
		System.out.println("\t 3 - Visualiser les erreurs");
		System.out.println("Choissisez une option par son num�ro (1 - 3): ");

		int choix = 0;
		do {
			choix = Clavier.entrerClavierInt();
			if (choix < 1 || choix > 3)
				System.out.print("Veuillez selectionner une option valide: ");
		} while (choix < 1 || choix > 3);

		switch (choix) {
		case 1: {
			afficherResultats();
			break;
		}
		case 2: {
			afficherNouveauResultats();
			break;
		}
		case 3: {
			afficherErreursResultats();
			break;
		}
		default: {
			System.out.println("Il semblerai que l'option choisis ne soit pas correcte.");
			break;
		}
		}
	}

	private void afficherResultats() {
		List<EnsembleResultat> resultats = controlVisualiserResultat.recupererResultats();
		if (resultats.size() == 0) {
			System.out.println("Il n'y a aucun resultat dans l'historique de disponnible.");
			return;
		}

		System.out.println(String.format("Affichage de %d résultat(s).", resultats.size()));
		Iterator<EnsembleResultat> iterator = resultats.iterator();
		int resultatId = 1;
		while (iterator.hasNext()) {
			EnsembleResultat ensembleResultat = iterator.next();
			Moteur[] moteurs = ensembleResultat.getMoteurs();
			Moteur moteur = moteurs[0];
			System.out.println(
					String.format("Résultat n°%d de type %s:", resultatId, ensembleResultat.getTypeDocument()));
			System.out.println(String.format("-- Moteur utilisé pour cette recherche:\n\tNom: %s\n\tGroupe: %d",
					moteur.getNom(), moteur.getGroupeDev()));

			Iterator<Resultat> resultatIterator = ensembleResultat.iterator();
			while (resultatIterator.hasNext()) {
				System.out.println("\t" + resultatIterator.next().toString());
			}

			resultatId++;
		}
	}

	private void afficherNouveauResultats() {
		Optional<EnsembleResultat> optResultat = controlVisualiserResultat.recupererNouveauResultat();
		if (!optResultat.isPresent()) {
			System.out.println("Il n'y a aucun nouveau résultat de disponnible.");
			return;
		}

		EnsembleResultat ensembleResultat = optResultat.get();
		System.out.println(String.format("Voici le nouveau résultat de type %s:", ensembleResultat.getTypeDocument()));
		Moteur[] moteurs = ensembleResultat.getMoteurs();
		Moteur moteur = moteurs[0];
		System.out.println(String.format("-- Moteur utilisé pour cette recherche:\n\tNom: %s\n\troupe: %d",
				moteur.getNom(), moteur.getGroupeDev()));

		Iterator<Resultat> resultatIterator = ensembleResultat.iterator();
		while (resultatIterator.hasNext()) {
			System.out.println("\t" + resultatIterator.next().toString());
		}
	}

	private void afficherErreursResultats() {
		List<EnsembleResultat> resultats = controlVisualiserResultat.recupererErreursResultats();
		if (resultats.size() == 0) {
			System.out.println("Il n'y a aucune erreurs (résultats) dans l'historique de disponnible.");
			return;
		}

		System.out.println(String.format("Affichage de %d erreurs résultat(s).", resultats.size()));
		Iterator<EnsembleResultat> iterator = resultats.iterator();
		int resultatId = 1;
		while (iterator.hasNext()) {
			System.out.println(String.format("Resultat erreur n°%d:", resultatId));
			EnsembleResultat ensembleResultat = iterator.next();
			Moteur[] moteurs = ensembleResultat.getMoteurs();
			Moteur moteur = moteurs[0];

			System.out.println(String.format("\t Ce résultat d'une recherche %s avait pour critères: %s",
					ensembleResultat.getTypeDocument(), Arrays.toString(ensembleResultat.getCriteres())));
			System.out.println(String.format("-- Moteur utilisé pour cette recherche:\n\tNom: %s\n\tGroupe: %d",
					moteur.getNom(), moteur.getGroupeDev()));

			resultatId++;
		}
	}

}