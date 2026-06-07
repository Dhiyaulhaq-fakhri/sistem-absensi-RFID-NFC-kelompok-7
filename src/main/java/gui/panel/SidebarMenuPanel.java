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
        JButton defaultBtn = null;

        for (String menu : menus) {
            JButton btn = createMenuButton(menu);
            btn.setPreferredSize(new Dimension(100, 40));
            add(btn);
            System.out.println("✅ Button added: " + menu);
            
            if (menu.equals("Peserta Didik")) {
                defaultBtn = btn;
            }
        }
        
        if (defaultBtn != null) {
            final JButton btnToClick = defaultBtn;
            // Gunakan invokeLater agar dipanggil setelah seluruh Adminpanel selesai loading
            SwingUtilities.invokeLater(() -> {
                btnToClick.doClick(); 
            });
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
        System.out.println("====== 🟢 TRACE: switchModule dipicu untuk " + moduleName + " ======");
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
            System.out.println("   -> Modul berhasil diambil dari Factory");
            // Update CRUD panel
            // Cek apakah Instance CRUD panel aman
            if(SidebarCrudPanel.getInstance() != null) {
                SidebarCrudPanel.getInstance().setModule(module);
                System.out.println("   -> setModule() ke SidebarCrudPanel sukses");
            } else {
                System.out.println("   -> ❌ ERROR: SidebarCrudPanel.getInstance() bernilai NULL!");
            } 
            
            // Update content panel
            updateContentPanel(module.getTablePanel());
        }
        
        else {
            System.out.println("   -> ❌ ERROR: Modul " + moduleName + " tidak ditemukan di ModuleFactory!");
        }
    }
    
    private void updateContentPanel(JPanel newContent) {
        if (Adminpanel.jPanel3 == null) {
            System.out.println("   -> ❌ ERROR: Adminpanel.jPanel3 masih NULL saat mau diisi!");
            return;
        }
        
        
        System.out.println("   -> Memulai proses inject panel ke jPanel3...");
        // ← FIX: appContentPane jadi jPanel3
        
        Adminpanel.jPanel3.setLayout(new BorderLayout());
        Adminpanel.jPanel3.removeAll();
        Adminpanel.jPanel3.add(newContent, BorderLayout.CENTER);
        // testing
        // Adminpanel.jPanel3.add(new JButton("TES: JIKAP PANEL INI MUNCUL, ARTINYA JPANEL3 AMAN"), BorderLayout.CENTER);
        
        Adminpanel.jPanel3.revalidate();
        Adminpanel.jPanel3.repaint();
        System.out.println("====== 🏁 TRACE: Selesai Render Modul ======");
    }
}
