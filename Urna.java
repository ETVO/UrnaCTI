import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class Urna extends JFrame implements ActionListener, MenuListener{

    private JMenuBar bar;
    private JMenu mVotar, mCadastro,mRelatorio,mSobre,mExit;
    private JMenuItem iCadCandidato, iCadPartido, iRelVotos, iRelPartidos, iRelCandidatos;
    private JLabel title, text;


    public Urna() {
        super("Urna Eletronica");
        
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(420, 90);
    	setSize(550, 550);
    	setLayout(new FlowLayout());
    	getContentPane().setBackground(new Color(240, 240, 240));

        bar = new JMenuBar();
        setJMenuBar(bar);

        mVotar = new JMenu("Votar");
        mVotar.setMnemonic('V');
        mVotar.addMenuListener(this);

        mCadastro = new JMenu("Cadastro");
        mCadastro.setMnemonic('C');
        mCadastro.addMenuListener(this);
        
        iCadCandidato = new JMenuItem("Candidato");
        iCadCandidato.addActionListener(this);
        mCadastro.add(iCadCandidato);
        
        iCadPartido = new JMenuItem("Partido");
        iCadPartido.addActionListener(this);
        mCadastro.add(iCadPartido);

        mRelatorio = new JMenu("Relatorio");
        mRelatorio.setMnemonic('R');
        mRelatorio.addMenuListener(this);
        
        iRelVotos = new JMenuItem("Votos");
        iRelVotos.addActionListener(this);
        mRelatorio.add(iRelVotos);
        
        iRelCandidatos = new JMenuItem("Candidatos");
        iRelCandidatos.addActionListener(this);
        mRelatorio.add(iRelCandidatos);
        
        iRelPartidos = new JMenuItem("Partidos");
        iRelPartidos.addActionListener(this);
        mRelatorio.add(iRelPartidos);

        mSobre = new JMenu("Sobre");
        mSobre.setMnemonic('S');
        mSobre.addMenuListener(this);

        mExit = new JMenu("Exit");
        mExit.setMnemonic('E');
        mExit.addMenuListener(this);

        bar.add(mVotar);bar.add(mCadastro);bar.add(mRelatorio);
        bar.add(mSobre);bar.add(mExit);
        
        title = new JLabel("Urna Eletrônica - CTI");
		title.setFont (title.getFont ().deriveFont(30.0f));
        
        add(title);
        
        show();
    }

    public static void main(String args[]) {
        new Urna();
    }

    public void actionPerformed(ActionEvent e){
    } 

    public void menuSelected(MenuEvent e) {
        if(e.getSource()==mVotar)
        {
            //setVisible(false);
            //new Votar();
        }
        else if(e.getSource()==mExit)
        {
        	int dialogButton = JOptionPane.YES_NO_OPTION;
        	int dialogResult = JOptionPane.showConfirmDialog (null, "Deseja realmente sair?","Warning",dialogButton);
			if(dialogResult == JOptionPane.YES_OPTION){
        		System.exit(0);
			}
        }
    }
    public void menuDeselected(MenuEvent e) {
    }
    public void menuCanceled(MenuEvent e) {
    }
}