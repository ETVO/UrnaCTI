import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;//default table model
import java.io.*;//file 
import java.util.*;//ArrayList

public class CadCandidato extends JFrame implements ActionListener, FocusListener {
	
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
	private JLabel lblNovoPartido;
	private JLabel lblRecarregar;
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
	private Font flink = new Font("Arial", Font.PLAIN, 12);
	private Font fbold = new Font("Arial", Font.BOLD, 13);

	// layout
	private int wbtn = 100; private int hbtn = 30;
	private int wtable = 440; private int htable = 190;  
	private int wlbl = 60; private int hlbl = 15; 
	private int wtxt = 50; private int htxt = 20;  
	private int wframe = 610; private int hframe = 400;

	Candidato candidato;
	
	CadCandidato()
	{
		super("Cadastro de Candidato");
		banco = new Banco();
		candidato = new Candidato();
		
		this.getContentPane().setBackground(Color.GRAY);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(wframe,hframe);
		setLayout(null);

		modelo = new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		modelo.addColumn("Numero");
		modelo.addColumn("Partido");
		modelo.addColumn("Nome");
		modelo.addColumn("Cargo");
		
		tabela = new JTable(modelo);
		tabela.getTableHeader().setReorderingAllowed(false);
		
		int col = 0;
		tabela.getColumnModel().getColumn(col).setMaxWidth(60);
		tabela.getColumnModel().getColumn(col++).setMinWidth(60);
		tabela.getColumnModel().getColumn(col).setMaxWidth(60);
		tabela.getColumnModel().getColumn(col++).setMinWidth(60);
		tabela.getColumnModel().getColumn(col++).setMinWidth(100);
		tabela.getColumnModel().getColumn(col++).setMinWidth(80);
		
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
		cmbCargo.addItem("Senador");
		cmbCargo.addItem("Presidente");
		cmbCargo.addItem("Deputado Estadual");
		cmbCargo.addItem("Governador");
		cmbCargo.setSelectedIndex(-1);
		cmbCargo.setBounds(x, y, wcmbCargo, htxt);
		cmbCargo.setFont(ftxt);
		add(cmbCargo);

		x -= 15 + wtxtNumero;
		y += 15 + hlbl;

		lblN_Partido = new JLabel("Partido");
		lblN_Partido.setBounds(x, y, wtxtNumero, hlbl);
		lblN_Partido.setFont(flbl);
		add(lblN_Partido);

		x += 0;
		y += 5 + hlbl;

		txtN_Partido  = new JTextField();
		txtN_Partido.setBounds(x, y, wtxtN_Partido, htxt);
		txtN_Partido.setFont(ftxt);
		add(txtN_Partido);

		x += 15 + wtxtNumero;
		y -= 5 + hlbl;

		lblNome= new JLabel("Nome do Candidato");
		lblNome.setBounds(x, y, wtxtNome, hlbl);
		lblNome.setFont(flbl);
		add(lblNome);

		x += 0;
		y += 5 + hlbl;

		txtNome  = new JTextField();
		txtNome.setBounds(x, y, wtxtNome, htxt);
		txtNome.setFont(ftxt);
		add(txtNome);

		x -= 15 + wtxtNumero;
		y += 10 + hlbl;

		lblNovoPartido = new JLabel("<html><u>Adicionar partido</u></html>");
		lblNovoPartido.setBounds(x, y, wtxtNome, hlbl);
		lblNovoPartido.setFont(flink);
		lblNovoPartido.setForeground(Color.blue);
		lblNovoPartido.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				new CadPartido();	
			}
		 });
		add(lblNovoPartido);

		x = 15 + wframe - wtxtNome;
		y += 0;

		lblRecarregar = new JLabel("<html><u>RECARREGAR</u></html>");
		lblRecarregar.setBounds(x, y, wtxtNome, hlbl);
		lblRecarregar.setFont(fbold);
		lblRecarregar.setForeground(Color.blue);
		lblRecarregar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				leBanco();	
			}
		 });
		add(lblRecarregar);
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

		addFocusListener(this);
		setLocationRelativeTo(null);
        setVisible(true);
	}

	public static void main(String args[])
	{
		new CadCandidato();
	}
	
	public void leBanco() { //PREENCHER A TABELA COM TODOS OS REGISTROS ENCONTRADOS
		ArrayList tudo = null;
		modelo.setRowCount(0);
		
		try {
			tudo = new ArrayList();
		
			banco.conectar();
			tudo = banco.tudoCandidato(); // numero, n_partido, nome, cargo
			banco.desconectar();
			
			if(tudo == null)
			{
				JOptionPane.showMessageDialog(null, "Tabela vazia...");
				return;
			}
			
			for(int i = 0; i < tudo.size(); i++) { // numero, n_partido, nome, cargo
				candidato.setNumero(Integer.parseInt(tudo.get(i++).toString()));
				candidato.setN_Partido(Integer.parseInt(tudo.get(i++).toString()));
				candidato.setNome(tudo.get(i++).toString());
				candidato.setCargo(Integer.parseInt(tudo.get(i).toString()));
				
				String linha[] = {
					String.valueOf(candidato.getNumero()), 
					String.valueOf(candidato.getN_Partido()), 
					candidato.getNome(), 
					candidato.getCargoString()
				};
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
			
			int i = 0;
			candidato.setNumero(Integer.parseInt(""+tabela.getValueAt(linalt, i++)));
			candidato.setNumPrev(candidato.getNumero());
			candidato.setN_Partido(Integer.parseInt(""+tabela.getValueAt(linalt, i++)));
			candidato.setNome(""+tabela.getValueAt(linalt, i++));
			candidato.setCargoString(""+tabela.getValueAt(linalt, i));
			
			ativaCampos(true);
			
			txtNumero.setText("" + candidato.getNumero());
			txtN_Partido.setText("" + candidato.getN_Partido());
			txtNome.setText(candidato.getNome());
			cmbCargo.setSelectedIndex(candidato.getCargo());
			
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
				
				banco.getCandidato().setNumero(Integer.parseInt(""+tabela.getValueAt(linex, 0)));
				
				banco.excluirCandidato();
				
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
			
			if(txtNumero.getText().length()>5)//
			{
				JOptionPane.showMessageDialog(null,"Numero deve ter 5 ou menos algarismos!");
				txtNumero.setText("");
				txtNumero.grabFocus();
				return;
			}

			
			try{
				int m = Integer.parseInt(txtN_Partido.getText());
			}
			catch(Exception e)
			{ 
				JOptionPane.showMessageDialog(null,"O campo Partido deve ter apenas numeros!");
				txtN_Partido.setText("");
				txtN_Partido.grabFocus();
				return; 
			}
			
			if(txtN_Partido.getText().length()!=2)//
			{
				JOptionPane.showMessageDialog(null,"Partido deve ter 2 algarismos!");
				txtN_Partido.setText("");
				txtN_Partido.grabFocus();
				return;
			}
			
			banco.conectar();
			boolean existe = banco.procuraCandidato(Integer.parseInt(txtNumero.getText()));
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

			if(cmbCargo.getSelectedIndex()==-1)
			{
				JOptionPane.showMessageDialog(null,"O campo Cargo eh obrigatorio!");
				cmbCargo.grabFocus();
				return;
			}
			
			ativaCampos(false);

			candidato.setNumero(Integer.parseInt(txtNumero.getText()));
			candidato.setN_Partido(Integer.parseInt(txtN_Partido.getText()));
			candidato.setNome(txtNome.getText());
			candidato.setCargo(cmbCargo.getSelectedIndex());

			banco.conectar();
			boolean existePartido = banco.procuraPartido(candidato.getN_Partido());
			banco.desconectar();
			if(!existePartido)
			{
				JOptionPane.showMessageDialog(null,"Esse partido nao existe!");
				txtN_Partido.setText("");
				txtN_Partido.grabFocus();
				return;
			}

			banco.setCandidato(candidato);
			
			String linha[] = {
				String.valueOf(candidato.getNumero()), 
				String.valueOf(candidato.getN_Partido()), 
				candidato.getNome(), 
				candidato.getCargoString()
			};
				
			if (linalt==-1) // USUARIO ESTA INCLUINDO REGISTRO
			{
				modelo.addRow(linha); // ADIC. LINHA NA TABELA
				
				banco.conectar();
				banco.inserirCandidato();
				banco.desconectar();
			}
			else{ // USUARIO ESTA ALTERANDO REGISTRO JA EXISTENTE
				int i = 0;
				tabela.setValueAt(String.valueOf(candidato.getNumero()),linalt,i++);
				tabela.setValueAt(String.valueOf(candidato.getN_Partido()),linalt,i++);
				tabela.setValueAt(candidato.getNome(),linalt,i++);
				tabela.setValueAt(candidato.getCargoString(),linalt,i++);
				
				banco.conectar();
				banco.alterarCandidato();
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

		if(ae.getSource() == lblNovoPartido)
		{			
            new CadPartido();

			return;
		}
	}

	public void focusGained(FocusEvent fe)
	{
		leBanco();
	}

	public void focusLost(FocusEvent fe)
	{
		leBanco();
	}
	
	public void ativaCampos(boolean ativado) // MÉTODO UTILIZADO PARA ATIVAR/DESATIVAR CAMPOS PARA INCLUSAO OU ALTERAÇÃO
	{
		txtNumero.setEnabled(ativado);
		txtNome.setEnabled(ativado);
		txtN_Partido.setEnabled(ativado);
		cmbCargo.setEnabled(ativado);
		btnIncluir.setEnabled(!ativado);
		btnAlterar.setEnabled(!ativado);
		btnExcluir.setEnabled(!ativado);
		btnCancela.setEnabled(ativado);
		btnSalvar.setEnabled(ativado);
	}

	public void limpaCampos() {
		txtNumero.setText("");
		cmbCargo.setSelectedIndex(-1);
		txtN_Partido.setText("");
		txtNome.setText("");
		txtNumero.grabFocus();
	}
}

