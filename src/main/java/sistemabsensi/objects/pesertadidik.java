/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabsensi.objects;

/**
 *
 * @author Lenovo
 */
public class pesertadidik {
    private String uidRfid;
    private String idsiswa;
    private String namaLengkap;
    private String kelas;

    public pesertadidik() {
    }

    public pesertadidik(String uidRfid, String idsiswa, String namaLengkap, String kelas) {
        this.uidRfid = uidRfid;
        this.idsiswa = idsiswa;
        this.namaLengkap = namaLengkap;
        this.kelas = kelas;
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getIdsiswa() {
        return idsiswa;
    }

    public void setIdsiswa(String idsiswa) {
        this.idsiswa = idsiswa;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    @Override
    public String toString() {
        return "pesertadidik{" +
                "uidRfid=" + uidRfid +
                ", idsiswa=" + idsiswa +
                ", namaLengkap=" + namaLengkap +
                ", kelas=" + kelas + '}';
    }
    
    
    
}
