package gui.modules.impl;

import gui.modules.MasterDataModule;
import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;
import java.util.Locale;
import pallete.SlidingStatusToggle;
import pallete.SlidingLanguageToggle;
import service.I18nServices;

/**
 *
 * @author Lenovo
 */
public class SettingsModule implements MasterDataModule {

    // Global Preferences & Variabel State
    public static String statusAbsen;
    public static final Preferences prefs = Preferences.userNodeForPackage(SettingsModule.class);

    // Komponen UI setelah disederhanakan
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JLabel lblInfo;
    private pallete.SlidingStatusToggle slidingStatusToggle1;
    private pallete.SlidingLanguageToggle slidingLanguageToggle1;

    @Override
    public String getModuleName() {
        return I18nServices.get("ui.settings.modulename");
    }

    @Override
    public JPanel getCrudFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(33, 37, 41)); 
        panel.setLayout(new BorderLayout());

        lblInfo = new JLabel(I18nServices.get("ui.settings.kioskmode"), SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInfo.setForeground(new Color(100, 110, 120));
        panel.add(lblInfo, BorderLayout.NORTH);

        return panel;
    }

    @Override
    public JPanel getTablePanel() {
        // Inisialisasi Komponen Utama
        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        jPanel5 = new JPanel();
        jPanel6 = new JPanel();

        slidingStatusToggle1 = new SlidingStatusToggle();
        slidingLanguageToggle1 = new SlidingLanguageToggle();
        
        // ===============================
        // Panel Bahasa
        // ===============================
        jPanel4.setLayout(new GridBagLayout());

        jPanel4.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                I18nServices.get("ui.settings.border.language"),
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14)
        ));

        slidingLanguageToggle1.setPreferredSize(new Dimension(320, 50));
        
        // Menghubungkan ke method action listener bahasa
        slidingLanguageToggle1.addActionListener(evt -> slidingLanguageToggle1ActionPerformed(evt));

        GridBagConstraints gbcLanguage = new GridBagConstraints();
        gbcLanguage.gridx = 0;
        gbcLanguage.gridy = 0;
        gbcLanguage.anchor = GridBagConstraints.CENTER;

        jPanel4.add(slidingLanguageToggle1, gbcLanguage);
        jPanel5 = new JPanel();
        jPanel6 = new JPanel();

        // Main Container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(245, 245, 245));

        jPanel1.setBorder(BorderFactory.createEmptyBorder(19, 19, 19, 19));
        jPanel1.setLayout(new GridLayout(2, 2, 15, 15));

        // Setup Box Status Absensi
        jPanel3.setLayout(new GridBagLayout());
        jPanel3.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                I18nServices.get("ui.settings.border.status"),
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14)
        ));

        slidingStatusToggle1.setPreferredSize(new Dimension(240, 50)); 
        slidingStatusToggle1.addActionListener(evt -> slidingStatusToggle1ActionPerformed(evt));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;         
        gbc.gridy = 0;         
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.fill = GridBagConstraints.NONE;     

        jPanel3.add(slidingStatusToggle1, gbc);

        jPanel1.add(jPanel3);
        jPanel1.add(jPanel4); 
        jPanel1.add(jPanel5); 
        jPanel1.add(jPanel6); 

        mainContainer.add(jPanel1, BorderLayout.CENTER);

        // LOAD DATA PREFERENCE
        statusAbsen = prefs.get("LAST_STATUS", "Masuk");
        slidingStatusToggle1.setStatusByString(statusAbsen);
        
        String langSaved = prefs.get("LAST_LANGUAGE", "id");
        if (slidingLanguageToggle1 != null) {
            int targetIndex = 0; // Default Indonesia (0)
            if (langSaved.equalsIgnoreCase("en")) {
                targetIndex = 1; // Inggris
            } else if (langSaved.equalsIgnoreCase("de")) {
                targetIndex = 2; // Jerman
            }
            slidingLanguageToggle1.setSelectedLanguageIndex(targetIndex);
        }

        return mainContainer;
    }

    private void slidingStatusToggle1ActionPerformed(java.awt.event.ActionEvent evt) {
        statusAbsen = slidingStatusToggle1.getStatusString();
        prefs.put("LAST_STATUS", statusAbsen);
        System.out.println("✓ Konfigurasi tersimpan lokal. Status Absen: " + statusAbsen);
    }

    private void slidingLanguageToggle1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (slidingLanguageToggle1 != null) {
            // Menggunakan nama method asli dari class kamu: getSelectedLanguageString()
            String selectedLang = slidingLanguageToggle1.getSelectedLanguageString(); 
            
            // Simpan bahasa terpilih secara permanen ke preference komputer
            prefs.put("LAST_LANGUAGE", selectedLang);
            
            // Pemicu perubahan bahasa global
            I18nServices.setLocale(new Locale(selectedLang));
            System.out.println("✓ Konfigurasi tersimpan lokal. Bahasa diubah ke: " + selectedLang);
            
            // Segarkan teks border dan komponen di modul ini secara real-time
            refreshModuleLabels();
        }
    }

    private void refreshModuleLabels() {
        if (lblInfo != null) {
            lblInfo.setText(I18nServices.get("ui.settings.kioskmode"));
        }
        if (jPanel4 != null && jPanel4.getBorder() instanceof javax.swing.border.TitledBorder) {
            ((javax.swing.border.TitledBorder) jPanel4.getBorder()).setTitle(I18nServices.get("ui.settings.border.language"));
        }
        if (jPanel3 != null && jPanel3.getBorder() instanceof javax.swing.border.TitledBorder) {
            ((javax.swing.border.TitledBorder) jPanel3.getBorder()).setTitle(I18nServices.get("ui.settings.border.status"));
        }
        
        if (jPanel1 != null) {
            jPanel1.revalidate();
            jPanel1.repaint();
        }
    }

    @Override public void loadTableData() {}
    @Override public void save() {}
    @Override public void update() {}
    @Override public void delete() {}
    @Override public void refresh() {}
}