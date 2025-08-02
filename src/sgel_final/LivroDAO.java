package sgel_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para Livro com integração ao banco de dados
 * @author Tiago
 */
public class LivroDAO {
    
    public static void cadastrarLivro(LivroDTO livro) {
        String sql = "INSERT INTO livro (titulo, autor, editora, anoPublicacao, isbn, quantidade, isDisponivel, genero) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getIsbn());
            stmt.setInt(6, livro.getQuantidade());
            stmt.setBoolean(7, livro.IsDisponivel());
            stmt.setString(8, livro.getGenero());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        livro.setId(rs.getInt(1));
                    }
                }
                System.out.println("Livro cadastrado com ID: " + livro.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar livro", e);
        }
    }
    
    public static LivroDTO buscarPorId(int id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractLivroFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por ID", e);
        }
        
        return null;
    }
    
    public static List<LivroDTO> listarTodos() {
        String sql = "SELECT * FROM livro";
        List<LivroDTO> livros = new ArrayList<>();
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                livros.add(extractLivroFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros", e);
        }
        
        return livros;
    }
    
    public static List<LivroDTO> filtrarLivros(String termoBusca) {
        if (termoBusca == null || termoBusca.isEmpty()) {
            return listarTodos();
        }
        
        String sql = "SELECT * FROM livro WHERE titulo LIKE ? OR autor LIKE ? OR editora LIKE ? OR isbn LIKE ? OR genero LIKE ?";
        List<LivroDTO> livros = new ArrayList<>();
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String termo = "%" + termoBusca + "%";
            stmt.setString(1, termo);
            stmt.setString(2, termo);
            stmt.setString(3, termo);
            stmt.setString(4, termo);
            stmt.setString(5, termo);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                livros.add(extractLivroFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao filtrar livros", e);
        }
        
        return livros;
    }
    
    private static LivroDTO extractLivroFromResultSet(ResultSet rs) throws SQLException {
        LivroDTO livro = new LivroDTO();
        livro.setId(rs.getInt("idLivro"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setEditora(rs.getString("editora"));
        livro.setAnoPublicacao(rs.getInt("anoPublicacao"));
        livro.setIsbn(rs.getString("isbn"));
        livro.setGenero(rs.getString("genero"));
        livro.setQuantidade(rs.getInt("quantidade"));
        livro.setIsDisponivel(rs.getBoolean("isDisponivel"));
        return livro;
    }
    
    public static List<LivroDTO> listarLivros() {
        return listarTodos();
    }
}
