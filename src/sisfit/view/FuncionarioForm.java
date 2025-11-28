package sisfit.view;

import sisfit.dao.FuncionarioDAO;
import sisfit.model.Funcionario;

import javax.swing.*;
import java.awt.*;

public class FuncionarioForm extends JFrame {
    private JTextField txtNome, txtCpf, txtTelefone, txtEmail;
    private JButton btnSalvar, btnListar;
    private JTextArea areaFuncionarios;

    public FuncionarioForm() {
        setTitle("Cadastro de Funcionários - SISFIT");
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
        btnListar = new JButton("Listar Funcionários");

        panelForm.add(btnSalvar);
        panelForm.add(btnListar);

        add(panelForm, BorderLayout.NORTH);

        areaFuncionarios = new JTextArea();
        areaFuncionarios.setEditable(false);
        add(new JScrollPane(areaFuncionarios), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarFuncionario());
        btnListar.addActionListener(e -> listarFuncionarios());
    }

    private void salvarFuncionario() {
        Funcionario f = new Funcionario();
        f.setNome(txtNome.getText());
        f.setCpf(txtCpf.getText());
        f.setTelefone(txtTelefone.getText());
        f.setEmail(txtEmail.getText());

        FuncionarioDAO dao = new FuncionarioDAO();
        dao.salvar(f);

        JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso!");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
    }

    private void listarFuncionarios() {
        FuncionarioDAO dao = new FuncionarioDAO();
        areaFuncionarios.setText("");

        for (Funcionario f : dao.listarTodos()) {
            areaFuncionarios.append(String.format("ID: %d | Nome: %s | CPF: %s | Tel: %s | Email: %s\n",
                    f.getId(), f.getNome(), f.getCpf(), f.getTelefone(), f.getEmail()));
        }
    }
}