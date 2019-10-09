package Main;

import DAOs.DAOLivro;
import DAOs.DAOLivroalugado;
import DAOs.DAOUsuario;
import Entidades.Livro;
import Entidades.Livroalugado;
import Entidades.LivroalugadoPK;
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
import java.util.Date;

class GUIlistaEmprestimo extends JFrame {

    String att;

    private Container cp;

    private MyDateTimeFormat mdt = new MyDateTimeFormat();

    private JPanel pnNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel pnCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pnOeste = new JPanel();
    private JPanel pnLeste = new JPanel();

    private JButton btVoltar = new JButton("Voltar");

    private JButton btListar = new JButton("Listar");

    private CardLayout CardLayout = new CardLayout();
    private JPanel pnSul = new JPanel(CardLayout);
    private JPanel pnSulMsg = new JPanel();
    private JPanel pnSulListagem = new JPanel(new GridLayout(1, 1));

    private String[] colunas = new String[]{"Usuario", "Livro", "idLivro", "Empréstimo", "Devolução"};
    private String[][] dados = new String[0][5];

    private DefaultTableModel model = new DefaultTableModel(dados, colunas);
    private JTable tabela = new JTable(model);

    private JScrollPane scrollList = new JScrollPane();

    private JScrollPane scrollMensagem = new JScrollPane();

    private JTextArea textAreaMsg = new JTextArea(10, 150);

    private DefaultCaret caret = (DefaultCaret) textAreaMsg.getCaret();

    private Livro livro = new Livro();
    private DAOLivro daoLivro = new DAOLivro();

    private Usuario usuario = new Usuario();
    private DAOUsuario daoUsuario = new DAOUsuario();

    private DAOLivroalugado daoLivroalugado = new DAOLivroalugado();
    private Livroalugado livroalugado = new Livroalugado();
    private LivroalugadoPK livroalugadoPK = new LivroalugadoPK();

    public GUIlistaEmprestimo(Usuario usuario) {

        setSize(540, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        setTitle("Listas de empréstimos");

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

        List<Livro> livros = daoLivro.listInOrderId();

        if (usuario.getEmail().equals("adm")) {

            btListar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout.show(pnSul, "pnLst");
                    scrollList.setPreferredSize(tabela.getPreferredSize());
                    pnSulListagem.add(scrollList);
                    scrollList.setViewportView(tabela);
                    List<Livroalugado> livrosAlugados = daoLivroalugado.list();
                    colunas = new String[]{"Usuario", "Livro", "idLivro", "Empréstimo", "Devolução"};
                    dados = new String[0][5];
                    model.setDataVector(dados, colunas);
                    for (Livroalugado l : livrosAlugados) {
                        String nome = daoUsuario.obter(l.getLivroalugadoPK().getUsuarioIdUsuario()).getNome();
                        String nomeLivro = daoLivro.obter(l.getLivroalugadoPK().getLivroIdLivro()).getTitulo();
                        String idLivro = String.valueOf(l.getLivroalugadoPK().getLivroIdLivro());
                        String[] linha = new String[]{nome, nomeLivro, idLivro, mdt.getDateParaStringBr(l.getDataEmp()), mdt.getDateParaStringBr(l.getDataDev())};
                        model.addRow(linha);
                    }
                }
            });

        } else {

            btListar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout.show(pnSul, "pnLst");
                    scrollList.setPreferredSize(tabela.getPreferredSize());
                    pnSulListagem.add(scrollList);
                    scrollList.setViewportView(tabela);
                    List<Livroalugado> livrosAlugados = daoLivroalugado.list();
                    colunas = new String[]{"Usuario", "Livro", "idLivro", "Empréstimo", "Devolução"};
                    dados = new String[0][5];
                    model.setDataVector(dados, colunas);
                    for (Livroalugado l : livrosAlugados) {
                        if (usuario.getIdUsuario() == l.getLivroalugadoPK().getUsuarioIdUsuario()) {
                            String nome = usuario.getNome();
                            String nomeLivro = daoLivro.obter(l.getLivroalugadoPK().getLivroIdLivro()).getTitulo();
                            String idLivro = String.valueOf(l.getLivroalugadoPK().getLivroIdLivro());
                            String[] linha = new String[]{nome, nomeLivro, idLivro, mdt.getDateParaStringBr(l.getDataEmp()), mdt.getDateParaStringBr(l.getDataDev())};
                            model.addRow(linha);
                        }
                    }
                }
            });

        }

        btVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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
