package sgel_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Tiago
 */
public class ResponsavelDAO {
    
    public static void cadastrarResponsavel(ResponsavelDTO responsavel) {
        String sql = "INSERT INTO responsavel (nome, telefone, email, grauParentesco, cpf) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, responsavel.getNome());
            stmt.setString(2, responsavel.getTelefone());
            stmt.setString(3, responsavel.getEmail());
            stmt.setString(4, responsavel.getGrauParentesco());
            stmt.setString(5, responsavel.getCpf());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        responsavel.setId(rs.getInt(1));
                    }
                }
                System.out.println("Responsável cadastrado com ID: " + responsavel.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar responsável", e);
        }
    }
    
    public ResponsavelDTO buscarPorId(int id) {
        String sql = "SELECT * FROM responsavel WHERE id = ?";
        ResponsavelDTO responsavel = null;
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                responsavel = new ResponsavelDTO();
                responsavel.setId(rs.getInt("id"));
                responsavel.setNome(rs.getString("nome"));
                responsavel.setTelefone(rs.getString("telefone"));
                responsavel.setEmail(rs.getString("email"));
                responsavel.setGrauParentesco(rs.getString("grauParentesco"));
                responsavel.setCpf(rs.getString("cpf"));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar responsável", e);
        }
        
        return responsavel;
    }
}

