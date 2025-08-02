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
import javax.swing.table.TableCellRenderer;

public class PainelEmprestimosAluno extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private String matriculaAluno;
    
    public PainelEmprestimosAluno(String matricula) {
        this.matriculaAluno = matricula;
        setLayout(new BorderLayout());
        

        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Meus Empréstimos");
        Tipografia.applyH1(title);
        header.add(title, BorderLayout.WEST);
        

        String[] columns = {"Livro", "Data Empréstimo", "Data Devolução", "Status", "Ação"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; 
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 4 ? JButton.class : String.class;
            }
        };
        
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                

                if (column == 3) {
                    String status = (String) getValueAt(row, column);
                    if ("Devolvido".equals(status)) {
                        c.setBackground(new Color(200, 255, 200)); // Verde claro
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        };
        

        table.getColumn("Ação").setCellRenderer(new ButtonRenderer());
        table.getColumn("Ação").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.setRowHeight(40);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        

        atualizarListaEmprestimos();
    }
    
    public void atualizarListaEmprestimos() {
        model.setRowCount(0); // Limpa a tabela
        

        Object[][] dadosEmprestimos = {};
        
        for (Object[] row : dadosEmprestimos) {

            Object[] newRow = new Object[row.length + 1];
            System.arraycopy(row, 0, newRow, 0, row.length);
            newRow[4] = ""; 
            model.addRow(newRow);
        }
        

        for (int i = 0; i < table.getRowCount(); i++) {
            if ("Ativo".equals(model.getValueAt(i, 3))) {
                JButton devolverBtn = new JButton("Devolver");
                styleButton(devolverBtn, Tipografia.DEVOLVER_COLOR, false);
                
                final int rowIndex = i;
                devolverBtn.addActionListener(e -> devolverLivro(rowIndex));
                
                model.setValueAt(devolverBtn, i, 4);
            }
        }
    }
    
    private void devolverLivro(int row) {
        String livro = (String) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Confirmar devolução do livro:\n" + livro + "?", 
            "Confirmar Devolução", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            model.setValueAt("Devolvido", row, 3);
            model.setValueAt("", row, 4); 
            
            JOptionPane.showMessageDialog(this,
                "Devolução registrada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
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
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            this.removeAll();
            if (value instanceof Component) {
                this.add((Component) value);
            }
            return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            this.setClickCountToStart(1);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (value instanceof JButton) {
                button = (JButton) value;
            } else {
                button = new JButton();
            }
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            return button;
        }
    }
}
