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
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonStylesSwing {
    // Cores dos botões (alinhadas ao wireframe)
    private static final Color PRIMARY_COLOR = new Color(42, 92, 130);  // #2A5C82
    private static final Color SECONDARY_COLOR = new Color(108, 117, 125); // #6C757D
    private static final Color DANGER_COLOR = new Color(220, 53, 69);   // #DC3545

    

    private static JButton createButton(String text, Color bgColor, boolean isPrimary) {
        JButton button = new JButton(text);
        
        // Estilo base
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Tipografia
        button.setFont(Tipografia.getButtonFont(isPrimary));
        
        // Efeito hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(darkenColor(bgColor, 0.9f));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private static Color darkenColor(Color color, float factor) {
        return new Color(
            Math.max((int)(color.getRed() * factor), 0),
            Math.max((int)(color.getGreen() * factor), 0),
            Math.max((int)(color.getBlue() * factor), 0)
        );
    }
}
