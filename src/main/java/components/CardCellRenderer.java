/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 *
 * @author Lenovo
 */
public class CardCellRenderer extends JPanel implements TableCellRenderer {
    private JLabel lblUID;
    private JLabel lblNama;
    private JLabel lblID;
    private JLabel lblKelas;
    
    public CardCellRenderer() {
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
         GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        lblUID = new JLabel();
        lblNama = new JLabel();
        lblID = new JLabel();
        lblKelas = new JLabel();
        
        gbc.gridx = 0;
        add(lblUID, gbc);
        gbc.gridx = 1;
        add(lblNama, gbc);
        gbc.gridx = 2;
        add(lblID, gbc);
        gbc.gridx = 3;
        add(lblKelas, gbc);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, 
            boolean hasFocus, int row, int column) {
        
        if (isSelected) {
            setBackground(new Color(173, 216, 230));
        } else {
            setBackground(Color.WHITE);
        }
        
        lblUID.setText(table.getValueAt(row, 0).toString());
        lblNama.setText(table.getValueAt(row, 1).toString());
        lblID.setText(table.getValueAt(row, 2).toString());
        lblKelas.setText(table.getValueAt(row, 3).toString());
        
        return this;
    }
}
