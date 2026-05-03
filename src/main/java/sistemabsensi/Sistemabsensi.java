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
        pesertadidik PD = new pesertadidik();
        if (PD instanceof pesertadidik) {
            System.err.println("Karyawan");
        } else {
            System.err.println("Something else");
        }// //
        // //
    }
}
