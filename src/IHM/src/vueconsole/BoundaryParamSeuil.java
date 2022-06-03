package vueconsole;

import controleur.ControlParamSeuil;
import modele.TypeDocument;

public class BoundaryParamSeuil {

	private ControlParamSeuil controlParamSeuil;

	public BoundaryParamSeuil(ControlParamSeuil controlParamSeuil) {
		this.controlParamSeuil = controlParamSeuil;
	}

	public void lancerParamSeuil() {
		int choix = 0;
		while (choix < 1 || choix > 4) {
			System.out.println(
					"Pour quel type de document voulez vous paramètrer les seuils : \n1. Documents audio 2. Documents texte 3. Documents image 4. Retour");
			choix = Clavier.entrerClavierInt();

			if (choix < 1 || choix > 4) {
				System.out.println("Veuillez saisir une entrée valide.");
			}
		}

		TypeDocument typeDoc = null;
		switch (choix) {
		case 1:
			typeDoc = TypeDocument.SON;
			break;
		case 2:
			typeDoc = TypeDocument.TEXTE;
			break;
		case 3:
			typeDoc = TypeDocument.IMAGE;
			break;

		default:
			break;
		}

		int seuil = -1;
		while (seuil < 0 || seuil > 100) {
			System.out.println("Veuillez saisir le nouveau seuil  (entier entre 0 et 100): ");
			seuil = Clavier.entrerClavierInt();

			if (seuil < 0 || seuil > 100) {
				System.out.println("Veuillez saisir une entrée valide.");
			}
		}

		if (typeDoc == TypeDocument.TEXTE) {
			int nbApparition = -1;
			while (nbApparition < 0 || nbApparition > 100) {
				System.out.println("Veuillez saisir le nouveau nombre d'apparition (entier entre 0 et 100): ");
				nbApparition = Clavier.entrerClavierInt();

				if (nbApparition < 0 || nbApparition > 100) {
					System.out.println("Veuillez saisir une entrée valide.");
				}
			}
			controlParamSeuil.modificationNbApparition(typeDoc, nbApparition);
			System.out.println("Nombre d'apparition modifié à " + nbApparition);

		}

		controlParamSeuil.modificationSeuil(typeDoc, seuil);
		System.out.println("Seuil modifié à " + seuil + "%");

		System.out.println("retour au menu d'administation...");
	}
}
