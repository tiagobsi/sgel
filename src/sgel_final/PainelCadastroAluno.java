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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PainelCadastroAluno extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;
    private JTextField txtNomeAluno, txtMatricula, txtSerieTurma, txtEmailAluno, txtTelefoneAluno;
    private JTextField txtNomeResponsavel, txtEmailResponsavel, txtTelefoneResponsavel, txtCpfResponsavel;
    private JComboBox<String> cbGrauParentesco;

    public PainelCadastroAluno() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.getHSBColor(0, 0, 0.9333f));
        
        JLabel titulo = new JLabel("Cadastro de Aluno");
        titulo.setFont(Tipografia.getH1Font());
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel alunoPanel = painelAluno();
        tabbedPane.addTab("Dados do Aluno", alunoPanel);
        
        JPanel responsavelPanel = painelResponsavel();
        tabbedPane.addTab("Dados do Responsável", responsavelPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnCadastrar = new JButton("Cadastrar");
        styleButton(btnCadastrar, Tipografia.PRIMARY_COLOR, true);
        btnCadastrar.addActionListener(this::cadastrarAluno);
        
        JButton btnLimpar = new JButton("Limpar Cadastro");
        styleButton(btnLimpar, Tipografia.SECONDARY_COLOR, false);
        btnLimpar.addActionListener(e -> limparCampos());
        
        buttonPanel.add(btnCadastrar);
        buttonPanel.add(btnLimpar);
        
        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel painelAluno() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.getHSBColor(0, 0, 0.9333f));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNomeAluno = new JTextField(25);
        panel.add(txtNomeAluno, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panel.add(new JLabel("Matrí­cula:"), gbc);
        gbc.gridx = 1;
        txtMatricula = new JTextField(25);
        txtMatricula.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { verificarMatricula(); }
            @Override public void removeUpdate(DocumentEvent e) { verificarMatricula(); }
            @Override public void changedUpdate(DocumentEvent e) { verificarMatricula(); }
            
            private void verificarMatricula() {
                String matricula = txtMatricula.getText().trim();
                if (!matricula.isEmpty() && AlunoDAO.buscarPorMatricula(matricula) != null) {
                    txtMatricula.setBackground(new Color(255, 200, 200));
                } else {
                    txtMatricula.setBackground(Color.WHITE);
                }
            }
        });
        panel.add(txtMatricula, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panel.add(new JLabel("Série/Turma:"), gbc);
        gbc.gridx = 1;
        txtSerieTurma = new JTextField(25);
        panel.add(txtSerieTurma, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        txtEmailAluno = new JTextField(25);
        panel.add(txtEmailAluno, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefoneAluno = new JTextField(25);
        panel.add(txtTelefoneAluno, gbc);
        
        return panel;
    }
    
    private JPanel painelResponsavel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.getHSBColor(0, 0, 0.9333f));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNomeResponsavel = new JTextField(25);
        panel.add(txtNomeResponsavel, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        txtEmailResponsavel = new JTextField(25);
        panel.add(txtEmailResponsavel, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefoneResponsavel = new JTextField(25);
        panel.add(txtTelefoneResponsavel, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panel.add(new JLabel("Grau Parentesco:"), gbc);
        gbc.gridx = 1;
        cbGrauParentesco = new JComboBox<>(new String[]{"Pai", "Mãe", "Tutor", "Outro"});
        styleComboBox(cbGrauParentesco);
        panel.add(cbGrauParentesco, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpfResponsavel = new JTextField(25);
        panel.add(txtCpfResponsavel, gbc);
        
        return panel;
    }
    
    private void cadastrarAluno(ActionEvent e) {
        // ValidaÃ§Ã£o bÃ¡sica
        if (txtNomeAluno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do aluno é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNomeAluno.requestFocus();
            return;
        }
        
        if (txtMatricula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A matrí­cula e obrigatória", "Erro", JOptionPane.ERROR_MESSAGE);
            txtMatricula.requestFocus();
            return;
        }
        
        if (AlunoDAO.buscarPorMatricula(txtMatricula.getText()) != null) {
            JOptionPane.showMessageDialog(this, 
                "Já existe um aluno com esta matrí­cula", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            txtMatricula.requestFocus();
            return;
        }
        
        if (!txtEmailAluno.getText().trim().isEmpty() && 
            !txtEmailAluno.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "E-mail do aluno inválido", "Erro", JOptionPane.ERROR_MESSAGE);
            txtEmailAluno.requestFocus();
            return;
        }
        
        if (txtNomeResponsavel.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do responsável é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNomeResponsavel.requestFocus();
            return;
        }
        
        Responsavel responsavel = new Responsavel();
        responsavel.setNome(txtNomeResponsavel.getText().trim());
        responsavel.setEmail(txtEmailResponsavel.getText().trim());
        responsavel.setTelefone(txtTelefoneResponsavel.getText().trim());
        responsavel.setGrauParentesco((String) cbGrauParentesco.getSelectedItem());
        responsavel.setCpf(txtCpfResponsavel.getText().trim());
        
        ResponsavelDTO responsavelDTO = new ResponsavelDTO();
        responsavelDTO.setNome(responsavel.nome);
        responsavelDTO.setCpf(responsavel.cpf);
        responsavelDTO.setEmail(responsavel.email);
        responsavelDTO.setGrauParentesco(responsavel.grauParentesco);
        responsavelDTO.setTelefone(responsavel.telefone);
        
        ResponsavelDAO.cadastrarResponsavel(responsavelDTO);
        responsavel.setId(responsavelDTO.getId());
        
        
        Aluno aluno = new Aluno();
        aluno.setNome(txtNomeAluno.getText().trim());
        aluno.setMatricula(txtMatricula.getText().trim());
        aluno.setSerieTurma(txtSerieTurma.getText().trim());
        aluno.setEmail(txtEmailAluno.getText().trim());
        aluno.setTelefone(txtTelefoneAluno.getText().trim());
        
        aluno.setResponsavel(responsavel);
        
        
        
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setNome(aluno.getNome());
        alunoDTO.setMatricula(aluno.getMatricula());
        alunoDTO.setSerieTurma(aluno.getSerieTurma());
        alunoDTO.setEmail(aluno.getEmail());
        alunoDTO.setTelefone(aluno.getTelefone());
        alunoDTO.setIdPerfil(1);
        alunoDTO.setIdResponsavel(aluno.getResponsavel().getId());
        
        
        AlunoDAO.cadastrarAluno(alunoDTO);
        
        JOptionPane.showMessageDialog(this, 
            "Aluno cadastrado com sucesso!\nMatrí­cula: " + aluno.getMatricula(), 
            "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);
        
        limparCampos();
    }
    
    private void limparCampos() {
        txtNomeAluno.setText("");
        txtMatricula.setText("");
        txtSerieTurma.setText("");
        txtEmailAluno.setText("");
        txtTelefoneAluno.setText("");
        txtNomeResponsavel.setText("");
        txtEmailResponsavel.setText("");
        txtTelefoneResponsavel.setText("");
        cbGrauParentesco.setSelectedIndex(0);
        txtCpfResponsavel.setText("");
        txtNomeAluno.requestFocus();
    }
    
    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(Tipografia.getButtonFont(false));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8))
        );
    }
    
    private void styleButton(JButton button, Color bgColor, boolean isPrimary) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(Tipografia.getButtonFont(isPrimary));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
    
    public static List<Aluno> buscarAlunos(String termo) {
        List<AlunoDTO> alunosDTO = AlunoDAO.buscarAlunos(termo);
        List<Aluno> alunos = new ArrayList<>();
        for (AlunoDTO dto : alunosDTO) {
            Aluno aluno = new Aluno();
            aluno.setNome(dto.getNome());
            aluno.setMatricula(dto.getMatricula());
            aluno.setSerieTurma(dto.getSerieTurma());
            aluno.setEmail(dto.getEmail());
            aluno.setTelefone(dto.getTelefone());
            alunos.add(aluno);
        }
        return alunos;
    }
    
    public List<Aluno> getAlunosCadastrados() {
        return buscarAlunos(""); // Retorna todos os alunos
    }
    
public static class Aluno implements Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private String nome, matricula, serieTurma, email, telefone;
        private Responsavel responsavel;
        
        public int getId() { 
            return id; 
        }        
        public void setId(int id) {
            this.id = id;
        }        
        public String getNome() { 
            return nome; 
        }
        public void setNome(String nome) { 
            this.nome = nome; 
        }
        public String getMatricula() { 
            return matricula; 
        }
        public void setMatricula(String matricula) {
            this.matricula = matricula; 
        }
        public String getSerieTurma() { 
            return serieTurma; 
        }
        public void setSerieTurma(String serieTurma) { 
            this.serieTurma = serieTurma; 
        }
        public String getEmail() { 
            return email; 
        }
        public void setEmail(String email) { 
            this.email = email; 
        }
        public String getTelefone() { 
            return telefone; 
        }
        public void setTelefone(String telefone) { 
            this.telefone = telefone; 
        }
        public Responsavel getResponsavel() { 
            return responsavel; 
        }
        public void setResponsavel(Responsavel responsavel) { 
            this.responsavel = responsavel; 
        }
        
        public String toSearchString() {
            return String.format("%s | %s | %s", nome, matricula, serieTurma);
        }
        
        @Override
        public String toString() {
            return nome + " (" + matricula + ")";
        }
    }
    
    public static class Responsavel implements Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private String nome, email, telefone, grauParentesco, cpf;
        
        public int getId() { 
            return id; 
        }        
        public void setId(int id) {
            this.id = id;
        }
        public String getNome() { 
            return nome; 
        }
        public void setNome(String nome) { 
            this.nome = nome; 
        }
        public String getEmail() { 
            return email; 
        }
        public void setEmail(String email) { 
            this.email = email; 
        }
        public String getTelefone() { 
            return telefone; 
        }
        public void setTelefone(String telefone) { 
            this.telefone = telefone; 
        }
        public String getGrauParentesco() { 
            return grauParentesco; 
        }
        public void setGrauParentesco(String grauParentesco) { 
            this.grauParentesco = grauParentesco; 
        }
        public String getCpf() { 
            return cpf; 
        }
        public void setCpf(String cpf) { 
            this.cpf = cpf; 
        }
    }
    
}
