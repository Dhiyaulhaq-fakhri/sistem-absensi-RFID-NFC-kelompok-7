/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.panel;

import gui.modules.MasterDataModule;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Lenovo
 */
public class SidebarCrudPanel extends JPanel {

    private static SidebarCrudPanel instance;
    private JPanel formContainer;
    private MasterDataModule currentModule;
    private JScrollPane scrollPane;

    public static SidebarCrudPanel getInstance() {
        if (instance == null) {
            instance = new SidebarCrudPanel();
        }
        return instance;
    }

    private SidebarCrudPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(33, 37, 41));

        formContainer = new JPanel();
        formContainer.setLayout(new BorderLayout());
        formContainer.setBackground(new Color(33, 37, 41));

        // PERBAIKAN: Hapus kata 'JScrollPane' di awal agar mengisi global variable!
        scrollPane = new JScrollPane(formContainer); 
        scrollPane.setBackground(new Color(33, 37, 41));
        scrollPane.getViewport().setBackground(new Color(33, 37, 41));

        // Pastikan scrollbar vertikal SELALU muncul jika komponen melebihi batas
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setModule(MasterDataModule module) {
        this.currentModule = module;

        formContainer.removeAll();

        JPanel crudPanel = module.getCrudFormPanel();
        if (crudPanel != null) {
            // Naikkan tingginya ke 500 atau 550 jika field paling bawah masih sedikit nanggung
            crudPanel.setPreferredSize(new Dimension(240, 500)); 
            formContainer.add(crudPanel, BorderLayout.CENTER);
        }

        formContainer.revalidate();
        formContainer.repaint();

        // Jauh lebih ringkas dan langsung sasaran:
        if (scrollPane != null) {
            scrollPane.revalidate();
            scrollPane.repaint();
        }
    }
}
