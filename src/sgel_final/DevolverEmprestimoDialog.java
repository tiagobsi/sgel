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
import java.text.SimpleDateFormat;
import java.util.Date;

public class DevolverEmprestimoDialog extends JDialog {
    private boolean confirmado = false;
    private JLabel lblDataDevolucao;
    
    public DevolverEmprestimoDialog(Frame parent) {
        super(parent, "Registrar Devolução", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        infoPanel.add(new JLabel("Confirma a devolução do empréstimo?"));
        
        JPanel dataPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dataPanel.add(new JLabel("Data de devolução:"));
        lblDataDevolucao = new JLabel(dataAtual);
        dataPanel.add(lblDataDevolucao);
        infoPanel.add(dataPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnConfirmar = new JButton("Confirmar Devolução");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnConfirmar.addActionListener(e -> {
            confirmado = true;
            dispose();
        });
        
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnConfirmar);
        buttonPanel.add(btnCancelar);
        
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
    public String getDataDevolucao() {
        return lblDataDevolucao.getText();
    }
}
