<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %> 
<%@page language="Java" import="java.util.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Partido"%>
<%@page language="Java" import="database.Candidato"%>

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
    int n_partido = 0;
    String nome = "";
    String cargo = "";
    
    int freq = 4;
    banco.conectar();
    ArrayList tudo = banco.tudoCandidato();
    banco.desconectar();
%>
<body>
    <div class="concontent">
        <div class="tab">
            <h3>Consultar Candidatos</h3>
        </div>
        <div class="table">
            <table>
                <tr class="throw">
                    <th>
                        Número
                    </th>
                    <th>
                        No. Partido
                    </th>
                    <th>
                        Nome
                    </th>
                    <th>
                        Cargo
                    </th>
                    <th id="h">
                        Ações
                    </th>
                </tr>
                <%
                    for(int i = 0; i < tudo.size(); i += freq)
                    {
                        numero = Integer.parseInt(tudo.get(i).toString());  
                        n_partido = Integer.parseInt(tudo.get(i+1).toString());                         
                        nome = tudo.get(i+2).toString();                      
                        cargo = tudo.get(i+3).toString();
                        
                        String a_alterar = "<a href='altcandidato.jsp?numero=" + numero + "'>Alterar</a>";
                        String a_excluir = "<a href='exccandidato.jsp?numero=" + numero + "&opcao=Excluir' class='danger'>Excluir</a>";
                        
                        String candidato = "<tr>";
                        candidato += "<td>";
                        candidato += numero;
                        candidato += "</td>";
                        candidato += "<td>";
                        candidato += n_partido;
                        candidato += "</td>";
                        candidato += "<td>";
                        candidato += nome;
                        candidato += "</td>";
                        candidato += "<td>";
                        candidato += cargo;
                        candidato += "</td>";
                        candidato += "<td class='d'>";
                        candidato += a_alterar;
                        candidato += "&nbsp;&nbsp;";
                        candidato += a_excluir;
                        candidato += "</td>";
                        candidato += "</tr>";
                        out.print(candidato);
                    }
                %>
            </table>
        </div>
        <div class="flex">
            <div class="mauto">
                <a id="imprimir" onclick="imprimir()">Imprimir</a>
                <input type="hidden" id="title" value="Candidatos">
            <div>
        </div>
    </div>
</body>

<script src="js/main.js"></script>

</html>