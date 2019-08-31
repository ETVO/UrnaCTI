import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;//default table model
import java.io.*;//file 
import java.util.*;//scanner e formatter




public class Arqtxt extends JFrame implements ActionListener
{

	private DefaultTableModel modelo;
	private JTable tabela;
	private JScrollPane grade; 
	 
	 
	 
    private JTextArea objeto1;
    private JLabel lblmat;
    private JLabel lblnome;
    private JLabel lblturma;
    
    private JTextField txtmat;
    private JTextField txtnome;
    private JComboBox txtturma;
    
    private JButton btnincluir;
    private JButton btnalterar;
    private JButton btnexcluir;
    private JButton btnsalvar;
    private JButton btncancela;
    private JButton btnsair;
    
    private int linalt=0; 
    	
    	public void salvadisco()
    	{
    		Formatter arquivo;
    		String mat,nom,tur;
    		try{
    		
    		arquivo=new Formatter("Dados.txt");
    		for(int i=0;i<tabela.getRowCount();i++)
    		{
    		
    		mat=tabela.getValueAt(i,0)+"";
    		nom=tabela.getValueAt(i,1)+"";
    		nom=nom.replaceAll(" ","_");
    		tur=tabela.getValueAt(i,2)+"";
    			arquivo.format("%s %s %s \n",mat,nom,tur);
    		}//for
    		arquivo.close();
    		}catch(Exception erro)
    		{
    			JOptionPane.showMessageDialog(this,"Erro ao salvar no disco");
    			return;
    		}
    	}
    	public void ledisco()
    	{
    		File picanha; Scanner learquivo;
    		String mat,nom,tur;
    		try{
    			picanha=new File("Dados.txt");
    			if(picanha.exists())
    			{
    				learquivo=new Scanner(picanha);
    				while(learquivo.hasNext())
    				{
    				mat=learquivo.next();
    				
    				nom=learquivo.next();
    				nom=nom.replaceAll("_"," ");
    				tur=learquivo.next();
    				String linha[]={mat,nom,tur};
    				modelo.addRow(linha);
    			}
    				learquivo.close();
    			}
    			else
    			{
    				JOptionPane.showMessageDialog(this,"Dados.txt nao existe...:)");
    				return;
    			}
    		}
    		catch(Exception errole)
    		{
    			JOptionPane.showMessageDialog(this,"erro ao salvart no disco");
    			return ;
    		}
    	}
    
    public boolean procuramat(String xmat)
    {
		for (int j=0;j<tabela.getRowCount();j++)
		   if(xmat.equals(tabela.getValueAt(j,0)))return  true;
	    return  false;
	}
	 
    public void actionPerformed(ActionEvent xae)
	{
		if(xae.getSource()==btnsair)dispose();
		if(xae.getSource()==btnalterar)
		{
			linalt=tabela.getSelectedRow();
			if(linalt==-1)
			{
				JOptionPane.showMessageDialog(null,"Escolha uma linha, por favor..:)");
				if(tabela.getRowCount()>0)
				tabela.setRowSelectionInterval(0,0);
				return ;
				
			}
			String mat=""+tabela.getValueAt(linalt,0);
			String nom=""+tabela.getValueAt(linalt,1);
			String tur=""+tabela.getValueAt(linalt,2);
			tudo(true);
			txtmat.setText(mat);txtmat.setEnabled(false);
			txtnome.setText(nom);txtnome.grabFocus();
			txtturma.setSelectedItem(tur);
			return ;
		}
		if(xae.getSource()==btnexcluir)
		{
			int  linex = tabela.getSelectedRow();
			if(linex==-1)
			{
				JOptionPane.showMessageDialog(null,"escolha uma linha, por favor");
				if(tabela.getRowCount()>0)
					tabela.setRowSelectionInterval(0,0);
					return ;
			}
			if(JOptionPane.showConfirmDialog(null,"Confirma exclusão?")==0)
			modelo.removeRow(linex);
			salvadisco();
			return ;
			  
		}
		
		
		if(xae.getSource()==btnincluir)
		{
			
			tudo(true);linalt=-1;
			txtmat.setText("");
			txtnome.setText("");
			txtturma.setSelectedIndex(-1);
			txtmat.grabFocus();
			return;
			  
		}
		
		if(xae.getSource()==btncancela)
		{
			tudo(!true);
			txtmat.setText("");
			txtnome.setText("");
			txtturma.setSelectedIndex(-1);
			if(tabela.getRowCount()>0)
			tabela.setRowSelectionInterval(0,0);
			return;
			  
		}
		
		if(xae.getSource()==btnsalvar)
		{
			try{
				int m=Integer.parseInt(txtmat.getText());
			}catch(Exception erromat)
			{ txtmat.setText("");
			   txtmat.grabFocus();
				return; 
			}
			if(txtmat.getText().length()!=7)
			{
				txtmat.grabFocus();
				return ;
			}
			if(procuramat(txtmat.getText())&& linalt==-1)
			{
				JOptionPane.showMessageDialog(null,"matricula ja existe...:(");
				txtmat.grabFocus();
				return ;
			}
			if(txtnome.getText().length()==0)
			{
				txtnome.grabFocus();
				return ;
			}
			if(txtturma.getSelectedIndex()==-1)
			{
				txtturma.grabFocus();
				return ;
			}
			tudo(!true);
			String linha[] = { txtmat.getText(),
			txtnome.getText(),""+txtturma.getSelectedItem()
			};
			if (linalt==-1)
			modelo.addRow(linha);
			else{
				String mat = txtmat.getText();
				String nom = txtnome.getText();
				String tur = txtturma.getSelectedItem()+"";
				tabela.setValueAt(mat,linalt,0);
				tabela.setValueAt(nom,linalt,1);
				tabela.setValueAt(tur,linalt,2);
			}
			tabela.setRowSelectionInterval(0,0);
			salvadisco();

			return;
			  
		}
	}//castroo
	
