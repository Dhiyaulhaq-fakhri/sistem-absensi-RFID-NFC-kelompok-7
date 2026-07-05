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
import service.I18nServices; 
import java.util.ArrayList;

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
    
    // List pembantu untuk mencatat komponen di Sidebar
    private final java.util.List<I18nCompRef> localizedComponents = new ArrayList<>();
    
    private static class I18nCompRef {
        JButton button;
        String i18nKey;
        boolean isHeader; // Untuk membedakan header accordion dan item biasa jika perlu

        I18nCompRef(JButton button, String i18nKey, boolean isHeader) {
            this.button = button;
            this.i18nKey = i18nKey;
            this.isHeader = isHeader;
        }
    }

    public SidebarMenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(33, 37, 41));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        this.add(createAccordion(
                "ui.sidebarpanel.master",
                new String[]{"ui.sidebarpanel.master.teacher", "ui.sidebarpanel.master.student"}
        ));
        add(Box.createVerticalStrut(5));

        // 2. KELOMPOK ATTENDANCE (Halaman absensi / KiosK)
        this.add(createAccordion(
                "ui.sidebarpanel.attendance",
                new String[]{"ui.sidebarpanel.attendance.kiosk", "ui.sidebarpanel.attendance.riwayat", "ui.sidebarpanel.attendance.analisis"}
        ));
        add(Box.createVerticalStrut(5));

        // 3. KELOMPOK SETTINGS
        this.add(createAccordion(
                "ui.sidebarpanel.settings",
                new String[]{"ui.sidebarpanel.settings.general", "ui.sidebarpanel.settings.security"}
        ));
        add(Box.createVerticalStrut(5));

        this.add(Box.createVerticalGlue());
        
        // Pemicu Auto-Click halaman pertama saat aplikasi dibuka (Opsional)
        // triggerDefaultClick();
    }


    private JPanel createAccordion(String headerKey, String[] menuKeys) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(33, 37, 41));
        
        // HEADER BUTTON (Daftarkan ke I18n)
        JButton header = new JButton(I18nServices.get(headerKey));
        localizedComponents.add(new I18nCompRef(header, headerKey, true));

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

        for (String menuKey : menuKeys) {
            // Ambil teks terjemahan untuk nama tombol fisik
            JButton btn = new JButton(I18nServices.get(menuKey));
            localizedComponents.add(new I18nCompRef(btn, menuKey, false));
            
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
                switchModule(menuKey, btn);
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

    private void switchModule(String moduleKey, JButton btn) {
        System.out.println("====== 🟢 TRACE: switchModule dipicu untuk " + moduleKey + " ======");
        
        // Reset warna tombol aktif lama ke warna default submenu
        if (activeButton != null) {
            activeButton.setBackground(SUBMENU_BG);
        }
        
        // Set tombol baru sebagai tombol aktif
        activeButton = btn;
        btn.setBackground(BUTTON_ACTIVE);
        
        // Memetakan i18n key ke string nama modul riil di Factory
        String targetModule = "";
        if (moduleKey.equals("ui.sidebarpanel.master.teacher")) targetModule = "Guru";
        else if (moduleKey.equals("ui.sidebarpanel.master.student")) targetModule = "Peserta Didik";
        else if (moduleKey.equals("ui.sidebarpanel.attendance.kiosk")) targetModule = "KiosK";
        else if (moduleKey.equals("ui.sidebarpanel.attendance.riwayat")) targetModule = "Riwayat";
        else if (moduleKey.equals("ui.sidebarpanel.attendance.analisis")) targetModule = "Analisis";
        else if (moduleKey.equals("ui.sidebarpanel.settings.general")) targetModule = "General";
        else if (moduleKey.equals("ui.sidebarpanel.settings.security")) targetModule = "Security";
        
        // Panggil Module dari Factory
        MasterDataModule module = ModuleFactory.getModule(targetModule);
        
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
            System.out.println("   -> ❌ ERROR: Modul " + moduleKey + " tidak ditemukan di ModuleFactory!");
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
    
    // Panggil method ini saat ada trigger perubahan bahasa global di aplikasi Anda
    public void onLanguageChanged() {
        SwingUtilities.invokeLater(() -> {
            // Jalankan perulangan otomatis update text tombol sidebar terdaftar
            for (I18nCompRef ref : localizedComponents) {
                ref.button.setText(I18nServices.get(ref.i18nKey));
            }
            
            // Refresh Visual Layout Sidebar
            this.revalidate();
            this.repaint();
        });
    }
}