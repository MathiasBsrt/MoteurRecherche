package modele;

import java.util.ArrayList;
import java.util.List;

public class BDMoteur {

	List<Moteur> moteur = new ArrayList<Moteur>();

	private static class BDMoteurHolder {

		public static BDMoteur INSTANCE = new BDMoteur();

	}

	public static BDMoteur getInstance() {

		return BDMoteurHolder.INSTANCE;

	}

	public List<Moteur> getListeMoteur() {

		return (moteur);

	}

	public void ajouterMoteur(Moteur nouveaumoteur) {

		moteur.add(nouveaumoteur);

	}

}
