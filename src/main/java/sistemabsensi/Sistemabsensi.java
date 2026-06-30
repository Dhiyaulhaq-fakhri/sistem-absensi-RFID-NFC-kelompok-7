/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package sistemabsensi;

import com.formdev.flatlaf.FlatLightLaf;
import gui.Login;
import service.SerialService; 

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

        // ===== SETUP SERIAL SERVICE UNTUK RFID READER =====
        System.out.println("🔄 Initializing RFID Serial Service...\n");

        boolean rfidConnected = SerialService.getInstance().connect("COM3", 9600);

        if (rfidConnected) {
            System.out.println("✅ ========== RFID READER BERHASIL TERHUBUNG! ==========");
            System.out.println("Port: COM3 | Baud Rate: 9600");
            System.out.println("========================================================\n");
        } else {
            System.err.println("⚠️  ========== RFID READER TIDAK TERHUBUNG! ==========");
            System.err.println("Port COM3 tidak ditemukan atau tidak responsif.");
            System.err.println("Aplikasi akan tetap berjalan dalam mode MANUAL TEST.");
            System.err.println("Periksa Device Manager dan pastikan RFID Reader terhubung.");
            System.err.println("=====================================================\n");
        }

        // Register global handler untuk logging semua event RFID
        SerialService.getInstance().addHandler(rfidData -> {
            System.out.println("[GLOBAL LOG] RFID Tag terdeteksi: " + rfidData);
        });

        // ===== BUKA GUI (hanya jika koneksi berhasil) =====
        System.out.println("🚀 Membuka aplikasi...\n");
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
