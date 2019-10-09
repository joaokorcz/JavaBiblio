package Main;

import DAOs.DAOAutor;
import DAOs.DAOEditora;
import DAOs.DAOGenero;
import DAOs.DAOLivro;
import Entidades.Autor;
import Entidades.Editora;
import Entidades.Genero;
import Entidades.Livro;
import Entidades.Usuario;
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
import javax.swing.JFrame;
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
import javax.swing.JComboBox;

public class GUI extends JFrame {

    private Container cp;

    private JPanel pnNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel pnCentro = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JPanel pnOeste = new JPanel();
    private JPanel pnLeste = new JPanel();

    private CardLayout CardLayout = new CardLayout();
    private JPanel pnSul = new JPanel(CardLayout);
    private JPanel pnSulMsg = new JPanel();
    private JPanel pnSulListagem = new JPanel(new GridLayout(1, 1));

    private JButton btVoltar = new JButton("Menu");
    private JButton btBuscar = new JButton("Buscar");
    private JButton btInserir = new JButton("Inserir");
    private JButton btAlterar = new JButton("Alterar");
    private JButton btExcluir = new JButton("Excluir");
    private JButton btListar = new JButton("Listar");
    private JButton btSalvar = new JButton("Salvar");
    private JButton btCancelar = new JButton("Cancelar");
    private JButton btLimpar = new JButton("Limpar");

    private JButton btGravar = new JButton();

    private JLabel lbId = new JLabel("Código");
    private JTextField tfId = new JTextField(5);

    private JLabel lbV = new JLabel("Título");
    private JTextField tfV = new JTextField(30);

