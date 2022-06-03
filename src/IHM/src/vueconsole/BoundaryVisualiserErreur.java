package vueconsole;

import java.util.List;

import controleur.ControlVisualiserResultat;
import modele.EnsembleResultat;

public class BoundaryVisualiserErreur {
	private ControlVisualiserResultat controlVisualiserResultat;

	
	public BoundaryVisualiserErreur(ControlVisualiserResultat controlVisualiserResultat)
	{
		this.controlVisualiserResultat=controlVisualiserResultat;
	}
	public void visualiserErreur() {
		List<EnsembleResultat> listeErreurs=controlVisualiserResultat.recupererErreursResultats();
		System.out.println("Voici les dernières erreurs :" + listeErreurs);
	}
	
}
