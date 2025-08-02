package sgel_final;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Tiago
 */
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class Tipografia {
    
    public static final Color PRIMARY_COLOR = new Color(42, 92, 130);   // #2A5C82
    public static final Color SECONDARY_COLOR = new Color(108, 117, 125); // #6C757D
    public static final Color DEVOLVER_COLOR = new Color(16, 185, 129); // #10B981
    public static final Color DANGER_COLOR = new Color(220, 53, 69);    // #DC3545
    public static final Color TEXT_PRIMARY = new Color(33, 37, 41);   // #212529
    public static final Color TEXT_SECONDARY = new Color(73, 80, 87); // #495057
    public static final Color TEXT_LIGHT = new Color(248, 249, 250);  // #F8F9FA
    
    private static final String BASE_FONT = "Segoe UI"; 
    
    public static void applyH1(JLabel label) {
        label.setFont(new Font(BASE_FONT, Font.BOLD, 32));
        label.setForeground(TEXT_PRIMARY);
    }
    
    public static void applyH2(JLabel label) {
        label.setFont(new Font(BASE_FONT, Font.BOLD, 24));
        label.setForeground(TEXT_PRIMARY);
    }
    
    public static void applyH3(JComponent component) {
        component.setFont(new Font(BASE_FONT, Font.PLAIN, 24));
        component.setForeground(TEXT_PRIMARY);
    }
    
    public static void applyBody(JTextComponent component) {
        component.setFont(new Font(BASE_FONT, Font.PLAIN, 14));
        component.setForeground(TEXT_PRIMARY);
    }
    
    public static void applyCaption(JLabel label) {
        label.setFont(new Font(BASE_FONT, Font.PLAIN, 12));
        label.setForeground(TEXT_SECONDARY);
    }
    
    public static Font getH1Font() {
        return new Font(BASE_FONT, Font.BOLD, 32);
    }
    
    public static Font getButtonFont(boolean bold) {
        return new Font(BASE_FONT, bold ? Font.BOLD : Font.PLAIN, 14);
    }
}
