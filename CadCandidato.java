import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;//default table model
import java.io.*;//file 
import java.util.*;//ArrayList

public class CadCandidato extends JFrame implements ActionListener {
	
	// modelo -> tabela -> grade
	// VISUALIZAÇÃO DOS DADOS
	private DefaultTableModel modelo;
	private JTable tabela;
	private JScrollPane grade;
	
	// INCLUSÃO OU ALTERAÇÃO
	private JLabel lblNumero;
	private JLabel lblNome;
	private JLabel lblN_Partido;
	private JLabel lblCargo;
	private JTextField txtNumero; private int wtxtNumero = 60;
	private JTextField txtNome; private int wtxtNome = 150;
	private JTextField txtN_Partido; private int wtxtN_Partido = 40;
	private JComboBox cmbCargo; private int wcmbCargo = 150;
	
	// BOTÕES DE OPERAÇÕES
	private JButton btnIncluir;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JButton btnCancela;
	private JButton btnSair;
	
	// OBJETO DA CLASSE BANCOSQL
	private Banco banco;
	
	// MONITORAMENTO DA LINHA SELECIONADA
	private int linalt=0;
	
	//Font
	private Font font;
	private Font flbl = new Font("Arial", Font.PLAIN, 13);
	private Font fbtn = new Font("Arial", Font.PLAIN, 13);
	private Font ftxt = new Font("Arial", Font.PLAIN, 14);

	// layout
	private int wbtn = 100; private int hbtn = 30;
	private int wtable = 440; private int htable = 190;  
	private int wlbl = 60; private int hlbl = 15; 
	private int wtxt = 50; private int htxt = 20;  
	
