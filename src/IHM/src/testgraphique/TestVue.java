package testgraphique;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controleur.ControlAdmin;
import controleur.ControlIndexation;
import controleur.ControlParamSeuil;
import controleur.ControlRecherche;
import controleur.ControlRecupererErreursResultats;
import controleur.ControlRecupererNouveauResultat;
import controleur.ControlRecupererResultats;
import controleur.ControlVisualiserResultat;
import modele.BDMoteur;
import modele.MoteurQuiMarcheMal;
import modele.MoteurSeekBitUnimate;
import modele.os.OSManager;
import vuegraphique.FrameApp;

public class TestVue {

	public static void main(String[] args) {
		try {
			if (OSManager.isOSLinux()) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} else if (OSManager.isOSWindows()) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else if (OSManager.isMacOS()) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		ControlAdmin controlAdmin = new ControlAdmin();
		ControlRecherche controlRecherche = new ControlRecherche();
		ControlParamSeuil controlParamSeuil = new ControlParamSeuil();
		ControlIndexation controlIndexation = new ControlIndexation();

		ControlRecupererResultats controlRecupererResultats = new ControlRecupererResultats();
		ControlRecupererNouveauResultat controlRecupererNouveauResultat = new ControlRecupererNouveauResultat();
		ControlRecupererErreursResultats controlRecupererErreursResultats = new ControlRecupererErreursResultats();
		ControlVisualiserResultat controlVisualiserResultat = new ControlVisualiserResultat(controlRecupererResultats,
				controlRecupererNouveauResultat, controlRecupererErreursResultats);
		BDMoteur.getInstance().ajouterMoteur(new MoteurSeekBitUnimate());
		BDMoteur.getInstance().ajouterMoteur(new MoteurQuiMarcheMal());

		new FrameApp(controlAdmin, controlRecherche, controlParamSeuil, controlVisualiserResultat, controlIndexation);
	}
}
