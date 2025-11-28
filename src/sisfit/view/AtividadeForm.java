package sisfit.view;

import sisfit.dao.AtividadeDAO;
import sisfit.dao.ClienteDAO;
import sisfit.dao.FuncionarioDAO;
import sisfit.model.Atividade;
import sisfit.model.Cliente;
import sisfit.model.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AtividadeForm extends JFrame {
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboFuncionario;
    private JTextArea txtDescricao;
    private JTextField txtSeries, txtRepeticoes;
    private JSpinner spinnerData;
    private JButton btnSalvar, btnListar;
    private JTextArea areaAtividades;

    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;

    public AtividadeForm() {
        setTitle("Cadastro de Atividades - SISFIT");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        carregarDados();

        panelForm.add(new JLabel("Cliente:"));
        comboCliente = new JComboBox<>();
        for (Cliente c : clientes) {
            comboCliente.addItem(c.getId() + " - " + c.getNome());
        }
        panelForm.add(comboCliente);

        panelForm.add(new JLabel("Funcionário:"));
        comboFuncionario = new JComboBox<>();
        for (Funcionario f : funcionarios) {
            comboFuncionario.addItem(f.getId() + " - " + f.getNome());
        }
        panelForm.add(comboFuncionario);

        panelForm.add(new JLabel("Data:"));
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerData = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy");
        spinnerData.setEditor(dateEditor);
        panelForm.add(spinnerData);

        panelForm.add(new JLabel("Descrição:"));
        txtDescricao = new JTextArea(3, 20);
        txtDescricao.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        panelForm.add(scrollDesc);

        panelForm.add(new JLabel("Séries:"));
        txtSeries = new JTextField();
        panelForm.add(txtSeries);

        panelForm.add(new JLabel("Repetições:"));
        txtRepeticoes = new JTextField();
        panelForm.add(txtRepeticoes);

        btnSalvar = new JButton("Salvar");
        btnListar = new JButton("Listar Atividades");

        panelForm.add(btnSalvar);
        panelForm.add(btnListar);

        add(panelForm, BorderLayout.NORTH);

        areaAtividades = new JTextArea();
        areaAtividades.setEditable(false);
        add(new JScrollPane(areaAtividades), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarAtividade());
        btnListar.addActionListener(e -> listarAtividades());
    }

    private void carregarDados() {
        ClienteDAO clienteDAO = new ClienteDAO();
        clientes = clienteDAO.listarTodos();

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarios = funcionarioDAO.listarTodos();
    }

    private void salvarAtividade() {
        try {
            if (comboCliente.getSelectedIndex() == -1 || comboFuncionario.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione cliente e funcionário!");
                return;
            }

            Atividade a = new Atividade();

            String clienteSelecionado = (String) comboCliente.getSelectedItem();
            int clienteId = Integer.parseInt(clienteSelecionado.split(" - ")[0]);
            a.setClienteId(clienteId);

            String funcionarioSelecionado = (String) comboFuncionario.getSelectedItem();
            int funcionarioId = Integer.parseInt(funcionarioSelecionado.split(" - ")[0]);
            a.setFuncionarioId(funcionarioId);

            // Converter data do JSpinner para LocalDate
            java.util.Date dataUtil = (java.util.Date) spinnerData.getValue();
            java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
            a.setDataAtividade(dataSql.toLocalDate());

            a.setDescricao(txtDescricao.getText());
            a.setSeries(Integer.parseInt(txtSeries.getText()));
            a.setRepeticoes(Integer.parseInt(txtRepeticoes.getText()));

            AtividadeDAO dao = new AtividadeDAO();
            dao.salvar(a);

            JOptionPane.showMessageDialog(this, "Atividade salva com sucesso!");
            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos!");
        }
    }

    private void listarAtividades() {
        AtividadeDAO dao = new AtividadeDAO();
        areaAtividades.setText("");    for (Atividade a : dao.listarTodos()) {
            String cliente = buscarNomeCliente(a.getClienteId());
            String funcionario = buscarNomeFuncionario(a.getFuncionarioId());
            areaAtividades.append(String.format(
                    "ID: %d | Data: %s | Cliente: %s | Funcionário: %s\nDescrição: %s | Séries: %d | Repetições: %d\n\n",
                    a.getId(), a.getDataAtividade(), cliente, funcionario, a.getDescricao(), a.getSeries(), a.getRepeticoes()
            ));
        }
    }private String buscarNomeCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c.getNome();
        }
        return "Desconhecido";
    }private String buscarNomeFuncionario(int id) {
        for (Funcionario f : funcionarios) {
            if (f.getId() == id) return f.getNome();
        }
        return "Desconhecido";
    }private void limparCampos() {
        txtDescricao.setText("");
        txtSeries.setText("");
        txtRepeticoes.setText("");
        spinnerData.setValue(new java.util.Date());
    }
}