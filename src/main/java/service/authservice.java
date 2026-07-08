/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import objects.Guru;
import dao.GenericDAO;
import gui.Adminpanel;
import gui.Login;
import util.SecurityUtils;
import com.mongodb.client.model.Filters;
import java.awt.Frame;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;

/**
 *
 * @author Lenovo
 */
public class authservice {

    private final GenericDAO<Guru> guruDAO = new GenericDAO<>("guru", Guru.class);

    public void login(String username, String Plainpassword, Login Login) {
        String hashedinput = SecurityUtils.getHash(Plainpassword, SecurityUtils.SHA_256);

        Guru guru = guruDAO.findOne(Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", hashedinput)
        ));

        if (guru != null) {
            guru.setLastlogin(LocalDateTime.now());
            guruDAO.update(Filters.eq("username", username), guru);

            // Berhasil: Masuk ke Halaman Admin
            String successMsg = String.format(I18nServices.get("ui.auth.login.success"), guru.getFullname());
            JOptionPane.showMessageDialog(
                    null,
                    successMsg,
                    I18nServices.get("ui.auth.title.success"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            Adminpanel admPage = new Adminpanel();
            admPage.setLocationRelativeTo(null);
            admPage.setVisible(true);
            admPage.setExtendedState(Frame.MAXIMIZED_BOTH);
            Login.setVisible(false);
        } else {
            // Gagal: Notifikasi Error
            JOptionPane.showMessageDialog(
                    null,
                    I18nServices.get("ui.auth.login.failed"),
                    I18nServices.get("ui.auth.title.error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void registerUser(String fullname, String username, String plainPassword) {
        // 1. Proses Hashing: Mengamankan password mentah menggunakan SHA-256 [1]
        String hashedPassword = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);

        // 2. Instansiasi Objek: Membuat objek User baru dengan password yang sudah di-hash
        // lastLogin disetel null karena user baru belum pernah masuk sistem
        Guru newUser = new Guru(fullname, username, hashedPassword, null);

        // 3. Operasi Create: Menyimpan dokumen user ke koleksi MongoDB melalui GenericDAO [3], [4]
        try {
            guruDAO.save(newUser); // Memanggil insertOne melalui GenericDAO [5]
        } catch (Exception e) {
            String errorMsg = String.format(I18nServices.get("ui.auth.register.failed"), e.getMessage());
            // Standar Debugging: Mengidentifikasi error log secara mandiri [6]
            JOptionPane.showMessageDialog(
                    null, 
                    errorMsg, 
                    I18nServices.get("ui.auth.title.reg.error"), 
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
