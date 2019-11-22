<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Partido"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <%@ page contentType="text/html; charset=UTF-8" %><% request.setCharacterEncoding("UTF-8"); %>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Incluir Partido</title>
 

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/cadastro.css">
</head>

<%
    String numero = request.getParameter("numero");
    Banco banco = new Banco();
    Partido partido = new Partido();

    banco.conectar();
    banco.procuraPartido(Integer.parseInt(numero));
    banco.desconectar();

    partido = banco.getPartido();
%>
<body>
    <div class="cadcontent">
        <div class="tab">
            <h3>Alterar Partido</h3>
        </div>
        <div class="form">
            <form method="post" action="salvaraltpartido.jsp">
                <label for="numero">Número</label>
                <input type="number" name="numero" id="numero" max="99" min="10" step="1" autocomplete="off" required  value="<% out.print(partido.getNumero()); %>">
                
                <br>
                
                <small id="reminder" title="Número atual">
                    <i>
                        <% out.print(partido.getNumero()); %>
                    </i>
                </small>
                
                <input type="hidden" name="numprev" value="<% out.print(partido.getNumero()); %>">
        
                <br><br>
                
                <label for="nome">Nome do partido</label>
                <input type="text" name="nome" id="nome" autocomplete="off" required value="<% out.print(partido.getNome()); %>">
                
                <br>
                
                <small id="reminder" title="Nome atual">
                    <i>
                        <% out.print(partido.getNome()); %>
                    </i>
                </small>
        
                <br><br>

                <input type="submit" name="subAltPartido" value="Alterar">
            </form>
        </div>
    </div>
</body>
    
</html>