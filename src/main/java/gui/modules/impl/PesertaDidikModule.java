/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.modules.impl;

import gui.modules.MasterDataModule;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import service.I18nServices;
import util.EncryptionUtils;
import util.SecurityUtils;
import service.pesertadidikservice;

/**
 *
 * @author Lenovo
 */
public class PesertaDidikModule implements MasterDataModule, I18nServices.I18nChangeListener {

    private final service.pesertadidikservice pesertaService = new service.pesertadidikservice();

    // List pembantu untuk melacak semua komponen (Label & Button) beserta Key I18n-nya
    private final java.util.List<I18nCompRef> localizedComponents = new ArrayList<>();

    private static class I18nCompRef {

        JComponent component;
        String i18nKey;

        I18nCompRef(JComponent component, String i18nKey) {
            this.component = component;
            this.i18nKey = i18nKey;
        }
    }

    private void registerAndSetText(JComponent comp, String key) {
        // Daftarkan ke list biar dilacak
        localizedComponents.add(new I18nCompRef(comp, key));

        // Set teksnya langsung saat pertama kali dibuat
        if (comp instanceof JLabel) {
            ((JLabel) comp).setText(I18nServices.get(key));
        } else if (comp instanceof JButton) {
            ((JButton) comp).setText(I18nServices.get(key));
        }
    }

    // CRUD Components
    private JTextField txtUID;
    private JTextField txtIdPeserta;
    private JTextField txtNama;
    private JComboBox<String> cboKelas;
    private JButton btnTambah;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JTextField txtSearch;

    // 2. NAIKKAN LABEL KE SINI AGAR BISA DI-UPDATE OLEH METHOD onLanguageChanged()
    private JLabel titleLabel;
    private JLabel lblUID;
    private JLabel lblIdPeserta;
    private JLabel lblNama;
    private JLabel lblKelas;
    private JLabel lblCari;
    private JLabel headerLabel;

    // Table
    private JPanel tablePanel;
    private JPanel cardsContainerPanel;

    @Override
    public String getModuleName() {
        return "Peserta Didik";
    }

