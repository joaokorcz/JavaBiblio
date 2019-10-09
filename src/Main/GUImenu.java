package Main;

import Entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class GUImenu extends JFrame {

    private Container cp;

    private JPanel pnCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pnN = new JPanel();
    private JPanel pnS = new JPanel();
    private JPanel pnO = new JPanel();
    private JPanel pnL = new JPanel();
    private JPanel pnBts = new JPanel();

    private JLabel lbIc = new JLabel("");

    private JButton btAutor = new JButton("Autores");
    private JButton btSis = new JButton("Sistema");
    private JButton btEdit = new JButton("Editoras");
    private JButton btAlug = new JButton("Alug. e Dev.");
    private JButton btGen = new JButton("Gêneros");
    private JButton btSair = new JButton("Sair");
    
    private void usuNormal(){
        btAutor.setEnabled(false);
        btEdit.setEnabled(false);
        btGen.setEnabled(false);
        btSis.setEnabled(false);
    }

    public GUImenu(Usuario usuario) {
        
        if (usuario.getEmail().equals("adm")){            
        } else {
            usuNormal();
        }


        JLabel lbLog = new JLabel("Logado como: " + usuario.getNome());

        setSize(750, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        setTitle("Menu Principal");

        cp.add(pnN, BorderLayout.NORTH);
        lbIc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/banner.png")));
        pnN.add(lbIc);
        cp.add(pnS, BorderLayout.SOUTH);
        pnS.setLayout(new FlowLayout(FlowLayout.RIGHT));
        cp.add(pnO, BorderLayout.WEST);
        cp.add(pnL, BorderLayout.EAST);
        cp.add(pnCentro, BorderLayout.CENTER);

        pnCentro.setLayout(new GridLayout(5, 7));
        cp.add(pnCentro);
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(btAutor);
        pnCentro.add(new JPanel());
        pnCentro.add(btSis);
        pnCentro.add(new JPanel());
        pnCentro.add(btEdit);
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(btAlug);
        pnCentro.add(new JPanel());
        pnCentro.add(btGen);
        pnCentro.add(new JPanel());
        pnCentro.add(btSair);
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());
        pnCentro.add(new JPanel());

        lbLog.setForeground(Color.RED);
        pnS.add(lbLog);

        btAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUIautor guiAutor = new GUIautor(usuario);
                dispose();
            }
        });

        btSis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUI gui = new GUI(usuario);
                dispose();
            }
        });

        btGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUIgen GUIgen = new GUIgen(usuario);
                dispose();
            }
        });

        btEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUIedit guiEdit = new GUIedit(usuario);
                dispose();
            }
        });

        btAlug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUIaluga guIaluga = new GUIaluga(usuario);
                dispose();
            }
        });

        btSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });

        pnS.setBackground(Color.ORANGE);

        pnN.setBackground(Color.ORANGE);

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
