package vuegraphique.recherche;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.TypeDocument;

public class PanRechercheCriteres extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8416864760401702989L;

	private JComboBox<CriteresDocument> cbDocuments = new JComboBox<>();
	private JLabel lblTypeDocument = new JLabel("Type de document:");

	private Box parentBox = Box.createVerticalBox();

	private Component previousComponent;

	public PanRechercheCriteres() {
		cbDocuments.addItem(new CriteresDocumentSon());
		cbDocuments.addItem(new CriteresDocumentImage());
		cbDocuments.addItem(new CriteresDocumentTexte());

		parentBox.add(justifierGauche(lblTypeDocument));
		parentBox.add(cbDocuments);
		parentBox.add(Box.createRigidArea(new Dimension(0, 50)));
		add(parentBox);

		// Action Listener de la JComboBox demandant
		// le type de document
		cbDocuments.addActionListener(this);

		// Sélectionne le premier type de document
		CriteresDocument cd = cbDocuments.getItemAt(0);
		Component selector = cd.getSelector();
		int position = parentBox.getComponentCount() - 1;
		parentBox.add(selector, position);
		previousComponent = selector;
		parentBox.repaint();
		parentBox.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		int selectedIndex = cbDocuments.getSelectedIndex();
		CriteresDocument cd = cbDocuments.getItemAt(selectedIndex);
		Component selector = cd.getSelector();

		if (previousComponent != null)
			parentBox.remove(previousComponent);

		int position = parentBox.getComponentCount() - 1;
		parentBox.add(selector, position);
		previousComponent = selector;

		parentBox.repaint();
		parentBox.revalidate();
	}

	private Component justifierGauche(JLabel label) {
		Box b = Box.createHorizontalBox();
		b.add(label);
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la droite du label
		return b;
	}

	public List<String> getCriteres() {
		int selectedIndex = cbDocuments.getSelectedIndex();
		if (selectedIndex == -1) {
			return new ArrayList<String>();
		}
		CriteresDocument cd = cbDocuments.getItemAt(selectedIndex);
		return cd.getCriteres();
	}

	public boolean isTypeDocumentSelectionne() {
		int selectedIndex = cbDocuments.getSelectedIndex();
		return selectedIndex != -1;
	}

	public TypeDocument getTypeDocument() {
		int selectedIndex = cbDocuments.getSelectedIndex();
		if (selectedIndex == -1)
			return null;
		CriteresDocument cd = cbDocuments.getItemAt(selectedIndex);
		return cd.getTypeDocument();
	}

	public boolean isTypeRechercheSelectionne() {
		int selectedIndex = cbDocuments.getSelectedIndex();
		if (selectedIndex == -1)
			return false;
		CriteresDocument cd = cbDocuments.getItemAt(selectedIndex);
		return cd.isTypeRechercheSelectionne();
	}

	public TypeRecherche getTypeRecherche() {
		int selectedIndex = cbDocuments.getSelectedIndex();
		if (selectedIndex == -1)
			return null;
		CriteresDocument cd = cbDocuments.getItemAt(selectedIndex);
		return cd.getTypeRecherche();
	}

}
