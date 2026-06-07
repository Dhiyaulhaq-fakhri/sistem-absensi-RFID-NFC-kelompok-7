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

/**
 *
 * @author Lenovo
 */
public class PesertaDidikModule implements MasterDataModule {
    
    // CRUD Components
    private JTextField txtUID;
    private JTextField txtIdPeserta;
    private JTextField txtNama;
    private JTextField txtKelas;
    private JButton btnTambah;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JTextField txtSearch;
    
    // Table
    private JPanel tablePanel;
    private JTable tabelPeserta;
    
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
        JLabel titleLabel = new JLabel("Form Input Peserta Didik Baru");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));
        
        // Form Fields
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        formPanel.setBackground(new Color(33, 37, 41));
        
        // UID
        JLabel lblUID = new JLabel("UID :");
        lblUID.setForeground(Color.WHITE);
        lblUID.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtUID = new JTextField();
        txtUID.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblUID);
        formPanel.add(txtUID);
        
        // ID Peserta
        JLabel lblIdPeserta = new JLabel("ID Peserta :");
        lblIdPeserta.setForeground(Color.WHITE);
        lblIdPeserta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtIdPeserta = new JTextField();
        txtIdPeserta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblIdPeserta);
        formPanel.add(txtIdPeserta);
        
        // Nama
        JLabel lblNama = new JLabel("Nama :");
        lblNama.setForeground(Color.WHITE);
        lblNama.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNama = new JTextField();
        txtNama.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblNama);
        formPanel.add(txtNama);
        
        // Kelas
        JLabel lblKelas = new JLabel("Kelas :");
        lblKelas.setForeground(Color.WHITE);
        lblKelas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtKelas = new JTextField();
        txtKelas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblKelas);
        formPanel.add(txtKelas);
        
        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(15));
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBackground(new Color(33, 37, 41));
        
        btnTambah = new JButton("Tambah");
        btnTambah.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnTambah.addActionListener(e -> save());
        buttonPanel.add(btnTambah);
        
        btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnUpdate.addActionListener(e -> update());
        buttonPanel.add(btnUpdate);
        
        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnDelete.addActionListener(e -> delete());
        buttonPanel.add(btnDelete);
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefresh.addActionListener(e -> refresh());
        buttonPanel.add(btnRefresh);
        
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(10));
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(33, 37, 41));
        
        JLabel lblCari = new JLabel("Cari Peserta :");
        lblCari.setForeground(Color.WHITE);
        lblCari.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchPanel.add(lblCari);
        
        txtSearch = new JTextField(15);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel("Data Peserta Didik");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerPanel.add(headerLabel);
        tablePanel.add(headerPanel, BorderLayout.PAGE_START);
        
        // Table
        tabelPeserta = new JTable();
        tabelPeserta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelPeserta.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelPeserta);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        loadTableData();
        return tablePanel;
    }
    
    @Override
    public void loadTableData() {
        String keyword = txtSearch.getText();
        
        // Dummy data - ganti dengan service yang sebenarnya
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("UID");
        model.addColumn("ID Peserta");
        model.addColumn("Nama");
        model.addColumn("Kelas");
        
        tabelPeserta.setModel(model);
    }
    
    @Override
    public void save() {
        String uid = txtUID.getText();
        String idPeserta = txtIdPeserta.getText();
        String nama = txtNama.getText();
        String kelas = txtKelas.getText();
        
        if (uid.isEmpty() || idPeserta.isEmpty() || nama.isEmpty() || kelas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // TODO: Call PesertaDidikService.tambahPeserta(...)
        JOptionPane.showMessageDialog(null, "Data peserta berhasil ditambahkan!", "Success", JOptionPane.INFORMATION_MESSAGE);
        refresh();
    }
    
    @Override
    public void update() {
        String idPeserta = txtIdPeserta.getText();
        
        if (idPeserta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pilih data peserta terlebih dahulu!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // TODO: Call PesertaDidikService.updatePeserta(...)
        JOptionPane.showMessageDialog(null, "Data peserta berhasil diupdate!", "Success", JOptionPane.INFORMATION_MESSAGE);
        refresh();
    }
    
    @Override
    public void delete() {
        String idPeserta = txtIdPeserta.getText();
        
        if (idPeserta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pilih data peserta terlebih dahulu!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Call PesertaDidikService.deletePeserta(...)
            JOptionPane.showMessageDialog(null, "Data peserta berhasil dihapus!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refresh();
        }
    }
    
    @Override
    public void refresh() {
        txtUID.setText("");
        txtIdPeserta.setText("");
        txtNama.setText("");
        txtKelas.setText("");
        txtSearch.setText("");
        loadTableData();
    }
}
