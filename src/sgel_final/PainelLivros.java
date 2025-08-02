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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class PainelLivros extends JPanel {
    private static JTable table;
    private static DefaultTableModel model;
    private JTextField searchField;
    private JComboBox<String> filterType;

    public PainelLivros() {
        setLayout(new BorderLayout());
        
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Livros Cadastrados");
        Tipografia.applyH1(title);
        header.add(title, BorderLayout.WEST);

        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchField.setToolTipText("Digite para filtrar...");
        
        filterType = new JComboBox<>(new String[]{"Todos", "Tí­tulo", "Autor", "Editora", "Ano", "ISBN"});
        styleComboBox(filterType);
        
        JButton searchBtn = new JButton("Filtrar");
        styleButton(searchBtn, Tipografia.PRIMARY_COLOR, false);
        searchBtn.addActionListener(this::aplicarFiltro);

        searchField.addActionListener(this::aplicarFiltro);
        
        searchPanel.add(new JLabel("Filtrar por:"));
        searchPanel.add(filterType);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        
        header.add(searchPanel, BorderLayout.EAST);
        
        String[] columns = {"Tí­tulo", "Autor", "Editora", "Ano", "Gênero", "ISBN", "Qtd"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        atualizarTabela();
    }

    private void aplicarFiltro(ActionEvent e) {
        String termo = searchField.getText().trim();
        String tipoFiltro = (String) filterType.getSelectedItem();
        
        List<LivroDTO> livrosFiltrados;
        
        if (tipoFiltro.equals("Todos") || termo.isEmpty()) {
            livrosFiltrados = new LivroDAO().listarLivros();
        } else {
            livrosFiltrados = new LivroDAO().listarLivros().stream()
                .filter(l -> {
                    switch (tipoFiltro) {
                        case "Tí­tulo": return l.getTitulo().toLowerCase().contains(termo.toLowerCase());
                        case "Autor": return l.getAutor().toLowerCase().contains(termo.toLowerCase());
                        case "Editora": return l.getEditora().toLowerCase().contains(termo.toLowerCase());
                        case "Ano": return String.valueOf(l.getAnoPublicacao()).contains(termo);
                        case "ISBN": return l.getIsbn().toLowerCase().contains(termo.toLowerCase());
                        case "Gênero": return l.getGenero().equalsIgnoreCase(termo);
                        default: return true;
                    }
                })
                .collect(Collectors.toList());
        }

        model.setRowCount(0);
        for (LivroDTO livro : livrosFiltrados) {
            model.addRow(new Object[]{
                livro.getTitulo(),
                livro.getAutor(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.getGenero(),
                livro.getIsbn(),
                livro.getQuantidade()
            });
        }
    }

    public static void atualizarTabela() {
        model.setRowCount(0);
        for (LivroDTO livro : new LivroDAO().listarLivros()) {
            model.addRow(new Object[]{
                livro.getTitulo(),
                livro.getAutor(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.getGenero(),
                livro.getIsbn(),
                livro.getQuantidade()
            });
        }
    }

    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
    }
    
    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(Tipografia.getButtonFont(false));
        combo.setBackground(Color.WHITE);
    }
}
