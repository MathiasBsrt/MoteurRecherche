package vuegraphique.recherche;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class CriteresTexte extends Box implements ActionListener {

	private JTextField txtContent = new JTextField();
	private JSpinner spOccurence = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
	private JButton btnDelete = new JButton("X");

	private PropertyChangeSupport support;

	public CriteresTexte(String content, PropertyChangeSupport support) {
		super(BoxLayout.X_AXIS);
		txtContent.setText(content);

		add(txtContent);
		add(Box.createHorizontalGlue());
		add(spOccurence);
		add(Box.createHorizontalGlue());
		add(btnDelete);

		this.support = support;

		btnDelete.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		support.firePropertyChange("CRITERES_SUPPRIMER", this, null);
	}

	public String getCriteres() {
		String content = txtContent.getText();
		Integer value = (Integer) spOccurence.getValue();
		return String.format("%s:%d", content, value);
	}

}
