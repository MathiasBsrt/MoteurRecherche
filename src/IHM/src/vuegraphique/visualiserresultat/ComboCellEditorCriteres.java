package vuegraphique.visualiserresultat;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ComboCellEditorCriteres extends DefaultCellEditor implements TableCellRenderer {

	private JComboBox<String> combo;

	public ComboCellEditorCriteres() {
		super(new JComboBox<>());
		combo = (JComboBox<String>) getComponent();
		combo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				combo.setPopupVisible(true);
			}
		});
		super.setClickCountToStart(1);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		combo.setModel(new DefaultComboBoxModel<>((String[]) table.getModel().getValueAt(row, column)));
		if (isSelected) {
			combo.setBackground(table.getSelectionBackground());
		} else {
			combo.setBackground(table.getBackground());
		}
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JComboBox<String> renderCombo = new JComboBox<>(new DefaultComboBoxModel<>((String[]) value));
		if (isSelected) {
			renderCombo.setBackground(table.getSelectionBackground());
		} else {
			renderCombo.setBackground(table.getBackground());
		}
		return renderCombo;
	}

}
