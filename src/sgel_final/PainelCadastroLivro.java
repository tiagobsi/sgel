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

public class PainelCadastroLivro extends JPanel {
    private JTextField tituloField, autorField, editoraField, anoField, isbnField, qtdField;
    private JComboBox<String> generoCombo;

    public PainelCadastroLivro() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
         
        JLabel titulo = new JLabel("Cadastro de Livro");
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
        formPanel.add(new JLabel("Tí­tulo:"), gbc);
        gbc.gridx = 1;
        tituloField = new JTextField(25);
        formPanel.add(tituloField, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        autorField = new JTextField(25);
        formPanel.add(autorField, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("Editora:"), gbc);
        gbc.gridx = 1;
        editoraField = new JTextField(25);
        formPanel.add(editoraField, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("Ano Publicação:"), gbc);
        gbc.gridx = 1;
        anoField = new JTextField(25);
        formPanel.add(anoField, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        generoCombo = new JComboBox<>(getGenerosLiterarios());
        styleComboBox(generoCombo);
        formPanel.add(generoCombo, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        isbnField = new JTextField(25);
        formPanel.add(isbnField, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        formPanel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1;
        qtdField = new JTextField(25);
        formPanel.add(qtdField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton cadastrarBtn = new JButton("Cadastrar");
        styleButton(cadastrarBtn, Tipografia.PRIMARY_COLOR, true);
        cadastrarBtn.addActionListener(this::cadastrarLivro);
        buttonPanel.add(cadastrarBtn);
        
        JButton limparBtn = new JButton("Limpar");
        styleButton(limparBtn, Tipografia.SECONDARY_COLOR, false);
        limparBtn.addActionListener(e -> limparCampos());
        buttonPanel.add(limparBtn);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(Tipografia.getButtonFont(false));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );
    }

    private String[] getGenerosLiterarios() {
        return new String[]{
            "Conto", "Fábula", "Lenda", "Mitologia", "Romance", 
            "Poesia", "Teatro", "Crônica", "Enciclopédia", "Dicionário",
            "Atlas", "Biografia", "Livro didático", "Livro paradidático",
            "Reportagem", "Artigo informativo", "Literatura infantil",
            "Literatura infantojuvenil", "História em quadrinhos (HQs)"
        };
    }

    private void cadastrarLivro(ActionEvent e) {
        try {
            LivroDTO novoLivro = new LivroDTO();
            novoLivro.setTitulo(tituloField.getText());
            novoLivro.setAutor(autorField.getText());
            novoLivro.setEditora(editoraField.getText());
            novoLivro.setAnoPublicacao(Integer.parseInt(anoField.getText()));
            novoLivro.setIsbn(isbnField.getText());
            novoLivro.setQuantidade(Integer.parseInt(qtdField.getText()));
            novoLivro.setGenero((String) generoCombo.getSelectedItem());
            novoLivro.setIsDisponivel(true); // Definir como disponÃ­vel por padrÃ£o

            new LivroDAO().cadastrarLivro(novoLivro);
            JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
            limparCampos();
            //PainelLivros.atualizarTabela();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Ano e Quantidade devem ser números válidos!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        tituloField.setText("");
        autorField.setText("");
        editoraField.setText("");
        anoField.setText("");
        generoCombo.setSelectedIndex(0);
        isbnField.setText("");
        qtdField.setText("");
    }

    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
}
