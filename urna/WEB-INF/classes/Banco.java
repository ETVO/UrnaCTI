package database;

import java.sql.*;
import javax.swing.*;
import java.util.*;

public class Banco {
    private Candidato candidato;
    private Partido partido;
    private Voto voto;
	
	// Conexão com o banco
	private Connection con;
	private String url, user, senha, driver;
	private String sql;
	
	// Manipulação de tabelas e dados
	private PreparedStatement ps;
	private ResultSet rs;
	
	public Banco(){
		con=null;
		url="jdbc:postgresql://localhost:5432/bancocti"; // url para conexão (postgres, servidor e banco)
		user="postgres";
		senha="postgres";
		driver="org.postgresql.Driver";
		sql=""; // string para armazenar comandos sql

		candidato = new Candidato();
		partido = new Partido();
		voto = new Voto();
	}
	
	public static void main(String args[])
	{
		new Banco();
	}
	
	public void conectar()
	{
		try
		{
			Class.forName(driver);// DEFINE O DRIVER QUE SERA USADO
			con=DriverManager.getConnection(url,user,senha);// CRIA E INICIA CONEXÃO DENTRO DA VARIAVEL con
			
			// CONEXÃO REALIZADA COM SUCESSO!
		}
		catch(Exception erroc)  
		{
			JOptionPane.showMessageDialog(null,"Erro na conexao");
		}
	}
	
	public void desconectar()
	{
		try
		{
			con.close();
		}
		catch(Exception e)  
		{
			JOptionPane.showMessageDialog(null,"Erro na desconexao");
		}
	}
	
	// GET E SET DOS OBJETOS LOCAIS bCandidato, bPartido, bVoto -------------------------
	public Candidato getCandidato() { return candidato;}
	public void setCandidato(Candidato candidato) { this.candidato = candidato; }
	
	public Partido getPartido() { return partido;}
    public void setPartido(Partido partido) { this.partido = partido; }
    
	public Voto getVoto() { return voto;}
	public void setVoto(Voto voto) { this.voto = voto; }
    
    
    // MÉTODOS DE INSERÇÃO NO BANCO --------------------------------------------------
	public void inserirCandidato() {
		sql = "INSERT INTO urna.candidato VALUES(?, ?, ?, ?)";
        
        try {
            ps = con.prepareStatement(sql);// numero, n_partido, nome, cargo
            ps.setInt(1, candidato.getNumero());
            ps.setInt(2, candidato.getN_Partido());
            ps.setString(3, candidato.getNome());
            ps.setInt(4, candidato.getCargo());
            ps.execute();
            ps.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro ao inserir candidato "+candidato.getNome()+": " + e.getMessage());
        }
    }

    public void inserirPartido() {
		sql = "INSERT INTO urna.partido VALUES(?, ?)";
        
        try {
            ps = con.prepareStatement(sql);// numero, nome, obs
            ps.setInt(1, partido.getNumero());
            ps.setString(2, partido.getNome());
            ps.execute();
            ps.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro ao inserir partido "+partido.getNome()+": " + e.getMessage());
        }
    }

    public void inserirVoto() {
        if(voto.getId_Voto() != 0)
            sql = "INSERT INTO urna.voto VALUES(?, ?, DEFAULT)";
        else
            sql = "INSERT INTO urna.voto VALUES(DEFAULT, ?, DEFAULT)";
        
        try {
            ps = con.prepareStatement(sql);// id_voto, n_candidato, momento
            
            int i = 1;
            if(voto.getId_Voto() != 0)
                ps.setLong(i++, voto.getId_Voto());
            ps.setInt(i, voto.getN_Candidato());

            ps.execute();
            ps.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro ao inserir voto: " + e.getMessage());
        }
    }

    // MÉTODOS DE ALTERAÇÃO NO BANCO --------------------------------------------------
    public void alterarCandidato(){
		sql = "UPDATE urna.candidato SET numero = ?, n_partido = ?, nome = ?, cargo = ? WHERE numero = ?";
        
        try {
			int i = 1;
            ps = con.prepareStatement(sql);// n_partido, nome, cargo, numero
            ps.setInt(i++, candidato.getNumero());
            ps.setInt(i++, candidato.getN_Partido());
            ps.setString(i++, candidato.getNome());
            ps.setInt(i++, candidato.getCargo());
            ps.setInt(i++, candidato.getNumPrev());
            ps.execute();
            ps.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro ao alterar candidato "+candidato.getNome()+": " + e.getMessage());
        }
	}
    
