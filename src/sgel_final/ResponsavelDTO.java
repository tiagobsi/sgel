package sgel_final;

/**
 * 
 * @author Tiago
 */
public class ResponsavelDTO {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String grauParentesco;
    private String cpf;
    
    public ResponsavelDTO(int id, String nome, String telefone, String email, String grauParentesco, String cpf){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.grauParentesco = grauParentesco;
        this.cpf = cpf;
    }

    public ResponsavelDTO() {
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getGrauParentesco() {
        return grauParentesco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGrauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    public void setCpf(String cpf) {
        if (cpf != null && !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dí­gitos numéricos");
        }
        this.cpf = cpf;
    }
}

