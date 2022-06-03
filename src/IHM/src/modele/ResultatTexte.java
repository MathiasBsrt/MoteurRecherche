package modele;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class ResultatTexte extends Resultat {

	private HashMap<String, Integer> occurences = new HashMap<>();

	public ResultatTexte(String cheminFichier, double tauxRessemblance, Moteur moteur) {
		super(cheminFichier, tauxRessemblance, TypeDocument.TEXTE, moteur);
	}

	@Override
	void setCriteres(String... criteres) {
		super.setCriteres(criteres);
		for (String critere : criteres) {
			if (!occurences.containsKey(critere))
				occurences.put(critere, 0);
		}
	}

	public int getNbOccurence(String mot) {
		if (occurences == null)
			return 0;
		return occurences.getOrDefault(mot, 0);
	}

	public void setNbOccurence(String mot, int nbOccruence) {
		occurences.put(mot, nbOccruence);
	}

	public String[] toArray() {
		String[] array = new String[occurences.size()];
		Set<String> keys = occurences.keySet();
		int index = 0;
		for (String key : keys) {
			array[index] = String.format("%s y est %d fois", key, occurences.get(key));
			index++;
		}
		return array;
	}

	@Override
	public String toString() {
		return String.format("ResultatImage [cheminFichier = %s, tauxRessemblance = %f, criteres = %s]", cheminFichier,
				tauxRessemblance, Arrays.toString(criteres));
	}

}
