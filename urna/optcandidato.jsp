<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %> 
<%@page language="Java" import="java.util.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Candidato"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <%@ page contentType="text/html; charset=UTF-8" %><% request.setCharacterEncoding("UTF-8"); %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Trabalho 73A</title>

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/altera.css">
</head>

<%
    Banco banco = new Banco();
    
    int numero = 0;
    String nome = "";
    
    int freq = 4;
    banco.conectar();
    ArrayList tudo = banco.tudoCandidato();
    banco.desconectar();
%>
<body>
    <div class="altcontent">
        <div class="tab">
            <h3>Alterar ou Excluir Candidato</h3>
        </div>
        <div class="form">
            <form method="get" id="form" action="exccandidato.jsp">
                <label for="numero">Selecione um candidato</label>
                <select name="numero" id="numero" required>
                <option value='' disabled selected>-- Selecione um candidato --</option>
                    <%
                    for(int i = 0; i < tudo.size(); i += freq)
                    {
                        numero = Integer.parseInt(tudo.get(i).toString());                        
                        nome = tudo.get(i+2).toString();
                        String candidato = "<option value='" + numero + "'>" + numero + " - " + nome + "</option>";
                        out.print(candidato);
                    }
                    %>
                </select>
        
                <br><br>

                <div class="submit">
                    <div class="submitContent">
                        <input type="submit" name="opcao" value="Alterar" class="twobtns">
                        <input type="submit" name="opcao" value="Excluir" class="twobtns danger">
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
</html>