	CadCandidato()
	{
		super("Cadastro de Candidato");
		banco = new Banco();
		
		this.getContentPane().setBackground(Color.GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(610,400);
		setLayout(null);

		modelo = new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		modelo.addColumn("Matricula");
		modelo.addColumn("Nome do aluno(a)");
		
		tabela = new JTable(modelo); // Matricula [0], Nome do aluno(a) [1]
		tabela.getTableHeader().setReorderingAllowed(false); //trava o troca troca de ordem
		
		tabela.getColumnModel().getColumn(0).setMaxWidth(80);
		tabela.getColumnModel().getColumn(0).setMinWidth(80);
		
		int x = 20, y = 10;
		
		grade = new JScrollPane(tabela);
		grade.setBounds(x, y, wtable, htable);
		grade.setViewportView(tabela);
		add(grade);

		x += 0;
		y += 15 + htable;

		lblNumero = new JLabel("Numero");
		lblNumero.setBounds(x, y, wlbl, hlbl);
		lblNumero.setFont(flbl);
		add(lblNumero);

		x += 0;
		y += 5 + hlbl;

		txtNumero  = new JTextField();
		txtNumero.setEnabled(false);
		txtNumero.setBounds(x, y, wtxtNumero, htxt);
		txtNumero.setFont(ftxt);
		add(txtNumero);

		x += 15 + wtxtNumero;
		y -= 5 + hlbl;

		lblCargo = new JLabel("Cargo");
		lblCargo.setBounds(x, y, wlbl, hlbl);
		lblCargo.setFont(flbl);
		add(lblCargo);

		x += 0;
		y += 5 + hlbl;

		cmbCargo  = new JComboBox<>();
		cmbCargo.addItem("Deputado Federal");
		cmbCargo.setSelectedIndex(-1);
		cmbCargo.setBounds(x, y, wcmbCargo, htxt);
		cmbCargo.setFont(ftxt);
		add(cmbCargo);

	/* 
		NÃO SERA USADO NESSE CASO
		txtturma = new JComboBox();
		txtturma.addItem("73A");//0
		txtturma.addItem("73B");//1
		txtturma.addItem("73C");//2
		txtturma.setSelectedIndex(-1);
		txtturma.setEnabled(false);
		txtturma.setBounds(80, 280, 100, 20);
		add(txtturma);
	*/

		x = 10;
		y = 20 + wtable + 20;

		btnIncluir = new JButton("Incluir");
		btnIncluir.setBounds(y, x, wbtn, hbtn);
		btnIncluir.addActionListener(this);
		btnIncluir.setFont(fbtn);
		add(btnIncluir);

		x += wbtn/2;
		y += 0;

		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(y, x, wbtn, hbtn);
		btnAlterar.addActionListener(this);
		btnAlterar.setFont(fbtn);
		add(btnAlterar);

		x += wbtn/2;
		y += 0;

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(y, x, wbtn, hbtn);
		btnExcluir.addActionListener(this);
		btnExcluir.setFont(fbtn);
		add(btnExcluir);

		x += wbtn/2;
		y += 0;

		//SÓ APARECEM QUANDO O USER ESTA INCLUINDO OU ALTERANDO ALGUM REGISTRO
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		btnSalvar.setBounds(y, x, wbtn, hbtn);
		btnSalvar.setEnabled(false);
		btnSalvar.setFont(fbtn);
		add(btnSalvar);

		x += wbtn/2;
		y += 0;

		btnCancela = new JButton("Cancelar");
		btnCancela.setBounds(y, x, wbtn, hbtn);
		btnCancela.setEnabled(false);
		btnCancela.addActionListener(this);
		btnCancela.setFont(fbtn);
		add(btnCancela);

		x += wbtn/2;
		y += -10;
		
		font = new Font("Arial", Font.BOLD, 15);

		btnSair = new JButton("Sair");
		btnSair.setToolTipText("Aperte para sair");
		btnSair.addActionListener(this);
		btnSair.setBounds(y, x, wbtn+10, hbtn+10);
		btnSair.setFont(font);
		add(btnSair);
		
		// leBanco();
		
		setLocationRelativeTo(null);
        setVisible(true);
	}

	public static void main(String args[])
	{
		new CadCandidato();
	}
	
	// public void leBanco() { //PREENCHER A TABELA COM TODOS OS REGISTROS ENCONTRADOS
	// 	ArrayList tudo = null;
	// 	String mat, nome;
		
	// 	try {
	// 		tudo = new ArrayList();
		
	// 		banco.conectar();
	// 		tudo = banco.pegaTudo();
	// 		banco.desconectar();
			
	// 		if(tudo == null)
	// 		{
	// 			JOptionPane.showMessageDialog(null, "Tabela vazia...");
	// 			return;
	// 		}
			
	// 		for(int i = 0; i < tudo.size(); i+=2) { // ArrayList -> ['1757075', 'Estevão', '1757063', 'Joãozinho']
	// 			mat = "" + tudo.get(i);
	// 			nome = "" + tudo.get(i+1);
				
	// 			String linha[] = {mat, nome};
	// 			modelo.addRow(linha);
	// 		}
	// 	}
	// 	catch (Exception e)
	// 	{
	// 		JOptionPane.showMessageDialog(null, "Erro na leitura!");
	// 	}
			
	// }
	
	
	public void actionPerformed(ActionEvent ae){}
	// {
	// 	if(ae.getSource() == btnSair) // FECHA JANELA
	// 	{
	// 		dispose();
	// 	}
		
	// 	if(ae.getSource() == btnIncluir) // ABRE EM BRANCO OS CAMPOS DE INCLUSÃO
	// 	{
	// 		linalt = -1;
	// 		ativaCampos(true);
	// 		txtMat.setText("");
	// 		txtNome.setText("");
	// 		txtMat.grabFocus();
	// 		return;
	// 	}
		
	// 	if(ae.getSource() == btnAlterar) // ABRE ALTERAÇÃO DE REGISTRO SELECIONADO
	// 	{
	// 		linalt = tabela.getSelectedRow();
	// 		if(linalt == -1)
	// 		{
	// 			JOptionPane.showMessageDialog(null,"Escolha uma linha, por favor... :)");
	// 			if(tabela.getRowCount()>0)
	// 				tabela.setRowSelectionInterval(0,0); // LIMPAR A SELECAO
	// 			return;
	// 		}
			
	// 		String mat = ""+tabela.getValueAt(linalt, 0);
	// 		String nome = ""+tabela.getValueAt(linalt, 1);
	// 		//String tur=""+tabela.getValueAt(linalt,2);
			
	// 		ativaCampos(true);
			
	// 		txtMat.setText(mat);
	// 		txtNome.setText(nome);
	// 		//txtturma.setSelectedItem(tur);
			
	// 		txtMat.grabFocus();
			
	// 		return;
	// 	}
		
	// 	if(ae.getSource() == btnExcluir) // EXCLUI REGISTRO SELECIONADO
	// 	{
	// 		int linex = tabela.getSelectedRow();
			
	// 		if(linex == -1)
	// 		{
	// 			JOptionPane.showMessageDialog(null,"Escolha uma linha, por favor... :)");
	// 			if(tabela.getRowCount()>0)
	// 				tabela.setRowSelectionInterval(0,0); // LIMPAR A SELECAO
	// 			return;
	// 		}
			
	// 		if(JOptionPane.showConfirmDialog(null,"Confirma exclusao?")==0)
	// 		{
	// 			banco.conectar();
	// 			String mat = tabela.getValueAt(linex, 0).toString();
				
	// 			banco.setMat(mat);
				
	// 			banco.excluir();
				
	// 			banco.desconectar();
				
	// 			modelo.removeRow(linex);
				
	// 			return;
	// 		}
	// 	}
		
	// 	if(ae.getSource() == btnSalvar) // SALVA A INCLUSÃO OU A ALTERAÇÃO
	// 	{
	// 		// CONSISTENCIA GERAL (LER OS JOPTIONPANES) 
	// 		try{
	// 			int m = Integer.parseInt(txtMat.getText());
	// 		}
	// 		catch(Exception e)
	// 		{ 
	// 			JOptionPane.showMessageDialog(null,"Matricula deve ter apenas numeros!");
	// 			txtMat.setText("");
	// 		  txtMat.grabFocus();
	// 			return; 
	// 		}
			
	// 		if(txtMat.getText().length()!=7)//1757057
	// 		{
	// 			JOptionPane.showMessageDialog(null,"Matricula deve ter 7 algarismos!");
	// 			txtMat.grabFocus();
	// 			return;
	// 		}
			
	// 		banco.conectar();
	// 		boolean existe = banco.procuraPK(txtMat.getText());
	// 		banco.desconectar();
	// 		if(existe && linalt==-1)
	// 		{
	// 			JOptionPane.showMessageDialog(null,"Matricula ja existe... :(");
	// 			txtMat.grabFocus();
	// 			return;
	// 		}
			
	// 		if(txtNome.getText().length()==0)
	// 		{
	// 			JOptionPane.showMessageDialog(null,"O campo Nome eh obrigatorio");
	// 			txtNome.grabFocus();
	// 			return;
	// 		}
			
	// 		/* CONSISTENCIA PARA VER SE SELECIONOU ALGO NA TURMA 
	// 		 * (não usamos nesse programa, mas pode ser que use na prova)
	// 		if(txtturma.getSelectedIndex()==-1)
	// 		{
	// 			txtturma.grabFocus();
	// 			return;
	// 		}
	// 		*/ 
			
	// 		ativaCampos(false);
			
	// 		String mat = txtMat.getText();
	// 		String nome = txtNome.getText();
	// 		//String tur = txtturma.getSelectedItem()+"";
						
	// 		banco.setMat(mat);
	// 		banco.setNome(nome);
			
	// 		String linha[] = { mat, nome }; // LINHA A SER INSERIDA NA TABELA
			
	// 		if (linalt==-1) // USUARIO ESTA INCLUINDO REGISTRO
	// 		{
	// 			modelo.addRow(linha); // ADIC. LINHA NA TABELA
				
	// 			banco.conectar();
	// 			banco.incluir();
	// 			banco.desconectar();
	// 		}
	// 		else{ // USUARIO ESTA ALTERANDO REGISTRO JA EXISTENTE
	// 			tabela.setValueAt(mat,linalt,0);
	// 			tabela.setValueAt(nome,linalt,1);
	// 			//tabela.setValueAt(tur,linalt,2);
				
	// 			banco.conectar();
	// 			banco.alterar();
	// 			banco.desconectar();
	// 		}
	// 		tabela.setRowSelectionInterval(0,0); // LIMPAR A SELECAO

	// 		return;
	// 	}
		
	// 	if(ae.getSource() == btnCancela) // CANCELA A INCLUSÃO OU A ALTERAÇÃO
	// 	{
	// 		ativaCampos(false);
	// 		txtMat.setText("");
	// 		txtNome.setText("");
	// 		//txtturma.setSelectedIndex(-1);
	// 		if(tabela.getRowCount()>0)
	// 			tabela.setRowSelectionInterval(0,0); // LIMPAR A SELECAO
	// 		return;
	// 	}
	// }
	
	public void ativaCampos(boolean ativado) // MÉTODO UTILIZADO PARA ATIVAR/DESATIVAR CAMPOS PARA INCLUSAO OU ALTERAÇÃO
	{
		txtNumero.setEnabled(ativado);
		txtNome.setEnabled(ativado);
		btnIncluir.setEnabled(!ativado);
		btnAlterar.setEnabled(!ativado);
		btnExcluir.setEnabled(!ativado);
		btnCancela.setEnabled(ativado);
		btnSalvar.setEnabled(ativado);
	}
}

