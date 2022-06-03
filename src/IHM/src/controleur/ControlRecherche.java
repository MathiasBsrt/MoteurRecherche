package controleur;

import java.util.Iterator;
import java.util.List;

import modele.BDMoteur;
import modele.BDResultat;
import modele.EnsembleResultat;
import modele.Moteur;
import modele.ParametreRecherche;
import modele.TypeDocument;

public class ControlRecherche {

	private BDMoteur bdMoteur = BDMoteur.getInstance();
	private BDResultat bdResultat = BDResultat.getInstance();

	public void rechercherParDocument(String cheminFichier, TypeDocument typeDocument, ParametreRecherche param) {
		List<Moteur> moteurs = param.getMoteurs();
		Iterator<Moteur> iterator = moteurs.iterator();
		while (iterator.hasNext()) {
			Moteur moteur = iterator.next();
			EnsembleResultat resultats = moteur.rechercheParDocument(cheminFichier, typeDocument, param);
			bdResultat.enregistrerResultats(resultats);
		}
	}

	public void rechercheParCritere(TypeDocument typeDocument, String[] criteres, ParametreRecherche param) {
		List<Moteur> moteurs = param.getMoteurs();
		Iterator<Moteur> iterator = moteurs.iterator();
		while (iterator.hasNext()) {
			Moteur moteur = iterator.next();
			EnsembleResultat resultats = moteur.rechercheParCritere(typeDocument, criteres, param);
			bdResultat.enregistrerResultats(resultats);
		}
	}

	public List<Moteur> getMoteurs() {
		return bdMoteur.getListeMoteur();
	}

}
