package modele;

import java.util.Arrays;

public class ResultatImage extends Resultat {

	public ResultatImage(String cheminFichier, double tauxRessemblance, Moteur moteur) {
		super(cheminFichier, tauxRessemblance, TypeDocument.IMAGE, moteur);
	}

	@Override
	public String toString() {
		return String.format("ResultatImage [cheminFichier = %s, tauxRessemblance = %f, criteres = %s]", cheminFichier,
				tauxRessemblance, Arrays.toString(criteres));
	}

}
