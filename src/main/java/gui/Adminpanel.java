/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import objects.pesertadidik;
import service.pesertadidikservice;

/**
 *
 * @author Lenovo
 */
public class Adminpanel extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Adminpanel.class.getName());

    private pesertadidikservice service;

    /**
     * Creates new form Adminpanel
     */
    public Adminpanel() {
        initComponents();
        setLocationRelativeTo(null);

        makeButtonPill(edit_jadwal);
        makeButtonPill(Rekapbtn);
        makeButtonPill(btntambahsiswa);
        makeButtonPill(btnupdatesiswa);
        makeButtonPill(btnrefreshdata);
        makeButtonPill(jButton1);

        // ... kode styling yang sudah ada ...
        service = new pesertadidikservice();
        tampilCard("");  // Load data pertama kali

        // ===== TAMBAH EVENT LISTENERS =====
        btntambahsiswa.addActionListener(e -> btnTambahSiswaActionPerformed());
        btnupdatesiswa.addActionListener(e -> btnUpdateSiswaActionPerformed());
        btnrefreshdata.addActionListener(e -> btnRefreshDataActionPerformed());
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tampilCard(jTextField5.getText());
            }
        });
    }

    // ===== EVENT BUTTON TAMBAH =====
    private void btnTambahSiswaActionPerformed() {
        if (validateInput()) {
            pesertadidik siswa = new pesertadidik();
            siswa.setUidRfid(jTextField1.getText());
            siswa.setIdsiswa(jTextField2.getText());
            siswa.setNamaLengkap(jTextField3.getText());
            siswa.setKelas(jTextField4.getText());

            service.tambahPesertaDidik(siswa);
            tampilCard("");
            resetForm();
            JOptionPane.showMessageDialog(this, "Data berhasil ditambah!");
        }
    }

    // ===== EVENT BUTTON UPDATE =====
    private void btnUpdateSiswaActionPerformed() {
        if (validateInput()) {
            pesertadidik siswa = new pesertadidik();
            siswa.setUidRfid(jTextField1.getText());
            siswa.setIdsiswa(jTextField2.getText());
            siswa.setNamaLengkap(jTextField3.getText());
            siswa.setKelas(jTextField4.getText());

            service.updatePesertaDidik(siswa);
            tampilCard("");
            resetForm();
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
        }
    }

    // ===== EVENT BUTTON REFRESH =====
    private void btnRefreshDataActionPerformed() {
        resetForm();
        tampilCard("");
    }

// ===== VALIDASI INPUT =====
    private boolean validateInput() {
        if (jTextField1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "UID tidak boleh kosong!");
            return false;
        }
        if (jTextField2.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Siswa tidak boleh kosong!");
            return false;
        }
        if (jTextField3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!");
            return false;
        }
        return true;
    }

    // ===== RESET FORM =====
    private void resetForm() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        btnupdatesiswa.setEnabled(false);
        btntambahsiswa.setEnabled(true);
        jTextField1.requestFocus();
    }

