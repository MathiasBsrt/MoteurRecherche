package controleur;

import java.util.List;

import modele.BDMoteur;
import modele.Moteur;

public class ControlParametreRecherche {

	private ControlDescripteur controlDescripteur;
	private BDMoteur bdMoteur = BDMoteur.getInstance();

	public ControlParametreRecherche(ControlDescripteur controlDescripteur) {

		this.controlDescripteur = controlDescripteur;

	}

	public List<Moteur> getListeMoteur() {

		List<Moteur> Moteur = bdMoteur.getListeMoteur();
		return (Moteur);

	}

	public void ajouterMoteur(Moteur nouveaumoteur) {

		bdMoteur.ajouterMoteur(nouveaumoteur);

	}

	public void verifierNouveauDescripteurs() {

		controlDescripteur.verifierNouveauDescripteur();

	}

}
