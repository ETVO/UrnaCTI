import java.sql.*;
import javax.swing.*;
import java.util.*; //arraylist

class Banco {
    private Candidato candidato;
    private Partido partido;

    //voto
    private long v_id_voto;
    private int v_n_candidato;
    private String v_momento;

    private String bd;
    private String fsql;
    private String url, usuario, senha, drive;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private Statement stmt;
    
    public Banco()
    { bdid=0; bdnome="";
      fsql="";
      con=null;
      usuario="turma73a";
      senha="picanha_fatiada";
      drive="org.postgresql.Driver";
      url="jdbc:postgresql://200.145.153.172:5432/urna";
    }//construtor
    
    
    	
    public int getId()
    { return bdid; }
    		
    public String getTipo()
    { return bdtipo;  }		
    
    
    public void incluir()//pkchave (chamada,turma)
    { fsql="insert into tabela(nome) values (?)";
    	try{
    		pstmt=con.prepareStatement(fsql);
    		pstmt.setString(1,bdtipo);
    		
    		pstmt.execute();
    		pstmt.close();
    	}catch(Exception erro)
    	{JOptionPane.showMessageDialog(null,
         "Erro na inclusao tabela 2:"+erro);
    	}
    }	
    public void excluir()
    { fsql="delete from tabela2 where id=?";
    	try{
    		pstmt=con.prepareStatement(fsql);
    		pstmt.setInt(1,bdid);
    		pstmt.execute();
    		pstmt.close();
    	}catch(Exception erro)
    	{JOptionPane.showMessageDialog(null,
         "Erro na exclusao:"+erro);
    	}
    }		
    public void alterar()
    { fsql="update tabela2 set nome=? where id=?";
    	try{
    		pstmt=con.prepareStatement(fsql);
    	
    		pstmt.setString(1,bdnome);
    		pstmt.setInt(2,bdid);
    		pstmt.execute();
    		pstmt.close();
    	}catch(Exception erro)
    	{JOptionPane.showMessageDialog(null,
         "Erro na alteracao tabela 2:"+erro);
    	}	
    }		
   public void connect()
   {  try{  
   	      Class.forName(drive);
   	      con=DriverManager.getConnection(url,usuario,senha);
   	      //JOptionPane.showMessageDialog(null,"ok");
      }catch(Exception erro)
      {  JOptionPane.showMessageDialog(null,
         "Erro na conexao:"+erro);
      }
   }//conectyyyyyy
   public void disconnect()
   {  try{ con.close();
      }catch(Exception erro)
      { JOptionPane.showMessageDialog(null,
         "Erro na desconexao:"+erro);
      }	
   } ///////////desconecta
   
   public ArrayList pegadados()
   {  ArrayList dados;
      dados=new ArrayList();
   	  fsql="select * from tabela2";
   	  try{
   	  	stmt=con.createStatement();
   	  	rs=stmt.executeQuery(fsql);
   	  	while(rs.next())
   	  	{
   	  		bdid=rs.getInt("id");
   	  		bdtipo=rs.getString("tipo");
   	  		dados.add(bdid);
   	  		dados.add(bdnome);
   	  	}///while	
   	  	stmt.close();
   	  }catch(Exception erro)
   	  {  JOptionPane.showMessageDialog(null,
         "Erro na leitura:"+erro);
   	  }
   	  return dados;
   }//pegadados
   
    public boolean procura(String id)
    {
        fsql="Select * from tabela2 where id=?";
        try{pstmt = con.prepareStatement(fsql);
            int idd=Integer.parseInt(id);
            pstmt.setInt(1, idd);
            rs=pstmt.executeQuery();
            if(rs.next())
            { return true;//achou
            }    
            pstmt.close();    
        }    catch(Exception erroi)
        {
             JOptionPane.showMessageDialog(null,
                " Erro procura:"+erroi);
        }
        return false;
}//procura 

    public String pegatipo(String id)
    {
        fsql="Select * from tabela2 where id=?";
        try{pstmt = con.prepareStatement(fsql);
            int idd=Integer.parseInt(id);
            pstmt.setInt(1, idd);
            rs=pstmt.executeQuery();
            if(rs.next())
            { return rs.getString("tipo");//achou
            }    
            pstmt.close();    
        }    catch(Exception erroi)
        {
             JOptionPane.showMessageDialog(null,
                " Erro procura:"+erroi);
        }
        return "vazio";
}//procura 



	public String retorna()
    {
        String volta="";
        fsql="select * from tabela2";
        try{
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_READ_ONLY);
            // executa
            rs=stmt.executeQuery(fsql);
            if(rs.last())
            {
                volta=rs.getString("id");
                //JOptionPane.showMessageDialog(null,"ultimo"+volta);
                
                return volta;
            }
        }    catch(Exception erroi)
        {
             JOptionPane.showMessageDialog(null,
                " Erro leitura :"+erroi);
        }
        return volta;            
    }    
    	
    	///////////////
   public static void main(String oi[])
   { bancotipo b2=new bancotipo();
     b2.connect();
     b2.setNome("tipocti bauru");
     b2.incluir();
     b2.disconnect();
   }
}///class