// ===== TAMPILKAN SEMUA DATA (HELPER) =====
    public void tampilkanSemuaData(String key) {
        tampilCard(key);
    }

    private void tampilCard(String key) {
        listdatapanel.removeAll();
        listdatapanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        listdatapanel.setBackground(Color.WHITE);

        // AMBIL DATA DARI DATABASE
        List<pesertadidik> daftarSiswa;
        if (key.isEmpty()) {
            daftarSiswa = service.ambilSemuaPeserta();
        } else {
            daftarSiswa = service.cariByNama(key);
        }

        // LOOP DARI DATABASE
        for (pesertadidik siswa : daftarSiswa) {
            String uid = siswa.getUidRfid();
            String nama = siswa.getNamaLengkap();
            String kelas = siswa.getKelas();
            String idsiswa = siswa.getIdsiswa();

            // Bikin card...
            JPanel card = new JPanel();
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
            card.setLayout(new BorderLayout());

            // PANEL DATA
            JPanel dataPanel = new JPanel();
            dataPanel.setBackground(Color.WHITE);
            dataPanel.setLayout(new GridLayout(3, 1, 0, 5));
            dataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lbNama = new JLabel("Nama : " + nama);
            JLabel lbUID = new JLabel("UID : " + uid);
            JLabel lbKelas = new JLabel("Kelas : " + kelas);

            dataPanel.add(lbNama);
            dataPanel.add(lbUID);
            dataPanel.add(lbKelas);

            // PANEL TOMBOL
            JPanel actionPanel = new JPanel();
            actionPanel.setBackground(Color.WHITE);
            actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

            JButton btnEdit = new JButton("Edit");
            JButton btnDelete = new JButton("Delete");

            btnEdit.setBackground(new Color(255, 153, 0));
            btnEdit.setForeground(Color.WHITE);
            btnDelete.setBackground(Color.RED);
            btnDelete.setForeground(Color.WHITE);

            // ===== EVENT EDIT =====
            btnEdit.addActionListener(e -> {
                jTextField1.setText(uid);
                jTextField2.setText(idsiswa);
                jTextField2.setEditable(false);
                jTextField3.setText(nama);
                jTextField4.setText(kelas);

                btnupdatesiswa.setEnabled(true);
                btntambahsiswa.setEnabled(false);
                jTextField1.requestFocus();
            });

            // ===== EVENT DELETE =====
            btnDelete.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Hapus data " + nama + "?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    service.hapusPesertaDidik(idsiswa);
                    tampilCard(key);
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                }
            });

            actionPanel.add(btnEdit);
            actionPanel.add(btnDelete);

            card.add(dataPanel, BorderLayout.CENTER);
            card.add(actionPanel, BorderLayout.SOUTH);

            listdatapanel.add(card);
        }

        listdatapanel.revalidate();
        listdatapanel.repaint();
    }

    private void makeButtonPill(JButton button) {

        button.putClientProperty(
                "FlatLaf.style",
                "arc:40;focusWidth:0;borderWidth:1"
        );

        button.setFocusPainted(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btncari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listdatapanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        btntambahsiswa = new javax.swing.JButton();
        btnupdatesiswa = new javax.swing.JButton();
        btnrefreshdata = new javax.swing.JButton();
        Rekapbtn = new javax.swing.JButton();
        edit_jadwal = new javax.swing.JButton();
        labelpenyapa = new javax.swing.JLabel();
        adminname1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 201, 167));

        jButton1.setText("pfp");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Data siswa");

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Cari siswa :");

        btncari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btncari.setText("cari");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        listdatapanel.setBackground(new java.awt.Color(255, 255, 255));
        listdatapanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout listdatapanelLayout = new javax.swing.GroupLayout(listdatapanel);
        listdatapanel.setLayout(listdatapanelLayout);
        listdatapanelLayout.setHorizontalGroup(
            listdatapanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );
        listdatapanelLayout.setVerticalGroup(
            listdatapanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(listdatapanel);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncari)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Form Input siswa baru");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("UID :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("ID siswa :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Nama :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Kelas :");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btntambahsiswa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btntambahsiswa.setText("Tambah");

        btnupdatesiswa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnupdatesiswa.setText("Update");

        btnrefreshdata.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnrefreshdata.setText("Refresh");

        Rekapbtn.setText("Lihat Rekap");

        edit_jadwal.setText("Edit Kelas dan Jadwal");
        edit_jadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_jadwalActionPerformed(evt);
            }
        });

        labelpenyapa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelpenyapa.setText("Halo,");

        adminname1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        adminname1.setText("Admin");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 48, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Rekapbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(edit_jadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btntambahsiswa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(btnupdatesiswa))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(labelpenyapa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adminname1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(btnrefreshdata)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelpenyapa)
                    .addComponent(adminname1))
                .addGap(26, 26, 26)
                .addComponent(edit_jadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(Rekapbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambahsiswa)
                    .addComponent(btnupdatesiswa))
                .addGap(44, 44, 44)
                .addComponent(btnrefreshdata)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edit_jadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_jadwalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_jadwalActionPerformed

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btncariActionPerformed

    /**
     * @param args the command line arguments
     */
//    public class Sistemabsensi {
//
//        public static void main(String[] args) {
//
//            // ===== TEST KONEKSI MONGODB =====
//            System.out.println("🔄 Testing MongoDB Connection...\n");
//
//            try {
//                // Import dari MongoManager
//                util.MongoManager.getDatabase();
//
//                // Jika tidak error, koneksi berhasil
//                System.out.println("✅ ========== KONEKSI BERHASIL! ==========");
//                System.out.println("Database: sekolah_db");
//                System.out.println("==========================================\n");
//
//            } catch (Exception e) {
//                System.err.println("❌ ========== KONEKSI GAGAL! ==========");
//                System.err.println("Error: " + e.getMessage());
//                System.err.println("=========================================\n");
//                e.printStackTrace();
//                return;  // ❌ STOP, jangan buka GUI
//            }
//
//            // ===== BUKA GUI (hanya jika koneksi berhasil) =====
//            System.out.println("🚀 Membuka aplikasi...\n");
//
//            java.awt.EventQueue.invokeLater(() -> {
//                new Adminpanel().setVisible(true);
//            });
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Rekapbtn;
    private javax.swing.JLabel adminname1;
    private javax.swing.JButton btncari;
    private javax.swing.JButton btnrefreshdata;
    private javax.swing.JButton btntambahsiswa;
    private javax.swing.JButton btnupdatesiswa;
    private javax.swing.JButton edit_jadwal;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel labelpenyapa;
    private javax.swing.JPanel listdatapanel;
    // End of variables declaration//GEN-END:variables
}
