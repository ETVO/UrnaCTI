import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.*;
import java.awt.Image;
import java.util.Locale;

public class Urna extends JFrame implements ActionListener, MenuListener{

    private static final long serialVersionUID = 1L;
    
    private JMenuBar bar;
    private JMenu mVotar, mCadastro,mRelatorio,mSobre,mExit;
    private JMenuItem iCadCandidato, iCadPartido, iRelVotos;
    private JLabel txtTitle, txtDesc;
    private Font font;

    private Sobre sobre;
    private Sessao sessao;

    public Urna() {
        super("Urna Eletronica");
        
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 550);
    	setLayout(new GridBagLayout());
    	getContentPane().setBackground(new Color(240, 240, 240));

        // MenuBar ----------------

        bar = new JMenuBar();
        setJMenuBar(bar);

        // votar
        mVotar = new JMenu("Votar");
        mVotar.setMnemonic('V');
        mVotar.addMenuListener(this);

        // cadastro
        mCadastro = new JMenu("Cadastro");
        mCadastro.setMnemonic('C');
        mCadastro.addMenuListener(this);

        iCadPartido = new JMenuItem("Partido");
        iCadPartido.addActionListener(this);
        mCadastro.add(iCadPartido);
        
        iCadCandidato = new JMenuItem("Candidato");
        iCadCandidato.addActionListener(this);
        mCadastro.add(iCadCandidato);

        // relatorio
        mRelatorio = new JMenu("Relatorio");
        mRelatorio.setMnemonic('R');
        mRelatorio.addMenuListener(this);
        
        iRelVotos = new JMenuItem("Votos");
        iRelVotos.addActionListener(this);
        mRelatorio.add(iRelVotos);
        
        // sobre
        mSobre = new JMenu("Sobre");
        mSobre.setMnemonic('S');
        mSobre.addMenuListener(this);

        // exit
        mExit = new JMenu("Exit");
        mExit.setMnemonic('E');
        mExit.addMenuListener(this);

        bar.add(mVotar);bar.add(mCadastro);bar.add(mRelatorio);
        bar.add(mSobre);bar.add(mExit);

        // Layout -------------

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));


        font = new Font("Arial", Font.BOLD, 30);
        JPanel pTitle = new JPanel(new FlowLayout());
        
        txtTitle = new JLabel("Urna Eletronica - CTI");
        txtTitle.setFont(font);
        pTitle.add(txtTitle);
        // pTitle.setBackground(Color.RED);
        
        
        font = new Font("Arial", Font.ITALIC, 15);
        JPanel pDesc = new JPanel(new FlowLayout());
        
        txtDesc = new JLabel("Cadastre Partidos, Candidatos e Votos.");
        txtDesc.setFont(font);
        pDesc.add(txtDesc);
        // pDesc.setBackground(Color.YELLOW);
		
        box.add(pTitle);
        box.add(pDesc);
        

        add(box, new GridBagConstraints());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String args[]) {
        new Urna();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == iCadCandidato)
        {
            new CadCandidato();
        }
        else if (e.getSource() == iCadPartido)
        {
            new CadPartido();
        }
    } 

    public void menuSelected(MenuEvent e) {
        if(e.getSource()==mVotar)
        {
            if(JOptionPane.showConfirmDialog(null, "Deseja iniciar a sessao de votacao?", "Votar",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            {
                sessao = new Sessao();
            }
        }
        else if(e.getSource()==mExit)
        {
            if(JOptionPane.showConfirmDialog(null, "Deseja realmente sair?", "Tem certeza?",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) 
            == JOptionPane.YES_OPTION){
        		System.exit(0);
			}
        }
        else if(e.getSource() == mSobre)
        {
            sobre = new Sobre();
        }
    }
    public void menuDeselected(MenuEvent e) {
    }
    public void menuCanceled(MenuEvent e) {
    }
}