    private JLabel lbG = new JLabel("Gênero");
    private JComboBox gen = new JComboBox();
    private JLabel lbEd = new JLabel("Editora");
    private JComboBox ed = new JComboBox();
    private JLabel lbAt = new JLabel("Autor");
    private JComboBox at = new JComboBox();
    private JLabel lbAn = new JLabel("Ano de lançamento");
    private String[] anos = {"2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "Outro"};
    private JComboBox ano = new JComboBox(anos);

    private String[] colunas = new String[]{"Código", "Livro", "Editora", "Gênero", "Autor", "Ano", "Editora", "Autor"};
    private String[][] dados = new String[0][6];

    private DefaultTableModel model = new DefaultTableModel(dados, colunas);
    private JTable tabela = new JTable(model);

    private JScrollPane scrollList = new JScrollPane();

    private JScrollPane scrollMensagem = new JScrollPane();

    private JTextArea textAreaMsg = new JTextArea(8, 150);

    private DefaultCaret caret = (DefaultCaret) textAreaMsg.getCaret();

    private Livro livro = new Livro();
    private DAOLivro daoLivro = new DAOLivro();

    private DAOGenero daoGenero = new DAOGenero();
    private DAOAutor daoAutor = new DAOAutor();
    private DAOEditora daoEditora = new DAOEditora();

    String ins;

    private void setLog(String msg) {
        textAreaMsg.append(msg + "\n");
        textAreaMsg.setCaretPosition(textAreaMsg.getDocument().getLength());
    }

    private void travar(boolean campo) {
        tfId.setEditable(campo);
        tfV.setEnabled(!campo);
        at.setEnabled(!campo);
        ed.setEnabled(!campo);
        gen.setEnabled(!campo);
        ano.setEnabled(!campo);
        if (!campo) {
            tfV.requestFocus();
            tfV.selectAll();
        }
    }

    private void pintarAchou() {
        tfV.setBackground(Color.DARK_GRAY);
        at.setEnabled(false);
        ed.setEnabled(false);
        gen.setEnabled(false);
        ano.setEnabled(false);
    }

    private void pintarNao() {
        tfId.setBackground(Color.ORANGE);
        tfV.setBackground(Color.WHITE);
        at.setEnabled(false);
        ed.setEnabled(false);
        gen.setEnabled(false);
        ano.setEnabled(false);
    }

    private void limpar() {
        tfV.setText("");
        at.setSelectedIndex(0);
        ed.setSelectedIndex(0);
        gen.setSelectedIndex(0);
        ano.setSelectedIndex(0);
    }

    private boolean inserindo;

    public GUI(Usuario usuario) {

        List<Genero> generos = daoGenero.listInOrderId();
        if (generos != null) {
            for (Genero genr : generos) {
                gen.addItem(String.valueOf(genr.getGenero()));
            }
        }

        List<Editora> editoras = daoEditora.listInOrderId();
        if (editoras != null) {
            for (Editora editr : editoras) {
                ed.addItem(String.valueOf(editr.getEditora()));
            }
        }

        List<Autor> autores = daoAutor.listInOrderId();
        if (autores != null) {
            for (Autor atr : autores) {
                at.addItem(String.valueOf(atr.getAutor()));
            }
        }

        setSize(750, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("Sistema");

        cp.add(pnOeste, BorderLayout.WEST);

        cp.add(pnLeste, BorderLayout.EAST);

        cp.add(pnSul, BorderLayout.SOUTH);

        cp.add(pnNorte, BorderLayout.NORTH);

        btVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/home.png")));
        pnNorte.add(btVoltar);
        btVoltar.setToolTipText("Voltar ao menu principal");
        pnNorte.add(lbId);
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
        pnCentro.setLayout(new GridLayout(5, 1));
        JPanel pnCentro1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnCentro1.add(lbV);
        pnCentro1.add(tfV);
        tfV.setEnabled(false);
        pnCentro.add(pnCentro1);
        JPanel pnCentro2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnCentro2.add(lbAt);
        pnCentro2.add(at);
        at.setEnabled(false);
        pnCentro.add(pnCentro2);
        JPanel pnCentro3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnCentro3.add(lbEd);
        pnCentro3.add(ed);
        ed.setEnabled(false);
        pnCentro.add(pnCentro3);
        JPanel pnCentro4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnCentro4.add(lbG);
        pnCentro4.add(gen);
        gen.setEnabled(false);
        pnCentro.add(pnCentro4);
        JPanel pnCentro5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnCentro5.add(lbAn);
        pnCentro5.add(ano);
        ano.setEnabled(false);
        pnCentro.add(pnCentro5);

        pnNorte.setBackground(Color.ORANGE);

        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollMensagem.setViewportView(textAreaMsg);
        scrollMensagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        UsarGridBagLayout usarGridBagLayoutSul = new UsarGridBagLayout(pnSulMsg);
        usarGridBagLayoutSul.add(new JLabel("log"), scrollMensagem);
        pnSul.add(pnSulMsg, "pnMsg");
        pnSul.add(pnSulListagem, "pnLst");

        btBuscar.addActionListener((ActionEvent e) -> {
            if (tfId.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(cp, "Nada informado");
            } else {
                try {
                    tfId.setBackground(Color.GREEN);
                    travar(true);

                    boolean vf = false;

                    try {
                        livro = daoLivro.obter(Integer.valueOf(tfId.getText()));
                        if (livro != null) {
                            vf = true;
                        }
                    } catch (Exception erro) {
                        setLog("ERRO > " + erro);
                    }

                    if (livro == null || livro.getTitulo().equals(null) || livro.getTitulo().equals("null")) {
                        btInserir.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        limpar();
                        JOptionPane.showMessageDialog(cp, "Não achou na lista, pode inserir se quiser!");
                        pintarNao();
                        CardLayout.show(pnSul, "pnMsg");
                        setLog("Não achou na lista, pode inserir se quiser");
                    } else {
                        tfId.setEnabled(false);
                        btLimpar.setVisible(true);
                        btAlterar.setVisible(true);
                        btInserir.setVisible(false);
                        btExcluir.setVisible(true);
                        tfId.setText(String.valueOf(livro.getIdLivro()));
                        tfV.setText(livro.getTitulo());

                        Genero gen2 = new Genero();
                        int axx = 0;
                        for (Genero g2 : generos) {
                            if (g2 == livro.getGeneroIdGenero()) {
                                gen.setSelectedIndex(axx);
                            }
                            axx++;
                        }

                        Autor au2 = new Autor();
                        int axx2 = 0;
                        for (Autor autx : autores) {
                            if (autx == livro.getAutorIdAutor()) {
                                at.setSelectedIndex(axx2);
                            }
                            axx2++;
                        }

                        Editora ed2 = new Editora();
                        int axx3 = 0;
                        for (Editora edx2 : editoras) {
                            if (edx2 == livro.getEditoraIdEditora()) {
                                ed.setSelectedIndex(axx3);
                            }
                            axx3++;
                        }

                        int aux = livro.getAno();
                        for (int i = 0; i < (anos.length) - 1; i++) {
                            if (aux == Integer.valueOf(anos[i])) {
                                aux = i;
                            }
                        }
                        ano.setSelectedIndex(aux);

                        CardLayout.show(pnSul, "pnMsg");
                        setLog("Achou (" + livro.getIdLivro() + " - " + livro.getTitulo() + ")");
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
                tfV.requestFocus();
                btInserir.setVisible(false);
                btSalvar.setVisible(true);
                btCancelar.setVisible(true);
                btBuscar.setVisible(false);
                btListar.setVisible(false);
                travar(false);
                limpar();
                CardLayout.show(pnSul, "pnMsg");
                setLog("Inserindo um novo registro");
                inserindo = true;
            }
        });

        btSalvar.addActionListener((ActionEvent e) -> {

            livro = new Livro();

            Genero gens = new Genero();
            String gg = (String) gen.getSelectedItem();
            for (Genero g1 : generos) {
                if (g1.getGenero().equals(gg)) {
                    livro.setGeneroIdGenero(g1);
                }
            }

            String aa = (String) at.getSelectedItem();
            for (Autor at1 : autores) {
                if (at1.getAutor().equals(aa)) {
                    livro.setAutorIdAutor(at1);
                }
            }

            String ee = (String) ed.getSelectedItem();
            for (Editora ed1 : editoras) {
                if (ed1.getEditora().equals(ee)) {
                    livro.setEditoraIdEditora(ed1);
                }
            }

            livro.setIdLivro(Integer.valueOf(tfId.getText()));

            if (tfV.getText().equals("")) {
                JOptionPane.showMessageDialog(cp, "Você precisa informar um titulo ao livro");
            } else {
                try {
                    livro.setTitulo(tfV.getText());
                } catch (Exception erro123) {
                    setLog("ERRO no titulo > " + erro123.getMessage());
                }

                livro.setAno(ano.getSelectedIndex());
                System.out.println(livro.getIdLivro() + " esse é o id do livro que deveria estar sendo inserido...");
                if (inserindo) {
                    daoLivro.inserir(livro);
                    CardLayout.show(pnSul, "pnMsg");
                    setLog("Inseriu (" + livro.getIdLivro() + " - " + livro.getTitulo() + ")");
                } else {
                    daoLivro.atualizar(livro);
                    CardLayout.show(pnSul, "pnMsg");
                    setLog("Alterou (" + livro.getIdLivro() + " - " + livro.getTitulo() + ")");
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
                livro = new Livro();
            }
        });

        btAlterar.addActionListener((ActionEvent e) -> {
            tfV.requestFocus();
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btExcluir.setVisible(false);
            btListar.setVisible(false);
            btLimpar.setVisible(false);
            inserindo = false;
            pintarNao();
            travar(false);
            CardLayout.show(pnSul, "pnMsg");
            setLog("Alterando um registro existente");
        });

        btExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(cp, "Vai excluir ("
                        + tfV.getText() + ")?", "Exclui da lista", NORMAL);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    daoLivro.remover(livro);
                    CardLayout.show(pnSul, "pnMsg");
                    setLog("Excluiu (" + livro.getIdLivro() + " - " + livro.getTitulo() + ")");
                    btGravar.doClick();
                    btLimpar.setVisible(false);
                    btExcluir.setVisible(false);
                    btAlterar.setVisible(false);
                    limpar();
                    tfId.setText("");
                    tfId.setEnabled(true);
                    tfId.requestFocus();
                    pintarNao();
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
                btLimpar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                travar(true);
                limpar();
                tfId.setText("");
                tfId.setEnabled(true);
                tfId.requestFocus();
                CardLayout.show(pnSul, "pnMsg");
                setLog("Cancelou a alteração ou inclusão do registro");
            }
        });

        btListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout.show(pnSul, "pnLst");
                scrollList.setPreferredSize(tabela.getPreferredSize());
                pnSulListagem.add(scrollList);
                scrollList.setViewportView(tabela);
                List<Livro> livros = daoLivro.listInOrderId();
                colunas = new String[]{"Código", "Livro", "Autor", "Editora", "Gênero", "Ano"};
                dados = new String[0][6];
                model.setDataVector(dados, colunas);
                for (Livro l : livros) {
                    String[] linha = new String[]{String.valueOf(l.getIdLivro()), l.getTitulo(), l.getAutorIdAutor().getAutor(), l.getEditoraIdEditora().getEditora(), l.getGeneroIdGenero().getGenero(), anos[l.getAno()]};
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
                Livro livro = new Livro();
            }
        });

        btVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUImenu guimenu = new GUImenu(usuario);
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

        setLocationRelativeTo(null);
        setVisible(true);

    }

}
