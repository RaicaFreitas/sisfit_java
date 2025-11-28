package sisfit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Ajuste a URL de acordo com seu banco
    private static final String URL = "jdbc:mysql://127.0.0.1:3307/sisfit?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";      // seu usuário MySQL
    private static final String PASSWORD = "Senac@123"; // sua senha MySQL

    public static Connection getConnection() {
        try {
            // Garante que o driver seja carregado
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do MySQL não encontrado! Adicione o mysql-connector-j ao projeto.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}
