package sgel_final;

import java.time.LocalDateTime;

/**
 * Data Transfer Object para Emprestimo
 * @author Tiago
 */
public class EmprestimoDTO {
    private int id;
    private AlunoDTO aluno;
    private LivroDTO livro;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao;
    private int idStatus;

    public EmprestimoDTO(int id, AlunoDTO aluno, LivroDTO livro, int idStatus) {
        this.id = id;
        this.aluno = aluno;
        this.livro = livro;
        this.dataEmprestimo = LocalDateTime.now();
        this.dataDevolucao = null;
        if (livro != null) {
            livro.setIsDisponivel(false);
        }
        this.idStatus = idStatus;
    }

    public EmprestimoDTO() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public AlunoDTO getAluno() {
        return aluno;
    }

    public LivroDTO getLivro() {
        return livro;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDateTime getDataDevolucao() { 
        return dataDevolucao; 
    }

    public int getIdStatus() {
        return idStatus;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }

    public void setLivro(LivroDTO livro) {
        this.livro = livro;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setDataDevolucao(LocalDateTime data) { 
        this.dataDevolucao = data;
        if (livro != null) {
            livro.setIsDisponivel(true);
        }
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
}

