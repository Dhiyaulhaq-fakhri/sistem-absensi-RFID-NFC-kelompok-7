/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.panel;

import gui.modules.ModuleFactory;
import gui.modules.MasterDataModule;
import gui.Adminpanel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Lenovo
 */
public class SidebarMenuPanel extends JPanel {

    private JButton activeButton = null;
    private final Color BUTTON_BG = new Color(51, 65, 85);
    private final Color BUTTON_HOVER = new Color(37, 99, 235);
    private final Color BUTTON_ACTIVE = new Color(59, 130, 246);
    private final Color TEXT_COLOR = Color.WHITE;

    public SidebarMenuPanel() {
        setLayout(new GridLayout(4, 1, 5, 5));
        setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(33, 37, 41));
        setPreferredSize(new Dimension(260, 200));

        String[] menus = {"Guru", "Peserta Didik", "Kelas", "Jam Pelajaran"};

        for (String menu : menus) {
            JButton btn = createMenuButton(menu);
            btn.setPreferredSize(new Dimension(100, 40));
            add(btn);
            System.out.println("✅ Button added: " + menu);
        }
    }

    private JButton createMenuButton(String menuName) {
        JButton btn = new JButton(menuName);
        btn.setBackground(BUTTON_BG);
        btn.setForeground(TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn != activeButton) {
                    btn.setBackground(BUTTON_HOVER);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn != activeButton) {
                    btn.setBackground(BUTTON_BG);
                }
            }
        });

        // Click action
        btn.addActionListener(e -> {
            switchModule(menuName, btn);
        });

        return btn;
    }

    private void switchModule(String moduleName, JButton btn) {
        // Reset previous active button
        if (activeButton != null) {
            activeButton.setBackground(BUTTON_BG);
        }
        
        // Set new active button
        activeButton = btn;
        btn.setBackground(BUTTON_ACTIVE);
        
        // Get module instance
        MasterDataModule module = ModuleFactory.getModule(moduleName);
        
        if (module != null) {
            // Update CRUD panel
            SidebarCrudPanel.getInstance().setModule(module);
            
            // Update content panel
            updateContentPanel(module.getTablePanel());
        }
    }
    
    private void updateContentPanel(JPanel newContent) {
        // ← FIX: appContentPane jadi jPanel3
        Adminpanel.jPanel3.removeAll();
        Adminpanel.jPanel3.add(newContent, BorderLayout.CENTER);
        Adminpanel.jPanel3.revalidate();
        Adminpanel.jPanel3.repaint();
    }
}
