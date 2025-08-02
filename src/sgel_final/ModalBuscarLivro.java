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

public class ModalBuscarLivro extends JDialog {
    private LivroDTO livroSelecionado;
    private JTable livrosTable;
    private DefaultTableModel model;
    private JTextField txtValorFiltro;
    private JComboBox<String> cmbFiltro;
    
    public ModalBuscarLivro(JDialog parent) {
        super(parent, "Selecionar Livro", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        JPanel filtroPainel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPainel.setBackground(Color.WHITE);
        filtroPainel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        filtroPainel.add(new JLabel("Filtrar por:"));
        cmbFiltro = new JComboBox<>(new String[]{"Todos", "Tí­tulo", "Autor", "Editora", "Ano", "ISBN"});
        styleComboBox(cmbFiltro);
        filtroPainel.add(cmbFiltro);
        
        txtValorFiltro = new JTextField(20);
        txtValorFiltro.setToolTipText("Digite para filtrar...");
        filtroPainel.add(txtValorFiltro);
        
        JButton btnBuscar = new JButton("Filtrar");
        styleButton(btnBuscar, Tipografia.PRIMARY_COLOR, false);
        btnBuscar.addActionListener(this::aplicarFiltro);
        filtroPainel.add(btnBuscar);
        
        add(filtroPainel, BorderLayout.NORTH);
        
        String[] columns = {"Tí­tulo", "Autor", "Editora", "Ano", "ISBN"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        livrosTable = new JTable(model);
        livrosTable.setAutoCreateRowSorter(true);
        livrosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(livrosTable);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnSelecionar = new JButton("Selecionar");
        styleButton(btnSelecionar, Tipografia.PRIMARY_COLOR, true);
        btnSelecionar.addActionListener(this::selecionarLivro);
        buttonPanel.add(btnSelecionar);
        
        JButton btnCancelar = new JButton("Cancelar");
        styleButton(btnCancelar, Tipografia.SECONDARY_COLOR, false);
        btnCancelar.addActionListener(e -> dispose());
        buttonPanel.add(btnCancelar);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        aplicarFiltro(null);
    }
    
    private void selecionarLivro(ActionEvent e) {
        int selectedRow = livrosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String isbn = (String) model.getValueAt(livrosTable.convertRowIndexToModel(selectedRow), 4);
            
            livroSelecionado = LivroDAO.listarLivros().stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
                
            if (livroSelecionado != null) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Livro não encontrado", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Selecione um livro na tabela", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void aplicarFiltro(ActionEvent e) {
        String termo = txtValorFiltro.getText().trim();
        String tipoFiltro = (String) cmbFiltro.getSelectedItem();
        
        List<LivroDTO> livrosFiltrados;
        
        if (tipoFiltro.equals("Todos") || termo.isEmpty()) {
            livrosFiltrados = LivroDAO.listarLivros();
        } else {
            livrosFiltrados = LivroDAO.filtrarLivros(termo).stream()
                .filter(l -> {
                    switch (tipoFiltro) {
                        case "Tí­tulo": 
                            return l.getTitulo().toLowerCase().contains(termo.toLowerCase());
                        case "Autor": 
                            return l.getAutor().toLowerCase().contains(termo.toLowerCase());
                        case "Editora": 
                            return l.getEditora().toLowerCase().contains(termo.toLowerCase());
                        case "Ano": 
                            return String.valueOf(l.getAnoPublicacao()).contains(termo);
                        case "ISBN": 
                            return l.getIsbn().toLowerCase().contains(termo.toLowerCase());
                        default: 
                            return true;
                    }
                })
                .collect(Collectors.toList());
        }
        
        atualizarTabela(livrosFiltrados);
    }
    
    private void atualizarTabela(List<LivroDTO> livros) {
        model.setRowCount(0);
        for (LivroDTO livro : livros) {
            model.addRow(new Object[]{
                livro.getTitulo(),
                livro.getAutor(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.getIsbn()
            });
        }
    }
    
    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
    
    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(Tipografia.getButtonFont(false));
        combo.setBackground(Color.WHITE);
    }
    
    public LivroDTO getLivroSelecionado() {
        return livroSelecionado;
    }
}
