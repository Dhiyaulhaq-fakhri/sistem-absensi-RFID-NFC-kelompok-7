/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.GenericDAO;
import java.time.LocalDateTime;
import java.util.UUID;
import objects.LogAbsensi;

/**
 *
 * @author Lenovo
 */
public class LogAbsensiService {
    
    private final GenericDAO<LogAbsensi> logDAO = new GenericDAO<>("log_absensi",LogAbsensi.class);
    
    public void simpanLog(String hashedUid, String status) {
        // Membuat objek LogAbsensi sesuai parameter di sumber [6]
        LogAbsensi log = new LogAbsensi(
            UUID.randomUUID().toString(), 
            hashedUid, 
            LocalDateTime.now(), 
            status
        );
        logDAO.save(log); // Menyimpan ke MongoDB [7]
    }
}
