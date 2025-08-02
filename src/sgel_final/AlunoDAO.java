package sgel_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Tiago
 */
public class AlunoDAO {
    
    public static void cadastrarAluno(AlunoDTO aluno) {
        String sql = "INSERT INTO aluno (nome, matricula, turma, email, telefone, idResponsavel, idPerfil) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            AlunoDAO alunoDAO = new AlunoDAO();
            alunoDAO.setAlunoParametros(stmt, aluno);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        aluno.setId(rs.getInt(1));
                    }
                }
                System.out.println("Aluno cadastrado com ID: " + aluno.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar aluno", e);
        }
    }
    
    public static AlunoDTO buscarPorId(int id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractAlunoFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por ID", e);
        }
        
        return null;
    }
    
    public static AlunoDTO buscarPorMatricula(String matricula) {
        String sql = "SELECT * FROM aluno WHERE matricula = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractAlunoFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por matrícula", e);
        }
        
        return null;
    }
    
    public static List<AlunoDTO> listarTodos() {
        String sql = "SELECT * FROM aluno";
        List<AlunoDTO> alunos = new ArrayList<>();
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                alunos.add(extractAlunoFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos", e);
        }
        
        return alunos;
    }
    
    public static List<AlunoDTO> buscarAlunos(String termoBusca) {
        if (termoBusca == null || termoBusca.isEmpty()) {
            return listarTodos();
        }
        
        String sql = "SELECT * FROM aluno WHERE nome LIKE ? OR matricula LIKE ? OR turma LIKE ?";
        List<AlunoDTO> alunos = new ArrayList<>();
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String termo = "%" + termoBusca + "%";
            stmt.setString(1, termo);
            stmt.setString(2, termo);
            stmt.setString(3, termo);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                alunos.add(extractAlunoFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar alunos", e);
        }
        
        return alunos;
    }
    
    private static AlunoDTO extractAlunoFromResultSet(ResultSet rs) throws SQLException {
        AlunoDTO aluno = new AlunoDTO();
        aluno.setId(rs.getInt("idAluno"));
        aluno.setNome(rs.getString("nome"));
        aluno.setMatricula(rs.getString("matricula"));
        aluno.setSerieTurma(rs.getString("turma"));
        aluno.setEmail(rs.getString("email"));
        aluno.setTelefone(rs.getString("telefone"));
        aluno.setIdResponsavel(rs.getInt("idResponsavel"));
        aluno.setIdPerfil(rs.getInt("idPerfil"));
        return aluno;
    }
    
    private static void setAlunoParametros(PreparedStatement stmt, AlunoDTO aluno) throws SQLException {
        stmt.setString(1, aluno.getNome());
        stmt.setString(2, aluno.getMatricula());
        stmt.setString(3, aluno.getSerieTurma());
        stmt.setString(4, aluno.getEmail());
        stmt.setString(5, aluno.getTelefone());
        stmt.setInt(6, aluno.getIdResponsavel());
        stmt.setInt(7, aluno.getIdPerfil());
    }
    
    public static AlunoDTO buscarPorEmail(String email) {
        String sql = "SELECT * FROM aluno WHERE email = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractAlunoFromResultSet(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por email", e);
        }

        return null;
    }
}
