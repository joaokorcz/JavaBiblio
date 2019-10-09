package Main;

import DAOs.DAOEditora;
import DAOs.DAOLivro;
import Entidades.Editora;
import Entidades.Livro;
import Entidades.Usuario;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import ferramentas.UsarGridBagLayout;

class GUIedit extends JFrame {

    String att;

    private Container cp;

    private JPanel pnNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel pnCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pnOeste = new JPanel();
    private JPanel pnLeste = new JPanel();

    private JButton btVoltar = new JButton("Menu");

    private JLabel lbGen = new JLabel("Código");
    private JTextField tfId = new JTextField(5);

    private JLabel lbG = new JLabel("Editora");
    private JTextField tfE = new JTextField(30);

    private JButton btBuscar = new JButton("Buscar");
    private JButton btInserir = new JButton("Inserir");
    private JButton btAlterar = new JButton("Alterar");
    private JButton btExcluir = new JButton("Excluir");
    private JButton btListar = new JButton("Listar");
    private JButton btSalvar = new JButton("Salvar");
    private JButton btCancelar = new JButton("Cancelar");
    private JButton btLimpar = new JButton("Limpar");

    private JButton btGravar = new JButton();

    private CardLayout CardLayout = new CardLayout();
    private JPanel pnSul = new JPanel(CardLayout);
    private JPanel pnSulMsg = new JPanel();
    private JPanel pnSulListagem = new JPanel(new GridLayout(1, 1));

    private String[] colunas = new String[]{"Código", "Editora"};
    private String[][] dados = new String[0][2];

    private DefaultTableModel model = new DefaultTableModel(dados, colunas);
    private JTable tabela = new JTable(model);

    private JScrollPane scrollList = new JScrollPane();

    private JScrollPane scrollMensagem = new JScrollPane();

    private JTextArea textAreaMsg = new JTextArea(6, 150);

    private DefaultCaret caret = (DefaultCaret) textAreaMsg.getCaret();

    private void setLog(String msg) {
        textAreaMsg.append(msg + "\n");
        textAreaMsg.setCaretPosition(textAreaMsg.getDocument().getLength());
    }

    private void travar(boolean campo) {
        tfId.setEditable(campo);
        tfE.setEnabled(!campo);
        if (!campo) {
            tfE.requestFocus();
            tfE.selectAll();
        }
    }

    private void pintarAchou() {
        tfE.setBackground(Color.DARK_GRAY);
        tfE.setForeground(Color.LIGHT_GRAY);
    }

    private void pintarNao() {
        tfId.setBackground(Color.ORANGE);
        tfE.setBackground(Color.WHITE);
    }

    private void limpar() {
        tfE.setText("");
    }

    private boolean vf = false;
    private boolean inserindo;

    private DAOLivro daoLivro = new DAOLivro();

    private Editora editora = new Editora();
    private DAOEditora daoEditora = new DAOEditora();