	public static void main (String[] args){
		new Arqtxt();
	}
	
	public void tudo (boolean liga)
	{
		txtmat.setEnabled(liga);
		txtnome.setEnabled(liga);
		txtturma.setEnabled(liga);
		btnincluir.setEnabled(!liga);
		btnalterar.setEnabled(!liga);
		btnexcluir.setEnabled(!liga);
		btncancela.setEnabled(liga);
		btnsalvar.setEnabled(liga);
	}
	
    public Arqtxt()
    {
    	super("Arquivo Texto");
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setBounds(150,150,600,400);
    	setLayout(null);
    	getContentPane().setBackground(Color.RED);
    	// Bloco 3 - Criação dos Objetos na Janela
        btnsair = new JButton("Sair");
        btnsair.setToolTipText("Aperte para sair");
        btnsair.addActionListener(this);
        btnsair.setBounds(260, 320, 100, 30);
        add(btnsair);
        
        
       modelo = new DefaultTableModel()
       {
       	public boolean isCellEditable(int row, int column)
       	{
       		return false;
       	}
       };
       modelo.addColumn("Matricula");
       modelo.addColumn("Nome do aluno(a)");
       modelo.addColumn("Turma");
       tabela = new JTable(modelo);
       tabela.getTableHeader().setReorderingAllowed(false);
       //trava o troca troca
       tabela.getColumnModel().getColumn(0).setMaxWidth(80);
       tabela.getColumnModel().getColumn(0).setMinWidth(80);
       
            
       
              tabela.getColumnModel().getColumn(2).setMaxWidth(80);
       tabela.getColumnModel().getColumn(2).setMinWidth(80);
       /// tabela.setModel(model);
       grade = new JScrollPane(tabela);
       grade.setBounds(10,10,450,190);
       grade.setViewportView(tabela);
       add(grade);

        
        //objeto1 = new JTextArea();
        //objeto1.setBounds(10, 10, 450, 190);
        //add(objeto1);
        
        lblmat = new JLabel("Matricula:");
        lblmat.setBounds(10, 220, 60, 15);
        add(lblmat);
        
        lblnome = new JLabel("Nome.....:");
        lblnome.setBounds(10, 250, 60, 15);
        add(lblnome);
        
        lblturma = new JLabel("Turma....:");
        lblturma.setBounds(10, 280, 60, 15);
        add(lblturma);
        
        txtmat = new JTextField();
        txtmat.setEnabled(false);
        txtmat.setBounds(80, 220, 100, 20);
        add(txtmat);
        
        txtnome = new JTextField();
        txtnome.setEnabled(false);
        
        txtnome.setBounds(80, 250, 220, 20);
        add(txtnome);
        
        txtturma = new JComboBox();
        txtturma.addItem("73A");
        txtturma.addItem("73B");
        txtturma.addItem("73C");
        txtturma.setSelectedIndex(-1);
        txtturma.setEnabled(false);
        
        txtturma.setBounds(80, 280, 100, 20);
        add(txtturma);
        
        btnincluir = new JButton("Incluir");
        btnincluir.setBounds(480, 50, 100, 30);
        btnincluir.addActionListener(this);//ação
        add(btnincluir);
        
        btnalterar = new JButton("Alterar");
        btnalterar.setBounds(480, 90, 100, 30);
        btnalterar.addActionListener(this);
        add(btnalterar);
        
        btnexcluir = new JButton("Excluir");
        btnexcluir.setBounds(480, 140, 100, 30);
         btnexcluir.addActionListener(this);
        add(btnexcluir);
        
        btnsalvar = new JButton("Salvar");
        btnsalvar.addActionListener(this);
        btnsalvar.setBounds(480, 190, 100, 30);
        btnsalvar.setEnabled(!true);
        add(btnsalvar);
        
        btncancela = new JButton("Cancelar");
        btncancela.setBounds(480, 250, 100, 30);
        btncancela.setEnabled(!true);
        btncancela.addActionListener(this);//ação
        add(btncancela);
        ledisco();
    	show();
    }
}