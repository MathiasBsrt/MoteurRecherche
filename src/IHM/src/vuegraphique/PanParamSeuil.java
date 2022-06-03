package vuegraphique;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;

import controleur.ControlParamSeuil;
import modele.TypeDocument;

public class PanParamSeuil extends JPanel {

	private static final long serialVersionUID = 1L;
	private ControlParamSeuil controleurParamSeuil;

	// Police d'écriture
	Font policeTitre = new Font("Calibri", Font.BOLD, 24);
	Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);

	// Declaration et creation des elements d'actions
	private JComboBox<String> comboBoxDoc = new JComboBox<>();
	private JSlider sliderSeuil;
	private JLabel labelAncienSeuil = new JLabel();
	private JSlider sliderNbApparition;
	private JLabel labelnbApparition;
	private CardLayout cardLayout;
	// Declaration et creation des Button
	private JButton validerParam = new JButton("Valider");
	private JButton retour = new JButton("Retour");
	private FrameApp frameApp;
	// Element de Mise en page
	private Box boxPrincipale = Box.createVerticalBox();
	private Box boxTitre = Box.createVerticalBox();
	private Box boxActions = Box.createVerticalBox();
	private Box boxValiderRetour = Box.createHorizontalBox();

	private Box boxTypeDoc = Box.createVerticalBox();
	private Box boxChoixSeuil = Box.createVerticalBox();
	private Box boxChoixNbApparition = Box.createVerticalBox();

	// Choix utilisateur
	private TypeDocument typeDoc = TypeDocument.SON;
	private int nbApparitionTexte;

	private int seuilTexte;
	private int seuilAudio;
	private int seuilImage;

	private JLabel texteLigneDeVie;
	private JLabel texteTypeDoc;
	private JLabel texteSeuil;
	private JLabel texteNbApparition;

	/***
	 * COnstructeur necessitant le controleur administrateur pour fonctionner.
	 * 
	 * @param controleurAdmin
	 */
	public PanParamSeuil(ControlParamSeuil controleurParamSeuil) {
		this.controleurParamSeuil = controleurParamSeuil;
		seuilTexte = controleurParamSeuil.getSeuilTexte();
		seuilAudio = controleurParamSeuil.getSeuilAudio();
		seuilImage = controleurParamSeuil.getSeuilImage();

		nbApparitionTexte = controleurParamSeuil.getNbApparitionTexte();

	}

	/***
	 * Cette méthodé définie la correspondance entre l'index de combo box et le type
	 * de document
	 */
	public void affichageChoixTypeDoc() {
		affichageTypeDoc(); // Génération de la combo box
		comboBoxDoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choix = comboBoxDoc.getSelectedIndex();

				switch (choix) {
				case 1:
					typeDoc = TypeDocument.TEXTE;
					labelAncienSeuil.setText("Ancien seuil: " + String.valueOf(controleurParamSeuil.getSeuilTexte()));
					boxChoixNbApparition.setVisible(true);
					break;
				case 2:
					typeDoc = TypeDocument.IMAGE;
					labelAncienSeuil.setText("Ancien seuil: " + String.valueOf(controleurParamSeuil.getSeuilImage()));
					boxChoixNbApparition.setVisible(false);

					break;
				case 3:
					typeDoc = TypeDocument.SON;
					labelAncienSeuil.setText("Ancien seuil: " + String.valueOf(controleurParamSeuil.getSeuilAudio()));
					boxChoixNbApparition.setVisible(false);
					break;

				default:
					break;
				}

				repaint();
			}
		});

		comboBoxDoc.setSelectedIndex(3);

	}

	/**
	 * Cette méthode permet de creer les différents éléments à afficher avec leurs
	 * actions associées
	 */
	public void creationActions() {
		// Choix type document
		affichageChoixTypeDoc();
		// CHoix seuil
		labelAncienSeuil = new JLabel("AncienSeuil = " + String.valueOf(controleurParamSeuil.getSeuilAudio()));
		sliderSeuil = new JSlider(0, 100, 0);
		sliderSeuil.setMinorTickSpacing(5);
		sliderSeuil.setMajorTickSpacing(10);
		sliderSeuil.setPaintTicks(true);
		sliderSeuil.setValue(controleurParamSeuil.getSeuilAudio()); // Par defaut : audio

		sliderSeuil.addChangeListener((ChangeEvent event) -> {

			int seuil = sliderSeuil.getValue();
			texteSeuil.setText("Nouveau seuil: " + Integer.toString(seuil));
			switch (typeDoc) {
			case SON:
				seuilAudio = seuil;
				break;
			case IMAGE:
				seuilImage = seuil;
				break;
			case TEXTE:
				seuilTexte = seuil;

				break;

			default:
				break;
			}

		});

		// Choix nb Aparition
		labelnbApparition = new JLabel(String.valueOf(nbApparitionTexte));
		sliderNbApparition = new JSlider(0, 100, 0);
		sliderNbApparition.setMinorTickSpacing(5);
		sliderNbApparition.setMajorTickSpacing(10);
		sliderNbApparition.setPaintTicks(true);
		sliderNbApparition.setValue(nbApparitionTexte);

		sliderNbApparition.addChangeListener((ChangeEvent event) -> {

			nbApparitionTexte = sliderNbApparition.getValue();
			labelnbApparition.setText(Integer.toString(nbApparitionTexte));

		});
		// Valider
		validerParam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (typeDoc) {
				case SON:
					controleurParamSeuil.modificationSeuil(typeDoc, seuilAudio);
					break;
				case IMAGE:
					controleurParamSeuil.modificationSeuil(typeDoc, seuilImage);
					break;
				case TEXTE:
					controleurParamSeuil.modificationSeuil(typeDoc, seuilTexte);
					controleurParamSeuil.modificationNbApparition(typeDoc, nbApparitionTexte);

					break;

				default:
					break;
				}

				// Pop up pour indiquer que le changement a été effectué
				JOptionPane.showMessageDialog(null, "Les paramètres ont bien été modifiés", "Succès",
						JOptionPane.PLAIN_MESSAGE);

				// Retour au menu admin
				retour();

			}
		});

		retour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				retour();
			}

		});
	}

	public void retour() {
		frameApp.afficherMenuAdministrateur();
	}

	/****
	 * Permet de placer les différents éléments dans la vue
	 */
	public void miseEnForme() {

		setBackground(Color.WHITE);

		// Header
		boxTitre.add(texteLigneDeVie);
		boxTitre.setBorder(new EmptyBorder(10, 0, 20, 0));

		boxPrincipale.add(boxTitre);

		// Actions réalisables

		boxTypeDoc.add(texteTypeDoc);
		boxTypeDoc.add(comboBoxDoc);
		boxTypeDoc.setBorder(new EmptyBorder(10, 0, 10, 0));

		boxActions.add(boxTypeDoc);

		boxChoixSeuil.add(texteSeuil);
		boxChoixSeuil.add(sliderSeuil);
		boxChoixSeuil.add(labelAncienSeuil);
		boxChoixSeuil.setBorder(new EmptyBorder(0, 0, 10, 0));

		boxActions.add(boxChoixSeuil);

		boxChoixNbApparition.add(texteNbApparition);
		boxChoixNbApparition.add(sliderNbApparition);
		boxChoixNbApparition.add(labelnbApparition);
		boxChoixNbApparition.setVisible(false);
		boxActions.add(boxChoixNbApparition);

		boxActions.setBorder(new EmptyBorder(0, 0, 20, 0));

		boxPrincipale.add(boxActions);

		// Footer
		boxValiderRetour.add(Box.createRigidArea(new Dimension(100, 0)));
		boxValiderRetour.add(validerParam);
		boxValiderRetour.add(Box.createRigidArea(new Dimension(50, 0)));
		boxValiderRetour.add(retour);
		retour.setHorizontalAlignment(JButton.CENTER);
		validerParam.setHorizontalAlignment(JButton.CENTER);
		boxPrincipale.add(boxValiderRetour);

		this.add(boxPrincipale);
	}

	/***
	 * Méthode principale de la classe qui permet d'intialiser les différents
	 * composant de la vue et les placer
	 */
	public void initialisation(FrameApp frameapp) {
		// Init global
		this.frameApp = frameapp;
		texteLigneDeVie = new JLabel("Paramètres de seuils");
		texteLigneDeVie.setFont(policeTitre);
		texteTypeDoc = new JLabel("Choisiez le type de document : ");
		texteTypeDoc.setFont(policeParagraphe);

		texteSeuil = new JLabel("Choisissez un seuil d'indexation : ");
		texteSeuil.setFont(policeParagraphe);
		texteNbApparition = new JLabel("Choisiez un nombre d'apparition du mot : ");
		texteNbApparition.setFont(policeParagraphe);

		// Creation des actions possibles :
		creationActions();
		// mise en forme du panel (couleur, ...)
		miseEnForme();

	}

	/***
	 * Cette méthode permet de générer le contenu de la combo box
	 */
	public void affichageTypeDoc() {
		comboBoxDoc.removeAll();
		comboBoxDoc.addItem("");

		// Ajout des items dans la combo liste
		comboBoxDoc.addItem(TypeDocument.TEXTE.toString());
		boxTypeDoc.add(Box.createRigidArea(new Dimension(10, 0)));
		comboBoxDoc.addItem(TypeDocument.IMAGE.toString());
		boxTypeDoc.add(Box.createRigidArea(new Dimension(10, 0)));
		comboBoxDoc.addItem(TypeDocument.SON.toString());
		boxTypeDoc.add(Box.createRigidArea(new Dimension(10, 0)));
	}

	public void paramSeuil() {
		// Actualisation des modifs
		comboBoxDoc.setSelectedItem(3);
		sliderSeuil.setValue(controleurParamSeuil.getSeuilAudio()); // Par defaut : audio
		seuilAudio = controleurParamSeuil.getSeuilAudio();
		seuilImage = controleurParamSeuil.getSeuilImage();
		seuilTexte = controleurParamSeuil.getSeuilTexte();
		labelAncienSeuil.setText("Ancien seuil: " + String.valueOf(controleurParamSeuil.getSeuilAudio()));
		repaint();
	}

}
