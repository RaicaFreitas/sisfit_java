package sisfit.view;

import sisfit.dao.ItemDAO;
import sisfit.model.Item;

import javax.swing.*;
import java.awt.*;

public class ItemForm extends JFrame {
    private JTextField txtNome, txtValor;
    private JComboBox<String> comboTipo; // MUDANÇA AQUI
    private JButton btnSalvar, btnListar;
    private JTextArea areaItens;

    public ItemForm() {
        setTitle("Cadastro de Itens - SISFIT");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);

        panelForm.add(new JLabel("Tipo:"));
        // Substitua pelos valores que existem no seu ENUM do banco de dados
        comboTipo = new JComboBox<>(new String[]{
                "Suplemento",
                "Equipamento",
                "Acessório",
                "Serviço",
                "Produto"
        });
        panelForm.add(comboTipo);

        panelForm.add(new JLabel("Valor:"));
        txtValor = new JTextField();
        panelForm.add(txtValor);

        btnSalvar = new JButton("Salvar");
        btnListar = new JButton("Listar Itens");

        panelForm.add(btnSalvar);
        panelForm.add(btnListar);

        add(panelForm, BorderLayout.NORTH);

        areaItens = new JTextArea();
        areaItens.setEditable(false);
        add(new JScrollPane(areaItens), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarItem());
        btnListar.addActionListener(e -> listarItens());
    }

    private void salvarItem() {
        try {
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o nome do item!");
                return;
            }

            Item i = new Item();
            i.setNome(txtNome.getText().trim());
            i.setTipo((String) comboTipo.getSelectedItem()); // Pega do combo
            i.setValor(Double.parseDouble(txtValor.getText()));

            ItemDAO dao = new ItemDAO();
            dao.salvar(i);

            JOptionPane.showMessageDialog(this, "Item salvo com sucesso!");
            txtNome.setText("");
            txtValor.setText("");
            comboTipo.setSelectedIndex(0);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido! Use apenas números.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void listarItens() {
        ItemDAO dao = new ItemDAO();
        areaItens.setText("");

        for (Item i : dao.listarTodos()) {
            areaItens.append(String.format("ID: %d | Nome: %s | Tipo: %s | Valor: R$ %.2f\n",
                    i.getId(), i.getNome(), i.getTipo(), i.getValor()));
        }
    }
}