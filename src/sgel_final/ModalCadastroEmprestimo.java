package sgel_final;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

public class ModalCadastroEmprestimo extends JDialog {
    
    private PainelEmprestimos painelEmprestimos;
    
    private PainelCadastroAluno.Aluno alunoSelecionado;
    private LivroDTO livroSelecionado;
    
    private EmprestimoListener listener;
    
    private JTextField txtAluno;
    private JTextField txtLivro;
    private JFormattedTextField txtDataEmprestimo;
    private JFormattedTextField txtDataDevolucao;
    private JButton btnBuscarAluno;
    private JButton btnBuscarLivro;
    private JButton btnSalvar;
    private JButton btnLimpar;
    
    public ModalCadastroEmprestimo(Frame parent) {
        super(parent, "Novo Empréstimo", true);
        this.painelEmprestimos = painelEmprestimos;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        initComponents();
    }
    
    public void setEmprestimoListener(EmprestimoListener listener) {
        this.listener = listener;
    }
    
    private void initComponents() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titulo = new JLabel("Cadastrar Empréstimo");
        titulo.setFont(new Font("Sans-serif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titulo, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        contentPanel.add(new JLabel("Aluno:"), gbc);
        
        txtAluno = new JTextField(20);
        txtAluno.setEditable(false);
        gbc.gridx = 1;
        contentPanel.add(txtAluno, gbc);
        
        btnBuscarAluno = new JButton("Buscar");
        styleButton(btnBuscarAluno, Tipografia.PRIMARY_COLOR, false);
        btnBuscarAluno.addActionListener(e -> buscarAluno());
        gbc.gridx = 2;
        contentPanel.add(btnBuscarAluno, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        contentPanel.add(new JLabel("Livro:"), gbc);
        
        txtLivro = new JTextField(20);
        txtLivro.setEditable(false);
        gbc.gridx = 1;
        contentPanel.add(txtLivro, gbc);
        
        btnBuscarLivro = new JButton("Buscar");
        styleButton(btnBuscarLivro, Tipografia.PRIMARY_COLOR, false);
        btnBuscarLivro.addActionListener(e -> buscarLivro());
        gbc.gridx = 2;
        contentPanel.add(btnBuscarLivro, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        contentPanel.add(new JLabel("Data Empréstimo:"), gbc);

        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            txtDataEmprestimo = new JFormattedTextField(dateFormatter);
            txtDataEmprestimo.setValue(getDataAtualFormatada());
            txtDataEmprestimo.setColumns(10);
            txtDataEmprestimo.setEditable(true); // Garante que é editÃ¡vel
        } catch (ParseException e) {
            txtDataEmprestimo = new JFormattedTextField();
            txtDataEmprestimo.setValue(getDataAtualFormatada());
        }
        gbc.gridx = 1;
        contentPanel.add(txtDataEmprestimo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        contentPanel.add(new JLabel("Data Devolução:"), gbc);
        
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            txtDataDevolucao = new JFormattedTextField(dateFormatter);
            txtDataDevolucao.setValue(getDataDevolucaoFormatada());
            txtDataDevolucao.setColumns(10);
            txtDataDevolucao.setEditable(true); // Garante que é editÃ¡vel
        } catch (ParseException e) {
            txtDataDevolucao = new JFormattedTextField();
            txtDataDevolucao.setValue(getDataDevolucaoFormatada());
        }
        
        gbc.gridx = 1;
        contentPanel.add(txtDataDevolucao, gbc);
         
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        btnLimpar = new JButton("Limpar Cadastro");
        styleButton(btnLimpar, Tipografia.SECONDARY_COLOR, false);
        btnLimpar.addActionListener(e -> limparCampos());
        buttonPanel.add(btnLimpar);
        
        btnSalvar = new JButton("Salvar");
        styleButton(btnSalvar, Tipografia.PRIMARY_COLOR, true);
        btnSalvar.addActionListener(e -> salvarEmprestimo());
        buttonPanel.add(btnSalvar);
        
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void buscarAluno() {
        try {
            
            Window janelaAncestral = SwingUtilities.getWindowAncestor(this);

           
            if (!(janelaAncestral instanceof BibliotecarioDashboard)) {
                throw new ClassCastException("A janela principal não é um BibliotecarioDashboard");
            }

            ModalBuscarAluno modalBuscarAluno = new ModalBuscarAluno((BibliotecarioDashboard) janelaAncestral);
            modalBuscarAluno.setVisible(true);

            this.alunoSelecionado = modalBuscarAluno.getAlunoSelecionado();
            if (this.alunoSelecionado != null) {
                txtAluno.setText(this.alunoSelecionado.getNome());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao buscar aluno: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }
    
    private void buscarLivro() {
        ModalBuscarLivro modalBuscarLivro = new ModalBuscarLivro(this);
        modalBuscarLivro.setVisible(true);
        
        this.livroSelecionado = modalBuscarLivro.getLivroSelecionado();
        
        if (this.livroSelecionado != null) {
             txtLivro.setText(this.livroSelecionado.getTitulo());
         }
    }
    
    private void limparCampos() {
        txtAluno.setText("");
        txtLivro.setText("");
        txtDataEmprestimo.setValue(getDataAtualFormatada());
        txtDataDevolucao.setValue(getDataDevolucaoFormatada());
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        txtDataDevolucao.setValue(calendar.getTime());
    }
    
    private void salvarEmprestimo() {
        if (txtAluno.getText().isEmpty() || txtLivro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Preencha todos os campos obrigatórios!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            EmprestimoDTO emprestimo = new EmprestimoDTO();

            // Converter aluno da interface para DTO
            AlunoDTO alunoDTO = new AlunoDTO();
            
            alunoDTO.setId(alunoSelecionado.getId());
            alunoDTO.setNome(alunoSelecionado.getNome());

            emprestimo.setAluno(alunoDTO);
            emprestimo.setLivro(livroSelecionado);
            emprestimo.setDataEmprestimo(converterData(txtDataEmprestimo.getText()));
            emprestimo.setDataDevolucao(converterData(txtDataDevolucao.getText()));
            emprestimo.setIdStatus(1); // Status "Ativo"
          
            EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
            emprestimoDAO.cadastrarEmprestimo(emprestimo);

            if (listener != null) {
                listener.onEmprestimoCadastrado(
                    txtAluno.getText(),
                    txtLivro.getText(),
                    txtDataEmprestimo.getText(),
                    txtDataDevolucao.getText()
                );
            }

            JOptionPane.showMessageDialog(this, "Empréstimo cadastrado com sucesso!");
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar empréstimo: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private LocalDateTime converterData(String dataStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            LocalDate data = LocalDate.parse(dataStr, formatter);
            
            return data.atStartOfDay();

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data invÃ¡lido. Use DD/MM/AAAA", e);
        }
    }
    
    private String getDataAtualFormatada() {
        Calendar cal = Calendar.getInstance();
        return String.format("%02d/%02d/%04d", 
            cal.get(Calendar.DAY_OF_MONTH),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.YEAR));
    }

    private String getDataDevolucaoFormatada() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7); // Adiciona 7 dias
        return String.format("%02d/%02d/%04d", 
            cal.get(Calendar.DAY_OF_MONTH),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.YEAR));
    }
    
    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        if (button == null) {
            System.err.println("Tentativa de estilizar botão nulo");
            return;
        }
        
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
}
