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
import java.time.LocalDateTime;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class PainelEmprestimos extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    
    
    public PainelEmprestimos() {
        setLayout(new BorderLayout());
        
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Painel Empréstimos");
        Tipografia.applyH1(title);
        
        JButton addBtn = new JButton("Adicionar novo empréstimo");
        styleButton(addBtn, Tipografia.PRIMARY_COLOR, true);
        addBtn.addActionListener(e -> abrirCadastroEmprestimo());
        
        header.add(title, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);
        
        String[] columns = {"Aluno", "Livro", "Data Empr.", "Prev. Entrega", "Status", "Ações"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        
        table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 5 ? JPanel.class : Object.class;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (column == 4) {
                    String status = (String) getValueAt(row, column);
                    if ("Devolvido".equals(status)) {
                        c.setBackground(new Color(200, 255, 200)); 
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        };

        table.getColumn("Ações").setCellRenderer(new ButtonRenderer());
        table.getColumn("Ações").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.setRowHeight(55);
        
                
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        carregarEmprestimos();
        
    }
    
    private void abrirCadastroEmprestimo() {
        ModalCadastroEmprestimo cadastroEmprestimo = new ModalCadastroEmprestimo(
            (Frame)SwingUtilities.getWindowAncestor(this));

        cadastroEmprestimo.setEmprestimoListener((aluno, livro, dataEmp, dataDev) -> {
            adicionarEmprestimoATabela(aluno, livro, dataEmp, dataDev, "Ativo");
            revalidateButtons();
        });

        cadastroEmprestimo.setVisible(true);
    }
    
    private void carregarEmprestimos() {
        try {
            List<EmprestimoDTO> emprestimos = new EmprestimoDAO().listarTodos();

            for (EmprestimoDTO emp : emprestimos) {
                String status = converterStatus(emp.getIdStatus());
                String dataDevFormatada = emp.getDataDevolucao() != null ? 
                    formatarData(emp.getDataDevolucao()) : "";

                adicionarEmprestimoATabela(
                    emp.getAluno().getNome(),
                    emp.getLivro().getTitulo(),
                    formatarData(emp.getDataEmprestimo()),
                    dataDevFormatada,
                    status
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar empréstimos: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String converterStatus(int idStatus) {
    switch(idStatus) {
        case 1:
            return "Em andamento";
        case 2:
            return "Devolvido";
        case 3:
            return "Em atraso";
        default:
            return "Desconhecido";
    }
}
    
    public void adicionarEmprestimoATabela(String aluno, String livro, String dataEmprestimo, 
                                     String dataDevolucao, String status) {
        
        if ("Ativo".equals(status)) {
            for (int i = 0; i < model.getRowCount(); i++) {
                String alunoExistente = (String) model.getValueAt(i, 0);
                String livroExistente = (String) model.getValueAt(i, 1);
                String statusExistente = (String) model.getValueAt(i, 4);

                if (alunoExistente.equals(aluno) && 
                    livroExistente.equals(livro) && 
                    "Ativo".equals(statusExistente)) {

                    JOptionPane.showMessageDialog(this,
                        "Já existe um empréstimo ativo para este aluno e livro",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        }

        // Cria os botÃµes de aÃ§Ã£o
        JButton editarBtn = new JButton("Editar");
        editarBtn.setPreferredSize(new Dimension(60, 25));
        JButton excluirBtn = new JButton("Excluir");
        excluirBtn.setPreferredSize(new Dimension(60, 25));
        JButton devolverBtn = new JButton("Devolver");
        devolverBtn.setPreferredSize(new Dimension(80, 25));

        configurarBotoesAcao(editarBtn, excluirBtn, devolverBtn);

        JPanel painelBotoes = criarPainelBotoes(editarBtn, excluirBtn, devolverBtn);
        painelBotoes.setOpaque(false);

       
        if ("Devolvido".equals(status)) {
            devolverBtn.setEnabled(false);
        }

        model.addRow(new Object[]{
            aluno, livro, dataEmprestimo, dataDevolucao, status, painelBotoes
        });

        table.setRowSorter(new TableRowSorter<>(model));
    }
    
    private String formatarData(LocalDateTime data) {
        if (data == null) return "";
        return String.format("%02d/%02d/%04d", 
            data.getDayOfMonth(),
            data.getMonthValue(),
            data.getYear());
    }
    private JPanel criarPainelBotoes(JButton... botoes) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setOpaque(false);

        for (JButton btn : botoes) {
            if (btn.getText().equals("Editar")) {
                styleButton(btn, Tipografia.SECONDARY_COLOR, false);
            } else if (btn.getText().equals("Excluir")) {
                styleButton(btn, Tipografia.DANGER_COLOR, false);
            } else if (btn.getText().equals("Devolver")) {
                styleButton(btn, Tipografia.DEVOLVER_COLOR, false); 
            }
            panel.add(btn);
        }
        return panel;
    }
    
    private void configurarBotoesAcao(JButton editarBtn, JButton excluirBtn, JButton devolverBtn) {
        editarBtn.addActionListener(e -> {

            int row = -1;
            for (int i = 0; i < table.getRowCount(); i++) {
                Component comp = table.prepareRenderer(
                    table.getCellRenderer(i, 5), i, 5);
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    for (Component btn : panel.getComponents()) {
                        if (btn == e.getSource()) {
                            row = i;
                            break;
                        }
                    }
                }
                if (row != -1) break;
            }

            if (row >= 0) {

                String aluno = (String) model.getValueAt(row, 0);
                String livro = (String) model.getValueAt(row, 1);
                String dataEmp = (String) model.getValueAt(row, 2);
                String dataDev = (String) model.getValueAt(row, 3);


                EditarEmprestimoDialog cadasrroEmprestimo = new EditarEmprestimoDialog(
                    (Frame)SwingUtilities.getWindowAncestor(this),
                    aluno, livro, dataEmp, dataDev);
                cadasrroEmprestimo.setVisible(true);


                if (cadasrroEmprestimo.isConfirmado()) {
                    model.setValueAt(cadasrroEmprestimo.getDataEmprestimo(), row, 2);
                    model.setValueAt(cadasrroEmprestimo.getDataDevolucao(), row, 3);
                    model.setValueAt("Ativo", row, 4);
                }
            }
        });

        excluirBtn.addActionListener(e -> {

            int row = -1;
            for (int i = 0; i < table.getRowCount(); i++) {
                Component comp = table.prepareRenderer(
                    table.getCellRenderer(i, 5), i, 5);
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    for (Component btn : panel.getComponents()) {
                        if (btn == e.getSource()) {
                            row = i;
                            break;
                        }
                    }
                }
                if (row != -1) break;
            }

            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(
                    this, 
                    "Tem certeza que deseja excluir este empréstimo?", 
                    "Confirmar Exclusão", 
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            }
        });

        devolverBtn.addActionListener(e -> {
            int row = -1;
            for (int i = 0; i < table.getRowCount(); i++) {
                Component comp = table.prepareRenderer(
                    table.getCellRenderer(i, 5), i, 5);
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    for (Component btn : panel.getComponents()) {
                        if (btn == e.getSource()) {
                            row = i;
                            break;
                        }
                    }
                }
                if (row != -1) break;
            }

            if (row >= 0) {

                if ("Devolvido".equals(model.getValueAt(row, 4))) {
                    JOptionPane.showMessageDialog(this,
                        "Este empréstimo já foi devolvido",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }


                DevolverEmprestimoDialog cadasrroEmprestimo = new DevolverEmprestimoDialog(
                    (Frame)SwingUtilities.getWindowAncestor(this));
                cadasrroEmprestimo.setVisible(true);

                if (cadasrroEmprestimo.isConfirmado()) {

                    model.setValueAt(cadasrroEmprestimo.getDataDevolucao(), row, 3);
                    model.setValueAt("Devolvido", row, 4); 

                    JOptionPane.showMessageDialog(this,
                        "Devolução registrada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });        
    }
    
    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(Tipografia.TEXT_LIGHT);
        button.setFont(Tipografia.getButtonFont(isPrimary));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

        

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            return (Component) value;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }
        
        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {
            return (Component) value;
        }
    }
    
    public void revalidateButtons() {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < model.getRowCount(); i++) {
                Object value = model.getValueAt(i, 5);
                if (value instanceof JPanel) {
                    ((JPanel) value).revalidate();
                }
            }
            table.revalidate();
            table.repaint();
        });
    }
}