    public void alterarPartido(){
		sql = "UPDATE urna.partido SET numero = ?, nome = ? WHERE numero = ?";
        
        try {
			ps = con.prepareStatement(sql);// nome, numero 
			ps.setInt(1, partido.getNumero());
            ps.setString(2, partido.getNome());
            ps.setInt(3, partido.getNumPrev());
            ps.execute();
            ps.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro ao alterar partido "+partido.getNome()+": " + e.getMessage());
		}
    }
    
    
    // MÉTODOS DE EXCLUSÃO NO BANCO --------------------------------------------------
	public void excluirCandidato(){
		sql = "DELETE FROM urna.candidato WHERE numero = ?";
		
        try {
            ps = con.prepareStatement(sql);// numero
            ps.setInt(1, candidato.getNumero());
            ps.execute();
            ps.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro ao excluir candidato "+candidato.getNome()+": " + e.getMessage());
        }
    }
    
    public void excluirPartido(){ // partido e todos seus candidatos são excluídos
		sql = "DELETE FROM urna.partido WHERE numero = ?";
		
        try {
            ps = con.prepareStatement(sql);// numero 
            ps.setInt(1, partido.getNumero());
            ps.execute();
            ps.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro ao excluir partido "+partido.getNome()+": " + e.getMessage());
        }

        sql = "DELETE FROM urna.candidato WHERE n_partido = ?";
		
        try {
            ps = con.prepareStatement(sql);// numero 
            ps.setInt(1, partido.getNumero());
            ps.execute();
            ps.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro ao excluir candidatos do "+partido.getNome()+": " + e.getMessage());
        }
    }
    
	// MÉTODOS DE SELEÇÃO --------------------------------------------------
	public ArrayList tudoCandidato() {
		ArrayList dados = null;
		
		sql = "SELECT * FROM urna.candidato";
		
		try {
			dados = new ArrayList();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery(); // tabela de dados retornada pelo banco (result set)
			
			while(rs.next()){// numero, n_partido, nome, cargo
				candidato.setNumero(rs.getInt("numero"));
				candidato.setN_Partido(rs.getInt("n_partido"));
				candidato.setNome(rs.getString("nome"));
				candidato.setCargo(rs.getInt("cargo"));

                dados.add(candidato.getNumero());
                dados.add(candidato.getN_Partido());
                dados.add(candidato.getNome());
                dados.add(candidato.getCargoString());
			}
			
			ps.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Erro em selecionar todos os candidatos: " + e.getMessage());
		}
		
		return dados;
    }
    
    public ArrayList tudoPartido() {
		ArrayList dados = null;
		
		sql = "SELECT * FROM urna.partido";
		
		try {
			dados = new ArrayList();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery(); // tabela de dados retornada pelo banco (result set)
			
			while(rs.next()){// numero, nome
				partido.setNumero(rs.getInt("numero"));
				partido.setNome(rs.getString("nome"));
				
                dados.add(partido.getNumero());
                dados.add(partido.getNome());
			}
			
			ps.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Erro em selecionar todos os partidos: " + e.getMessage());
		}
		
		return dados;
    }
    
    public ArrayList tudoVoto() {
		ArrayList dados = null;
		
		sql = "SELECT * FROM urna.voto";
		
		try {
			dados = new ArrayList();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery(); // tabela de dados retornada pelo banco (result set)
			
			while(rs.next()){// id_voto, n_candidato, momento
				voto.setId_Voto(rs.getLong("id_voto"));
				voto.setN_Candidato(rs.getInt("n_candidato"));
				voto.setMomento(rs.getString("momento"));
				
                dados.add(voto.getId_Voto());
                dados.add(voto.getN_Candidato());
                dados.add(voto.getMomentoDate());
			}
			
			ps.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Erro em selecionar todos os votos: " + e.getMessage());
		}
		
		return dados;
	}
	
	public boolean procuraCandidato(int pk) {
		boolean volta = false;
        sql = "SELECT * FROM urna.candidato WHERE numero = ?";
        		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, pk);
			
			rs = ps.executeQuery(); // guardar na tabela rs (result set) o retorno do banco
			
			if(rs.next())// numero, n_partido, nome, cargo
			{
				volta = true;

				candidato.setNumero(rs.getInt("numero"));
				candidato.setN_Partido(rs.getInt("n_partido"));
				candidato.setNome(rs.getString("nome"));
				candidato.setCargo(rs.getInt("cargo"));
			}
			
			ps.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Erro em procurar PK "+pk+" de candidato: " + e);
		}
		
		return volta;
    }
    
    public boolean procuraPartido(int pk) {
		boolean volta = false;
        sql = "SELECT * FROM urna.partido WHERE numero = ?";
        		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, pk);
			
			rs = ps.executeQuery();
			
			if(rs.next())// numero, nome
			{
				volta = true;
				
				partido.setNumero(rs.getInt("numero"));
				partido.setNome(rs.getString("nome"));
			}
			
			ps.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Erro em procurar PK "+pk+" de partido: " + e.getMessage());
		}
		
		return volta;
	}
}
