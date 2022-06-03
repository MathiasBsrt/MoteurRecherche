package vuegraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.ControlAdmin;

public class PanIdentificationAdministrateur extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// controleurs du cas
	private ControlAdmin controlAdmin;
	// les attributs metiers (ex : num admin ) ???

	private int numAdmin;

	// Les elements graphiques :
	// Declaration et creation des polices d'ecritures

	private Font policeTitre = new Font("Calibri", Font.BOLD, 24);
	private Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);
	private Font policeAremplacer = new Font("Arial", Font.ITALIC, 12);
	private Font policeChoixUtilisateur = new Font("Arial", Font.TRUETYPE_FONT, 12);

	// Declaration et creation des ComboBox

	// Declaration et creation des Button

	private JButton validationIdentification = new JButton();
	private JButton retour = new JButton();
	private JButton change = new JButton();

	// Declaration et creation des TextArea
	private JTextField textAreaIdentifiant = new JTextField();
	private JTextField textAreaMotdepasse = new JTextField();
	// Declaration et creation des Labels

	// Mise en page : les Box

	private Box boxMiseEnPageIdentification = Box.createVerticalBox();
	private Box boxIdentifiant = Box.createHorizontalBox();
	private Box boxmdp = Box.createHorizontalBox();
	private Box boxValiderIdentification = Box.createHorizontalBox();
	private Box boxchange = Box.createHorizontalBox();

	private FrameApp frame;

	public PanIdentificationAdministrateur(
			// parametres pour l'initialisation des attributs metiers
			// parametres correspondants au controleur du cas + panels
			ControlAdmin controlAdmin
	// des cas inclus ou etendus en lien avec un acteur
	) {
		// initialisation des attributs metiers
		// initilaisation du controleur du cas + panels
		this.controlAdmin = controlAdmin;
		// des cas inclus ou etendus en lien avec un acteur
	}

	// Methode d'initialisation du panel
	public void initialisation(FrameApp frame) {
		// mise en forme du panel (couleur, ...)

		// creation des differents elements graphiques (JLabel, Combobox, Button,
		// TextAera ...)

		Label texteIdentification = new Label("Connexion Administrateur");
		texteIdentification.setFont(policeTitre);
		boxMiseEnPageIdentification.add(texteIdentification);

		Label texteIdentifiant = new Label("Identifiant");
		texteIdentifiant.setFont(policeParagraphe);
		boxIdentifiant.add(texteIdentifiant);

		Label textemdp = new Label("Mot de passe");
		textemdp.setFont(policeParagraphe);
		boxmdp.add(textemdp);

		Label textechangemode = new Label("Pas encore de compte ? Créez en un !");
		textechangemode.setFont(policeParagraphe);
		boxchange.add(textechangemode);

		validationIdentification.setText("Valider");
		validationIdentification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String identifiant = textAreaIdentifiant.getText();
				String mdp = textAreaMotdepasse.getText();
				if (traitementIdentification(identifiant, mdp)) {
					frame.afficherMenuAdministrateur();
				} else {
					JOptionPane.showMessageDialog(null, "Identifiants incorrectes.", "Erreur d'authentification",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});

		retour.setText("Retour");
		retour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherMenuPrincipal();
			}

		});

		change.setText("Créer un compte");
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherCreationCompteAdministrateur();
			}

		});

		textAreaIdentifiant.setPreferredSize(new Dimension(100, 50));
		textAreaMotdepasse.setForeground(Color.GRAY);
		textAreaMotdepasse.setPreferredSize(new Dimension(100, 50));
		textAreaIdentifiant.setFont(policeAremplacer);

		textAreaIdentifiant.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
				textAreaMotdepasse.setText(null);
				textAreaMotdepasse.setFont(policeChoixUtilisateur);
				textAreaMotdepasse.setForeground(Color.black);
			}
		});

		textAreaMotdepasse.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
				textAreaMotdepasse.setText(null);
				textAreaMotdepasse.setFont(policeChoixUtilisateur);
				textAreaMotdepasse.setForeground(Color.black);
			}
		});
		// mise en page : placements des differents elements graphiques dans des Box

		boxMiseEnPageIdentification.add(texteIdentification);
		boxIdentifiant.add(texteIdentifiant);
		boxmdp.add(textemdp);
		boxValiderIdentification.add(validationIdentification);
		boxValiderIdentification.add(retour);
		boxIdentifiant.add(textAreaIdentifiant);
		boxmdp.add(textAreaMotdepasse);
		boxchange.add(change);

		// mise en page : placements des differentes box dans une box principale

		boxMiseEnPageIdentification.add(boxIdentifiant);
		boxMiseEnPageIdentification.add(boxmdp);
		boxMiseEnPageIdentification.add(boxValiderIdentification);
		boxMiseEnPageIdentification.add(boxchange);

		// mise en page : ajout de la box principale dans le panel
		this.add(boxMiseEnPageIdentification);
		boxMiseEnPageIdentification.setVisible(true);
		this.setVisible(false);

	}

	// Methode correspondante au nom du cas
	/*
	 * public void enregistrerCoordonneesBancaires(int numClient,
	 * IUseEnregistrerCoordonneesBancaires panAppelant) { this.numClient =
	 * numClient; this.panAppelant = panAppelant; textAreaNumeroCarte.setText("");
	 * textAreaDateExpiration.setText("MMAA"); this.setVisible(true);
	 * this.repaint(); }
	 */

	// Methodes privees pour le bon deroulement du cas

	private boolean traitementIdentification(String identifiant, String mdp) {
		boolean compteExiste = controlAdmin.compteCree();
		boolean indentificationReussie = false;
		if (compteExiste) {
			indentificationReussie = controlAdmin.connexion(identifiant, mdp);
		}
		return indentificationReussie;
	}

}
