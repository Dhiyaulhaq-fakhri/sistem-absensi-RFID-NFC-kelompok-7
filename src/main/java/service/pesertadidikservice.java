/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.GenericDAO;
import objects.pesertadidik;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import java.util.List;
import util.EncryptionUtils;

/**
 *
 * @author Lenovo
 */
public class pesertadidikservice {

    private final GenericDAO<pesertadidik> dao;

    public pesertadidikservice() {
        // Inisialisasi DAO untuk koleksi "peserta_didik"
        this.dao = new GenericDAO<>("peserta_didik", pesertadidik.class);
    }

    // Tambah peserta didik baru
    public void tambahPesertaDidik(pesertadidik peserta) {
        dao.save(peserta);
        System.out.println("✓ Peserta didik '" + peserta.getNamaLengkap() + "' berhasil ditambah");
    }

    // Ambil semua peserta didik
    public List<pesertadidik> ambilSemuaPeserta() {
        return dao.findAll();
    }

    // Cari peserta didik berdasarkan ID Siswa
    public pesertadidik cariByUidRfid(String uidRfid) {
        Bson filter = Filters.eq("uidRfid", uidRfid);
        return dao.findOne(filter);
    }

    // Cari peserta didik berdasarkan nama, kelas (regex), atau ID siswa (exact match enkripsi)
    public List<pesertadidik> cariByNama(String nama) {

        // Enkripsi kata kunci untuk dicocokkan dengan idsiswa di database
        String encryptedKeyword = EncryptionUtils.encrypt(nama);

        Bson filter = Filters.or(
                Filters.regex("namaLengkap", nama, "i"), // Pencarian nama (bisa potongan kata)
                Filters.regex("kelas", nama, "i"), // Pencarian kelas (bisa potongan kata)
                Filters.eq("idsiswa", encryptedKeyword) // WAJIB pakai .eq untuk data terenkripsi
        );

        // Langsung return tanpa konversiKeList
        return dao.findMany(filter);
    }

    // Cari peserta didik berdasarkan kelas
    public List<pesertadidik> cariByKelas(String kelas) {
        Bson filter = Filters.eq("kelas", kelas);
        return dao.findMany(filter);
    }

    // Update data peserta didik
    public void updatePesertaDidik(pesertadidik peserta) {
        Bson filter = Filters.eq("idsiswa", peserta.getIdsiswa());
        pesertadidik existing = dao.findOne(filter);

        if (existing != null) {
            dao.update(filter, peserta);
            System.out.println("✓ Data peserta didik '" + peserta.getNamaLengkap() + "' berhasil diupdate");
        } else {
            System.out.println("✗ Peserta didik dengan ID " + peserta.getIdsiswa() + " tidak ditemukan");
        }
    }

    // hapus peserta didik
    public void hapusPesertaDidik(String idsiswa) {
        Bson filter = Filters.eq("idsiswa", idsiswa);
        dao.delete(filter);
        System.out.println("✓ Peserta didik dengan ID '" + idsiswa + "' berhasil dihapus");
    }

    public void tampilkanSemuaPeserta() {
        List<pesertadidik> daftar = ambilSemuaPeserta();
        System.out.println("\n========== DAFTAR PESERTA DIDIK ==========");
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data peserta didik");
        } else {
            for (int i = 0; i < daftar.size(); i++) {
                pesertadidik p = daftar.get(i);
                System.out.printf("%d. %s (ID: %s) - Kelas %s\n",
                        i + 1, p.getNamaLengkap(), p.getIdsiswa(), p.getKelas());
            }
        }
        System.out.println("==========================================\n");
    }
}
