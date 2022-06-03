package testconsole;

import controleur.ControlRecupererErreursResultats;
import controleur.ControlRecupererNouveauResultat;
import controleur.ControlRecupererResultats;
import controleur.ControlVisualiserResultat;
import modele.BDResultat;
import modele.EnsembleResultatAudio;
import modele.EnsembleResultatImage;
import modele.Moteur;
import modele.MoteurSeekBitUnimate;
import modele.ResultatAudio;
import vueconsole.BoundaryVisualiserResultat;

public class TestCasVisualiserResultat {

	public static void main(String[] args) {
		ControlRecupererResultats controlRecupererResultats = new ControlRecupererResultats();
		ControlRecupererNouveauResultat controlRecupererNouveauResultat = new ControlRecupererNouveauResultat();
		ControlRecupererErreursResultats controlRecupererErreursResultats = new ControlRecupererErreursResultats();

		ControlVisualiserResultat controlVisualiserResultat = new ControlVisualiserResultat(controlRecupererResultats,
				controlRecupererNouveauResultat, controlRecupererErreursResultats);

		BoundaryVisualiserResultat boundaryVisualiserResultat = new BoundaryVisualiserResultat(
				controlVisualiserResultat);

		Moteur moteur = new MoteurSeekBitUnimate();

		EnsembleResultatAudio ensembleResultat = new EnsembleResultatAudio(new Moteur[] { moteur },
				"fichier/original.wav");
		ensembleResultat.add(new ResultatAudio("chemin/fichier/1.wav", 90, moteur));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/2.wav", 80, moteur));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/3.wav", 75, moteur));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/4.wav", Math.PI * 20, moteur));
		BDResultat.getInstance().enregistrerResultats(ensembleResultat);

		EnsembleResultatImage ensembleErreursResultat = new EnsembleResultatImage(new Moteur[] { moteur }, "Vert",
				"Bleu");
		BDResultat.getInstance().enregistrerResultats(ensembleErreursResultat);

		boundaryVisualiserResultat.visualiserResultat();
	}

}
