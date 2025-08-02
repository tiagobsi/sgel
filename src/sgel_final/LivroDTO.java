package sgel_final;

/**
 * Data Transfer Object para Livro
 * @author Tiago
 */
public class LivroDTO {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private String editora;
    private int anoPublicacao;
    private boolean isDisponivel;
    private String genero;
    private int quantidade;

    public LivroDTO(int id, String titulo, String autor, String editora, int anoPublicacao, String isbn, Boolean isDisponivel, String genero, int quantidade) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.isDisponivel = isDisponivel;
        this.genero = genero;
        this.quantidade = quantidade;
    }

    public LivroDTO() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEditora() {
        return editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public boolean IsDisponivel() {
        return isDisponivel;
    }

    public String getGenero() {
        return genero;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public void setIsDisponivel(boolean isDisponivel) {
        this.isDisponivel = isDisponivel;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}

