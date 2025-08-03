/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgel_final;

/**
 *
 * @author Tiago
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BibliotecarioDAO {
    
    private static final String SENHA_PADRAO = "sgel@1234";
    
    public static void cadastrarBibliotecario(BibliotecarioDTO bibliotecario) {
        String sql = "INSERT INTO bibliotecario (nome, email, telefone, cpf, idPerfil, senha) VALUES (?, ?, ?, ?, ?,?)";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            BibliotecarioDAO bibliotecarioDAO = new BibliotecarioDAO();
            bibliotecarioDAO.setBibliotecarioParametros(stmt, bibliotecario);
            
            
            
            int affectedRows = stmt.executeUpdate();
             if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        bibliotecario.setId(rs.getInt(1));
                    }
                }
                System.out.println("Bibliotecario cadastrado com ID: " + bibliotecario.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar bibliotecário", e);
        }
    }
    
    private static void setBibliotecarioParametros(PreparedStatement stmt, BibliotecarioDTO bibliotecario) throws SQLException {
        String senhaHash = gerarHashSHA256(SENHA_PADRAO);
        stmt.setString(1, bibliotecario.getNome());
        stmt.setString(2, bibliotecario.getEmail());
        stmt.setString(3, bibliotecario.getTelefone());
        stmt.setString(4, bibliotecario.getCpf());
        stmt.setInt(5, bibliotecario.getIdPerfil());
        stmt.setString(6, senhaHash);
    }
    
    public static boolean alterarSenha(int idBibliotecario, String novaSenha) {
        String sql = "UPDATE bibliotecario SET senha = ? WHERE idBibliotecario = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String senhaHash = gerarHashSHA256(novaSenha);
            
            stmt.setString(1, senhaHash);
            stmt.setInt(2, idBibliotecario);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar senha", e);
        }
    }
    
    public static boolean verificarSenhaPadrao(String senhaHash) {
        String hashPadrao = gerarHashSHA256(SENHA_PADRAO);
        return hashPadrao.equals(senhaHash);
    }
    
    static String gerarHashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash SHA-256", e);
        }
    }
    
    
    public static BibliotecarioDTO buscarPorEmail(String email) {
        String sql = "SELECT * FROM bibliotecario WHERE email = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                BibliotecarioDTO bibliotecario = new BibliotecarioDTO();
                bibliotecario.setId(rs.getInt("idBibliotecario"));
                bibliotecario.setNome(rs.getString("nome"));
                bibliotecario.setEmail(rs.getString("email"));
                bibliotecario.setSenha(rs.getString("senha"));
                return bibliotecario;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar bibliotecário por email", e);
        }
        
        return null;
    }

}
