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

public class ModalBuscarAluno extends JDialog {
    private PainelCadastroAluno.Aluno alunoSelecionado;
    private JTextField txtBusca;
    private DefaultTableModel model;
    private JTable table;

    public ModalBuscarAluno(JFrame parent) {
        super(parent, "Buscar Aluno", true);
        setSize(700, 500);
        setLocationRelativeTo(parent);
        
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel painelPesquisa = new JPanel(new BorderLayout(10, 10));
        txtBusca = new JTextField();
        txtBusca.setToolTipText("Busque por nome, matrí­cula ou série/turma");
        
        JButton btnBuscar = new JButton("Buscar");
        styleButton(btnBuscar, Tipografia.PRIMARY_COLOR, false);
        btnBuscar.addActionListener(e -> atualizarTabela());
        
        txtBusca.addActionListener(e -> atualizarTabela());
        
        painelPesquisa.add(new JLabel("Buscar:"), BorderLayout.WEST);
        painelPesquisa.add(txtBusca, BorderLayout.CENTER);
        painelPesquisa.add(btnBuscar, BorderLayout.EAST);
        
        String[] colunas = {"Nome", "Matrí­cula", "Série/Turma", "E-mail", "Telefone"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSelecionar = new JButton("Selecionar");
        styleButton(btnSelecionar, Tipografia.PRIMARY_COLOR, true);
        btnSelecionar.addActionListener(this::selecionarAluno);
        
        JButton btnCancelar = new JButton("Cancelar");
        styleButton(btnCancelar, Tipografia.SECONDARY_COLOR, false);
        btnCancelar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSelecionar);
        
        painelPrincipal.add(painelPesquisa, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(table), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        atualizarTabela(); 
    }

    private void atualizarTabela() {
        model.setRowCount(0);
        
        String termoBusca = txtBusca.getText().trim();
        List<AlunoDTO> alunos = new AlunoDAO().buscarAlunos(termoBusca);
        
        for (AlunoDTO aluno : alunos) {
            model.addRow(new Object[]{
                aluno.getNome(),
                aluno.getMatricula(),
                aluno.getSerieTurma(),
                aluno.getEmail(),
                aluno.getTelefone()
            });
        }
    }

    private void selecionarAluno(ActionEvent e) {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada >= 0) {
            String matricula = (String) model.getValueAt(linhaSelecionada, 1);
            AlunoDTO alunoDTO = AlunoDAO.buscarPorMatricula(matricula);
            if (alunoDTO != null) {
                alunoSelecionado = new PainelCadastroAluno.Aluno();
                alunoSelecionado.setId(alunoDTO.getId());
                alunoSelecionado.setNome(alunoDTO.getNome());
                alunoSelecionado.setMatricula(alunoDTO.getMatricula());
                alunoSelecionado.setSerieTurma(alunoDTO.getSerieTurma());
                alunoSelecionado.setEmail(alunoDTO.getEmail());
                alunoSelecionado.setTelefone(alunoDTO.getTelefone());
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Selecione um aluno na tabela", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public PainelCadastroAluno.Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }
}