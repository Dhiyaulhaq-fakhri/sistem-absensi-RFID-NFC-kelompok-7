/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author Lenovo
 */
public class I18nServices {
    private static ResourceBundle bundle;
    private static Locale currentLocale;
    
    public interface I18nChangeListener {
        void onLanguageChanged();
    }
    
    private static final List<I18nChangeListener> listeners = new ArrayList<>();
    
    static {
        setLocale(Locale.of("en")); 
    }
    
    public static void setLocale(Locale locale) {
    currentLocale = locale;
    try {
        // Coba jalur pertama
        bundle = ResourceBundle.getBundle("resources.i18n.messages", currentLocale);
    } catch (java.util.MissingResourceException e) {
        System.out.println("⚠️ Jalur pertama gagal, mencoba jalur alternatif (tanpa 'resources.')...");
        try {
            // Coba jalur alternatif jika folder resources dianggap root
            bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
        } catch (java.util.MissingResourceException ex) {
            System.err.println("❌ ERROR KRITIS: File bahasa tidak ditemukan di jalur manapun!");
            ex.printStackTrace();
        }
    }
    
    notifyListeners();
}
    
    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return "!" + key + "!"; 
        }
    }
    
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
    
    public static synchronized void registerListener(I18nChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public static synchronized void unregisterListener(I18nChangeListener listener) {
        listeners.remove(listener);
    }
    
    private static void notifyListeners() {
        // Jalankan perulangan untuk mengeksekusi fungsi update di setiap form
        for (I18nChangeListener listener : listeners) {
            if (listener != null) {
                listener.onLanguageChanged();
            }
        }
    }
}
