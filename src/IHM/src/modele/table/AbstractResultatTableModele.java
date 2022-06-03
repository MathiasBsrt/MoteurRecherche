package modele.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modele.EnsembleResultat;
import modele.Moteur;
import modele.Resultat;

public abstract class AbstractResultatTableModele<T extends Resultat> extends AbstractTableModel {

	protected List<Moteur> moteursAMontrer = new ArrayList<>();

	private EnsembleResultat ensembleResultat;

	private List<T> resultatsAAfficher;

	public AbstractResultatTableModele(EnsembleResultat<T> ensembleResultat) {
		this.ensembleResultat = ensembleResultat;
		Moteur[] moteurs = ensembleResultat.getMoteurs();
		for (Moteur moteur : moteurs)
			moteursAMontrer.add(moteur);
	}

	public void montrerMoteur(Moteur moteur) {
		if (!moteursAMontrer.contains(moteur))
			moteursAMontrer.add(moteur);
		actualiserResultatsAAfficher();
	}

	public void montrerMoteurs(List<Moteur> moteurs) {
		for (Moteur moteur : moteurs) {
			if (!moteursAMontrer.contains(moteur))
				moteursAMontrer.add(moteur);
		}
		actualiserResultatsAAfficher();
	}

	public void retirerMoteur(Moteur moteur) {
		moteursAMontrer.remove(moteur);
		actualiserResultatsAAfficher();
	}

	public void retirerToutLesMoteurs() {
		moteursAMontrer.clear();
		resultatsAAfficher.clear();
	}

	public List<T> getResultats() {
		return resultatsAAfficher;
	}

	public T getResultat(int rowIndex) {
		return resultatsAAfficher.get(rowIndex);
	}

	private void actualiserResultatsAAfficher() {
		resultatsAAfficher = ensembleResultat.getResultatsDesMoteur(moteursAMontrer);
	}

	@Override
	public int getRowCount() {
		actualiserResultatsAAfficher();
		return resultatsAAfficher.size();
	}

}
