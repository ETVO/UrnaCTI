import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.*;
import java.awt.Image;
import java.io.*;

public class Sobre extends JFrame implements ActionListener {

    JLabel lbUrna,  lbImg1, lbImg2;
    JLabel lbTitle, lbDesc, lbDesc1, lbDesc2;
    JButton btVolta;
    Font font;

    public Sobre()
    {
        super("Sobre - Urna Eletronica CTI");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(420, 90);
    	setSize(500, 500);
    	setLayout(null);
    	getContentPane().setBackground(new Color(240, 240, 240));

        ImageIcon imag = new ImageIcon("imgs/urna.png");
        Image img = imag.getImage().getScaledInstance(150,150,Image.SCALE_DEFAULT);
        lbUrna=new JLabel(new ImageIcon(img));
        lbUrna.setBounds(30, 20, 150, 150);
        add(lbUrna);

        font = new Font("Arial", Font.BOLD, 25);

        lbTitle = new JLabel("Urna Eletronica 73A");
        lbTitle.setFont(font);
        lbTitle.setBounds(200, 30, 400, 30);
        add(lbTitle);

        font = new Font("Arial", Font.ITALIC, 15);

        lbDesc = new JLabel("Cadastre Partidos, Candidatos e Votos.");
        lbDesc.setBounds(210, 60, 300, 20);
        add(lbDesc);

        imag = new ImageIcon("imgs/estevao.jpg");
        img = imag.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT);
        lbImg1=new JLabel(new ImageIcon(img));
        lbImg1.setBounds(60, 200, 125, 125);
        add(lbImg1);

        font = new Font("Arial", Font.PLAIN, 18);

        lbDesc1 = new JLabel("Estevao, no. 9");
        lbDesc1.setBounds(60, 350, 125, 20);
        lbDesc1.setFont(font);
        add(lbDesc1);

        imag = new ImageIcon("imgs/marcos.jpeg");
        img = imag.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT);
        lbImg2=new JLabel(new ImageIcon(img));
        lbImg2.setBounds(300, 200, 125, 125);
        add(lbImg2);

        lbDesc2 = new JLabel("Marcos, no. 25");
        lbDesc2.setBounds(300, 350, 125, 20);
        lbDesc2.setFont(font);
        add(lbDesc2);

        btVolta = new JButton("Voltar");
        btVolta.setMnemonic('V');
        btVolta.addActionListener(this);
        btVolta.setBounds(40, 400, 80, 30);
        add(btVolta);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btVolta)
        {
            dispose();
        }
    } 
}