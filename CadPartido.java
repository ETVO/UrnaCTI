import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;//default table model
import java.io.*;//file 
import java.util.*;//ArrayList

public class CadPartido extends JFrame implements ActionListener {
	
	Partido partido;
	// modelo -> tabela -> grade
	// VISUALIZAÇÃO DOS DADOS
	private DefaultTableModel modelo;
	private JTable tabela;
	private JScrollPane grade;
	
	// INCLUSÃO OU ALTERAÇÃO
	private JLabel lblNumero;
	private JLabel lblNome;
	private JTextField txtNumero; private int wtxtNumero = 60;
	private JTextField txtNome; private int wtxtNome = 150;
	
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
	
	CadPartido()
	{
		super("Cadastro de Partido");
		banco = new Banco();
		partido = new Partido();
		
		this.getContentPane().setBackground(Color.GRAY);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(610,400);
		setLayout(null);

		modelo = new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		modelo.addColumn("Numero");
		modelo.addColumn("Nome");
		
		tabela = new JTable(modelo);
		tabela.getTableHeader().setReorderingAllowed(false);
		
		int col = 0;
		tabela.getColumnModel().getColumn(col).setMaxWidth(60);
		tabela.getColumnModel().getColumn(col++).setMinWidth(60);
		tabela.getColumnModel().getColumn(col).setMinWidth(100);
		
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

		lblNome = new JLabel("Nome do Partido");
		lblNome.setBounds(x, y, wtxtNome, hlbl);
		lblNome.setFont(flbl);
		add(lblNome);

		x += 0;
		y += 5 + hlbl;

		txtNome  = new JTextField();
		txtNome.setBounds(x, y, wtxtNome, htxt);
		txtNome.setFont(ftxt);
		add(txtNome);

		/* BOTÕES
		 *
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
		
		leBanco();

		ativaCampos(false);
		
		setLocationRelativeTo(null);
        setVisible(true);
	}

	public static void main(String args[])
	{
		new CadPartido();
	}
	
	public void leBanco() { //PREENCHER A TABELA COM TODOS OS REGISTROS ENCONTRADOS
		ArrayList tudo = null;
		
		try {
			tudo = new ArrayList();
		
			banco.conectar();
			tudo = banco.tudoPartido(); // numero, nome
			banco.desconectar();
			
			if(tudo == null)
			{
				JOptionPane.showMessageDialog(null, "Tabela vazia...");
				return;
			}
			
			for(int i = 0; i < tudo.size(); i++) { // numero, nome
				partido.setNumero(Integer.parseInt(tudo.get(i++).toString()));
				partido.setNome(tudo.get(i).toString());
				
				String linha[] = {String.valueOf(partido.getNumero()), partido.getNome()};
				modelo.addRow(linha);
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Erro na leitura! " + e);
		}
			
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == btnSair) // FECHA JANELA
		{
			dispose();
		}
		
		if(ae.getSource() == btnIncluir) // ABRE EM BRANCO OS CAMPOS DE INCLUSÃO
		{
			linalt = -1;
			ativaCampos(true);
			limpaCampos();
			return;
		}
		
		if(ae.getSource() == btnAlterar) // ABRE ALTERAÇÃO DE REGISTRO SELECIONADO
		{
			linalt = tabela.getSelectedRow();
			if(linalt == -1)
			{
				JOptionPane.showMessageDialog(null,"Escolha uma linha, por favor... :)");
				if(tabela.getRowCount()>0)
					tabela.setRowSelectionInterval(0,0); // LIMPAR A SELECAO
				return;
			}
			
			partido.setNumero(Integer.parseInt(""+tabela.getValueAt(linalt, 0)));
			partido.setNumPrev(partido.getNumero());
			partido.setNome(""+tabela.getValueAt(linalt, 1));
			
			ativaCampos(true);
			
            txtNumero.setText("" + partido.getNumero());
			txtNome.setText("" + partido.getNome());
			
			txtNumero.grabFocus();
			
			return;
		}
		
		if(ae.getSource() == btnExcluir) // EXCLUI REGISTRO SELECIONADO
		{
			int linex = tabela.getSelectedRow();
			
			if(linex == -1)
			{
				JOptionPane.showMessageDialog(null,"Escolha uma linha, por favor... :)");
				if(tabela.getRowCount()>0)
					tabela.setRowSelectionInterval(0,0); // LIMPAR A SELECAO
				return;
			}
			
			if(JOptionPane.showConfirmDialog(null,"Confirma exclusao?")==0)
			{
				banco.conectar();
				
				banco.getPartido().setNumero(Integer.parseInt(""+tabela.getValueAt(linex, 0)));
				
				banco.excluirPartido();
				
				banco.desconectar();
				
				modelo.removeRow(linex);
			}
			
			return;
		}
		
		if(ae.getSource() == btnSalvar) // SALVA A INCLUSÃO OU A ALTERAÇÃO
		{
			// CONSISTENCIA GERAL (LER OS JOPTIONPANES) 
			try{
				int m = Integer.parseInt(txtNumero.getText());
			}
			catch(Exception e)
			{ 
				JOptionPane.showMessageDialog(null,"O campo Numero deve ter apenas numeros!");
				txtNumero.setText("");
				txtNumero.grabFocus();
				return; 
			}
			
			if(txtNumero.getText().length()!=2)//
			{
				JOptionPane.showMessageDialog(null,"Numero deve ter 2 algarismos!");
				txtNumero.setText("");
				txtNumero.grabFocus();
				return;
            }
            
			banco.conectar();
			boolean existe = banco.procuraPartido(Integer.parseInt(txtNumero.getText()));
			banco.desconectar();
			if(existe && linalt==-1)
			{
				JOptionPane.showMessageDialog(null,"Esse Numero ja esta cadastrado!");
				txtNumero.grabFocus();
				return;
			}
			
			if(txtNome.getText().length()==0)
			{
				JOptionPane.showMessageDialog(null,"O campo Nome eh obrigatorio!");
				txtNome.grabFocus();
				return;
			}
			
			ativaCampos(false);

			partido.setNumero(Integer.parseInt(txtNumero.getText()));
			partido.setNome(txtNome.getText());

			banco.setPartido(partido);
			
			String linha[] = {
				String.valueOf(partido.getNumero()),
				partido.getNome(), 
			};
				
			if (linalt==-1) // USUARIO ESTA INCLUINDO REGISTRO
			{
				modelo.addRow(linha); // ADIC. LINHA NA TABELA
				
				banco.conectar();
				banco.inserirPartido();
				banco.desconectar();
			}
			else{ // USUARIO ESTA ALTERANDO REGISTRO JA EXISTENTE
				tabela.setValueAt(String.valueOf(partido.getNumero()),linalt,0);
				tabela.setValueAt(partido.getNome(),linalt,1);
				
				banco.conectar();
				banco.alterarPartido();
				banco.desconectar();
			}
			tabela.setRowSelectionInterval(0,0); // LIMPAR A SELECAO

			return;
		}
		
		if(ae.getSource() == btnCancela) // CANCELA A INCLUSÃO OU A ALTERAÇÃO
		{
			ativaCampos(false);

			limpaCampos();

			if(tabela.getRowCount()>0)
				tabela.setRowSelectionInterval(0,0); // LIMPAR A SELECAO
			return;
		}
	}
	
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

	public void limpaCampos() {
		txtNumero.setText("");
		txtNome.setText("");
		txtNumero.grabFocus();
	}
}

