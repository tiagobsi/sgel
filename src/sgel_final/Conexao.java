package sgel_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe respons�vel pela conex�o com o banco de dados MySQL
 * @author Tiago
 */
public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/sgel";
    private static final String USER = "root";
    private static final String PASSWORD = "timao";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conex�o estabelecida com sucesso!");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC n�o encontrado!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar ao banco de dados", e);
        }
    }
}

