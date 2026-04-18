/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package sistemabsensi;

import sistemabsensi.objects.pesertadidik;

/**
 *
 * @author Lenovo
 */
public class Sistemabsensi {

    public static void main(String[] args) {
        pesertadidik s = new pesertadidik();
        s.setUidRfid("1234567");
        s.setIdsiswa("12");
        s.setNamaLengkap("fakhri");
        s.setKelas("X-1");
        
        System.err.println(s.toString());
    }
}
