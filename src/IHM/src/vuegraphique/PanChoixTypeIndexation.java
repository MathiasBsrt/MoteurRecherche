package vuegraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.ControlIndexation;
import modele.TypeDocument;

public class PanChoixTypeIndexation extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// controleurs du cas + panel des cas inclus ou etendus en lien avec un acteur
	private ControlIndexation controlIndexation;
	private IUseChoixTypeIndexation panAppelant;
	// les attributs metiers (ex : numClient)
	private TypeDocument typeDocument;
	private int docOuFich;
	private String chemin = null;

	// Les elements graphiques :
	// Declaration et creation des polices d'ecritures
	private Font policeTitre = new Font("Calibri", Font.BOLD, 24);
	private Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);

	// Declaration et creation des ComboBox
	// Declaration et creation des Button
	JButton selectionnerChemin = new JButton();
	JButton validerIndexation = new JButton();
	Box boxMiseEnPageChoixIndexation = Box.createVerticalBox();
	Box boxSelectionChemin = Box.createHorizontalBox();
	Box boxValiderChoix = Box.createHorizontalBox();
	// Declaration et creation des TextArea
	// Declaration et creation des Labels
	JLabel texteChemin = new JLabel();

	// Mise en page : les Box

	FrameApp frameApp;

	public PanChoixTypeIndexation(
			// parametres pour l�initialisation des attributs m�tiers

			// parametres correspondants au controleur du cas + panel des cas inclus ou
			// etendus
			// en relation avec un acteur
			ControlIndexation controlIndexation) {
		// initialisation des attributs metiers

		// initilaisation du controleur du cas + panels
		this.controlIndexation = controlIndexation;
		// des cas inclus ou etendus en lien avec un acteur
	}

	// M�thode d�initialisation du panel
	public void initialisation(FrameApp frameApp) {
		this.frameApp = frameApp;
		// mise en forme du panel (couleur, �)
		setBackground(Color.WHITE);

		// creation des diff�rents elements graphiques (JLabel, Combobox, Button,
		// TextAera �)
		JLabel textePageSelection = new JLabel("Choix du Dossier/Fichier : à modifier");
		textePageSelection.setFont(policeTitre);

		JLabel texteIndexer = new JLabel("Selectionner le chemin");
		texteIndexer.setFont(policeParagraphe);

		texteChemin.setText("Chemin : ");
		texteChemin.setFont(policeParagraphe);

		JButton buttonChoixChemin = new JButton();
		buttonChoixChemin.setText("Selectionner le chemin");
		buttonChoixChemin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialogue = new JFileChooser(".");
				File fichier;
				if (docOuFich == 2) {
					dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				} else {
					dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);
				}
				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fichier = dialogue.getSelectedFile();
					chemin = fichier.getPath();
					texteChemin.setText("Chemin : " + chemin);
				}
			}
		});

		JButton validationChemin = new JButton();
		validationChemin.setText("Valider");
		validationChemin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (chemin != null) {
					traitementCheminIndexation(chemin);
				}
			}
		});

		// mise en page : placements des differents elements graphiques dans des Box
		boxMiseEnPageChoixIndexation.add(textePageSelection);
		boxMiseEnPageChoixIndexation.add(Box.createRigidArea(new Dimension(0, 30)));
		boxMiseEnPageChoixIndexation.add(Box.createRigidArea(new Dimension(10, 0)));
		boxSelectionChemin.add(texteIndexer);
		boxSelectionChemin.add(Box.createRigidArea(new Dimension(0, 30)));
		boxSelectionChemin.add(Box.createRigidArea(new Dimension(10, 0)));
		boxSelectionChemin.add(buttonChoixChemin);
		boxSelectionChemin.add(Box.createRigidArea(new Dimension(0, 30)));
		boxSelectionChemin.add(Box.createRigidArea(new Dimension(10, 0)));
		boxSelectionChemin.add(texteChemin);
		boxSelectionChemin.add(Box.createRigidArea(new Dimension(0, 30)));
		boxSelectionChemin.add(Box.createRigidArea(new Dimension(10, 0)));

		boxValiderChoix.add(validationChemin);
		// mise en page : placements des differentes box dans une box principale
		boxMiseEnPageChoixIndexation.add(boxSelectionChemin);
		boxMiseEnPageChoixIndexation.add(Box.createRigidArea(new Dimension(0, 30)));
		boxMiseEnPageChoixIndexation.add(Box.createRigidArea(new Dimension(10, 0)));
		boxMiseEnPageChoixIndexation.add(boxValiderChoix);
		// mise en page : ajout de la box principale dans le panel
		this.add(boxMiseEnPageChoixIndexation);
		boxMiseEnPageChoixIndexation.setVisible(true);
		this.setVisible(false);
	}

	// Methode correspondante au nom du cas
	public void choixTypeIndexation(TypeDocument typeDocument, int docOuFich, IUseChoixTypeIndexation panAppelant) {
		texteChemin.setText("Chemin :");
		chemin = null;
		this.typeDocument = typeDocument;
		this.docOuFich = docOuFich;
		this.panAppelant = panAppelant;
		if (docOuFich == 1) {
			selectionnerChemin.setText("Parcourir les fichiers");
		} else {
			selectionnerChemin.setText("Parcourir les dossiers");
		}
		this.setVisible(true);
		this.repaint();
	}

	// Methodes priv�es pour le bon deroulement du cas
	public void traitementCheminIndexation(String chemin) {
		panAppelant.retourChoixTypeIndexation(true, chemin);
	}

}
