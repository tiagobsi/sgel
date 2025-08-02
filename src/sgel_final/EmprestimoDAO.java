package sgel_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para Emprestimo com integração ao banco de dados
 * @author Tiago
 */
public class EmprestimoDAO {
    
    public void cadastrarEmprestimo(EmprestimoDTO emprestimo) {
        String sql = "INSERT INTO emprestimo (idAluno, idLivro, dataEmprestimo, idStatus) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, emprestimo.getAluno().getId());
            stmt.setInt(2, emprestimo.getLivro().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setInt(4, emprestimo.getIdStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        emprestimo.setId(rs.getInt(1));
                    }
                }
                System.out.println("Empéstimo cadastrado com ID: " + emprestimo.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar empéstimo", e);
        }
    }
    
    public void devolverEmprestimo(int id) {
        String sql = "UPDATE emprestimo SET dataDevolucao = ?, idStatus = 2 WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Empéstimo devolvido com sucesso!");
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao devolver empéstimo", e);
        }
    }
    
    public List<EmprestimoDTO> listarTodos() {
        String sql = "SELECT e.*, a.nome as aluno_nome, a.matricula, l.titulo, l.autor " +
                     "FROM emprestimo e " +
                     "JOIN aluno a ON e.idAluno = a.idAluno " +
                     "JOIN livro l ON e.idLivro = l.idLivro";
        
        List<EmprestimoDTO> emprestimos = new ArrayList<>();
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                emprestimos.add(extractEmprestimoFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar empéstimos", e);
        }
        
        return emprestimos;
    }
    
    public List<EmprestimoDTO> listarPorAluno(int idAluno) {
        String sql = "SELECT e.*, a.nome as aluno_nome, a.matricula, l.titulo, l.autor " +
                     "FROM emprestimo e " +
                     "JOIN aluno a ON e.idAluno = a.id " +
                     "JOIN livro l ON e.idLivro = l.id " +
                     "WHERE e.idAluno = ?";
        
        List<EmprestimoDTO> emprestimos = new ArrayList<>();
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                emprestimos.add(extractEmprestimoFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar empéstimos do aluno", e);
        }
        
        return emprestimos;
    }
    
    private EmprestimoDTO extractEmprestimoFromResultSet(ResultSet rs) throws SQLException {
        EmprestimoDTO emprestimo = new EmprestimoDTO();
        emprestimo.setId(rs.getInt("idEmprestimo"));
        
        // Criar objetos simplificados de Aluno e Livro
        AlunoDTO aluno = new AlunoDTO();
        aluno.setId(rs.getInt("idAluno"));
        aluno.setNome(rs.getString("aluno_nome"));
        aluno.setMatricula(rs.getString("matricula"));
        
        LivroDTO livro = new LivroDTO();
        livro.setId(rs.getInt("idLivro"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        
        emprestimo.setAluno(aluno);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(rs.getTimestamp("dataEmprestimo").toLocalDateTime());
        
        Timestamp dataDevolucao = rs.getTimestamp("dataDevolucao");
        if (dataDevolucao != null) {
            emprestimo.setDataDevolucao(dataDevolucao.toLocalDateTime());
        }
        
        emprestimo.setIdStatus(rs.getInt("idStatus"));
        
        return emprestimo;
    }
}

