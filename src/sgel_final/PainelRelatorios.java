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

public class PainelRelatorios extends JPanel {
    public PainelRelatorios() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        
        JLabel mensagem = new JLabel("Relatórios Analí­ticos - Em Desenvolvimento");
        mensagem.setFont(Tipografia.getH1Font());
        mensagem.setHorizontalAlignment(SwingConstants.CENTER);
        add(mensagem, BorderLayout.CENTER);
    }
}
