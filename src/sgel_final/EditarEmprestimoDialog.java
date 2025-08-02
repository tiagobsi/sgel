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
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class EditarEmprestimoDialog extends JDialog {
    private JTextField txtAluno;
    private JTextField txtLivro;
    private JFormattedTextField txtDataEmprestimo;
    private JFormattedTextField txtDataDevolucao;
    private boolean confirmado = false;

    public EditarEmprestimoDialog(Frame parent, String aluno, String livro, 
                                String dataEmp, String dataDev) {
        super(parent, "Editar Empréstimo", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
       
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
       
        formPanel.add(new JLabel("Aluno:"));
        txtAluno = new JTextField(aluno);
        txtAluno.setEditable(false);
        formPanel.add(txtAluno);
        
        formPanel.add(new JLabel("Livro:"));
        txtLivro = new JTextField(livro);
        txtLivro.setEditable(false);
        formPanel.add(txtLivro);
        
        formPanel.add(new JLabel("Data Empréstimo:"));
        try {
            txtDataEmprestimo = new JFormattedTextField(new MaskFormatter("##/##/####"));
            txtDataEmprestimo.setValue(dataEmp);
        } catch (ParseException e) {
            txtDataEmprestimo = new JFormattedTextField();
            txtDataEmprestimo.setText(dataEmp);
        }
        formPanel.add(txtDataEmprestimo);
        

        formPanel.add(new JLabel("Data Devolução:"));
        try {
            txtDataDevolucao = new JFormattedTextField(new MaskFormatter("##/##/####"));
            txtDataDevolucao.setValue(dataDev);
        } catch (ParseException e) {
            txtDataDevolucao = new JFormattedTextField();
            txtDataDevolucao.setText(dataDev);
        }
        formPanel.add(txtDataDevolucao);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnSalvar.addActionListener(e -> {
            confirmado = true;
            dispose();
        });
        
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
    public String getDataEmprestimo() {
        return txtDataEmprestimo.getText();
    }
    
    public String getDataDevolucao() {
        return txtDataDevolucao.getText();
    }
}
