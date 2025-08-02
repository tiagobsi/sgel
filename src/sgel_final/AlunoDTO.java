package sgel_final;

/**
 * Data Transfer Object para Aluno
 * @author Tiago
 */
public class AlunoDTO {
    private int id;
    private String nome;
    private String matricula;
    private String serieTurma;
    private String email;
    private String telefone;
    private int idResponsavel;
    private int idPerfil;
    private String senha;

    public AlunoDTO(int id, String nome, String matricula, String serieTurma, String email, String telefone, int idResponsavel, int idPerfil) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.serieTurma = serieTurma;
        this.email = email;
        this.telefone = telefone;
        this.idResponsavel = idResponsavel;
        this.idPerfil = idPerfil;
    }

    public AlunoDTO() {
    }

    public int getId() { 
        return id; 
    }
    
    public String getNome() {
        return nome; 
    }
    
    public String getMatricula() {
        return matricula; 
    }

    public String getSerieTurma() {
        return serieTurma;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public int getIdPerfil() {
        return idPerfil;
    }
    
    public String getSenha() { 
        return senha; 
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setSerieTurma(String serieTurma) {
        this.serieTurma = serieTurma;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }
    
    public void setSenha(String senha) { 
        this.senha = senha; 
    }
}

