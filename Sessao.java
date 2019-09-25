import java.awt.*;
import java.awt.Color.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;//default table model

import java.io.*;//file 
import java.util.*;//ArrayList

public class Sessao extends JDialog implements ActionListener{
	
	private JLabel lblCandidato, lblPartido, lblNumero, lblTitle;
    private JButton btnSair, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnVotar, btnCancelar;
    private JInternalFrame info;
    
	private Font ftitle = new Font("Arial", Font.BOLD, 30);
    private Font fnum = new Font("Arial", Font.BOLD, 24);
    private Font fbtnG = new Font("Arial", Font.BOLD, 18);
    private Font fbtn = new Font("Arial", Font.BOLD, 12);
    private Font flblNum = new Font("Arial", Font.BOLD, 30);
    private Font flblCandidato = new Font("Arial", Font.PLAIN, 25);
    private Font flblPartido = new Font("Arial", Font.BOLD, 25);

    private Color colVotar = new Color(0, 153, 0);
    private Color colSair = new Color(153, 0, 0);
    private Color colBtn = new Color(40, 40, 40);
    private Color colNum = Color.BLACK;  
    private Color colFrame = new Color(240, 240, 240);

    private Color coltxtVotar = Color.white;
    private Color coltxtSair = Color.white;
    private Color coltxtBtn = Color.white;
    private Color coltxtNum = Color.white;  

    private int wbtn = 100; private int hbtn = 50;
    private int wtitle = 670; private int htitle = 50;
    private int wframe = 700; private int hframe = 475;

    private int xBtn = (7*wbtn)/6; private int yBtn = hbtn + 20;
    private int xi = 333; private int yi = 80;

    private String numero = "";
    private Candidato candidato;
    private Partido partido;
    private Voto voto;
    private Banco banco;

    public Sessao() 
    {
        super();
        this.setTitle("Sessao de voto");

        banco = new Banco();
        candidato = new Candidato(); 
        partido = new Partido();
        voto = new Voto();

		this.getContentPane().setBackground(colFrame);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(wframe, hframe);
		setLayout(null);
        
        int x = xi+20;
        int y = yi - 7*yi/8;
		
		lblTitle = new JLabel("Urna Eletronica - CTI", SwingConstants.LEFT);
		lblTitle.setBounds(x, y, wtitle, htitle);
        lblTitle.setFont(ftitle);
        add(lblTitle);
        
        x = xi;
        y = yi;
		
		btn1 = new JButton("1");
		btn1.addActionListener(this);
		btn1.setBounds(x, y, wbtn, hbtn);
		btn1.setFont(fnum);
        btn1.setBackground(colNum);
        btn1.setForeground(coltxtNum);
        add(btn1);
        
        x += xBtn;
        y += 0;
		
		btn2 = new JButton("2");
		btn2.addActionListener(this);
		btn2.setBounds(x, y, wbtn, hbtn);
		btn2.setFont(fnum);
        btn2.setBackground(colNum);
        btn2.setForeground(coltxtNum);
		add(btn2);
        
        x += xBtn;
        y += 0;
		
		btn3 = new JButton("3");
		btn3.addActionListener(this);
		btn3.setBounds(x, y, wbtn, hbtn);
		btn3.setFont(fnum);
        btn3.setBackground(colNum);
        btn3.setForeground(coltxtNum);
		add(btn3);
        
        x -= 2*xBtn;
        y += yBtn;
		
		btn4 = new JButton("4");
		btn4.addActionListener(this);
		btn4.setBounds(x, y, wbtn, hbtn);
		btn4.setFont(fnum);
        btn4.setBackground(colNum);
        btn4.setForeground(coltxtNum);
		add(btn4);
        
        x += xBtn;
        y += 0;
		
		btn5 = new JButton("5");
		btn5.addActionListener(this);
		btn5.setBounds(x, y, wbtn, hbtn);
		btn5.setFont(fnum);
        btn5.setBackground(colNum);
        btn5.setForeground(coltxtNum);
		add(btn5);
        
        x += xBtn;
        y += 0;
		
		btn6 = new JButton("6");
		btn6.addActionListener(this);
		btn6.setBounds(x, y, wbtn, hbtn);
		btn6.setFont(fnum);
        btn6.setBackground(colNum);
        btn6.setForeground(coltxtNum);
		add(btn6);
        
        x -= 2*xBtn;
        y += yBtn;
		
		btn7 = new JButton("7");
		btn7.addActionListener(this);
		btn7.setBounds(x, y, wbtn, hbtn);
		btn7.setFont(fnum);
        btn7.setBackground(colNum);
        btn7.setForeground(coltxtNum);
		add(btn7);
        
        x += xBtn;
        y += 0;
		
		btn8 = new JButton("8");
		btn8.addActionListener(this);
		btn8.setBounds(x, y, wbtn, hbtn);
		btn8.setFont(fnum);
        btn8.setBackground(colNum);
        btn8.setForeground(coltxtNum);
		add(btn8);
        
        x += xBtn;
        y += 0;
		
		btn9 = new JButton("9");
		btn9.addActionListener(this);
		btn9.setBounds(x, y, wbtn, hbtn);
		btn9.setFont(fnum);
        btn9.setBackground(colNum);
        btn9.setForeground(coltxtNum);
		add(btn9);
        
        x -= xBtn;
        y += yBtn;
		
		btn0 = new JButton("0");
		btn0.addActionListener(this);
		btn0.setBounds(x, y, wbtn, hbtn);
		btn0.setFont(fnum);
        btn0.setBackground(colNum);
        btn0.setForeground(coltxtNum);
		add(btn0);
        
        x -= xBtn;
        y += yBtn;
		
		btnSair = new JButton("SAIR");
		btnSair.addActionListener(this);
		btnSair.setBounds(x, y, wbtn, hbtn);
		btnSair.setFont(fbtnG);
        btnSair.setBackground(colSair);
        btnSair.setForeground(coltxtSair);
		add(btnSair);
        
        x += xBtn;
        y += 0;
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(x, y, wbtn, hbtn);
		btnCancelar.setFont(fbtn);
        btnCancelar.setBackground(colBtn);
        btnCancelar.setForeground(coltxtBtn);
		add(btnCancelar);
        
        x += xBtn;
        y += 0;
		
		btnVotar = new JButton("VOTAR");
		btnVotar.addActionListener(this);
		btnVotar.setBounds(x, y, wbtn, hbtn);
        btnVotar.setFont(fbtnG);
        btnVotar.setBackground(colVotar);
        btnVotar.setForeground(coltxtVotar);
        add(btnVotar);
        
        /*
            Labels
        */

        int align = SwingConstants.LEFT;

        x = 40;
        y = 80;
        
        lblNumero = new JLabel("", align);
		lblNumero.setBounds(x, y, xi - 20 - x, 50);
		lblNumero.setFont(flblNum);
        add(lblNumero);

        x += 0;
        y += 30;
        
        lblCandidato = new JLabel("", align);
		lblCandidato.setBounds(x, y, xi - 20 - x, 50);
		lblCandidato.setFont(flblCandidato);
        add(lblCandidato);

        x += 0;
        y += 30;
        
        lblPartido = new JLabel("", align);
		lblPartido.setBounds(x, y, xi - 20 - x, 50);
		lblPartido.setFont(flblPartido);
        add(lblPartido);


		this.setAlwaysOnTop(true);
		setLocationRelativeTo(null);
        setVisible(true);
		
    }
    
