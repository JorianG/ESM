package vue.pages;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class ImageRenderer extends DefaultTableCellRenderer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageRenderer instance = new ImageRenderer();
    public static final Integer size = 40;
    public static final Integer columnSize = 46;

    /**
     * Returns the default table cell renderer.
     * @param table  the <code>JTable</code>
     * @param value  the value to assign to the cell at
     *                  <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus true if cell has focus
     * @param row  the row of the cell to render
     * @param column the column of the cell to render
     * @return the default table cell renderer
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
        instance.setHorizontalAlignment(JLabel.CENTER);
        try {
            JLabel lbl = new JLabel();
            lbl.setText("");
            lbl.setIcon(IconResized.of((String) value, size, size));
            return lbl;
        }catch(Exception e) {
            return new JLabel();
        }
    }
}
