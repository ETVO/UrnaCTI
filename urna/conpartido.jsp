<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %> 
<%@page language="Java" import="java.util.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Partido"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <%@ page contentType="text/html; charset=UTF-8" %>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Trabalho 73A</title>

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/consulta.css">
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
    <div class="concontent">
        <div class="tab">
            <h3>Consultar Partidos</h3>
        </div>
        <div class="table">
            <table>
                <tr class="throw">
                    <th>
                        Número
                    </th>
                    <th>
                        Nome
                    </th>
                    <th id="h">
                        Ações
                    </th>
                </tr>
                <%
                    for(int i = 0; i < tudo.size(); i += freq)
                    {
                        numero = Integer.parseInt(tudo.get(i).toString());                        
                        nome = tudo.get(i+1).toString();
                        
                        String a_alterar = "<a href='altpartido.jsp?numero=" + numero + "'>Alterar</a>";
                        String a_excluir = "<a href='excpartido.jsp?numero=" + numero + "&opcao=Excluir' class='danger'>Excluir</a>";
                        
                        String partidos = "<tr>";
                        partidos += "<td>";
                        partidos += numero;
                        partidos += "</td>";
                        partidos += "<td>";
                        partidos += nome;
                        partidos += "</td>";
                        partidos += "<td class='d'>";
                        partidos += a_alterar;
                        partidos += "&nbsp;&nbsp;";
                        partidos += a_excluir;
                        partidos += "</td>";
                        partidos += "</tr>";
                        out.print(partidos);
                    }
                %>
            </table>
        </div>
        <div class="flex">
            <div class="mauto">
                <a id="imprimir" onclick="imprimir()">Imprimir</a>
                <input type="hidden" id="title" value="Partidos">
            <div>
        </div>
    </div>
</body>

<script src="js/main.js"></script>

</html>