    public GUIedit(Usuario usuario) {

        setSize(750, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        setTitle("Editoras");

        cp.add(pnOeste, BorderLayout.WEST);

        cp.add(pnLeste, BorderLayout.EAST);

        cp.add(pnSul, BorderLayout.SOUTH);

        cp.add(pnNorte, BorderLayout.NORTH);
        pnNorte.setBackground(Color.yellow);

        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollMensagem.setViewportView(textAreaMsg);
        scrollMensagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        UsarGridBagLayout usarGridBagLayoutSul = new UsarGridBagLayout(pnSulMsg);
        usarGridBagLayoutSul.add(new JLabel("log"), scrollMensagem);
        pnSul.add(pnSulMsg, "pnMsg");
        pnSul.add(pnSulListagem, "pnLst");

        btVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/home.png")));
        pnNorte.add(btVoltar);
        btVoltar.setToolTipText("Voltar ao menu principal");
        pnNorte.add(lbGen);
        pnNorte.add(tfId);
        pnNorte.add(btLimpar);
        btLimpar.setToolTipText("Limpar");
        btLimpar.setVisible(false);
        btBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/src.png")));
        pnNorte.add(btBuscar);
        btBuscar.setToolTipText("Buscar");
        btListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/list.png")));
        pnNorte.add(btListar);
        btListar.setToolTipText("Listar");
        btInserir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/in.png")));
        pnNorte.add(btInserir);
        btInserir.setToolTipText("Inserir");
        btInserir.setVisible(false);
        btAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/change.png")));
        pnNorte.add(btAlterar);
        btAlterar.setToolTipText("Alterar");
        btAlterar.setVisible(false);
        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/rem.png")));
        pnNorte.add(btExcluir);
        btExcluir.setToolTipText("Excluir");
        btExcluir.setVisible(false);
        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save.png")));
        pnNorte.add(btSalvar);
        btSalvar.setToolTipText("Salvar");
        btSalvar.setVisible(false);
        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancel.png")));
        pnNorte.add(btCancelar);
        btCancelar.setToolTipText("Cancelar");
        btCancelar.setVisible(false);

        cp.add(pnCentro, BorderLayout.CENTER);
        pnCentro.add(lbG);
        pnCentro.add(tfE);
        tfE.setEnabled(false);

        List<Livro> livros = daoLivro.listInOrderId();
        String idEs = "";

        for (Livro l : livros) {
            idEs += l.getEditoraIdEditora().getIdEditora() + ";";
        }
        String[] ids = idEs.split(";");

        btBuscar.addActionListener((ActionEvent e) -> {
            if (tfId.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(cp, "Nada informado");
            } else {
                try {
                    tfId.setBackground(Color.GREEN);
                    travar(true);
                    editora = daoEditora.obter(Integer.valueOf(tfId.getText()));
                    if (editora == null) {
                        btInserir.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        JOptionPane.showMessageDialog(cp, "Não achou na lista, pode inserir se quiser!");
                        pintarNao();
                        CardLayout.show(pnSul, "pnMsg");
                        setLog("Não achou na lista, pode inserir se quiser");
                    } else {
                        tfId.setEnabled(false);
                        btLimpar.setVisible(true);
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        btInserir.setVisible(false);
                        tfId.setText(String.valueOf(editora.getIdEditora()));
                        tfE.setText(editora.getEditora());
                        CardLayout.show(pnSul, "pnMsg");
                        setLog("Editora " + editora.getEditora() + " encontrada!");
                        travar(true);
                        pintarAchou();
                    }
                } catch (Exception x) {
                    tfId.selectAll();
                    tfId.requestFocus();
                    tfId.setBackground(Color.RED);
                    limpar();
                    CardLayout.show(pnSul, "pnMsg");
                    setLog("Erro no tipo de dados da chave. (" + x.getMessage() + ")");
                }
            }
            tfId.selectAll();
            tfId.requestFocus();
        });

        btInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                att = "b";
                tfE.requestFocus();
                btInserir.setVisible(false);
                btSalvar.setVisible(true);
                btCancelar.setVisible(true);
                btBuscar.setVisible(false);
                btListar.setVisible(false);
                inserindo = true;
                travar(false);
                CardLayout.show(pnSul, "pnMsg");
                setLog("Inserindo um novo registro");
            }
        });

        btSalvar.addActionListener((ActionEvent e) -> {
            if (inserindo) {
                editora = new Editora();
            }
            editora.setIdEditora(Integer.valueOf(tfId.getText()));
            if (tfE.getText().equals("")) {
                JOptionPane.showMessageDialog(cp, "Você precisa informar um nome a Editora");
            } else {
                try {
                    editora.setEditora(tfE.getText());
                } catch (Exception erro) {
                    setLog("ERRO no nome da EDITORA > " + erro);
                }
                if (inserindo) {
                    daoEditora.inserir(editora);
                    CardLayout.show(pnSul, "pnMsg");
                    setLog("Você inseriu a Editora " + editora.getEditora() + "");
                } else {
                    daoEditora.atualizar(editora);
                    CardLayout.show(pnSul, "pnMsg");
                    setLog("Alterou para " + editora.getEditora() + "");
                }
                tfId.requestFocus();
                tfId.selectAll();
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                btGravar.doClick();
                limpar();
                btLimpar.setVisible(false);
                tfId.setText("");
                tfId.setEnabled(true);
                tfId.requestFocus();
                travar(true);
                inserindo = false;
            }
        });

        btAlterar.addActionListener((ActionEvent e) -> {
            att = "a";
            tfId.requestFocus();
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btExcluir.setVisible(false);
            btListar.setVisible(false);
            btLimpar.setVisible(false);
            pintarNao();
            travar(false);
            CardLayout.show(pnSul, "pnMsg");
            setLog("Alterando um registro existente");
        });

        btExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(cp, "Vai excluir ("
                        + tfE.getText() + ")?", "Exclui da lista", NORMAL);
                if (dialogResult == JOptionPane.YES_OPTION) {

                    for (int i = 0; i < ids.length; i++) {
                        System.out.println("idV " + Integer.valueOf(ids[i]));
                        System.out.println("idT " + Integer.valueOf(tfId.getText()));
                        System.out.println(Integer.valueOf(tfId.getText()) == Integer.valueOf(ids[i]));
                        if (tfId.getText().equals(ids[i])) {
                            System.out.println(editora.getIdEditora());
                            System.out.println(Integer.valueOf(ids[i]));
                            vf = true;
                        }
                    }
                    try {

                        if (vf != true) {
                            daoEditora.remover(editora);
                            CardLayout.show(pnSul, "pnMsg");
                            setLog("Você excluiu a Editora " + editora.getEditora() + "");
                            btGravar.doClick();
                            limpar();
                            btLimpar.setVisible(false);
                            btExcluir.setVisible(false);
                            btAlterar.setVisible(false);
                            tfId.setText("");
                            tfId.setEnabled(true);
                            tfId.requestFocus();
                            pintarNao();
                        } else {
                            CardLayout.show(pnSul, "pnMsg");
                            setLog("Você não pode excluir essa Editora, pois existem livros cadastrados com ela!");
                            limpar();
                            tfId.setText("");
                            tfId.requestFocus();
                            pintarNao();
                        }
                    } catch (Exception erro2) {
                        setLog("ERRO: " + erro2);
                    }

                }
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfId.requestFocus();
                tfId.selectAll();
                btInserir.setVisible(false);
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                btLimpar.setVisible(false);
                travar(true);
                limpar();
                tfId.setText("");
                tfId.setEnabled(true);
                tfId.requestFocus();
                CardLayout.show(pnSul, "pnMsg");
                setLog("A alteração ou inclusão do registro foi cancelada");
            }
        });

        btListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout.show(pnSul, "pnLst");
                scrollList.setPreferredSize(tabela.getPreferredSize());
                pnSulListagem.add(scrollList);
                scrollList.setViewportView(tabela);
                List<Editora> editoras = daoEditora.listInOrderId();
                colunas = new String[]{"Código", "Editora"};
                dados = new String[0][2];
                model.setDataVector(dados, colunas);
                for (Editora g : editoras) {
                    String[] linha = new String[]{String.valueOf(g.getIdEditora()), g.getEditora()};
                    model.addRow(linha);
                }
            }
        });

        btLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpar();
                travar(true);
                pintarNao();
                CardLayout.show(pnSul, "pnMsg");
                setLog("Campos limpos");
                btAlterar.setVisible(false);
                btCancelar.setVisible(false);
                btExcluir.setVisible(false);
                btInserir.setVisible(false);
                btLimpar.setVisible(false);
                btSalvar.setVisible(false);
                tfId.setText("");
                tfId.setEnabled(true);
                tfId.requestFocus();
                Editora editora = new Editora();
            }
        });

        btVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUImenu GUImenu = new GUImenu(usuario);
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                btGravar.doClick();
                dispose();
            }
        });

        tabela.setEnabled(false);

        pnNorte.setBackground(Color.ORANGE);

        setLocationRelativeTo(null);
        setVisible(true);

    }

}
