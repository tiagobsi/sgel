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

public class AdminDashboard extends JFrame {
    private PainelLivros painelLivros;
    private CardLayout cardLayout;
    private JPanel cardsPanel;

    public AdminDashboard() {
        setTitle("SGEL - Administrador");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel navPanel = new JPanel(new GridLayout(1, 5));
        navPanel.setBackground(new Color(42, 92, 130));
        
        JButton btnCadBibliotecario = createNavButton("Cadastro Bibliotecário");
        JButton btnCadAluno = createNavButton("Cadastro Aluno");
        JButton btnCadLivro = createNavButton("Cadastro Livro");
        JButton btnRelatorios = createNavButton("Relatórios");
        JButton btnLogout = createNavButton("Sair");
        
        navPanel.add(btnCadBibliotecario);
        navPanel.add(btnCadAluno);
        navPanel.add(btnCadLivro);
        navPanel.add(btnRelatorios);
        navPanel.add(btnLogout);

        
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        
       
        PainelCadastroBibliotecario painelBibliotecario = new PainelCadastroBibliotecario();
        PainelCadastroAluno painelAluno = new PainelCadastroAluno();
        PainelCadastroLivro painelLivro = new PainelCadastroLivro();
        PainelRelatorios painelRelatorios = new PainelRelatorios();
        painelLivros = new PainelLivros();
        
        
        cardsPanel.add(painelBibliotecario, "BIBLIOTECARIO");
        cardsPanel.add(painelAluno, "ALUNO");
        cardsPanel.add(painelLivro, "LIVRO");
        cardsPanel.add(painelRelatorios, "RELATORIOS");
        cardsPanel.add(painelLivros, "LISTA_LIVROS");
        
        add(navPanel, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
        
        
        btnCadBibliotecario.addActionListener(e -> cardLayout.show(cardsPanel, "BIBLIOTECARIO"));
        btnCadAluno.addActionListener(e -> cardLayout.show(cardsPanel, "ALUNO"));
        btnCadLivro.addActionListener(e -> cardLayout.show(cardsPanel, "LIVRO"));
        btnRelatorios.addActionListener(e -> cardLayout.show(cardsPanel, "RELATORIOS"));
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
