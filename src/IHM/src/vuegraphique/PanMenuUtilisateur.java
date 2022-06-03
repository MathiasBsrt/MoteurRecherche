package vuegraphique;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanMenuUtilisateur extends JPanel {
	// controleurs du cas + panel des cas inclus ou etendus en lien avec un acteur
	// les attributs metiers (ex : numClient)

	// Les elements graphiques :
	// Declaration et creation des polices d'ecritures
	private Font policeTitre = new Font("Calibri", Font.BOLD, 24);
	private Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);

	// Declaration et creation des ComboBox
	// Declaration et creation des Button
	// Declaration et creation des TextArea
	// Declaration et creation des Labels

	// Mise en page : les Box
	private Box boxMiseEnpage = Box.createVerticalBox();
	private Box boxCentre = Box.createHorizontalBox();
	private Box boxTitre = Box.createHorizontalBox();

	private FrameApp frame;

	public PanMenuUtilisateur(
	// parametres pour l'initialisation des attributs metiers
	// parametres correspondants au controleur du cas + panels
	// des cas inclus ou etendus en lien avec un acteur
	) {
		// initialisation des attributs metiers
		// initilaisation du controleur du cas + panels
		// des cas inclus ou etendus en lien avec un acteur
	}

	// Methode d'initialisation du panel
	public void initialisation(FrameApp frame) {
		// mise en forme du panel (couleur, ...)
		// creation des differents elements graphiques (JLabel, Combobox, Button,
		// TextAera ...)
		this.frame = frame;

		JLabel titre = new JLabel("Menu Utilisateur");
		titre.setFont(policeTitre);
		titre.setHorizontalAlignment(JLabel.CENTER);
		boxTitre.add(Box.createHorizontalGlue());
		boxTitre.add(titre, BorderLayout.PAGE_START);
		boxTitre.add(Box.createHorizontalGlue());
		boxTitre.add(Box.createRigidArea(new Dimension(0, 200)));

		Box boxGauche = Box.createVerticalBox();
		JButton btnRecherche = new JButton("Effectuer une recherche");
		btnRecherche.setPreferredSize(new Dimension(300, 100));
		btnRecherche.setMaximumSize(new Dimension(300, 100));
		btnRecherche.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherMenuRecherche();
			}
		});

		JLabel lblRecherche = new JLabel("Permet de faire une recherche");
		boxGauche.add(btnRecherche);
		boxGauche.add(lblRecherche);

		Box boxDroite = Box.createVerticalBox();
		JButton btnVisualiserResultat = new JButton("Visualiser les résultats");
		btnVisualiserResultat.setPreferredSize(new Dimension(300, 100));
		btnVisualiserResultat.setMaximumSize(new Dimension(300, 100));
		btnVisualiserResultat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherMenuVisualiserResultat();
			}
		});

		JLabel lblVisualiserResultat = new JLabel("Permet de visualiser les derniers résultats");
		boxDroite.add(btnVisualiserResultat);
		boxDroite.add(lblVisualiserResultat);

		boxCentre.add(boxGauche);
		boxCentre.add(Box.createRigidArea(new Dimension(100, 0)));
		boxCentre.add(boxDroite);

		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherMenuPrincipal();
			}
		});

		boxMiseEnpage.add(Box.createVerticalStrut(50));
		boxMiseEnpage.add(boxTitre);
		boxMiseEnpage.add(boxCentre);
		boxMiseEnpage.add(Box.createVerticalStrut(50));
		boxMiseEnpage.add(justifierCenter(btnRetour));
		add(boxMiseEnpage);

	}

	// Methode correspondante au nom du cas
	public void menuUtilisateur( /* parametres metiers */) {
//		frame.pack();
	}

	// Methodes privees pour le bon deroulement du cas
	private Component justifierCenter(Component component) {
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la gauche et sur la droite du label
		b.add(component);
		b.add(Box.createHorizontalGlue());
		return b;
	}
}
