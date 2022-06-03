package vueconsole;

import java.util.List;

import controleur.ControlIndexation;
import modele.TypeDocument;

public class BoundaryIndexer {
	private ControlIndexation controlIndexation;

	public BoundaryIndexer(ControlIndexation controlIndexation) {
		this.controlIndexation = controlIndexation;
	}

	public void lancerIndexation() {
		System.out.println("Voulez vous indexer un fichier ou un dossier ?");
		System.out.println("1. Un Fichier \n2. Un Dossier");
		int docOuFich = Clavier.entrerClavierInt();
		System.out.println("Quel type de document voulez-vous indexer ?");
		System.out.println("1. Son\n2. Image\n3. Texte");
		int type = Clavier.entrerClavierInt();
		TypeDocument typeDocument;
		switch (type) {
		case 1:
			typeDocument = TypeDocument.SON;
			break;
		case 2:
			typeDocument = TypeDocument.IMAGE;
			break;
		default:
			typeDocument = TypeDocument.TEXTE;
			break;
		}
		String chemin = donnerChemin(docOuFich);
		switch (docOuFich) {
		case 1:
			System.out.println("Indexation en cours ...");
			String indexationFich = controlIndexation.lancerIndexationFichier(typeDocument, chemin);
			System.out.println(indexationFich);
			break;
		case 2:
			System.out.println("Indexation en cours ...");
			List<String> indexationDoc = controlIndexation.lancerIndexationDossier(typeDocument, chemin);
			System.out.println("Indexation terminée pour les fichiers: ");
			for (String elem : indexationDoc) {
				System.out.println("\t" + elem);
			}
			break;
		}
		System.out.println("--- Indexation terminée ---");
	}

	public String donnerChemin(int docOuFich) {
		String chemin = null;
		switch (docOuFich) {
		case 1:
			System.out.print("Entrez le chemin vers le fichier : ");
			chemin = Clavier.entrerClavierString();
			while (!controlIndexation.verifierCheminFichier(chemin)) {
				System.out.print("Chemin non valide\nEntrez le chemin vers le fichier : ");
				chemin = Clavier.entrerClavierString();
				controlIndexation.verifierCheminFichier(chemin);
			}
			break;
		case 2:
			System.out.print("Entrez le chemin vers le dossier : ");
			chemin = Clavier.entrerClavierString();
			while (!controlIndexation.verifierCheminDossier(chemin)) {
				System.out.print("Chemin non valide\nEntrez le chemin vers le dossier : ");
				chemin = Clavier.entrerClavierString();
				controlIndexation.verifierCheminFichier(chemin);
			}
			break;
		}
		return chemin;
	}
}
