package sisfit.view;

import sisfit.dao.PedidoDAO;
import sisfit.dao.ClienteDAO;
import sisfit.model.Pedido;
import sisfit.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PedidoListaForm extends JFrame {
    private JTable tabelaPedidos;
    private DefaultTableModel modeloTabela;
    private JButton btnAtualizar;

    private List<Cliente> clientes;

    public PedidoListaForm() {
        setTitle("Lista de Pedidos - SISFIT");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel superior com título e botão
        JPanel panelTopo = new JPanel(new BorderLayout());
        panelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Todos os Pedidos Realizados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTopo.add(lblTitulo, BorderLayout.WEST);

        btnAtualizar = new JButton("Atualizar Lista");
        panelTopo.add(btnAtualizar, BorderLayout.EAST);

        add(panelTopo, BorderLayout.NORTH);

        // Tabela de pedidos
        String[] colunas = {"ID Pedido", "Cliente", "Data/Hora", "Forma Pagamento", /*"Itens",*/ "Observações"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaPedidos = new JTable(modeloTabela);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPedidos.getTableHeader().setReorderingAllowed(false);

        // Ajustar largura das colunas
        tabelaPedidos.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tabelaPedidos.getColumnModel().getColumn(1).setPreferredWidth(200); // Cliente
        tabelaPedidos.getColumnModel().getColumn(2).setPreferredWidth(150); // Data
        tabelaPedidos.getColumnModel().getColumn(3).setPreferredWidth(120); // Pagamento
//        tabelaPedidos.getColumnModel().getColumn(4).setPreferredWidth(50); // Iten
        tabelaPedidos.getColumnModel().getColumn(4).setPreferredWidth(300); // Observações

        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
        add(scrollPane, BorderLayout.CENTER);

        // Painel inferior com informações
        JPanel panelRodape = new JPanel();
        panelRodape.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblInfo = new JLabel("Clique duas vezes em um pedido para ver os detalhes");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        panelRodape.add(lblInfo);
        add(panelRodape, BorderLayout.SOUTH);

        // Carregar dados
        carregarClientes();
        carregarPedidos();

        // Ações
        btnAtualizar.addActionListener(e -> carregarPedidos());

        // Duplo clique para ver detalhes
        tabelaPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = tabelaPedidos.getSelectedRow();
                    if (row != -1) {
                        int pedidoId = (int) modeloTabela.getValueAt(row, 0);
                        mostrarDetalhesPedido(pedidoId);
                    }
                }
            }
        });
    }

    private void carregarClientes() {
        ClienteDAO clienteDAO = new ClienteDAO();
        clientes = clienteDAO.listarTodos();
    }

    private void carregarPedidos() {
        PedidoDAO dao = new PedidoDAO();
        modeloTabela.setRowCount(0); // Limpar tabela

        List<Pedido> pedidos = dao.listarTodos();

        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum pedido encontrado!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Pedido p : pedidos) {
            String nomeCliente = buscarNomeCliente(p.getClienteId());
            String dataFormatada = p.getDataPedido() != null ? p.getDataPedido().format(formatter) : "Sem data";
            String observacoes = p.getObservacoes() != null && !p.getObservacoes().isEmpty()
                    ? p.getObservacoes()
                    : "Sem observações";

            modeloTabela.addRow(new Object[]{
                    p.getId(),
                    nomeCliente,
                    dataFormatada,
                    p.getFormaPagamento(),
                    //p.getItens(),
                    observacoes

            });
        }
    }

    private String buscarNomeCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c.getNome();
            }
        }
        return "Cliente não encontrado";
    }

    private void mostrarDetalhesPedido(int pedidoId) {
        // Aqui você pode abrir uma nova janela com os detalhes completos do pedido
        // incluindo os itens que foram comprados
        JOptionPane.showMessageDialog(this,
                "Funcionalidade de detalhes do pedido #" + pedidoId + "\n" +
                        "Aqui você pode mostrar os itens comprados neste pedido.",
                "Detalhes do Pedido",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PedidoListaForm().setVisible(true);
        });
    }
}