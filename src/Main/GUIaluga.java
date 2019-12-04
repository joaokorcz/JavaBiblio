package Main;

import DAOs.DAOLivro;
import DAOs.DAOLivroalugado;
import Entidades.Livro;
import Entidades.Livroalugado;
import Entidades.LivroalugadoPK;
import Entidades.Usuario;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;

class GUIaluga extends JFrame {

    String att;

    private Container cp;

    private MyDateTimeFormat mdt = new MyDateTimeFormat();

    private JPanel pnNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel pnCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pnOeste = new JPanel();
    private JPanel pnLeste = new JPanel();

    private JButton btVoltar = new JButton("Menu");

    private JLabel lbId = new JLabel("Código Livro");
    private JTextField tfId = new JTextField(5);

    private JLabel lbA = new JLabel("Livro");
    private JTextField tfA = new JTextField(30);

    private JButton btBuscar = new JButton("Buscar");
    private JButton btAlugar = new JButton("Alugar");
    private JButton btDev = new JButton("Devolver");
    private JButton btListar = new JButton("Listar");
    private JButton btListarAlugueis = new JButton("Meus empréstimos");
    private JButton btListarAlugueisAdm = new JButton("Listar aluguéis");
    private JButton btLimpar = new JButton("Limpar");

    private JButton btGravar = new JButton();

    private CardLayout CardLayout = new CardLayout();
    private JPanel pnSul = new JPanel(CardLayout);
    private JPanel pnSulMsg = new JPanel();
    private JPanel pnSulListagem = new JPanel(new GridLayout(1, 1));

    private String[] anos = {"2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "Outro"};

    private String[] colunas = new String[]{"Código", "Livro", "Editora", "Gênero", "Autor", "Ano", "Editora", "Autor"};
    private String[][] dados = new String[0][6];

    private DefaultTableModel model = new DefaultTableModel(dados, colunas);
    private JTable tabela = new JTable(model);

    private JScrollPane scrollList = new JScrollPane();

    private JScrollPane scrollMensagem = new JScrollPane();

    private JTextArea textAreaMsg = new JTextArea(25, 150);

    private DefaultCaret caret = (DefaultCaret) textAreaMsg.getCaret();

    private void setLog(String msg) {
        textAreaMsg.append(msg + "\n");
        textAreaMsg.setCaretPosition(textAreaMsg.getDocument().getLength());
    }

    private void travar(boolean campo) {
        tfId.setEditable(campo);
        tfA.setEnabled(!campo);
        if (!campo) {
            tfA.requestFocus();
            tfA.selectAll();
        }
    }

    private void pintarAchou() {
        tfA.setBackground(Color.DARK_GRAY);
        tfA.setForeground(Color.LIGHT_GRAY);
    }

    private void pintarNao() {
        tfId.setBackground(Color.RED);
        tfA.setBackground(Color.WHITE);
    }

    private void limpar() {
        tfA.setText("");
        btAlugar.setVisible(false);
        btDev.setVisible(false);
    }

    private void gerarComprovante(Usuario usuario, Livro livro, Livroalugado la) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream("Comprovante " + usuario.getNome() + " " + livro.getTitulo() + ".pdf"));

            document.open();

