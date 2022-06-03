package modele;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import vuegraphique.recherche.TypeRecherche;

/**
 * Repr�sente la sortie d'une recherche. Donc g�n�ralement, une sortie de
 * recherche donne un ensemble de r�sultat, et la BDResultat contient une liste
 * d'ensemble de r�sultat.
 * 
 * @author Th�o TRAFNY
 *
 */
public abstract class EnsembleResultat<T extends Resultat> {

	private List<T> resultats = new ArrayList<>();

	private String nom = "";

	private LocalDateTime creationDate = LocalDateTime.now();
	private TypeDocument typeDocument;
	private TypeRecherche typeRecherche;
	private Moteur[] moteursUtilises;
	private String[] criteres;

	public EnsembleResultat(TypeDocument typeDocument, Moteur[] moteursUtilises, String... criteres) {
		this.typeDocument = typeDocument;
		this.typeRecherche = TypeRecherche.CRITERES;
		this.moteursUtilises = moteursUtilises;
		this.criteres = criteres;
	}

	public EnsembleResultat(TypeDocument typeDocument, TypeRecherche typeRecherche, Moteur[] moteursUtilises,
			String... criteres) {
		this.typeDocument = typeDocument;
		this.typeRecherche = typeRecherche;
		this.moteursUtilises = moteursUtilises;
		this.criteres = criteres;
	}

	public T getResultat(int index) {
		return resultats.get(index);
	}

	public boolean aUtiliserLeMoteur(Moteur moteur) {
		for (Moteur m : moteursUtilises) {
			if (m.equals(moteur))
				return true;
		}
		return false;
	}

	public List<T> getResultatsDuMoteur(Moteur moteur) {
		return resultats.stream().filter(r -> r.getMoteur().equals(moteur)).collect(Collectors.toList());
	}

	public List<T> getResultatsDesMoteur(List<Moteur> moteurs) {
		return resultats.stream().filter(r -> moteurs.contains(r.getMoteur())).collect(Collectors.toList());
	}

	public int getNbResultatsDuMoteur(Moteur moteur) {
		return (int) resultats.stream().filter(r -> r.getMoteur().equals(moteur)).count();
	}

	public String[] getCriteres() {
		return criteres;
	}

	public Moteur[] getMoteurs() {
		return moteursUtilises;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void add(T e) {
		e.setCriteres(criteres);
		resultats.add(e);
	}

	public void remove(T e) {
		resultats.remove(e);
	}

	public void remove(int index) {
		resultats.remove(index);
	}

	public Iterator<T> iterator() {
		return resultats.iterator();
	}

	public int getNbResultats() {
		return resultats.size();
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public TypeRecherche getTypeRecherche() {
		return typeRecherche;
	}

	public LocalDateTime getDate() {
		return creationDate;
	}

	public static String getCheminFichierRechercheDocument(EnsembleResultat ensembleResultat) {
		if (ensembleResultat.getTypeRecherche().equals(TypeRecherche.DOCUMENT)) {
			String[] criteres = ensembleResultat.getCriteres();
			String critereImportant = criteres[0];
			Pattern p = Pattern.compile("\'.*\'");
			Matcher m = p.matcher(critereImportant);
			m.find();
			String founded = m.group(0);
			String filePath = founded.replace('\'', ' ');
			return filePath.trim();
		} else
			throw new IllegalArgumentException(
					String.format("Le TypeRecherche doit être %s.", TypeRecherche.DOCUMENT.toString()));
	}

}
