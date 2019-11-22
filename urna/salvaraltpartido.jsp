
<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Partido"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@ page contentType="text/html; charset=UTF-8" %><% request.setCharacterEncoding("UTF-8"); %>
            
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Incluir Partido</title>
        
    
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/cadastro.css">
    </head>
    <body>
    <%
        String numero = request.getParameter("numero");
        String numprev = request.getParameter("numprev");
        String nome = request.getParameter("nome");
        
        Banco banco = new Banco();
        Partido partido = new Partido();

        partido.setNumero(Integer.parseInt(numero));
        partido.setNumPrev(Integer.parseInt(numprev));
        partido.setNome(nome);

        String classe = "ok";
        String h2 = "Parabéns!";
        String h3 = "Partido <b>" + partido.getNome() + "</b> alterado com sucesso!";

        banco.setPartido(partido);

        if(partido.getNumero() != partido.getNumPrev())
        {
            banco.conectar();
            boolean existe = banco.procuraPartido(partido.getNumero());
            banco.desconectar();
            if(existe == true)
            {
                classe = "failed";
                h2 = "Falha!";
                h3 = "O número <b>" + partido.getNumero() + "</b> já está cadastrado pelo partido <b>" + banco.getPartido().getNome() + "</b>!";
            }   
            else
            {
                banco.conectar();
                banco.alterarPartido();
                banco.desconectar();  
            } 
        }
        else
        {
            banco.conectar();
            banco.alterarPartido();
            banco.desconectar();    
        }
    %>

        <div class="<%out.print(classe);%>">
            <h2><%out.print(h2);%></h2>
            <h3><%out.print(h3);%></h3>
            <a href='optpartido.jsp'>Voltar a Alterar/Excluir</a><br>
            <a href='conpartido.jsp'>Voltar a Consultar</a>
        </div>
    </body>
</html>