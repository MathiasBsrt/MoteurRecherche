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

public class PanCreationCompteAdministrateur extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// controleurs du cas
	private ControlAdmin controladmin;
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

	private JButton validationCreationdecompte = new JButton();
	private JButton retour = new JButton();
	private JButton change = new JButton();

	// Declaration et creation des TextArea
	private JTextField textAreaIdentifiant = new JTextField();
	private JTextField textAreaMotdepasse = new JTextField();
	private JTextField textAreaConfirmmotdepasse = new JTextField();

	// Declaration et creation des Labels

	// Mise en page : les Box

	private Box boxMiseEnPageCreationDeCompte = Box.createVerticalBox();
	private Box boxIdentifiant = Box.createHorizontalBox();
	private Box boxmdp = Box.createHorizontalBox();
	private Box boxconfirmmdp = Box.createHorizontalBox();
	private Box boxValiderCreationdecompte = Box.createHorizontalBox();
	private Box boxchange = Box.createHorizontalBox();

	private FrameApp frame;

	public PanCreationCompteAdministrateur(
			// parametres pour l'initialisation des attributs metiers
			// parametres correspondants au controleur du cas + panels
			ControlAdmin controladmin
	// des cas inclus ou etendus en lien avec un acteur
	) {
		// initialisation des attributs metiers
		// initilaisation du controleur du cas + panels
		this.controladmin = controladmin;
		// des cas inclus ou etendus en lien avec un acteur
	}

	// Methode d'initialisation du panel
	public void initialisation(FrameApp frame) {
		// mise en forme du panel (couleur, ...)

		// creation des differents elements graphiques (JLabel, Combobox, Button,
		// TextAera ...)

		this.frame = frame;

		Label texteIdentification = new Label("Creation de compte Administrateur");
		texteIdentification.setFont(policeTitre);
		boxMiseEnPageCreationDeCompte.add(texteIdentification);

		Label texteIdentifiant = new Label("Identifiant");
		texteIdentifiant.setFont(policeParagraphe);
		boxIdentifiant.add(texteIdentifiant);

		Label textemdp = new Label("Mot de passe");
		textemdp.setFont(policeParagraphe);
		boxmdp.add(textemdp);

		Label texteconfirmmdp = new Label("Confirmer le mot de passe");
		texteconfirmmdp.setFont(policeParagraphe);
		boxconfirmmdp.add(textemdp);

		Label textechangemode = new Label("Déjà un compte ? Connectez vous !");
		textechangemode.setFont(policeParagraphe);
		boxchange.add(textechangemode);

		validationCreationdecompte.setText("Valider");
		validationCreationdecompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String identifiant = textAreaIdentifiant.getText();
				String mdp = textAreaMotdepasse.getText();
				if (isChampsOK()) {
					if (Creationcompte(identifiant, mdp)) {
						frame.afficherMenuAdministrateur();
					} else {
						JOptionPane.showMessageDialog(null, "Identifiants incorrectes.", "Erreur d'authentification",
								JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Veuillez à saisir tous les champs et que les deux champs mots de passe soient égaux",
							"Erreur d'authentification", JOptionPane.ERROR_MESSAGE);
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

		change.setText("Se connecter");
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherIdentificationAdministrateur();
			}

		});

		textAreaIdentifiant.setPreferredSize(new Dimension(100, 50));
		textAreaMotdepasse.setForeground(Color.GRAY);
		textAreaMotdepasse.setPreferredSize(new Dimension(100, 50));
		textAreaIdentifiant.setFont(policeAremplacer);
		textAreaConfirmmotdepasse.setForeground(Color.GRAY);
		textAreaConfirmmotdepasse.setPreferredSize(new Dimension(100, 50));

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

		textAreaConfirmmotdepasse.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
				textAreaConfirmmotdepasse.setText(null);
				textAreaConfirmmotdepasse.setFont(policeChoixUtilisateur);
				textAreaConfirmmotdepasse.setForeground(Color.black);
			}
		});

		// mise en page : placements des differents elements graphiques dans des Box

		boxMiseEnPageCreationDeCompte.add(texteIdentification);
		boxIdentifiant.add(texteIdentifiant);
		boxmdp.add(textemdp);
		boxconfirmmdp.add(texteconfirmmdp);
		boxValiderCreationdecompte.add(validationCreationdecompte);
		boxValiderCreationdecompte.add(retour);
		boxIdentifiant.add(textAreaIdentifiant);
		boxmdp.add(textAreaMotdepasse);
		boxconfirmmdp.add(textAreaConfirmmotdepasse);
		boxchange.add(change);

		// mise en page : placements des differentes box dans une box principale

		boxMiseEnPageCreationDeCompte.add(boxIdentifiant);
		boxMiseEnPageCreationDeCompte.add(boxmdp);
		boxMiseEnPageCreationDeCompte.add(boxconfirmmdp);
		boxMiseEnPageCreationDeCompte.add(boxValiderCreationdecompte);
		boxMiseEnPageCreationDeCompte.add(boxchange);

		// mise en page : ajout de la box principale dans le panel
		this.add(boxMiseEnPageCreationDeCompte);
		boxMiseEnPageCreationDeCompte.setVisible(true);
		this.setVisible(false);

	}

	private boolean isChampsOK() {
		if (textAreaMotdepasse.getText().equals(textAreaConfirmmotdepasse.getText())
				&& !textAreaIdentifiant.getText().equals("") && !textAreaMotdepasse.getText().equals("")
				&& !textAreaConfirmmotdepasse.getText().equals("")) {
			return true;
		}
		return false;
	}

	public void creationCompteAdministrateur() {
		// frame.pack();
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

	private boolean Creationcompte(String Identifiant, String mdp) {
		boolean comptecree = controladmin.creerCompte(Identifiant, mdp);
		return (comptecree);

	}

}
