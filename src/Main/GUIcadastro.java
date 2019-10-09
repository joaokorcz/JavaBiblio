package Main;

import DAOs.DAOUsuario;
import Entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class GUIcadastro extends JFrame {

    private Container cp;

    private JPanel pnCentro = new JPanel();
    private JPanel pnBotaoPerf = new JPanel();

    private JLabel lbLog = new JLabel("Nome");
    private JTextField tfLog = new JTextField(20);
    private JLabel lbSen = new JLabel("Senha");
    private JTextField tfSen = new JTextField(20);
    private JLabel lbEm = new JLabel("Email");
    private JTextField tfEm = new JTextField(20);

    private JButton btReg = new JButton("Registrar");
    private JButton btPerf = new JButton("Eu tenho uma conta");

    public GUIcadastro() {

        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        setTitle("Cadastro");

        cp.add(pnCentro, BorderLayout.CENTER);
        pnCentro.setLayout(new GridLayout(5, 1));

        JPanel pnCentro1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnCentro12 = new JPanel(new GridLayout(2, 1));
        pnCentro12.add(lbLog);
        pnCentro12.add(tfLog);
        pnCentro1.add(pnCentro12);
        pnCentro.add(pnCentro1);
        //tfLog.setFont(new Font("Times New Roman", Font.BOLD, 18));
        tfLog.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        tfLog.setBackground(Color.lightGray);

        JPanel pnCentro2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnCentro22 = new JPanel(new GridLayout(2, 1));
        pnCentro22.add(lbSen);
        pnCentro22.add(tfSen);
        pnCentro2.add(pnCentro22);
        pnCentro.add(pnCentro2);
        tfSen.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        tfSen.setBackground(Color.lightGray);

        JPanel pnCentro3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnCentro32 = new JPanel(new GridLayout(2, 1));
        pnCentro32.add(lbEm);
        pnCentro32.add(tfEm);
        pnCentro3.add(pnCentro32);
        pnCentro.add(pnCentro3);
        tfEm.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        tfEm.setBackground(Color.lightGray);

        pnCentro.add(new JPanel());

        JPanel pnCentro4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnCentro4.add(btPerf);
        btPerf.setToolTipText("Efetuar login");
        pnCentro4.add(btReg);
        btReg.setToolTipText("Registra");
        pnCentro.add(pnCentro4);

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btReg.doClick();
            }
        };

        tfEm.addActionListener(action);

        btReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int axxx = 0;

                Usuario usuario = new Usuario();
                DAOUsuario daoUsuario = new DAOUsuario();
                List<Usuario> usuarios = daoUsuario.listInOrderId();

                String eM = tfEm.getText();
                String nM = tfLog.getText();
                String sN = tfSen.getText();

                if (tfEm.getText().equals("") | tfLog.getText().equals("") | tfSen.getText().equals("")) {

                    JOptionPane.showMessageDialog(cp, "Campos em branco ou inválidos");
                    
                    tfEm.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                    tfSen.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                    tfLog.setBorder(BorderFactory.createLineBorder(Color.red, 2));

                } else {

                    for (Usuario u : usuarios) {
                        if (eM.equals(u.getEmail())) {
                            axxx = 1;
                            JOptionPane.showMessageDialog(cp, "Este e-mail já está cadastrado...\n"
                                    + "Tente outro!");
                        }
                    }

                    if (axxx == 0) {
                        usuario.setIdUsuario(daoUsuario.autoIdUsuario());
                        usuario.setEmail(eM);
                        usuario.setLivrosAlugados(0);
                        usuario.setNome(nM);
                        usuario.setSenha(sN);
                        daoUsuario.inserir(usuario);
                        JOptionPane.showMessageDialog(cp, "Usuário cadastrado com sucesso!\n"
                                + "Você será redirecionado e já pode efetuar seu login!");
                        GUIlogin guIlogin = new GUIlogin();
                        dispose();
                    }
                }

            }
        });

        btPerf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                GUIlogin guIlogin = new GUIlogin();
                dispose();

            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
