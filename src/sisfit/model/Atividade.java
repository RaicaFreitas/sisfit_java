package sisfit.model;

import java.time.LocalDate;

public class Atividade {
    private int id;
    private int clienteId;
    private int funcionarioId;
    private String descricao;
    private int series;
    private int repeticoes;
    private LocalDate dataAtividade;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }

    public int getRepeticoes() { return repeticoes; }
    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }

    public LocalDate getDataAtividade() { return dataAtividade; }
    public void setDataAtividade(LocalDate dataAtividade) { this.dataAtividade = dataAtividade; }
}