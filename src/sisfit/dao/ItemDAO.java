package sisfit.dao;

import sisfit.db.ConnectionFactory;
import sisfit.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public void salvar(Item item) {
        String sql = "INSERT INTO itens (nome, tipo, valor) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getTipo());
            stmt.setDouble(3, item.getValor());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> listarTodos() {
        List<Item> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item i = new Item();
                i.setId(rs.getInt("id"));
                i.setNome(rs.getString("nome"));
                i.setTipo(rs.getString("tipo"));
                i.setValor(rs.getDouble("valor"));
                lista.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}