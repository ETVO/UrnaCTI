<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %> 
<%@page language="Java" import="java.util.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Partido"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <%@ page contentType="text/html; charset=UTF-8" %><% request.setCharacterEncoding("UTF-8"); %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Incluir Candidato</title>
 

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/cadastro.css">
</head>

<%
    Banco banco = new Banco();
    
    int numero = 0;
    String nome = "";
    
    int freq = 2;
    banco.conectar();
    ArrayList tudo = banco.tudoPartido();
    banco.desconectar();
%>

<body>
    <div class="cadcontent">
        <div class="tab">
            <h3>Incluir Candidato</h3>
        </div>
        <div class="form">
            <form method="post" action="salvarcandidato.jsp">
                <label for="numero">Número</label>
                <input type="number" name="numero" id="numero" max="99999" min="10" step="1" autocomplete="off" required>
        
                <br><br>
                
                <label for="n_partido">Partido</label>
                <select name="n_partido" id="n_partido" required>
                <option value='' disabled selected>-- Selecione um partido --</option>
                    <%
                    for(int i = 0; i < tudo.size(); i += freq)
                    {
                        numero = Integer.parseInt(tudo.get(i).toString());                        
                        nome = tudo.get(i+1).toString();
                        String partido = "<option value='" + numero + "'>" + numero + " - " + nome + "</option>";
                        out.print(partido);
                    }
                    %>
                </select>
        
                <br><br>
                
                <label for="nome">Nome do candidato</label>
                <input type="text" name="nome" id="nome" autocomplete="off" required>
        
                <br><br>
                
                <label for="cargo">Cargo</label>
                <select name="cargo" id="cargo" required>
                <option value='' disabled selected>-- Selecione um cargo --</option>
                <option value='Deputado Federal'>Deputado Federal</option>
                <option value='Senador'>Senador</option>
                <option value='Presidente'>Presidente</option>
                <option value='Deputado Estadual'>Deputado Estadual</option>
                <option value='Governador'>Governador</option>
                </select>
        
                <br><br>

                <input type="submit" name="subCadCandidato" value="Salvar">
            </form>
        </div>
    </div>
</body>
    
</html>