    @Override
    public JPanel getCrudFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(33, 37, 41));
        panel.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel();
        registerAndSetText(titleLabel, "ui.pesertadidik.title");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Pastikan rata kiri
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        // Form Fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS)); // Ganti ke BoxLayout
        formPanel.setBackground(new Color(33, 37, 41));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- PERBAIKAN DIMENSION ---
        Dimension maxFieldSize = new Dimension(Integer.MAX_VALUE, 30);
        Dimension lockedFieldSize = new Dimension(220, 30); // Kunci tinggi absolut di 30px

        // UID
        JLabel lblUID = new JLabel();
        registerAndSetText(lblUID, "ui.pesertadidik.uid");
        lblUID.setForeground(Color.WHITE);
        lblUID.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUID.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtUID = new JTextField();
        txtUID.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtUID.setMaximumSize(maxFieldSize);
        txtUID.setPreferredSize(lockedFieldSize); // Kunci ukuran ideal
        txtUID.setMinimumSize(lockedFieldSize);   // Cegah menciut jadi 0px
        txtUID.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(lblUID);
        formPanel.add(Box.createVerticalStrut(4)); // Jarak label ke textfield
        formPanel.add(txtUID);
        formPanel.add(Box.createVerticalStrut(10)); // Jarak antar grup input

        // ID Peserta
        JLabel lblIdPeserta = new JLabel();
        registerAndSetText(lblIdPeserta, "ui.pesertadidik.id");
        lblIdPeserta.setForeground(Color.WHITE);
        lblIdPeserta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblIdPeserta.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtIdPeserta = new JTextField();
        txtIdPeserta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtIdPeserta.setMaximumSize(maxFieldSize);
        txtIdPeserta.setPreferredSize(lockedFieldSize); // Tambahkan ini
        txtIdPeserta.setMinimumSize(lockedFieldSize);   // Tambahkan ini
        txtIdPeserta.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(lblIdPeserta);
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtIdPeserta);
        formPanel.add(Box.createVerticalStrut(10));

        // Nama
        JLabel lblNama = new JLabel();
        registerAndSetText(lblNama, "ui.pesertadidik.name");
        lblNama.setForeground(Color.WHITE);
        lblNama.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNama.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtNama = new JTextField();
        txtNama.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNama.setMaximumSize(maxFieldSize);
        txtNama.setPreferredSize(lockedFieldSize); // Tambahkan ini
        txtNama.setMinimumSize(lockedFieldSize);   // Tambahkan ini
        txtNama.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(lblNama);
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtNama);
        formPanel.add(Box.createVerticalStrut(10));

        // Kelas
        JLabel lblKelas = new JLabel();
        registerAndSetText(lblKelas, "ui.pesertadidik.grade");
        lblKelas.setForeground(Color.WHITE);
        lblKelas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblKelas.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Buat daftar pilihan kelas yang tersedia
        String[] daftarKelas = {"", "X-I", "X-II", "XI-I", "XI-II", "XII-I", "XII-II"};
        cboKelas = new JComboBox<>(daftarKelas);
        cboKelas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboKelas.setMaximumSize(maxFieldSize);
        cboKelas.setPreferredSize(lockedFieldSize); // Tambahkan ini
        cboKelas.setMinimumSize(lockedFieldSize);   // Tambahkan ini
        cboKelas.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(lblKelas);
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(cboKelas); // Masukkan combo box ke panel
        // PERBAIKAN: Masukkan formPanel ke panel utama di sini!
        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(15)); // Beri jarak ke tombol di bawahnya

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBackground(new Color(33, 37, 41));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70)); // Kunci tinggi panel tombol
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnTambah = new JButton();
        registerAndSetText(btnTambah, "ui.pesertadidik.add");
        btnTambah.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnTambah.addActionListener(e -> save());
        buttonPanel.add(btnTambah);

        btnDelete = new JButton();
        registerAndSetText(btnDelete, "ui.pesertadidik.delete");
        btnDelete.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnDelete.addActionListener(e -> delete());
        buttonPanel.add(btnDelete);

        btnUpdate = new JButton();
        registerAndSetText(btnUpdate, "ui.pesertadidik.update");
        btnUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnUpdate.addActionListener(e -> update());
        buttonPanel.add(btnUpdate);

        btnRefresh = new JButton();
        registerAndSetText(btnRefresh, "ui.pesertadidik.refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefresh.addActionListener(e -> refresh());
        buttonPanel.add(btnRefresh);

        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(10));

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS)); // Ubah ke vertikal juga agar konsisten
        searchPanel.setBackground(new Color(33, 37, 41));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblCari = new JLabel();
        registerAndSetText(lblCari, "ui.pesertadidik.find");
        lblCari.setForeground(Color.WHITE);
        lblCari.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCari.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.add(lblCari);
        searchPanel.add(Box.createVerticalStrut(4));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setMaximumSize(maxFieldSize);
        txtSearch.setPreferredSize(lockedFieldSize); // Tambahkan ini
        txtSearch.setMinimumSize(lockedFieldSize);   // Tambahkan ini
        txtSearch.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadTableData();
            }
        });
        searchPanel.add(txtSearch);

        panel.add(searchPanel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    @Override
    public JPanel getTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Header Judul Atas
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel();
        registerAndSetText(headerLabel, "ui.pesertadidik.panelheader");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        tablePanel.add(headerPanel, BorderLayout.PAGE_START);

        // Panel khusus penampung kartu-kartu siswa (Susunan Grid: baris dinamis, 3 kolom)
        cardsContainerPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        cardsContainerPanel.setBackground(new Color(245, 245, 245)); // Beri warna background abu soft
        cardsContainerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Bungkus kartu di dalam JScrollPane agar kalau siswanya banyak bisa di-scroll ke bawah
        JScrollPane scrollPane = new JScrollPane(cardsContainerPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Agar scroll-nya smooth

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        loadTableData(); // Panggil pengisi data kartu
        return tablePanel;
    }

    @Override
    public void loadTableData() {
        if (cardsContainerPanel == null) {
            return;
        }

        // 1. Bersihkan kontainer dan atur layout Grid (2 Kolom)
        cardsContainerPanel.removeAll();
        cardsContainerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;

        cardsContainerPanel.setBackground(new Color(245, 245, 245));

        // 2. Ambil kata kunci dari kolom pencarian (jika ada)
        String keyword = txtSearch.getText().trim();
        java.util.List<objects.pesertadidik> daftarSiswa;

        // 3. Ambil data dari database
        if (keyword.isEmpty()) {
            daftarSiswa = pesertaService.ambilSemuaPeserta();
        } else {
            daftarSiswa = pesertaService.cariByNama(keyword);
        }

        // 4. Render Data
        if (daftarSiswa.isEmpty()) {
            cardsContainerPanel.setLayout(new BorderLayout()); // Ubah layout sementara
            JLabel lblKosong = new JLabel(I18nServices.get("ui.pesertadidik.card.empty"), SwingConstants.CENTER);
            lblKosong.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 14));
            lblKosong.setForeground(Color.GRAY);
            cardsContainerPanel.add(lblKosong, BorderLayout.CENTER);
        } else {
            int col = 0;
            int row = 0;

            // LOOP DARI DATABASE
            for (objects.pesertadidik siswa : daftarSiswa) {
                String uid = siswa.getUidRfid();
                String nama = siswa.getNamaLengkap();
                String kelas = siswa.getKelas();
                String idsiswa = EncryptionUtils.decrypt(siswa.getIdsiswa());

                // Bikin card utama
                JPanel card = new JPanel();

                card.setPreferredSize(new Dimension(300, 160));
                card.setMinimumSize(new Dimension(300, 160));
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                card.setLayout(new BorderLayout());

                // PANEL DATA (Bagian Teks)
                JPanel dataPanel = new JPanel();
                dataPanel.setBackground(Color.WHITE);
                dataPanel.setLayout(new GridLayout(4, 1, 0, 5));
                dataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel lbNama = new JLabel(String.format(I18nServices.get("ui.pesertadidik.card.nama"), (nama != null ? nama : "-")));
                lbNama.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

                JLabel lbUID = new JLabel(String.format(I18nServices.get("ui.pesertadidik.card.uid"), (uid != null ? uid : "-")));
                JLabel lbIdPeserta = new JLabel(String.format(I18nServices.get("ui.pesertadidik.card.id"), (idsiswa != null ? idsiswa : "-")));
                JLabel lbKelas = new JLabel(String.format(I18nServices.get("ui.pesertadidik.card.kelas"), (kelas != null ? kelas : "-")));

                dataPanel.add(lbNama);
                dataPanel.add(lbUID);
                dataPanel.add(lbIdPeserta);
                dataPanel.add(lbKelas);

                // PANEL TOMBOL
                JPanel actionPanel = new JPanel();
                actionPanel.setBackground(Color.WHITE);
                actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                JButton btnEdit = new JButton(I18nServices.get("ui.pesertadidik.update"));
                JButton btnDelete = new JButton(I18nServices.get("ui.pesertadidik.delete"));

                btnEdit.setBackground(new Color(255, 153, 0));
                btnEdit.setForeground(Color.WHITE);
                btnDelete.setBackground(new Color(220, 53, 69));
                btnDelete.setForeground(Color.WHITE);

                // ===== EVENT EDIT =====
                btnEdit.addActionListener(e -> {
                    // PENTING: Ganti "txtUid", "txtIdSiswa" dengan nama variabel 
                    // textfield input form yang ada di dalam PesertaDidikModule kamu!

                    txtUID.setText(uid);
                    txtIdPeserta.setText(idsiswa);
                    txtIdPeserta.setEditable(false);
                    txtNama.setText(nama);
                    cboKelas.setSelectedItem(kelas);

                    btnUpdate.setEnabled(true);
                    btnTambah.setEnabled(false);

                    //logging
                    System.out.println("Tombol Edit ditekan untuk: " + nama);
                });

                // ===== EVENT DELETE =====
                btnDelete.addActionListener(e -> {
                    // Ambil template kalimat, lalu isi %s dengan variabel nama
                    String msgTemplate = I18nServices.get("ui.pesertadidik.alert.delete.confirm");
                    String message = String.format(msgTemplate, nama);

                    int confirm = JOptionPane.showConfirmDialog(
                            cardsContainerPanel,
                            message,
                            I18nServices.get("ui.pesertadidik.alert.title.confirm"),
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        pesertaService.hapusPesertaDidik(siswa.getIdsiswa());
                        loadTableData(); // Refresh UI Module dengan memanggil dirinya sendiri
                        String successTemplate = I18nServices.get("ui.pesertadidik.alert.delete.success");
                        JOptionPane.showMessageDialog(cardsContainerPanel, String.format(successTemplate, nama));
                    }
                });

                actionPanel.add(btnEdit);
                actionPanel.add(btnDelete);

                // Masukkan panel data dan tombol ke dalam card
                card.add(dataPanel, BorderLayout.CENTER);
                card.add(actionPanel, BorderLayout.SOUTH);

                // Masukkan card ke penampung utama (cardsContainerPanel)
                gbc.gridx = col;
                gbc.gridy = row;

                cardsContainerPanel.add(card, gbc);

                col++;

                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        }

        // 5. Refresh Layar
        cardsContainerPanel.revalidate();
        cardsContainerPanel.repaint();
    }

    @Override
    public void save() {
        String uid = txtUID.getText();
        String idPeserta = txtIdPeserta.getText();
        String nama = txtNama.getText();
        String kelas = cboKelas.getSelectedItem() != null ? cboKelas.getSelectedItem().toString() : "";

        if (uid.isEmpty() || idPeserta.isEmpty()
                || nama.isEmpty() || kelas.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    I18nServices.get("ui.pesertadidik.alert.validation.empty"),
                    I18nServices.get("ui.alert.title.warning"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        objects.pesertadidik peserta = new objects.pesertadidik();

        // HASH UID RFID
        peserta.setUidRfid(
                SecurityUtils.getHash(uid, SecurityUtils.SHA_256)
        );

        // ENKRIPSI ID PESERTA
        peserta.setIdsiswa(
                EncryptionUtils.encrypt(idPeserta)
        );

        // DATA BIASA
        peserta.setNamaLengkap(nama);
        peserta.setKelas(kelas);

        pesertaService.tambahPesertaDidik(peserta);

        JOptionPane.showMessageDialog(
                null,
                I18nServices.get("ui.pesertadidik.alert.save.success"),
                I18nServices.get("ui.pesertadidik.alert.title.success"),
                JOptionPane.INFORMATION_MESSAGE
        );

        refresh();
    }

    @Override
    public void update() {
        String uid = txtUID.getText();
        String idPeserta = txtIdPeserta.getText();

        txtUID.setEditable(false);
        txtIdPeserta.setEditable(false);

        String nama = txtNama.getText();
        String kelas = cboKelas.getSelectedItem() != null ? cboKelas.getSelectedItem().toString() : "";

        // 1. Validasi: Pastikan data tidak kosong saat di-update
        if (uid.isEmpty() || idPeserta.isEmpty() || nama.isEmpty() || kelas.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    I18nServices.get("ui.pesertadidik.alert.validation.empty"),
                    I18nServices.get("ui.alert.title.warning"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 2. Bungkus data ke dalam objek model pesertadidik
        objects.pesertadidik peserta = new objects.pesertadidik();

        // HASH UID RFID (Sama seperti logika save)
        peserta.setUidRfid(
                SecurityUtils.getHash(uid, SecurityUtils.SHA_256)
        );

        // ENKRIPSI ID PESERTA (Digunakan sebagai key filter pencarian di MongoDB/Bson)
        peserta.setIdsiswa(
                EncryptionUtils.encrypt(idPeserta)
        );

        // DATA BIASA BARU
        peserta.setNamaLengkap(nama);
        peserta.setKelas(kelas);

        try {
            // 3. Panggil service untuk mengeksekusi update
            pesertaService.updatePesertaDidik(peserta);

            JOptionPane.showMessageDialog(
                    null,
                    I18nServices.get("ui.pesertadidik.alert.update.success"),
                    I18nServices.get("ui.pesertadidik.alert.title.success"),
                    JOptionPane.INFORMATION_MESSAGE
            );

            // 4. Jalankan refresh untuk memperbarui grid kartu/tabel di layar tengah
            refresh();

        } catch (Exception e) {
            String errorMsg = String.format(I18nServices.get("ui.pesertadidik.alert.error.prefix"), e.getMessage());
            JOptionPane.showMessageDialog(
                    null,
                    errorMsg,
                    I18nServices.get("ui.pesertadidik.alert.title.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            // logging
            System.err.println("✗ Error saat update UI: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        String idPeserta = txtIdPeserta.getText().trim();

        if (idPeserta.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    I18nServices.get("ui.pesertadidik.alert.validation.delete.empty"),
                    I18nServices.get("ui.alert.title.warning"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                I18nServices.get("ui.pesertadidik.card.alert.delete.confirm"),
                I18nServices.get("ui.pesertadidik.alert.title.confirm"),
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Enkripsi dulu idPeserta dari textfield sebelum dikirim ke service (karena database pakai ID terenkripsi)
                String encryptedId = EncryptionUtils.encrypt(idPeserta);

                // Eksekusi hapus ke database
                pesertaService.hapusPesertaDidik(encryptedId);

                JOptionPane.showMessageDialog(
                        null,
                        I18nServices.get("ui.pesertadidik.card.alert.delete.success"),
                        I18nServices.get("ui.pesertadidik.alert.title.success"),
                        JOptionPane.INFORMATION_MESSAGE);
                refresh(); // Bersihkan form dan load ulang tabel
            } catch (Exception e) {
                // 4. Alert Error (Menggunakan String.format untuk menggabungkan pesan error sistem)
                String errorMsg = String.format(I18nServices.get("ui.pesertadidik.alert.error.prefix"), e.getMessage());
                JOptionPane.showMessageDialog(
                        null,
                        errorMsg,
                        I18nServices.get("ui.pesertadidik.alert.title.error"),
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    @Override
    public void refresh() {
        txtUID.setText("");
        txtIdPeserta.setText("");
        txtNama.setText("");
        cboKelas.setSelectedIndex(0);
        txtSearch.setText("");
        txtUID.setEditable(true);
        txtIdPeserta.setEditable(true);
        btnUpdate.setEnabled(false);
        btnTambah.setEnabled(true);
        loadTableData();
    }

    @Override
    public void onLanguageChanged() {
        SwingUtilities.invokeLater(() -> {
            // 1. Looping otomatis update semua Label dan Button yang terdaftar
            for (I18nCompRef ref : localizedComponents) {
                if (ref.component instanceof JLabel) {
                    ((JLabel) ref.component).setText(I18nServices.get(ref.i18nKey));
                } else if (ref.component instanceof JButton) {
                    ((JButton) ref.component).setText(I18nServices.get(ref.i18nKey));
                }
            }

            // 2. Khusus untuk kartu mahasiswa di dalam grid, kita render ulang datanya
            loadTableData();

            // 3. Refresh visual
            if (cardsContainerPanel != null) {
                cardsContainerPanel.revalidate();
                cardsContainerPanel.repaint();
            }
        });
    }
}
