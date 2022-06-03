package vuegraphique.visualiserresultat;

import javax.swing.JList;

import modele.EnsembleResultat;

public class JListCriteresTexte extends JList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7745719876651281659L;

	private String[] criteres;

	public JListCriteresTexte(EnsembleResultat ensembleResultat) {
		super(ensembleResultat.getCriteres());
		this.criteres = ensembleResultat.getCriteres();
	}

	public String[] getCriteres() {
		return criteres;
	}

}
