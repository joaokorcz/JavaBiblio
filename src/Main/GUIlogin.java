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

public class GUIlogin extends JFrame {

    private Container cp;

    private JPanel pnCentro = new JPanel();
    private JPanel pnBotaoLog = new JPanel();
    private JPanel pnBotaoPerf = new JPanel();

    private JLabel lbLog = new JLabel("Email");
    private JTextField tfLog = new JTextField(20);
    private JLabel lbSen = new JLabel("Senha");
    private JTextField tfSen = new JTextField(20);

    private JButton btLogin = new JButton("Entrar");
    private JButton btPerf = new JButton("Não tenho uma conta");

    public GUIlogin() {

        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        setTitle("Login Menu");

        cp.add(pnCentro, BorderLayout.CENTER);
        pnCentro.setLayout(new GridLayout(5, 1));

        pnBotaoLog.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnCentro.add(pnBotaoLog);

        JPanel pnCentro1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnCentro12 = new JPanel(new GridLayout(2,1));        
        pnCentro12.add(lbLog);
        pnCentro12.add(tfLog);
        pnCentro1.add(pnCentro12);
        pnCentro.add(pnCentro1);
        //tfLog.setFont(new Font("Times New Roman", Font.BOLD, 18));
        tfLog.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        tfLog.setBackground(Color.lightGray);

        JPanel pnCentro2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnCentro22 = new JPanel(new GridLayout(2,1));  
        pnCentro22.add(lbSen);
        pnCentro22.add(tfSen);
        pnCentro2.add(pnCentro22);
        pnCentro.add(pnCentro2);
        tfSen.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        tfSen.setBackground(Color.lightGray);
        
        pnCentro.add(new JPanel());

        JPanel pnCentro3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnCentro3.add(btPerf);
        btPerf.setToolTipText("Criar um perfil");
        pnCentro3.add(btLogin);
        btLogin.setToolTipText("Acessa");
        pnCentro.add(pnCentro3);

        //tfLog.setText("adm");
        //tfSen.setText("adm");

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btLogin.doClick();
            }
        };

        tfLog.addActionListener(action);
        tfSen.addActionListener(action);

        btLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                Usuario usuario = new Usuario();
                DAOUsuario daoUsuario = new DAOUsuario();
                List<Usuario> usuarios = daoUsuario.listInOrderId();

                String eL = tfLog.getText();
                String sN = tfSen.getText();
                int ax = 0;
                for (Usuario u : usuarios) {
                    if (u.getEmail().equals(eL) && u.getSenha().equals(sN)) {
                        daoUsuario.setIdLogado(u.getIdUsuario());
                        usuario.setIdUsuario(u.getIdUsuario());
                        usuario.setSenha(u.getSenha());
                        usuario.setNome(u.getNome());
                        usuario.setEmail(u.getEmail());
                        usuario.setLivrosAlugados(u.getLivrosAlugados());
                        System.out.println(usuario.getNome());
                        GUImenu guImenu = new GUImenu(usuario);
                        ax = 1;
                        dispose();
                    }
                }
                if (ax == 0) {
                    JOptionPane.showMessageDialog(cp, "Login ou senha inválidos!");
                    tfLog.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                    tfSen.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                }

            }
        });

        btPerf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                GUIcadastro guIcadastro = new GUIcadastro();
                dispose();

            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
