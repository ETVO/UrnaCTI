import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class Urna extends JFrame implements ActionListener, MenuListener{

    private JMenuBar bar;
    private JMenu mVotar, mCadastro,mRelatorio,mSobre,mExit;


    public Urna() {
        super("Urna Eletronica");
        
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(420, 90);
    	setSize(550, 550);
    	setLayout(null);
    	getContentPane().setBackground(new Color(240, 240, 240));

        bar = new JMenuBar();
        setJMenuBar(bar);

        mVotar = new JMenu("Votar");
        mVotar.setMnemonic('V');
        mVotar.addMenuListener(this);

        mCadastro = new JMenu("Cadastro");
        mCadastro.setMnemonic('C');
        mCadastro.addMenuListener(this);

        mRelatorio = new JMenu("Relatorio");
        mRelatorio.setMnemonic('R');
        mRelatorio.addMenuListener(this);

        mSobre = new JMenu("Sobre");
        mSobre.setMnemonic('S');
        mSobre.addMenuListener(this);

        mExit = new JMenu("Exit");
        mExit.setMnemonic('E');
        mExit.addMenuListener(this);

        bar.add(mVotar);bar.add(mCadastro);bar.add(mRelatorio);
        bar.add(mSobre);bar.add(mExit);

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
            setVisible(false);
            new Votar();
        }
        else if(e.getSource()==mCadastro)
        {

        }
    }
    public void menuDeselected(MenuEvent e) {
    }
    public void menuCanceled(MenuEvent e) {
    }
}