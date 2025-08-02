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

public class PainelCadastroBibliotecario extends JPanel {
    private JTextField nomeField, emailField, cpfField, telefoneField;

    public PainelCadastroBibliotecario() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        
        JLabel titulo = new JLabel("Cadastro de Bibliotecário");
        titulo.setFont(Tipografia.getH1Font());
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        nomeField = new JTextField(25);
        formPanel.add(nomeField, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(25);
        formPanel.add(emailField, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        cpfField = new JTextField(25);
        formPanel.add(cpfField, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        telefoneField = new JTextField(25);
        formPanel.add(telefoneField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton cadastrarBtn = new JButton("Cadastrar");
        styleButton(cadastrarBtn, Tipografia.PRIMARY_COLOR, true);
        cadastrarBtn.addActionListener(this::cadastrarBibliotecario);
        
        JButton limparBtn = new JButton("Limpar Cadastro");
        styleButton(limparBtn, Tipografia.SECONDARY_COLOR, false);
        limparBtn.addActionListener(e -> limparCampos());
        
        buttonPanel.add(cadastrarBtn);
        buttonPanel.add(limparBtn);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void cadastrarBibliotecario(ActionEvent e) {
       
        if (nomeField.getText().isEmpty() || emailField.getText().isEmpty() || 
            cpfField.getText().isEmpty() /*|| senhaField.getPassword().length == 0*/) {
            JOptionPane.showMessageDialog(this, 
                "Preencha os campos obrigatórios (Nome, E-mail e CPF)", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (BibliotecarioDAO.buscarPorEmail(emailField.getText()) != null) {
            JOptionPane.showMessageDialog(this, 
                "Este e-mail já está cadastrado", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        BibliotecarioDTO bibliotecario = new BibliotecarioDTO();
        bibliotecario.setNome(nomeField.getText());
        bibliotecario.setEmail(emailField.getText());
        bibliotecario.setCpf(cpfField.getText());
        bibliotecario.setTelefone(telefoneField.getText());
        bibliotecario.setIdPerfil(3);
        //bibliotecario.setSenha(new String(senhaField.getPassword()));


        BibliotecarioDAO.cadastrarBibliotecario(bibliotecario);

            JOptionPane.showMessageDialog(this, 
                "Bibliotecário cadastrado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            limparCampos();  
            
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
    }

    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
}