    public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == btnSair) // FECHA JANELA
		{
			dispose();
        }

        if(ae.getSource() == btnCancelar)
        {
            limpa();
            return;
        }

        if(ae.getSource() == btnVotar)
        {
            banco.conectar();
            boolean existeCandidato = banco.procuraCandidato(candidato.getNumero());
            banco.desconectar();
            if(existeCandidato)
            {
                voto = new Voto();
                voto.setN_Candidato(candidato.getNumero());

                try {
                    banco.conectar();
                    banco.setVoto(voto);
                    banco.inserirVoto();
                    banco.desconectar(); 

                    JOptionPane.showMessageDialog(null, "Voto ao candidato " + candidato.getNome() + " registrado com sucesso!", "Voto registrado!", JOptionPane.INFORMATION_MESSAGE);
        
                    limpa();
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null, "Algo deu errado ao registrar seu voto!\n\nMais detalhes: " + e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(numero == "")
            {
                JOptionPane.showMessageDialog(null, "Digite algum numero!", "Erro!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Nao ha nenhum candidato registrado com esse numero!", "Erro!", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        
        if(ae.getSource() == btn0)
        {
            addNum('0');
            return;
        }

        if(ae.getSource() == btn1)
        {
            addNum('1');
            return;
        }

        if(ae.getSource() == btn2)
        {
            addNum('2');
            return;
        }

        if(ae.getSource() == btn3)
        {
            addNum('3');
            return;
        }

        if(ae.getSource() == btn4)
        {
            addNum('4');
            return;
        }

        if(ae.getSource() == btn5)
        {
            addNum('5');
            return;
        }

        if(ae.getSource() == btn6)
        {
            addNum('6');
            return;
        }

        if(ae.getSource() == btn7)
        {
            addNum('7');
            return;
        }

        if(ae.getSource() == btn8)
        {
            addNum('8');
            return;
        }

        if(ae.getSource() == btn9)
        {
            addNum('9');
            return;
        }
	}
    
    public static void main (String args[])
    {
		new Sessao();
    }
    
    public void addNum(char num){
        if(numero.length() == 5) 
            JOptionPane.showMessageDialog(null, "O maximo eh de 5 algarismos!", "Atencao", JOptionPane.WARNING_MESSAGE);
        else 
            numero = numero + num;
        
        limpaLbl();
        procura();
    
        lblNumero.setText(numero);
    }

    public void procura() {
        banco.conectar();
        if(banco.procuraCandidato(Integer.parseInt(numero))){
            candidato = banco.getCandidato();
            if(!banco.procuraPartido(candidato.getN_Partido()))
            {
                banco.desconectar();
                return;
            }
            partido = banco.getPartido();
            lblCandidato.setText(candidato.getNome());
            lblPartido.setText(partido.getNome() + " - " + partido.getNumero());
        }
        else {
            candidato = new Candidato();
        }
        banco.desconectar();
    }
    
    public void limpa() {
        candidato = new Candidato();
        partido = new Partido();
        voto = new Voto();

        numero = "";
        limpaLbl();
    }

    public void limpaLbl() {
        lblNumero.setText("");
        lblCandidato.setText("");
        lblPartido.setText("");
    }
}
