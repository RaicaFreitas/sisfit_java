package sisfit.view;

import sisfit.dao.ClienteDAO;
import sisfit.dao.ItemDAO;
import sisfit.dao.PedidoDAO;
import sisfit.model.Cliente;
import sisfit.model.Item;
import sisfit.model.Pedido;
import sisfit.model.PedidoItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoForm extends JFrame {
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboItem;
    private JTextField txtQuantidade;
    private JComboBox<String> comboFormaPagamento;
    private JTextArea txtObservacoes;
    private JButton btnAdicionarItem, btnSalvarPedido, btnLimpar;
    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;

    private List<Cliente> clientes;
    private List<Item> itens;
    private List<PedidoItem> itensPedido = new ArrayList<>();

    public PedidoForm() {
        setTitle("Cadastro de Pedidos - SISFIT");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        carregarDados();

        JPanel panelDados = new JPanel(new GridLayout(3, 2, 5, 5));
        panelDados.setBorder(BorderFactory.createTitledBorder("Dados do Pedido"));

        panelDados.add(new JLabel("Cliente:"));
        comboCliente = new JComboBox<>();
        for (Cliente c : clientes) {
            comboCliente.addItem(c.getId() + " - " + c.getNome());
        }
        panelDados.add(comboCliente);

        panelDados.add(new JLabel("Forma de Pagamento:"));
        comboFormaPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartão Débito", "Cartão Crédito", "PIX"});
        panelDados.add(comboFormaPagamento);

        panelDados.add(new JLabel("Observações:"));
        txtObservacoes = new JTextArea(2, 20);
        txtObservacoes.setLineWrap(true);
        panelDados.add(new JScrollPane(txtObservacoes));

        add(panelDados, BorderLayout.NORTH);

        JPanel panelItens = new JPanel(new BorderLayout(5, 5));
        panelItens.setBorder(BorderFactory.createTitledBorder("Itens do Pedido"));

        JPanel panelAdicionar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAdicionar.add(new JLabel("Item:"));

        comboItem = new JComboBox<>();
        for (Item i : itens) {
            comboItem.addItem(i.getId() + " - " + i.getNome() + " (R$ " + String.format("%.2f", i.getValor()) + ")");
        }
        comboItem.setPreferredSize(new Dimension(300, 25));
        panelAdicionar.add(comboItem);

        panelAdicionar.add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField(5);
        panelAdicionar.add(txtQuantidade);

        btnAdicionarItem = new JButton("Adicionar Item");
        panelAdicionar.add(btnAdicionarItem);

        panelItens.add(panelAdicionar, BorderLayout.NORTH);

        String[] colunas = {"ID Item", "Nome", "Quantidade", "Valor Unitário", "Subtotal"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaItens = new JTable(modeloTabela);
        panelItens.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);

        add(panelItens, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvarPedido = new JButton("Salvar Pedido");
        btnLimpar = new JButton("Limpar");
        panelBotoes.add(btnLimpar);
        panelBotoes.add(btnSalvarPedido);

        add(panelBotoes, BorderLayout.SOUTH);

        btnAdicionarItem.addActionListener(e -> adicionarItem());
        btnSalvarPedido.addActionListener(e -> salvarPedido());
        btnLimpar.addActionListener(e -> limparTudo());
    }

    private void carregarDados() {
        ClienteDAO clienteDAO = new ClienteDAO();
        clientes = clienteDAO.listarTodos();

        ItemDAO itemDAO = new ItemDAO();
        itens = itemDAO.listarTodos();
    }

    private void adicionarItem() {
        try {
            if (comboItem.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um item!");
                return;
            }

            int quantidade = Integer.parseInt(txtQuantidade.getText());
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!");
                return;
            }

            String itemSelecionado = (String) comboItem.getSelectedItem();
            int itemId = Integer.parseInt(itemSelecionado.split(" - ")[0]);

            Item item = buscarItem(itemId);
            if (item == null) {
                JOptionPane.showMessageDialog(this, "Item não encontrado!");
                return;
            }

            PedidoItem pedidoItem = new PedidoItem();
            pedidoItem.setItemId(itemId);
            pedidoItem.setQuantidade(quantidade);
            itensPedido.add(pedidoItem);

            double subtotal = item.getValor() * quantidade;
            modeloTabela.addRow(new Object[]{
                    item.getId(),
                    item.getNome(),
                    quantidade,
                    String.format("R$ %.2f", item.getValor()),
                    String.format("R$ %.2f", subtotal)
            });

            txtQuantidade.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
        }
    }

    private void salvarPedido() {
        if (comboCliente.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!");
            return;
        }

        if (itensPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um item ao pedido!");
            return;
        }

        try {
            Pedido pedido = new Pedido();

            String clienteSelecionado = (String) comboCliente.getSelectedItem();
            int clienteId = Integer.parseInt(clienteSelecionado.split(" - ")[0]);
            pedido.setClienteId(clienteId);

            pedido.setDataPedido(LocalDateTime.now());
            pedido.setFormaPagamento((String) comboFormaPagamento.getSelectedItem());
            pedido.setObservacoes(txtObservacoes.getText());
            pedido.setItens(itensPedido);

            PedidoDAO dao = new PedidoDAO();
            dao.salvar(pedido);

            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
            limparTudo();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pedido: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private Item buscarItem(int id) {
        for (Item i : itens) {
            if (i.getId() == id) return i;
        }
        return null;
    }

    private void limparTudo() {
        txtObservacoes.setText("");
        txtQuantidade.setText("");
        itensPedido.clear();
        modeloTabela.setRowCount(0);
    }
}