/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgel_final;

/**
 *
 * @author Tiago
 */
public class BibliotecarioDTO {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String telefone;
    private int idPerfil;

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
    
    public String getSenha() { 
        return senha; 
    }
    
    public void setSenha(String senha) { 
        this.senha = senha; 
    }
    
    public String getCpf() {
        return cpf;
    }
    public String getTelefone() {
        return telefone;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public int getIdPerfil() {
        return idPerfil;
    }
    
    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }
    
}
