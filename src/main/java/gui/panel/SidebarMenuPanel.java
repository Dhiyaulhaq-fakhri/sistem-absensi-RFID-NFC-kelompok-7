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
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Lenovo
 */

public class SidebarMenuPanel extends JPanel {

    private JButton activeButton = null;
    private final Color HEADER_BG = new Color(51, 65, 85);       // Warna tombol utama (Kategori)
    private final Color SUBMENU_BG = new Color(15, 23, 42);      // Warna dasar submenu di dalam accordion
    private final Color BUTTON_HOVER = new Color(37, 99, 235);   // Warna saat mouse mendekat
    private final Color BUTTON_ACTIVE = new Color(59, 130, 246); // Warna menu aktif
    private final Color TEXT_COLOR = Color.WHITE;

    public SidebarMenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(33, 37, 41));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        this.add(createAccordion(
                "Data Master",
                new String[]{"Guru", "Peserta Didik"}
        ));
        add(Box.createVerticalStrut(5));

        // 2. KELOMPOK ATTENDANCE (Halaman absensi / KiosK)
        this.add(createAccordion(
                "Attendance",
                new String[]{"KiosK", "Riwayat", "Analisis"}
        ));
        add(Box.createVerticalStrut(5));

        // 3. KELOMPOK SETTINGS
        this.add(createAccordion(
                "Settings",
                new String[]{"General", "Security"}
        ));
        add(Box.createVerticalStrut(5));

        this.add(Box.createVerticalGlue());
        
        // Pemicu Auto-Click halaman pertama saat aplikasi dibuka (Opsional)
        // triggerDefaultClick();
    }


    private JPanel createAccordion(String title, String[] menus) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(33, 37, 41));

        // HEADER BUTTON (Tombol Kategori Utama)
        JButton header = new JButton(title);
        header.setFocusPainted(false);
        header.setBackground(HEADER_BG);
        header.setForeground(TEXT_COLOR);
        header.setBorder(new EmptyBorder(15, 15, 15, 15));
        header.setHorizontalAlignment(SwingConstants.LEFT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        header.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // BODY PANEL (Tempat Submenu berada)
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(new Color(30, 41, 59));
        body.setVisible(false);

        for (String menu : menus) {
            JButton btn = new JButton(menu);
            btn.setFocusPainted(false);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setBackground(SUBMENU_BG);
            btn.setForeground(TEXT_COLOR);
            btn.setBorder(new EmptyBorder(10, 20, 10, 10));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Efek Hover
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (btn != activeButton) btn.setBackground(BUTTON_HOVER);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (btn != activeButton) btn.setBackground(SUBMENU_BG);
                }
            });

        // Click action
        btn.addActionListener(e -> {
                switchModule(menu, btn);
            });

            body.add(btn);
        }

        // Aksi Klik Header untuk Buka-Tutup Accordion
        header.addActionListener(e -> {
            body.setVisible(!body.isVisible());
            container.revalidate();
            container.repaint();
        });

        container.add(header);
        container.add(body);

        return container;
    }

    private void switchModule(String moduleName, JButton btn) {
        System.out.println("====== 🟢 TRACE: switchModule dipicu untuk " + moduleName + " ======");
        
        // Reset warna tombol aktif lama ke warna default submenu
        if (activeButton != null) {
            activeButton.setBackground(SUBMENU_BG);
        }
        
        // Set tombol baru sebagai tombol aktif
        activeButton = btn;
        btn.setBackground(BUTTON_ACTIVE);
        
        // Panggil Module dari Factory
        MasterDataModule module = ModuleFactory.getModule(moduleName);
        
        if (module != null) {
            System.out.println("   -> Modul berhasil diambil dari Factory");
            
            // Perbarui data form di SidebarCrudPanel (Bawah)
            if (SidebarCrudPanel.getInstance() != null) {
                SidebarCrudPanel.getInstance().setModule(module);
                System.out.println("   -> setModule() ke SidebarCrudPanel sukses");
            } else {
                System.out.println("   -> ❌ ERROR: SidebarCrudPanel.getInstance() bernilai NULL!");
            } 
            
            // Perbarui tabel data di panel tengah (jPanel3)
            updateContentPanel(module.getTablePanel());
        } else {
            System.out.println("   -> ❌ ERROR: Modul " + moduleName + " tidak ditemukan di ModuleFactory!");
        }
    }
    
    private void updateContentPanel(JPanel newContent) {
        if (Adminpanel.jPanel3 == null) return;

        Adminpanel.jPanel3.setLayout(new BorderLayout());
        Adminpanel.jPanel3.removeAll();
        Adminpanel.jPanel3.add(newContent, BorderLayout.CENTER);

        // Menjaga kestabilan layout dinamis
        Adminpanel.jPanel3.setSize(Adminpanel.jPanel3.getParent().getSize());
        
        Component parent = Adminpanel.jPanel3.getParent();
        while (parent != null) {
            parent.revalidate();
            parent.repaint();
            if (parent instanceof JFrame) break;
            parent = parent.getParent();
        }

        Adminpanel.jPanel3.revalidate();
        Adminpanel.jPanel3.repaint();
    }
}