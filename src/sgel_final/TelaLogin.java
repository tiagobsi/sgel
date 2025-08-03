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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.JTextComponent;

public class TelaLogin {
    private static JFrame frame;    
    public static void main(String[] args) {
        telaLogin();
    }

    public static void telaLogin() {
        frame = new JFrame("SGEL - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 470);
        frame.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(Color.WHITE);

        JLabel logo = new JLabel(new ImageIcon("src/assets/logo.png"));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("SGEL - Biblioteca");
        Tipografia.applyH1(title);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1, 10, 15));
        formPanel.setOpaque(false);

        JComboBox<String> userType = new JComboBox<>(new String[]{"Aluno", "Bibliotecário", "Administrador"});
        userType.setSelectedIndex(-1);
        styleComboBox(userType);

        JTextField loginField = new JTextField();
        styleTextField(loginField, "Login");
        adicionarComportamento(loginField, "Login");
        
        JPasswordField passwordField = new JPasswordField();
        styleTextField(passwordField, "Senha");
        adicionarSenha(passwordField, "Senha");

        formPanel.add(userType);
        formPanel.add(loginField);
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Entrar");
        styleButton(loginButton, Tipografia.PRIMARY_COLOR, true);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton recoverButton = new JButton("Esqueci minha senha");
        styleButton(recoverButton, Tipografia.TEXT_SECONDARY, false);
        recoverButton.setContentAreaFilled(false);
        recoverButton.setBorderPainted(false);
        recoverButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addActionListener((ActionEvent e) -> handleLogin(
            (String) userType.getSelectedItem(),
            loginField.getText(),
            new String(passwordField.getPassword())
        ));

        mainPanel.add(logo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(recoverButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void adicionarComportamento(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    private static void adicionarSenha(JPasswordField passwordField, String placeholder) {
        passwordField.setEchoChar((char)0); 
        passwordField.setForeground(Color.GRAY);
        passwordField.setText(placeholder);
        
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setEchoChar((char)0);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholder);
                }
            }
        });
    }

    private static void handleLogin(String tipo, String email, String senha) {
        if (tipo == null) {
            JOptionPane.showMessageDialog(frame, "Selecione um tipo de usuário!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (email.equals("Login") || senha.equals("Senha")) {
            JOptionPane.showMessageDialog(frame, 
                "Preencha todos os campos corretamente!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean autenticado = false;
            String nomeUsuario = "";
            int idUsuario = 0;
            boolean isBibliotecario = false;

            switch (tipo) {
                case "Aluno":
                    AlunoDTO aluno = AlunoDAO.buscarPorEmail(email);
                    if (aluno != null && verificarSenha(aluno.getSenha(), senha)) {
                        autenticado = true;
                        nomeUsuario = aluno.getNome();
                        idUsuario = aluno.getId();
                        
                        if (BibliotecarioDAO.verificarSenhaPadrao(aluno.getSenha())) {
                            ModalAlterarSenha modal = new ModalAlterarSenha(frame, idUsuario, false);
                            modal.setVisible(true);
                            
                            if (modal.isSenhaAlterada()) {
                                JOptionPane.showMessageDialog(frame, 
                                    "Senha alterada com sucesso! Por favor, faça login novamente.", 
                                    "Sucesso", 
                                    JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(frame, 
                                    "Você deve alterar sua senha para continuar!", 
                                    "Aviso", 
                                    JOptionPane.WARNING_MESSAGE);
                            }
                            return;
                        }
                    }
                    break;

                case "Bibliotecário":
                    BibliotecarioDTO bibliotecario = BibliotecarioDAO.buscarPorEmail(email);
                    if (bibliotecario != null && verificarSenha(bibliotecario.getSenha(), senha)) {
                        autenticado = true;
                        nomeUsuario = bibliotecario.getNome();
                        idUsuario = bibliotecario.getId();
                        isBibliotecario = true;
                        
                        if (BibliotecarioDAO.verificarSenhaPadrao(bibliotecario.getSenha())) {
                            ModalAlterarSenha modal = new ModalAlterarSenha(frame, idUsuario, true);
                            modal.setVisible(true);
                            
                            if (modal.isSenhaAlterada()) {
                                JOptionPane.showMessageDialog(frame, 
                                    "Senha alterada com sucesso! Por favor, faça login novamente.", 
                                    "Sucesso", 
                                    JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(frame, 
                                    "Você deve alterar sua senha para continuar!", 
                                    "Aviso", 
                                    JOptionPane.WARNING_MESSAGE);
                            }
                            return;
                        }
                    }
                    break;

                case "Administrador":
              
                    autenticado = true;
                    nomeUsuario = "Administrador";
                    break;
            }

            if (autenticado) {
                frame.dispose();
                switch (tipo) {
                    case "Aluno":
                        new AlunoDashboard(nomeUsuario).setVisible(true);
                        break;
                    case "Bibliotecário":
                        new BibliotecarioDashboard().setVisible(true);
                        break;
                    case "Administrador":
                        new AdminDashboard().setVisible(true);
                        break;
                }
                JOptionPane.showMessageDialog(null, "Bem-vindo, " + nomeUsuario + "!");
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Credenciais inválidas!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, 
                "Erro ao acessar o banco de dados: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean verificarSenha(String senhaHash, String senhaDigitada) {
        String hashDigitado = BibliotecarioDAO.gerarHashSHA256(senhaDigitada);
        return senhaHash.equals(hashDigitado);
    }

    private static void styleComboBox(JComboBox<?> combo) {
        combo.setFont(Tipografia.getButtonFont(false));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
    }

    private static void styleTextField(JTextComponent field, String placeholder) {
        field.setFont(Tipografia.getButtonFont(false));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
    }

    private static void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(bgColor.equals(Tipografia.TEXT_SECONDARY) ? 
            Tipografia.TEXT_PRIMARY : Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }
}