            document.add(new Paragraph("Usuario: " + usuario.getNome() + "."));
            document.add(new Paragraph("Livro: " + livro.getTitulo() + "."));
            document.add(new Paragraph("Data do Empréstimo: " + mdt.getDateParaStringBr(livroalugado.getDataEmp()) + "."));
            document.add(new Paragraph("Data da Devolução: " + mdt.getDateParaStringBr(livroalugado.getDataDev()) + "."));
            document.add(new Paragraph("\nATENÇÃO A DEVOLUÇÃO, LIVROS ENTREGUES APÓS A DATA EXPIRADA SERÃO TARIFADOS EM 500 REAIS POR DIA DE ATRASO."));

        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        document.close();

    }

    private void enviarEmailComprovante(Usuario usuario, Livro livro, Livroalugado la) {

        String assunto = "Comprovante de aluguel de Livro";
        String mensagem = "Segue em anexo seu comprovante de aluguel de livro."
                + "\nUsuario: " + usuario.getNome() + "."
                + "\nLivro: " + la.getLivro().getTitulo() + "."
                + "\nData do emprestimo: " + mdt.getDateParaStringBr(la.getDataEmp()) + "."
                + "\nData da devoulução: " + mdt.getDateParaStringBr(la.getDataDev()) + "."
                + "\nAtencao aos dados e as respectivas >DATAS IMPORTANTES!<";
        String destinatario = usuario.getEmail();

        String eMail = "uzumcorp@gmail.com";
        String eSenha = "uzumjamal";

        SendMail sm = new SendMail("smtp.gmail.com", "465", eMail, eSenha);

        new Thread() {
            @Override
            public void run() {
                sm.sendMail(destinatario, assunto, mensagem, usuario, livro);
            }
        }.start();
        
    }

    private Livro livro = new Livro();
    private DAOLivro daoLivro = new DAOLivro();

    private DAOLivroalugado daoLivroalugado = new DAOLivroalugado();
    private Livroalugado livroalugado = new Livroalugado();
    private LivroalugadoPK livroalugadoPK = new LivroalugadoPK();

    public GUIaluga(Usuario usuario) {

        setSize(750, 570);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        setTitle("Alugar e Devolver Livros");

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
        btListar.setVisible(false);
        pnNorte.add(btListarAlugueis);
        btListarAlugueis.setToolTipText("Listar Aluguéis");
        btListarAlugueis.setVisible(false);
        btListarAlugueis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/list.png")));
        pnNorte.add(btListarAlugueisAdm);
        btListarAlugueisAdm.setToolTipText("Listar todos os aluguéis");
        btListarAlugueisAdm.setVisible(false);
        btListarAlugueisAdm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/list.png")));
        btAlugar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/in.png")));
        pnNorte.add(btAlugar);
        btAlugar.setToolTipText("Alugar um Livro");
        btAlugar.setVisible(false);
        btDev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/teste.png")));
        pnNorte.add(btDev);
        btDev.setToolTipText("Devolver um Livro");
        btDev.setVisible(false);

        cp.add(pnCentro, BorderLayout.CENTER);
        pnCentro.add(lbA);
        pnCentro.add(tfA);
        tfA.setEnabled(false);

        List<Livro> livros = daoLivro.listInOrderId();
        String idGs = "";

        for (Livro l : livros) {
            idGs += l.getAutorIdAutor().getIdAutor() + ";";
        }

        if (usuario.getEmail().equals("adm")) {
            btListarAlugueisAdm.setVisible(true);
        } else {
            btListarAlugueis.setVisible(true);
        }

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
                        JOptionPane.showMessageDialog(cp, "ERRO > " + erro);
                        pintarNao();
                    }

                    if (livro == null || livro.getTitulo().equals(null) || livro.getTitulo().equals("null")) {
                        limpar();
                        JOptionPane.showMessageDialog(cp, "O livro não foi encontrado na lista!");
                        pintarNao();
                    } else {
                        btBuscar.setVisible(false);
                        tfId.setEnabled(false);
                        btLimpar.setVisible(true);
                        tfId.setText(String.valueOf(livro.getIdLivro()));
                        btAlugar.setVisible(true);
                        btDev.setVisible(true);
                        tfA.setText(livro.getTitulo());
                        travar(true);
                        pintarAchou();
                    }
                } catch (Exception x) {
                    tfId.selectAll();
                    tfId.requestFocus();
                    tfId.setBackground(Color.RED);
                    limpar();
                }
            }
            tfId.selectAll();
            tfId.requestFocus();
        });

        btAlugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    livroalugadoPK.setUsuarioIdUsuario(usuario.getIdUsuario());
                    livroalugadoPK.setLivroIdLivro(Integer.valueOf(tfId.getText()));

                    livroalugado.setLivroalugadoPK(livroalugadoPK);
                    livroalugado.setLivro(livro);
                    livroalugado.setUsuario(usuario);
                    livroalugado.setDataEmp(new Date());
                    Date dev = new Date();
                    dev.setDate(dev.getDate() + 7);
                    livroalugado.setDataDev(dev);

                    daoLivroalugado.inserir(livroalugado);

                    JOptionPane.showMessageDialog(cp, "Parabéns " + usuario.getNome() + ", você alugou o livro " + livro.getTitulo() + ".\n"
                            + "A data marcada para a devolução é: " + mdt.getDateParaStringBr(livroalugado.getDataDev()) + "!");

                    gerarComprovante(usuario, livro, livroalugado);

                    enviarEmailComprovante(usuario, livro, livroalugado);

                    livro = new Livro();
                    livroalugado = new Livroalugado();
                    livroalugadoPK = new LivroalugadoPK();

                    btLimpar.doClick();

                } catch (Exception erro) {

                    JOptionPane.showMessageDialog(cp, usuario.getNome() + ", você não pode alugar o mesmo livro duas vezes!");

                }

            }
        });

        btDev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    livroalugadoPK.setUsuarioIdUsuario(usuario.getIdUsuario());
                    livroalugadoPK.setLivroIdLivro(Integer.valueOf(tfId.getText()));

                    livroalugado = daoLivroalugado.obter(livroalugadoPK);

                    daoLivroalugado.remover(livroalugado);

                    JOptionPane.showMessageDialog(cp, "Parabéns " + usuario.getNome() + ", você devolveu o livro " + livro.getTitulo() + ".");

                    livro = new Livro();
                    livroalugado = new Livroalugado();
                    livroalugadoPK = new LivroalugadoPK();

                    btLimpar.doClick();

                } catch (Exception erro) {

                    JOptionPane.showMessageDialog(cp, usuario.getNome() + ", você não pode devolver um livro que não tem alugado!" + erro);

                }

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

        btListarAlugueis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUIlistaEmprestimo guIlistaEmprestimo = new GUIlistaEmprestimo(usuario);

            }
        });

        btListarAlugueisAdm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUIlistaEmprestimo guIlistaEmprestimo = new GUIlistaEmprestimo(usuario);

            }
        });

        btLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpar();
                travar(true);
                pintarNao();
                btLimpar.setVisible(false);
                tfId.setText("");
                tfId.setEnabled(true);
                tfId.requestFocus();
                btBuscar.setVisible(true);
                tfId.setBackground(Color.WHITE);
                livro = new Livro();
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

        btListar.doClick();

        tabela.setEnabled(false);

        pnNorte.setBackground(Color.ORANGE);

        setLocationRelativeTo(null);
        setVisible(true);

    }

}
