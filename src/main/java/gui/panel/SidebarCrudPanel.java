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
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(new Color(33, 37, 41));
        formContainer.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(formContainer);
        scrollPane.setBackground(new Color(33, 37, 41));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void setModule(MasterDataModule module) {
        this.currentModule = module;
        
        formContainer.removeAll();
        formContainer.add(module.getCrudFormPanel());
        formContainer.revalidate();
        formContainer.repaint();
    }
}
