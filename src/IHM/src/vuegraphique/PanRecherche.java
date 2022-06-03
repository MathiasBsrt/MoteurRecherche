package vuegraphique;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import controleur.ControlRecherche;
import modele.BDResultat;
import modele.EnsembleResultat;
import modele.EnsembleResultatAudio;
import modele.EnsembleResultatImage;
import modele.EnsembleResultatTexte;
import modele.ModeRecherche;
import modele.Moteur;
import modele.ResultatAudio;
import modele.ResultatImage;
import modele.ResultatTexte;
import modele.TypeDocument;
import vuegraphique.recherche.PanRechercheCriteres;
import vuegraphique.recherche.TypeRecherche;
import vuegraphique.visualiserresultat.FrameVisualiserResultat;

public class PanRecherche extends JPanel {
	// controleurs du cas + panel des cas inclus ou etendus en lien avec un acteur
	private ControlRecherche controlRecherche;
	private PanRechercheCriteres panRechercheCriteres;
	// les attributs metiers (ex : numClient)

	// Les elements graphiques :
	// Declaration et creation des polices d'ecritures
	private Font policeTitre = new Font("Calibri", Font.BOLD, 24);
	private Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);

	// Declaration et creation des ComboBox
	// Declaration et creation des Button
	// Declaration et creation des TextArea
	// Declaration et creation des Labels

	private JList<Moteur> listMoteurs;
	private JComboBox<ModeRecherche> cbModeRecherche;
	private JProgressBar progressBar = new JProgressBar();

	// Mise en page : les Box
	private Box boxMiseEnpage = Box.createVerticalBox();
	private Box boxCentre = Box.createHorizontalBox();
	private Box boxTitre = Box.createHorizontalBox();
	private Box boxGauche = Box.createVerticalBox();
	private Box boxParam = Box.createVerticalBox();
	private Box boxDroite = Box.createVerticalBox();

	private FrameApp frame;

	public PanRecherche(
			// parametres pour l'initialisation des attributs metiers
			// parametres correspondants au controleur du cas + panels
			ControlRecherche controlRecherche
	// des cas inclus ou etendus en lien avec un acteur
	) {
		// initialisation des attributs metiers
		// initilaisation du controleur du cas + panels
		this.controlRecherche = controlRecherche;
		// des cas inclus ou etendus en lien avec un acteur
	}

	// Methode d'initialisation du panel
	public void initialisation(FrameApp frame) {
		// mise en forme du panel (couleur, ...)
		// creation des differents elements graphiques (JLabel, Combobox, Button,
		// TextAera ...)
		this.frame = frame;
		JLabel titre = new JLabel("Recherche");
		titre.setFont(policeTitre);
		titre.setHorizontalAlignment(JLabel.CENTER);
		boxTitre.add(Box.createHorizontalGlue());
		boxTitre.add(titre, BorderLayout.PAGE_START);
		boxTitre.add(Box.createHorizontalGlue());
		boxTitre.add(Box.createRigidArea(new Dimension(0, 10)));

		// Partie gauche de l'interface
		panRechercheCriteres = new PanRechercheCriteres();
		boxGauche.add(panRechercheCriteres);

		JLabel lblMoteurs = new JLabel("Liste des moteurs:");
		JLabel lblDescMoteurs = new JLabel("(Sélection multiple autorisée)");
		lblDescMoteurs.setFont(policeParagraphe);
		List<Moteur> moteurs = controlRecherche.getMoteurs();
		Moteur[] arrMoteurs = new Moteur[moteurs.size()];
		moteurs.toArray(arrMoteurs);
		listMoteurs = new JList<Moteur>(arrMoteurs);
		listMoteurs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listMoteurs.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listMoteurs.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(listMoteurs);
		listScroller.setPreferredSize(new Dimension(250, 80));
		boxGauche.add(justifierGauche(lblMoteurs));
		boxGauche.add(justifierGauche(lblDescMoteurs));
		boxGauche.add(listScroller);

		// Partie droite de l'interface
		JLabel lblModeRecherche = new JLabel("Mode de la recherche:");
		cbModeRecherche = new JComboBox<>(new ModeRecherche[] { ModeRecherche.OUVERT, ModeRecherche.FERME });
		cbModeRecherche.setPreferredSize(new Dimension(190, 20));
		cbModeRecherche.setMaximumSize(new Dimension(190, 20));
		boxDroite.add(justifierGauche(lblModeRecherche));
		boxDroite.add(cbModeRecherche);
		boxDroite.add(Box.createRigidArea(new Dimension(0, 50)));
		boxDroite.add(Box.createVerticalGlue());

		JButton btnLancerRecherche = new JButton("Lancer la recherche !");
		JLabel lblLancerRecherche = new JLabel("Et c'est parti !", SwingConstants.CENTER);
		lblLancerRecherche.setFont(policeTitre);
		boxDroite.add(lblLancerRecherche);
		boxDroite.add(btnLancerRecherche);

		btnLancerRecherche.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panRechercheCriteres.isTypeDocumentSelectionne()) {
					if (panRechercheCriteres.isTypeRechercheSelectionne()) {
						List<String> criteres = panRechercheCriteres.getCriteres();
						if (!criteres.isEmpty()) {
							System.out.println("Critères: " + criteres.toString());
							List<Moteur> moteurs = getMoteursSelectionnes();
							if (!moteurs.isEmpty()) {
								System.out.println("Moteurs: " + moteurs.toString());
								ModeRecherche modeRecherche = getModeRecherche();
								System.out.println("Mode de recherche " + modeRecherche.toString());

								// Pour l'effet de simulation, la méthode finRecherche
								// est appelée dans le Thread de afficherBarreDeProgres(...);
								afficherBarreDeProgres(btnLancerRecherche);
							} else {
								JOptionPane.showMessageDialog(null, "Vous devez au moins sélectionner un moteur.",
										"Erreur", JOptionPane.ERROR_MESSAGE);
							}

						} else {
							JOptionPane.showMessageDialog(null,
									"Vous devez remplir le formulaire pour spécifier les critères.", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Vous devez sélectionner un type de recherche.", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vous devez sélectionner un type de document à rechercher.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		boxCentre.add(boxGauche);
		boxCentre.add(Box.createRigidArea(new Dimension(100, 0)));
		boxCentre.add(boxDroite);

		boxMiseEnpage.add(Box.createVerticalStrut(50));
		boxMiseEnpage.add(boxTitre);
		boxMiseEnpage.add(Box.createVerticalStrut(50));
		boxMiseEnpage.add(boxCentre);

		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherMenuUtilisateur();
			}
		});

		boxMiseEnpage.add(Box.createVerticalStrut(50));
		boxMiseEnpage.add(btnRetour);
		add(boxMiseEnpage);

		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
	}

	// Methode correspondante au nom du cas
	public void recherche( /* parametres metiers */) {
	}

	private void afficherBarreDeProgres(JButton btnLancerRecherche) {
		boxDroite.remove(btnLancerRecherche);
		boxDroite.add(progressBar);

		boxDroite.repaint();
		boxDroite.revalidate();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cacherBarreDeProgres(btnLancerRecherche);
				finRecherche();
			}
		}).start();
	}

	private void finRecherche() {
		EnsembleResultat ensembleResultat = simulerFinRecherche();
		String[] options = { "Visualiser le résultat", "Fermer" };
		int selectedIndex = JOptionPane.showOptionDialog(null, "La recherche c'est terminer avec succès !", "Succès",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		if (selectedIndex == 0) {
			new FrameVisualiserResultat(ensembleResultat);
		}
	}

	private void cacherBarreDeProgres(JButton btnLancerRecherche) {
		boxDroite.remove(progressBar);
		boxDroite.add(btnLancerRecherche);

		boxDroite.repaint();
		boxDroite.revalidate();
	}

	// Methodes privees pour le bon deroulement du cas
	private Component justifierGauche(JLabel label) {
		Box b = Box.createHorizontalBox();
		b.add(label);
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la droite du label
		return b;
	}

	private Component justifierCenter(JLabel label) {
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la gauche et sur la droite du label
		b.add(label);
		b.add(Box.createHorizontalGlue());
		return b;
	}

	private List<Moteur> getMoteursSelectionnes() {
		int[] indices = listMoteurs.getSelectedIndices();
		List<Moteur> moteurs = new ArrayList<>();
		List<Moteur> listeMoteurs = controlRecherche.getMoteurs();

		for (int i : indices) {
			moteurs.add(listeMoteurs.get(i));
		}

		return moteurs;
	}

	private TypeDocument getTypeDocument() {
		return panRechercheCriteres.getTypeDocument();
	}

	private TypeRecherche getTypeRecherche() {
		return panRechercheCriteres.getTypeRecherche();
	}

	private ModeRecherche getModeRecherche() {
		int selectedIndex = cbModeRecherche.getSelectedIndex();
		return cbModeRecherche.getItemAt(selectedIndex);
	}

	private EnsembleResultat simulerFinRecherche() {
		List<String> criteres = panRechercheCriteres.getCriteres();

		List<Moteur> moteurs = getMoteursSelectionnes();
		Moteur[] arrMoteurs = new Moteur[moteurs.size()];
		moteurs.toArray(arrMoteurs);

		ModeRecherche modeRecherche = getModeRecherche();
		TypeDocument typeDocument = getTypeDocument();
		TypeRecherche typeRecherche = getTypeRecherche();

		EnsembleResultat ensembleResultat = null;

		switch (typeDocument) {
		case SON: {
			String cheminFichier = criteres.get(0);
			ensembleResultat = new EnsembleResultatAudio(arrMoteurs, cheminFichier);
			for (Moteur moteur : moteurs) {
				ResultatAudio res = new ResultatAudio("C:\\Users\\NadineMoranoPC\\Desktop\\corpus_fi.wav",
						Math.random() * 100, moteur);
				ensembleResultat.add(res);
			}
			break;
		}
		case IMAGE: {
			if (typeRecherche.equals(TypeRecherche.DOCUMENT)) {
				String cheminFichier = criteres.get(0);
				ensembleResultat = new EnsembleResultatImage(arrMoteurs, cheminFichier);
			} else {
				String[] arrCriteres = new String[criteres.size()];
				criteres.toArray(arrCriteres);
				ensembleResultat = new EnsembleResultatImage(arrMoteurs, arrCriteres);
			}
			for (Moteur moteur : moteurs) {
				ResultatImage res = new ResultatImage("C:\\Users\\NadineMoranoPC\\Desktop\\super_document_image.txt",
						Math.random() * 100, moteur);
				ensembleResultat.add(res);
			}
			break;
		}
		case TEXTE: {
			if (typeRecherche.equals(TypeRecherche.DOCUMENT)) {
				String cheminFichier = criteres.get(0);
				ensembleResultat = new EnsembleResultatTexte(arrMoteurs, cheminFichier);
			} else {
				String[] arrCriteres = new String[criteres.size()];
				criteres.toArray(arrCriteres);
				ensembleResultat = new EnsembleResultatTexte(arrMoteurs, arrCriteres);
			}
			for (Moteur moteur : moteurs) {
				ResultatTexte res = new ResultatTexte("C:\\Users\\NadineMoranoPC\\Desktop\\super_document_texte.txt",
						Math.random() * 100, moteur);
				ensembleResultat.add(res);
			}
			break;
		}
		default:
			throw new RuntimeException("Le TypeDocument sélectionné n'a pas encore été implémenté.");
		}
		BDResultat.getInstance().enregistrerResultats(ensembleResultat);

		return ensembleResultat;
	}

}
