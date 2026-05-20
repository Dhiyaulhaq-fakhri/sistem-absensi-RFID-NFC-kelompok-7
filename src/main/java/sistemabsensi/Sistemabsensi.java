/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package sistemabsensi;

import com.formdev.flatlaf.FlatLightLaf;
import gui.Adminpanel;

/**
 *
 * @author Lenovo
 */
public class Sistemabsensi {

    public static void main(String[] args) {

        // ===== TEST KONEKSI MONGODB =====
        System.out.println("🔄 Testing MongoDB Connection...\n");

        try {
            // Import dari MongoManager
            util.MongoManager.getDatabase();

            // Jika tidak error, koneksi berhasil
            System.out.println("✅ ========== KONEKSI BERHASIL! ==========");
            System.out.println("Database: sekolah_db");
            System.out.println("==========================================\n");

        } catch (Exception e) {
            System.err.println("❌ ========== KONEKSI GAGAL! ==========");
            System.err.println("Error: " + e.getMessage());
            System.err.println("=========================================\n");
            e.printStackTrace();
            return;  // ❌ STOP, jangan buka GUI
        }

        // ===== BUKA GUI (hanya jika koneksi berhasil) =====
        System.out.println("🚀 Membuka aplikasi...\n");
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            new Adminpanel().setVisible(true);
        });
    }
}
