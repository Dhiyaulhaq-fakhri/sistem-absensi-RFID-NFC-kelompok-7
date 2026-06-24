/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.modules.impl;

import gui.modules.MasterDataModule;
import javax.swing.*;
import java.awt.*;
import util.EncryptionUtils;
import util.SecurityUtils;
import service.DigitalClockService;
import service.pesertadidikservice;
import service.LogAbsensiService;
import service.SerialService;
import objects.pesertadidik;
import java.util.prefs.Preferences;

/**
 *
 * @author Lenovo
 */
public class AttendanceModule implements MasterDataModule {

    // Service yang dibutuhkan (Silakan sesuaikan dengan package kamu)
    private final service.pesertadidikservice pesertaService = new service.pesertadidikservice();
    private final service.LogAbsensiService logService = new service.LogAbsensiService();

    // UI Components untuk Layar Absensi (Ditaruh di getTablePanel)
    private JLabel lblJam;
    private JLabel lblStatusTap;
    private JLabel lblNamaSiswa;
    private JLabel lblIdSiswa;
    private JLabel lblKelasSiswa;
    private JLabel lblAvatar;

    private Thread clockThread;
    private Thread delayThread;

    @Override
    public String getModuleName() {
        return "Absensi Peserta Didik";
    }

    /**
     * Sesuai permintaan: Kolom Form CRUD ini dibuat kosong total. Hanya
     * diberikan warna background yang konsisten dengan sistem.
     */
    @Override
    public JPanel getCrudFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(33, 37, 41)); // Warna gelap mengikuti tema CRUD kamu

        // Opsional: Jika ingin benar-benar kosong, biarkan tanpa komponen.
        // Di bawah ini ditambahkan label samar hanya agar tidak terlalu hampa.
        panel.setLayout(new BorderLayout());
        JLabel lblInfo = new JLabel("MODE KIOSK AKTIF", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInfo.setForeground(new Color(100, 110, 120));
        panel.add(lblInfo, BorderLayout.NORTH);

        return panel;
    }

    /**
     * Berfungsi sebagai main container layar absensi utama (menggantikan
     * tabel/kartu).
     */
    @Override
    public JPanel getTablePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // ==========================================
        // 1. HEADER PANEL (Jam & Informasi)
        // ==========================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(226, 211, 188)); // Warna krem pastel dari kode awalmu
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("KIOSK ABSENSI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(104, 23, 39));

        lblJam = new JLabel("Memuat waktu...");
        lblJam.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblJam.setForeground(new Color(104, 23, 39));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblJam, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.PAGE_START);

        // ==========================================
        // 2. CENTER PANEL (Konten Pembaca RFID & Data Card)
        // ==========================================
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(240, 240, 240));

        // Kotak Kios Utama di Tengah Layar
        JPanel kioskCard = new JPanel();
        kioskCard.setLayout(new BoxLayout(kioskCard, BoxLayout.Y_AXIS));
        kioskCard.setBackground(Color.WHITE);
        kioskCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel lblPetunjuk = new JLabel("Silakan Tap Kartu Anda");
        lblPetunjuk.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPetunjuk.setAlignmentX(Component.CENTER_ALIGNMENT);
        kioskCard.add(lblPetunjuk);
        kioskCard.add(Box.createVerticalStrut(25));

        // Sub-Panel Data Siswa (Mirip jPanel5 di kode lamamu)
        JPanel dataSiswaPanel = new JPanel();
        dataSiswaPanel.setLayout(new BoxLayout(dataSiswaPanel, BoxLayout.X_AXIS));
        dataSiswaPanel.setBackground(new Color(52, 58, 64)); // Warna gelap elegan
        dataSiswaPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        dataSiswaPanel.setMaximumSize(new Dimension(450, 120));
        dataSiswaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Avatar Placeholder
        lblAvatar = new JLabel("No Card", SwingConstants.CENTER);
        lblAvatar.setPreferredSize(new Dimension(80, 80));
        lblAvatar.setMaximumSize(new Dimension(80, 80));
        lblAvatar.setBackground(new Color(108, 117, 125));
        lblAvatar.setForeground(Color.WHITE);
        lblAvatar.setOpaque(true);
        dataSiswaPanel.add(lblAvatar);
        dataSiswaPanel.add(Box.createVerticalStrut(15));

        // Teks Identitas Siswa
        JPanel infoSiswaTextPanel = new JPanel();
        infoSiswaTextPanel.setLayout(new GridLayout(3, 1, 0, 5));
        infoSiswaTextPanel.setBackground(new Color(52, 58, 64));
        infoSiswaTextPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        lblNamaSiswa = new JLabel("Nama Lengkap: -");
        lblNamaSiswa.setForeground(Color.WHITE);
        lblNamaSiswa.setFont(new Font("Segoe UI", Font.BOLD, 13));

        lblIdSiswa = new JLabel("ID Peserta: -");
        lblIdSiswa.setForeground(Color.WHITE);

        lblKelasSiswa = new JLabel("Kelas: -");
        lblKelasSiswa.setForeground(Color.WHITE);

        infoSiswaTextPanel.add(lblNamaSiswa);
        infoSiswaTextPanel.add(lblIdSiswa);
        infoSiswaTextPanel.add(lblKelasSiswa);
        dataSiswaPanel.add(infoSiswaTextPanel);

        kioskCard.add(dataSiswaPanel);
        kioskCard.add(Box.createVerticalStrut(20));

        // Label Status Tap (Berubah warna dinamis dinotifikasi)
        lblStatusTap = new JLabel("STANDBY", SwingConstants.CENTER);
        lblStatusTap.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStatusTap.setForeground(Color.WHITE);
        lblStatusTap.setBackground(new Color(104, 23, 39));
        lblStatusTap.setOpaque(true);
        lblStatusTap.setMaximumSize(new Dimension(450, 35));
        lblStatusTap.setPreferredSize(new Dimension(450, 35));
        lblStatusTap.setAlignmentX(Component.CENTER_ALIGNMENT);
        kioskCard.add(lblStatusTap);

        centerWrapper.add(kioskCard);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        // ==========================================
        // 3. RUN SERVICES (Clock & RFID)
        // ==========================================
        initClockEngine();
        setupAttendanceWorkflow();

        return mainPanel;
    }

    /**
     * Simulasi atau jembatan penerimaan data dari handler SerialService.
     * Tempelkan pembbacaan RFID hardware kamu ke sini.
     */
    private void setupAttendanceWorkflow() {
        // Mendaftarkan handler asinkron ke SerialService [11]
        SerialService.getInstance().addHandler(dataRfid -> {
            // 1. Ambil status absensi saat ini dari Preferences modul Settings
            java.util.prefs.Preferences settingsPrefs = java.util.prefs.Preferences.userNodeForPackage(gui.modules.impl.SettingsModule.class);
            String statusMode = settingsPrefs.get("LAST_STATUS", "Masuk");
            // 2. HASH: Mengamankan UID menggunakan SHA-256 [12, 13]
            String hashedUid = SecurityUtils.getHash(dataRfid, SecurityUtils.SHA_256);

            // 3. MATCH: Mencari data peserta didik (Menggunakan instance variabel 'pesertaService')
            pesertadidik p = pesertaService.cariByUidRfid(hashedUid);
            boolean isSuccess = (p != null);

            // 4. SAVE: Mencatat log absensi [6]
            logService.simpanLog(hashedUid, statusMode);

            // 4. NOTIFY: Update GUI secara aman (mencegah UI Freezing) [10]
            SwingUtilities.invokeLater(() -> {
                if (isSuccess) {
                    // Masukkan data dari objek 'pesertadidik' ke Label masing-masing
                    lblNamaSiswa.setText("Nama Lengkap: " + p.getNamaLengkap());
                    lblIdSiswa.setText("ID Peserta: " + EncryptionUtils.decrypt(p.getIdsiswa()));
                    lblKelasSiswa.setText("Kelas: " + p.getKelas());

                    // Ganti warna avatar jadi hijau tanda sukses
                    lblAvatar.setBackground(new Color(25, 135, 84));
                    lblAvatar.setText("OK");

                    // Update label status paling bawah dengan pesan sukses & warna hijau
                    lblStatusTap.setBackground(new Color(25, 135, 84));
                    updateLabelWithDelay(lblStatusTap, "ABSENSI (" + statusMode.toUpperCase() + ") DITERIMA");
                } else {
                    // Kasus jika kartu tidak ditemukan di database
                    lblAvatar.setBackground(new Color(220, 53, 69)); // Merah
                    lblAvatar.setText("ERR");
                    lblStatusTap.setBackground(new Color(220, 53, 69));
                    updateLabelWithDelay(lblStatusTap, "KARTU TIDAK TERDAFTAR!");
                }
            });
        });
    }

    private void updateLabelWithDelay(JLabel comp, String info) {
        comp.setText(info);

        if (delayThread != null && delayThread.isAlive()) {
            delayThread.interrupt();
        }

        delayThread = new Thread(() -> {
            comp.setText(info);
            try {
                Thread.sleep(3000); // Tampilkan pesan sukses/gagal selama 3 detik

                SwingUtilities.invokeLater(() -> {
                    // Kembalikan ke kondisi Standby awal
                    lblStatusTap.setText("STANDBY");
                    lblStatusTap.setBackground(new Color(104, 23, 39)); // Warna merah marun awal
                    lblNamaSiswa.setText("Nama Lengkap: -");
                    lblIdSiswa.setText("ID Peserta: -");
                    lblKelasSiswa.setText("Kelas: -");
                    lblAvatar.setBackground(new Color(108, 117, 125));
                    lblAvatar.setText("No Card");
                });
            } catch (InterruptedException e) {
                // Penanganan jika thread dihentikan paksa (Interrupted)
            }
        });

        delayThread.setName("delayThread");
        delayThread.setDaemon(true);
        delayThread.start();

    }

    /**
     * Jam digital lokal sederhana menggunakan Background Thread standard Java.
     */
    private void initClockEngine() {
        DigitalClockService service = new DigitalClockService(lblJam, "EEEE, d MMMM yyyy, HH:mm:ss");

        // 1. Ambil objek thread dari service
        clockThread = service.getThread();

        // 2. Beri nama secara mandiri untuk debugging/tracking
        clockThread.setName("Thread-Jam-Kiosk");

        // 3. Atur daemon secara mandiri sebelum start [Conversation History]
        clockThread.setDaemon(true);

        // 4. Jalankan thread (Fase New -> Runnable) [3]
        clockThread.start();

        System.out.println("Memulai: " + clockThread.getName() + " (Daemon: " + clockThread.isDaemon() + ")");
    }

    /**
     * Memperbarui status teks di layar bawah dan otomatis meresetnya kembali ke
     * status default.
     */

    // =========================================================================
    // SISA METHOD INTERFACE (Dikosongkan / Diabaikan karena ini modul Kiosk)
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
