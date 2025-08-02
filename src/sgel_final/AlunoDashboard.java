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
import java.awt.event.ActionEvent;

public class AlunoDashboard extends JFrame {
    private String matriculaAluno;
    
    public AlunoDashboard(String matricula) {
        this.matriculaAluno = matricula;
        setTitle("SGEL - Aluno (" + matricula + ")");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel navPanel = new JPanel(new GridLayout(1, 2));
        navPanel.setBackground(new Color(42, 92, 130));
        
        JButton btnEmprestimos = createNavButton("Meus Empréstimos");
        JButton btnLogout = createNavButton("Sair");
        
        navPanel.add(btnEmprestimos);
        navPanel.add(btnLogout);

        PainelEmprestimosAluno painelEmprestimos = new PainelEmprestimosAluno(matricula);
        
        add(navPanel, BorderLayout.NORTH);
        add(painelEmprestimos, BorderLayout.CENTER);
        
        btnEmprestimos.addActionListener(e -> {
            painelEmprestimos.atualizarListaEmprestimos();
        });
        
        btnLogout.addActionListener(this::logout);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Tipografia.getButtonFont(true));
        button.setBackground(Tipografia.PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        button.setFocusPainted(false);
        return button;
    }

    private void logout(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Deseja realmente sair do sistema?", 
            "Confirmar Saí­da", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            TelaLogin.telaLogin();
        }
    }
}
