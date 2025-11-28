package sisfit.dao;

import sisfit.db.ConnectionFactory;
import sisfit.model.Atividade;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AtividadeDAO {

    public void salvar(Atividade atividade) {
        String sql = "INSERT INTO atividades (cliente_id, funcionario_id, descricao, series, repeticoes, data_atividade) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, atividade.getClienteId());
            stmt.setInt(2, atividade.getFuncionarioId());
            stmt.setString(3, atividade.getDescricao());
            stmt.setInt(4, atividade.getSeries());
            stmt.setInt(5, atividade.getRepeticoes());
            stmt.setDate(6, Date.valueOf(atividade.getDataAtividade()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Atividade> listarTodos() {
        List<Atividade> lista = new ArrayList<>();
        String sql = "SELECT * FROM atividades";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Atividade a = new Atividade();
                a.setId(rs.getInt("id"));
                a.setClienteId(rs.getInt("cliente_id"));
                a.setFuncionarioId(rs.getInt("funcionario_id"));
                a.setDescricao(rs.getString("descricao"));
                a.setSeries(rs.getInt("series"));
                a.setRepeticoes(rs.getInt("repeticoes"));

                Date data = rs.getDate("data_atividade");
                if (data != null) {
                    a.setDataAtividade(data.toLocalDate());
                }

                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}