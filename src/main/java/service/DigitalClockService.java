/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JLabel;

/**
 *
 * @author Lenovo
 */
public class DigitalClockService {
    private final JLabel targetLabel;
    private final String pattern;

    public DigitalClockService(JLabel targetLabel, String pattern) {
        this.targetLabel = targetLabel;
        this.pattern = pattern;
    }
    
     public Thread getThread() {
        Runnable clockTask = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    LocalDateTime now = LocalDateTime.now();
                    
                    Locale currentLocale = I18nServices.getCurrentLocale();
                    if (currentLocale == null) {
                        currentLocale = new Locale("id", "ID"); // Fallback jika null
                    }
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, currentLocale);
                    String timeFormatted = now.format(formatter);
                    
                    // Update label secara asinkron
                    javax.swing.SwingUtilities.invokeLater( () -> targetLabel.setText(timeFormatted));
                    
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                // Penanganan saat thread di-track dan dihentikan sengaja
                System.out.println(Thread.currentThread().getName() + " dihentikan.");
            }
        };
        
         return new Thread(clockTask);
    }
}
