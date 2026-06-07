/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gui.modules;


import javax.swing.JPanel;
/**
 *
 * @author Lenovo
 */
public interface MasterDataModule {
     /**
     * Nama module untuk display
     */
    String getModuleName();
    
    /**
     * Return panel CRUD form (ditampin di LEFT BOTTOM)
     */
    JPanel getCrudFormPanel();
    
    /**
     * Return panel Table (ditampin di RIGHT)
     */
    JPanel getTablePanel();
    
    /**
     * Load/refresh data tabel
     */
    void loadTableData();
    
    /**
     * Save data baru
     */
    void save();
    
    /**
     * Update data yang dipilih
     */
    void update();
    
    /**
     * Delete data yang dipilih
     */
    void delete();
    
    /**
     * Refresh semua (clear form + reload table)
     */
    void refresh();
}
