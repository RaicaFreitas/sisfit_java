package sisfit.view;

import sisfit.dao.ClienteDAO;
import sisfit.model.Cliente;

import javax.swing.*;
import java.awt.*;

public class ClienteForm extends JFrame {
    private JTextField txtNome, txtCpf, txtTelefone, txtEmail;
    private JButton btnSalvar, btnListar;
    private JTextArea areaClientes;

    public ClienteForm() {
        setTitle("Cadastro de Clientes - SISFIT");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);

        panelForm.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        panelForm.add(txtCpf);

        panelForm.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        panelForm.add(txtTelefone);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        btnSalvar = new JButton("Salvar");
        btnListar = new JButton("Listar Clientes");

        panelForm.add(btnSalvar);
        panelForm.add(btnListar);

        add(panelForm, BorderLayout.NORTH);

        areaClientes = new JTextArea();
        areaClientes.setEditable(false);
        add(new JScrollPane(areaClientes), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarCliente());
        btnListar.addActionListener(e -> listarClientes());
    }

    private void salvarCliente() {
        Cliente c = new Cliente();
        c.setNome(txtNome.getText());
        c.setCpf(txtCpf.getText());
        c.setTelefone(txtTelefone.getText());
        c.setEmail(txtEmail.getText());

        ClienteDAO dao = new ClienteDAO();
        dao.salvar(c);

        JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
    }

    private void listarClientes() {
        ClienteDAO dao = new ClienteDAO();
        areaClientes.setText("");

        for (Cliente c : dao.listarTodos()) {
            areaClientes.append(String.format("ID: %d | Nome: %s | CPF: %s | Tel: %s | Email: %s\n",
                    c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail()));
        }
    }
}