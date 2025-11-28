package sisfit;

import sisfit.dao.ClienteDAO;
import sisfit.model.Cliente;

import javax.swing.*;
import java.awt.*;

public class ClienteForm extends JFrame {
    private JTextField txtNome, txtCpf, txtEmail;
    private JButton btnSalvar, btnListar;
    private JTextArea areaClientes;

    public ClienteForm() {
        setTitle("Cadastro de Clientes - SISFIT");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 2));

        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);

        panelForm.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        panelForm.add(txtCpf);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        btnSalvar = new JButton("Salvar");
        btnListar = new JButton("Listar Clientes");

        panelForm.add(btnSalvar);
        panelForm.add(btnListar);

        add(panelForm, BorderLayout.NORTH);

        // Área de listagem
        areaClientes = new JTextArea();
        add(new JScrollPane(areaClientes), BorderLayout.CENTER);

        // Ações dos botões
        btnSalvar.addActionListener(e -> salvarCliente());
        btnListar.addActionListener(e -> listarClientes());
    }

    private void salvarCliente() {
        Cliente c = new Cliente();
        c.setNome(txtNome.getText());
        c.setCpf(txtCpf.getText());
        c.setEmail(txtEmail.getText());

        ClienteDAO dao = new ClienteDAO();
        dao.salvar(c);

        JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
    }

    private void listarClientes() {
        ClienteDAO dao = new ClienteDAO();
        areaClientes.setText("");

        for (Cliente c : dao.listarTodos()) {
            areaClientes.append(c.getId() + " - " + c.getNome() + " - " + c.getCpf() + " - " + c.getEmail() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteForm().setVisible(true));
    }
}
