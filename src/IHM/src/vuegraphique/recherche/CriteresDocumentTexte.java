package vuegraphique.recherche;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import modele.TypeDocument;

public class CriteresDocumentTexte extends CriteresDocument implements PropertyChangeListener {

	private Box boxVertical = Box.createVerticalBox();
	private Box boxTypeRecherche = Box.createHorizontalBox();
	private Box boxCriteres = Box.createHorizontalBox();
	private Box boxMotsCles = Box.createVerticalBox();
	private JLabel lblTitreRecherche = new JLabel("Type de recherche:");

	private JList<TypeRecherche> listTypeRecherche = new JList<>(
			new TypeRecherche[] { TypeRecherche.DOCUMENT, TypeRecherche.CRITERES });

	private JButton btnDocument = new JButton("...");
	private File selectedFile;
	private JTextField txtCriteres = new JTextField();

	private List<CriteresTexte> listCriteresTexte = new ArrayList<>();

	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	public CriteresDocumentTexte() {
		super(TypeDocument.TEXTE);

		boxVertical.add(boxTypeRecherche);
		boxVertical.add(boxCriteres);
		boxVertical.add(boxMotsCles);

		boxTypeRecherche.add(lblTitreRecherche);
		boxTypeRecherche.add(Box.createHorizontalStrut(25));
		boxTypeRecherche.add(Box.createHorizontalGlue());

		listTypeRecherche.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(listTypeRecherche);
		scrollPane.setPreferredSize(new Dimension(100, 50));
		boxTypeRecherche.add(scrollPane);

		// Listener pour la JList demandant
		// le type de recherche (par document ou par critères)
		listTypeRecherche.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting() == false) {
					int selectedIndex = listTypeRecherche.getSelectedIndex();
					if (selectedIndex != -1) {
						btnDocument.setLabel("...");
						selectedFile = null;
						TypeRecherche typeRecherche = TypeRecherche.values()[selectedIndex];
						miseAJourBoxCriteres(typeRecherche);
						boxTypeRecherche.repaint();
						boxTypeRecherche.revalidate();
					}
				}
			}
		});

		btnDocument.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte d'images", "txt");
				chooser.setFileFilter(filter);
				chooser.setMultiSelectionEnabled(false);
				int returnVal = chooser.showOpenDialog(boxTypeRecherche);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					selectedFile = chooser.getSelectedFile();
					btnDocument.setLabel(chooser.getSelectedFile().getName());
				}
			}
		});

		support.addPropertyChangeListener("CRITERES_SUPPRIMER", this);

		txtCriteres.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String criteres = txtCriteres.getText();
					CriteresTexte ct = new CriteresTexte(criteres, support);
					listCriteresTexte.add(ct);
					txtCriteres.setText("");
					boxMotsCles.add(ct);
					boxMotsCles.repaint();
					boxMotsCles.revalidate();
				}
			}

		});
	}

	private void miseAJourBoxCriteres(TypeRecherche typeRecherche) {
		boxCriteres.removeAll();
		switch (typeRecherche) {
		case DOCUMENT: {
			boxCriteres.add(new JLabel("Sélectionner le fichier: "));
			boxCriteres.add(Box.createHorizontalGlue());
			boxCriteres.add(btnDocument);
			boxMotsCles.setVisible(false);
			break;
		}
		case CRITERES: {
			boxCriteres.add(new JLabel("Sélectionner les mots-clés: "));
			boxCriteres.add(Box.createHorizontalGlue());
			boxCriteres.add(txtCriteres);
			boxMotsCles.setVisible(true);
		}
		}
		boxCriteres.repaint();
		boxCriteres.revalidate();
	}

	@Override
	public Component getSelector() {
		return boxVertical;
	}

	@Override
	public List<String> getCriteres() {
		int selectedIndex = listTypeRecherche.getSelectedIndex();
		TypeRecherche typeRecherche = TypeRecherche.values()[selectedIndex];
		List<String> criteres = new ArrayList<>();
		switch (typeRecherche) {
		case DOCUMENT: {
			if (selectedFile != null) {
				criteres.add(selectedFile.getAbsolutePath());
			}
			break;
		}
		case CRITERES: {
			for (CriteresTexte ct : listCriteresTexte) {
				criteres.add(ct.getCriteres());
			}
			break;
		}
		}

		return criteres;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		CriteresTexte ct = (CriteresTexte) event.getOldValue();
		listCriteresTexte.remove(ct);
		boxMotsCles.remove(ct);
		boxMotsCles.repaint();
		boxMotsCles.revalidate();
	}

	@Override
	public boolean isTypeRechercheSelectionne() {
		int selectedIndex = listTypeRecherche.getSelectedIndex();
		return selectedIndex != -1;
	}

	@Override
	public TypeRecherche getTypeRecherche() {
		int selectedIndex = listTypeRecherche.getSelectedIndex();
		if (selectedIndex != -1)
			return listTypeRecherche.getSelectedValue();
		return null;
	}

}
