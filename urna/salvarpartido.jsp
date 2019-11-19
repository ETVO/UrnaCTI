
<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Partido"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@ page contentType="text/html; charset=UTF-8" %>
            
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Incluir Partido</title>
        
    
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/cadastro.css">
    </head>
    <body>
    <%
        String numero = request.getParameter("numero");
        String nome = request.getParameter("nome");
        
        Banco banco = new Banco();
        Partido partido = new Partido();

        String classe = "ok";
        String h2 = "Parabéns!";
        String h3 = "Partido " + partido.getNome() + " inserido com sucesso!";

        partido.setNumero(Integer.parseInt(numero));
        partido.setNome(nome);

        banco.setPartido(partido);

        banco.conectar();
        boolean existe = banco.procuraPartido(Integer.parseInt(numero));
        banco.desconectar();
        if(existe == true)
        {
            classe = "failed";
            h2 = "Falha!";
            h3 = "O número <b>" + partido.getNumero() + "</b> já está sendo utilizado pelo <b>" + partido.getNome() + "</b>!";
        }
        else
        {
            banco.conectar();
            banco.inserirPartido();
            banco.desconectar();

            classe = "ok";
            h2 = "Parabéns!";
            h3 = "Partido <b>" + partido.getNome() + "</b> inserido com sucesso!";
        }
    %>

        <div class="<%out.print(classe);%>">
            <h2><%out.print(h2);%></h2>
            <h3><%out.print(h3);%></h3>
            <a href='cadpartido.html'>Voltar à inclusão de partidos</a>
        </div>
    </body>
</html>