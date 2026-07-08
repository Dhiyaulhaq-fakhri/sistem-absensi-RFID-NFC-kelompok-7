/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pallete;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import javax.swing.JToggleButton;
import service.I18nServices;

/**
 *
 * @author Lenovo
 */
public class SlidingLanguageToggle extends JToggleButton implements I18nServices.I18nChangeListener {
    
    // Tema warna dark-mode modern
    private final Color COLOR_BG = new Color(39, 45, 54);              // Background wadah abu-abu gelap
    private final Color COLOR_SLIDER_ACTIVE = new Color(30, 144, 255);  // Slider Biru Modern (Dodger Blue)
    
    private final int cornerRadius = 24; // Bentuk kapsul/pil halus
    
    // Indeks Bahasa: 0 = Indonesia, 1 = Inggris, 2 = Jerman
    private int selectedLanguageIndex = 0; 
    
    // Label teks dan kode bahasa internasional
    private final String[] languages = {"Indonesia", "Inggris", "Jerman"};
    private final String[] langcodes = {"id", "en", "de"};

    public SlidingLanguageToggle() {
        super();

        // Matikan render bawaan Swing agar custom paint bekerja maksimal
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.BOLD, 12)); // Dikecilkan sedikit agar muat 3 bahasa dengan rapi

        // Daftarkan komponen ini sebagai listener perubahan bahasa
        I18nServices.registerListener(this);

        // Logika klik untuk mendeteksi sepertiga area horizontal mana yang ditekan
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int width = getWidth();
                int sectionWidth = width / 3;
                int clickX = e.getX();

                int targetIndex = 0;
                if (clickX < sectionWidth) {
                    targetIndex = 0; // Indonesia
                } else if (clickX < sectionWidth * 2) {
                    targetIndex = 1; // Inggris
                } else {
                    targetIndex = 2; // Jerman
                }
                
                // Update posisi visual slider internal
                setSelectedLanguageIndex(targetIndex);
                
                // Ubah locale global via service i18n menggunakan instansiasi standar Java yang aman
                try {
                    I18nServices.setLocale(new Locale(langcodes[targetIndex]));
                    
                    // Memicu ActionEvent agar didengar oleh SettingsModule (addActionListener)
                    fireActionPerformed(new java.awt.event.ActionEvent(SlidingLanguageToggle.this, 
                            java.awt.event.ActionEvent.ACTION_PERFORMED, "LanguageChanged"));
                            
                } catch (Exception ex) {
                    System.err.println("Gagal mengubah bahasa lewat toggle: " + ex.getMessage());
                }
            }
        });
        
        // Sinkronisasi posisi awal visual toggle dengan bahasa yang sedang aktif di service
        syncWithCurrentLocale();
    }
    /**
     * Mendapatkan indeks bahasa saat ini (0 = Indo, 1 = Eng, 2 = Mys)
     * @return 
     */
    private void syncWithCurrentLocale() {
        Locale current = I18nServices.getCurrentLocale();
        if (current != null) {
            String lang = current.getLanguage();
            for (int i = 0; i < langcodes.length; i++) {
                if (langcodes[i].equalsIgnoreCase(lang)) {
                    this.selectedLanguageIndex = i;
                    break;
                }
            }
        }
        repaint();
    }

    @Override
    public void onLanguageChanged() {
        // Terpicu otomatis jika ada bagian aplikasi lain yang merubah bahasa
        syncWithCurrentLocale();
    }

    public int getSelectedLanguageIndex() {
        return selectedLanguageIndex;
    }

    public void setSelectedLanguageIndex(int index) {
        if (index >= 0 && index <= 2) {
            this.selectedLanguageIndex = index;
            repaint();
        }
    }
    
    public String getSelectedLanguageString() {
        return langcodes[selectedLanguageIndex];
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Mengaktifkan anti-aliasing text dan shape
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int margin = 5; 
        
        // Lebar slider dibagi 3 bidang dikurangi margin padding
        int sliderWidth = (w / 3) - (margin + (margin / 3));
        int sliderHeight = h - (margin * 2);

        // 1. GAMBAR BACKGROUND KAPSUL UTAMA
        g2.setColor(COLOR_BG);
        g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);

        // 2. GAMBAR AKTOR SLIDER AKTIF (Biru)
        int sliderX = margin + (selectedLanguageIndex * (w / 3));
        if (selectedLanguageIndex == 1) sliderX += 2; // Mikro adjusment tengah
        if (selectedLanguageIndex == 2) sliderX = w - sliderWidth - margin; // Mikro adjustment kanan

        g2.setColor(COLOR_SLIDER_ACTIVE);
        g2.fillRoundRect(sliderX, margin, sliderWidth, sliderHeight, cornerRadius - 6, cornerRadius - 6);

        // 3. TATA LETAK STRINGS TEKS (Indonesia, Inggris, Jerman)
        FontMetrics fm = g2.getFontMetrics();
        int textY = (h / 2) + (fm.getAscent() / 2) - 2;

        for (int i = 0; i < languages.length; i++) {
            String text = languages[i];
            
            // Hitung titik tengah pembagian sepertiga area
            int targetCenterX = (w / 6) + (i * (w / 3));
            int textX = targetCenterX - (fm.stringWidth(text) / 2);

            // Kondisi pewarnaan teks
            if (i == selectedLanguageIndex) {
                g2.setColor(Color.WHITE); // Teks kontras putih jika terpilih oleh slider biru
            } else {
                g2.setColor(new Color(130, 135, 145)); // Teks redup abu-abu jika tidak aktif
            }
            
            g2.drawString(text, textX, textY);
        }

        g2.dispose();
    }
}
