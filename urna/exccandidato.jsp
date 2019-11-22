
<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Candidato"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@ page contentType="text/html; charset=UTF-8" %>
            
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Alterar ou Excluir Candidato</title>
        
    
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/cadastro.css">
    </head>
    <body>
    <%
        String numero = request.getParameter("numero");
        String opcao = request.getParameter("opcao");
        
        Banco banco = new Banco();
        Candidato candidato = new Candidato();

        boolean excluir = opcao.equals("Excluir");

        String classe = "ok";
        String h2 = "Parabéns!";
        String h3 = "Candidato " + candidato.getNome() + " excluído com sucesso!";

        banco.conectar();
        boolean existe = banco.procuraCandidato(Integer.parseInt(numero));
        banco.desconectar();
        if(existe == true)
        {
            if(excluir)
            {
                banco.conectar();
                banco.excluirCandidato();
                banco.desconectar();
            }
            else
            {
                out.print("<script>window.location.href='altcandidato.jsp?numero=" + numero + "';</script>");
            }
        }
        else
        {
            classe = "failed";
            h2 = "Falha!";
            h3 = "Esse candidato não existe!";
        }
    %>

        <div class="<%out.print(classe);%>">
            <h2><%out.print(h2);%></h2>
            <h3><%out.print(h3);%></h3>
            <a href='optcandidato.jsp'>Voltar a Alterar/Excluir</a><br>
            <a href='concandidato.jsp'>Voltar a Consultar</a>
        </div>
    </body>
</html>