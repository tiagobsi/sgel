/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgel_final;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author Tiago
 */
public class ModalAlterarSenha extends JDialog {
    private JPasswordField senhaAtualField;
    private JPasswordField novaSenhaField;
    private JPasswordField confirmarSenhaField;
    private boolean senhaAlterada = false;
    
    public ModalAlterarSenha(Frame parent, int idUsuario, boolean isBibliotecario) {
        super(parent, "Alterar Senha", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        formPanel.add(new JLabel("Senha Atual:"));
        senhaAtualField = new JPasswordField();
        formPanel.add(senhaAtualField);
        
        formPanel.add(new JLabel("Nova Senha:"));
        novaSenhaField = new JPasswordField();
        formPanel.add(novaSenhaField);
        
        formPanel.add(new JLabel("Confirmar Senha:"));
        confirmarSenhaField = new JPasswordField();
        formPanel.add(confirmarSenhaField);
        
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> {
            if (validarCampos() && alterarSenha(idUsuario, isBibliotecario)) {
                senhaAlterada = true;
                dispose();
            }
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnConfirmar);
        buttonPanel.add(btnCancelar);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private boolean validarCampos() {
        String senhaAtual = new String(senhaAtualField.getPassword());
        String novaSenha = new String(novaSenhaField.getPassword());
        String confirmarSenha = new String(confirmarSenhaField.getPassword());
        
        if (senhaAtual.isEmpty() || novaSenha.isEmpty() || confirmarSenha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Preencha todos os campos", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!novaSenha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, 
                "As senhas não coincidem", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (novaSenha.length() < 8) {
            JOptionPane.showMessageDialog(this, 
                "A senha deve ter pelo menos 8 caracteres", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean alterarSenha(int idUsuario, boolean isBibliotecario) {
        String senhaAtual = new String(senhaAtualField.getPassword());
        String novaSenha = new String(novaSenhaField.getPassword());
        
        String hashAtual = BibliotecarioDAO.gerarHashSHA256(senhaAtual);
        String hashPadrao = BibliotecarioDAO.gerarHashSHA256("sgel@1234");
        
        if (!hashAtual.equals(hashPadrao)) {
            JOptionPane.showMessageDialog(this, 
                "Senha atual incorreta", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean sucesso = isBibliotecario 
            ? BibliotecarioDAO.alterarSenha(idUsuario, novaSenha)
            : AlunoDAO.alterarSenha(idUsuario, novaSenha);
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                "Senha alterada com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erro ao alterar senha", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean isSenhaAlterada() {
        return senhaAlterada;
    }
}
