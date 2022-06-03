package modele;

import java.util.Arrays;

public class ResultatAudio extends Resultat {

	private double tempsTrouve;

	public ResultatAudio(String cheminFichier, double tauxRessemblance, Moteur moteur) {
		super(cheminFichier, tauxRessemblance, TypeDocument.SON, moteur);
		this.tempsTrouve = 0.0D;
	}

	public ResultatAudio(String cheminFichier, double tauxRessemblance, double tempsTrouve, Moteur moteur) {
		super(cheminFichier, tauxRessemblance, TypeDocument.SON, moteur);
		this.tempsTrouve = tempsTrouve;
	}

	public double getTempsTrouve() {
		return tempsTrouve;
	}

	@Override
	public String toString() {
		return String.format("ResultatAudio [cheminFichier = %s, tauxRessemblance = %f, criteres = %s]", cheminFichier,
				tauxRessemblance, Arrays.toString(criteres));
	}

}
