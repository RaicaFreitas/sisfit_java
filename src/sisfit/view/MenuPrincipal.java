package sisfit.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("SISFIT - Sistema de Gerenciamento de Academia");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menu Cadastros
        JMenu cadastroMenu = new JMenu("Cadastros");
        JMenuItem clienteItem = new JMenuItem("Clientes");
        JMenuItem funcionarioItem = new JMenuItem("Funcionários");
        JMenuItem itemItem = new JMenuItem("Itens");

        cadastroMenu.add(clienteItem);
        cadastroMenu.add(funcionarioItem);
        cadastroMenu.add(itemItem);

        // Menu Operações
        JMenu operacoesMenu = new JMenu("Operações");
        JMenuItem pedidoItem = new JMenuItem("Novo Pedido");
        JMenuItem atividadeItem = new JMenuItem("Atividades");

        operacoesMenu.add(pedidoItem);
        operacoesMenu.add(atividadeItem);

        // Menu Relatórios (NOVO)
        JMenu relatoriosMenu = new JMenu("Relatórios");
        JMenuItem listarPedidosItem = new JMenuItem("Listar Todos os Pedidos");

        relatoriosMenu.add(listarPedidosItem);

        menuBar.add(cadastroMenu);
        menuBar.add(operacoesMenu);
        menuBar.add(relatoriosMenu); // ADICIONAR ESTE MENU

        setJMenuBar(menuBar);

        // Painel central
        JPanel painelCentral = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("SISFIT - Sistema de Gerenciamento de Academia", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel lblSubtitulo = new JLabel("<html><center>Selecione uma opção no menu acima para começar<br><br>" +
                "• Cadastros: Gerencie clientes, funcionários e itens<br>" +
                "• Operações: Registre pedidos e atividades<br>" +
                "• Relatórios: Visualize pedidos e estatísticas</center></html>", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        painelCentral.add(lblTitulo, BorderLayout.NORTH);
        painelCentral.add(lblSubtitulo, BorderLayout.CENTER);

        add(painelCentral);

        // Ações dos menus
        clienteItem.addActionListener(e -> new ClienteForm().setVisible(true));
        funcionarioItem.addActionListener(e -> new FuncionarioForm().setVisible(true));
        itemItem.addActionListener(e -> new ItemForm().setVisible(true));
        pedidoItem.addActionListener(e -> new PedidoForm().setVisible(true));
        atividadeItem.addActionListener(e -> new AtividadeForm().setVisible(true));
        listarPedidosItem.addActionListener(e -> new PedidoListaForm().setVisible(true)); // NOVA AÇÃO

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
}