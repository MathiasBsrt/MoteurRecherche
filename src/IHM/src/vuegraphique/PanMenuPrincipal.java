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

import controleur.ControlAdmin;

public class PanMenuPrincipal extends JPanel {
	// controleurs du cas + panel des cas inclus ou etendus en lien avec un acteur

	private ControlAdmin controladmin;

	// les attributs metiers (ex : numClient)

	// Les elements graphiques :

	private PanMenuUtilisateur panMenuUtilisateur;
	private PanMenuAdministrateur panMenuAdministrateur;

	// Declaration et creation des polices d'ecritures
	private Font policeTitre = new Font("Calibri", Font.BOLD, 40);
	private Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);

	// Declaration et creation des ComboBox
	// Declaration et creation des Button
	// Declaration et creation des TextArea
	// Declaration et creation des Labels

	// Mise en page : les Box
	private Box boxMiseEnpage = Box.createVerticalBox();
	private Box boxCentre = Box.createHorizontalBox();
	private Box boxTitre = Box.createHorizontalBox();
	private Box boxLogo = Box.createHorizontalBox();

	private FrameApp frame;

	public PanMenuPrincipal(
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
		/*
		 * BufferedImage myPicture = null;
		 * 
		 * try { myPicture = ImageIO.read(new File("./seekbit.png")); } catch
		 * (IOException e1) { e1.printStackTrace(); } ImageIcon image = new
		 * ImageIcon(myPicture);
		 */
		JLabel logo = new JLabel();
		logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seekbit.png")));
		logo.setSize(200, 150);
		boxLogo = (Box) justifierCenter(logo);

		JLabel titre = new JLabel("Bienvenue sur SeekBit");
		titre.setFont(policeTitre);
		titre.setHorizontalAlignment(JLabel.CENTER);
		boxTitre.add(Box.createHorizontalGlue());
		boxTitre.add(titre, BorderLayout.PAGE_START);
		boxTitre.add(Box.createHorizontalGlue());
		boxTitre.add(Box.createRigidArea(new Dimension(0, 200)));

		JButton boutonmodeutil = new JButton("Mode Utilisateur");
		boutonmodeutil.setPreferredSize(new Dimension(300, 100));
		boutonmodeutil.setMaximumSize(new Dimension(300, 100));
		JButton boutonmodeadmin = new JButton("Mode Administrateur");
		boutonmodeadmin.setPreferredSize(new Dimension(300, 100));
		boutonmodeadmin.setMaximumSize(new Dimension(300, 100));
		boxCentre.add(boutonmodeutil);
		boxCentre.add(Box.createRigidArea(new Dimension(120, 0)));
		boxCentre.add(boutonmodeadmin);
		boxMiseEnpage.add(boxLogo);
		boxMiseEnpage.add(boxTitre);
		boxMiseEnpage.add(boxCentre);
		add(boxMiseEnpage);

		boutonmodeutil.setText("Mode Utilisateur");
		boutonmodeutil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.afficherMenuUtilisateur();
			}
		});

		boutonmodeadmin.setText("Mode Administrateur");
		boutonmodeadmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!controladmin.compteCree()) {
					frame.afficherCreationCompteAdministrateur();
				} else {
					if (!controladmin.isConnect()) {
						frame.afficherIdentificationAdministrateur();
					} else {
						frame.afficherMenuAdministrateur();
					}
				}
			}
		});

	}

	// Methode correspondante au nom du cas
	public void menuPrincipal( /* parametres metiers */) {
		frame.pack();
	}

	private Component justifierCenter(JLabel label) {
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la gauche et sur la droite du label
		b.add(label);
		b.add(Box.createHorizontalGlue());
		return b;
	}

	// Methodes privees pour le bon deroulement du cas

}