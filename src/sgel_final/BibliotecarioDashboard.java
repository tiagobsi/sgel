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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class BibliotecarioDashboard extends JFrame {
    private JPanel cardsPanel;
    private CardLayout cardLayout;
    private PainelCadastroAluno painelCadastroAluno;
    private PainelEmprestimos painelEmprestimos; // Adicionado como campo para controle

    public BibliotecarioDashboard() {
        setTitle("SGEL - Bibliotecário");
        setSize(1024, 768); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel painelNav = new JPanel(new GridLayout(1, 4));
        painelNav.setBackground(new Color(42, 92, 130));
        
        JButton btnLivros = criarBotaoNav("Livros");
        JButton btnEmprestimos = criarBotaoNav("Empréstimos");
        JButton btnCadastroLivro = criarBotaoNav("Cadastrar Livro");
        JButton btnCadastroAluno = criarBotaoNav("Cadastrar Aluno");
        JButton btnLogout = criarBotaoNav("Sair");
        
        painelNav.add(btnLivros);
        painelNav.add(btnEmprestimos);
        painelNav.add(btnCadastroLivro);
        painelNav.add(btnCadastroAluno);
        painelNav.add(btnLogout);

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        
        painelCadastroAluno = new PainelCadastroAluno();
        painelEmprestimos = new PainelEmprestimos(); // InstÃ¢ncia Ãºnica
        
        cardsPanel.add(new JScrollPane(new PainelLivros()), "LIVROS");
        cardsPanel.add(new JScrollPane(painelEmprestimos), "EMPRESTIMOS");
        cardsPanel.add(new JScrollPane(new PainelCadastroLivro()), "CADASTRO_LIVRO");
        cardsPanel.add(new JScrollPane(painelCadastroAluno), "CADASTRO_ALUNO");
        
        add(painelNav, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
        
        btnLivros.addActionListener(e -> {
            cardLayout.show(cardsPanel, "LIVROS");
            cardsPanel.revalidate();
        });
        
        btnEmprestimos.addActionListener(e -> {
            cardLayout.show(cardsPanel, "EMPRESTIMOS");
            painelEmprestimos.revalidateButtons(); // Novo método para atualizar botÃµes
            cardsPanel.revalidate();
        });
        
        btnCadastroLivro.addActionListener(e -> cardLayout.show(cardsPanel, "CADASTRO_LIVRO"));
        btnCadastroAluno.addActionListener(e -> cardLayout.show(cardsPanel, "CADASTRO_ALUNO"));
        btnLogout.addActionListener(this::logout);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                cardsPanel.revalidate();
                cardsPanel.repaint();
            }
        });
    }

    public List<PainelCadastroAluno.Aluno> getAlunosCadastrados() {
        return painelCadastroAluno.getAlunosCadastrados();
    }

    private JButton criarBotaoNav(String text) {
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
