package sisfit.dao;

import sisfit.db.ConnectionFactory;
import sisfit.model.Pedido;
import sisfit.model.PedidoItem;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void salvar(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedidos (cliente_id, data_pedido, forma_pagamento, observacoes) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO pedido_itens (pedido_id, item_id, quantidade) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setInt(1, pedido.getClienteId());
            stmtPedido.setTimestamp(2, Timestamp.valueOf(pedido.getDataPedido()));
            stmtPedido.setString(3, pedido.getFormaPagamento());
            stmtPedido.setString(4, pedido.getObservacoes());
            stmtPedido.executeUpdate();

            ResultSet rs = stmtPedido.getGeneratedKeys();
            int pedidoId = 0;
            if (rs.next()) {
                pedidoId = rs.getInt(1);
            }

            PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
            for (PedidoItem item : pedido.getItens()) {
                stmtItem.setInt(1, pedidoId);
                stmtItem.setInt(2, item.getItemId());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setClienteId(rs.getInt("cliente_id"));

                Timestamp ts = rs.getTimestamp("data_pedido");
                if (ts != null) {
                    p.setDataPedido(ts.toLocalDateTime());
                }

                p.setFormaPagamento(rs.getString("forma_pagamento"));
                p.setObservacoes(rs.getString("observacoes"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}