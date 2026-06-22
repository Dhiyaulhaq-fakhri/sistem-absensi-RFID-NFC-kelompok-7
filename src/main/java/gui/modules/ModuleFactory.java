/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.modules;

//import gui.modules.impl.GuruModule;
import gui.modules.impl.PesertaDidikModule;
//import gui.modules.impl.KelasModule;
//import gui.modules.impl.JamPelajaranModule;
import gui.modules.impl.AttendanceModule;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Lenovo
 */
public class ModuleFactory {
    
    private static final Map<String, MasterDataModule> modules = new HashMap<>();
    
    static {
//        modules.put("Guru", new GuruModule());
        modules.put("Peserta Didik", new PesertaDidikModule());
//        modules.put("Kelas", new KelasModule());
//        modules.put("Jam Pelajaran", new JamPelajaranModule());
        modules.put("KiosK", new AttendanceModule());
    }
    
    public static MasterDataModule getModule(String moduleName) {
        return modules.get(moduleName);
    }
}
