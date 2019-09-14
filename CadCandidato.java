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
	private JLabel Cargo;
	private JTextField txtNumero;
	private JTextField txtNome;
	private JTextField txtN_Partido;
	private JComboBox cmbCargo;
	
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
	
	CadCandidato()
	{
		super("Cadastro de Candidato");
		banco = new Banco();
		
		this.getContentPane().setBackground(Color.GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(200,100,700,500);
		setLayout(null);
		
		// Criando dos Objetos na Janela
		btnSair = new JButton("Sair");
		btnSair.setToolTipText("Aperte para sair");
		btnSair.addActionListener(this);
		btnSair.setBounds(260, 320, 100, 30);
		add(btnSair);


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
		
		grade = new JScrollPane(tabela);
		grade.setBounds(10,10,450,190);
		grade.setViewportView(tabela);
		add(grade);
		

		lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(10, 220, 60, 15);
		add(lblNumero);

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 250, 60, 15);
		add(lblNome);
		

		txtNumero  = new JTextField();
		txtNumero.setEnabled(false);
		txtNumero.setBounds(80, 220, 100, 20);
		add(txtNumero);

		txtNome = new JTextField();
		txtNome.setEnabled(false);
		txtNome.setBounds(80, 250, 220, 20);
		add(txtNome);

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


		btnIncluir = new JButton("Incluir");
		btnIncluir.setBounds(480, 50, 100, 30);
		btnIncluir.addActionListener(this);
		add(btnIncluir);

		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(480, 90, 100, 30);
		btnAlterar.addActionListener(this);
		add(btnAlterar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(480, 140, 100, 30);
		btnExcluir.addActionListener(this);
		add(btnExcluir);

		//SÓ APARECEM QUANDO O USER ESTA INCLUINDO OU ALTERANDO ALGUM REGISTRO
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		btnSalvar.setBounds(480, 190, 100, 30);
		btnSalvar.setEnabled(false);
		add(btnSalvar);

		btnCancela = new JButton("Cancelar");
		btnCancela.setBounds(480, 250, 100, 30);
		btnCancela.setEnabled(false);
		btnCancela.addActionListener(this);
		add(btnCancela);
		
		// leBanco();
		
		setVisible(true);
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

