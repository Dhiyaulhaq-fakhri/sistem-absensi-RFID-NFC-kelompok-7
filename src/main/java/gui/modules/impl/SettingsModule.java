/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.modules.impl;

import gui.modules.MasterDataModule;
import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;
import pallete.SlidingStatusToggle;

/**
 *
 * @author Lenovo
 */
public class SettingsModule implements MasterDataModule {

    // Global Preferences & Variabel State sesuai kode aslimu
    public static String statusAbsen;
    public static final Preferences prefs = Preferences.userNodeForPackage(SettingsModule.class);

    // Komponen UI setelah disederhanakan
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private pallete.SlidingStatusToggle slidingStatusToggle1;

    @Override
    public String getModuleName() {
        return "Pengaturan Umum";
    }

    /**
     * Sisi kiri (CRUD Form area) dikosongkan total agar fokus penuh ke panel
     * utama di sebelah kanan.
     */
    @Override
    public JPanel getCrudFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(33, 37, 41)); // Menjaga konsistensi warna sidebar sistem
        panel.setLayout(new BorderLayout());

        JLabel lblInfo = new JLabel("SETTINGS MODE", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInfo.setForeground(new Color(100, 110, 120));
        panel.add(lblInfo, BorderLayout.NORTH);

        return panel;
    }

    /**
     * Membangun komponen JTabbedPane, GridLayout, dan GroupLayout secara
     * programmatic persis seperti yang digenerate oleh NetBeans.
     */
    @Override
    public JPanel getTablePanel() {
        // Inisialisasi Komponen Utama
        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        slidingStatusToggle1 = new pallete.SlidingStatusToggle();
        jPanel4 = new JPanel();
        jPanel5 = new JPanel();
        jPanel6 = new JPanel();

        // Main Container langsung menampung Grid 2x2 tanpa JTabbedPane lagi
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(245, 245, 245));

        // Tata letak utama halaman (GridLayout 2x2) dengan margin luar 19px
        jPanel1.setBorder(BorderFactory.createEmptyBorder(19, 19, 19, 19));
        jPanel1.setLayout(new GridLayout(2, 2, 15, 15));

        // Setup Box Status Absensi (TitledBorder dengan LineBorder melengkung)
        jPanel3.setLayout(new GridBagLayout());
        jPanel3.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                "Status Absensi",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14)
        ));

        // Setup Custom Component Toggle Button
        slidingStatusToggle1.setPreferredSize(new Dimension(240, 50)); // Set ukuran tombol toggle
        slidingStatusToggle1.addActionListener(evt -> slidingStatusToggle1ActionPerformed(evt));

        // BIKIN ATURAN KUNCI TENGAH SECARA EKSPLISIT
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;         // Kolom ke-0
        gbc.gridy = 0;         // Baris ke-0
        gbc.anchor = GridBagConstraints.CENTER; // Kunci tepat di tengah
        gbc.fill = GridBagConstraints.NONE;     // Jangan biarkan tombol dipaksa melebar

        // Masukkan toggle ke dalam jPanel3. GridBagLayout otomatis menaruhnya di tengah-tengah!
        jPanel3.add(slidingStatusToggle1, gbc);

        // Memasukkan panel-panel ke dalam susunan Grid 2x2 utama
        jPanel1.add(jPanel3);
        jPanel1.add(jPanel4); // Placeholder kosong kanan atas
        jPanel1.add(jPanel5); // Placeholder kosong kiri bawah
        jPanel1.add(jPanel6); // Placeholder kosong kanan bawah

        // Tempelkan langsung Grid ke Container Utama
        mainContainer.add(jPanel1, BorderLayout.CENTER);

        // LOAD DATA PREFERENCE
        statusAbsen = prefs.get("LAST_STATUS", "Masuk");
        slidingStatusToggle1.setStatusByString(statusAbsen);

        return mainContainer;
    }

    /**
     * Logika Event Trigger saat Switch Toggle digeser oleh user.
     */
    private void slidingStatusToggle1ActionPerformed(java.awt.event.ActionEvent evt) {
        statusAbsen = slidingStatusToggle1.getStatusString();
        prefs.put("LAST_STATUS", statusAbsen);
        System.out.println("✓ Konfigurasi tersimpan lokal. Status Absen: " + statusAbsen);
    }

    // =========================================================================
    // SISA METHOD INTERFACE (Dikosongkan karena tidak menggunakan database)
    // =========================================================================
    @Override
    public void loadTableData() {
    }

    @Override
    public void save() {
    }

    @Override
    public void update() {
    }

    @Override
    public void delete() {
    }

    @Override
    public void refresh() {
    }
}
