
<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Candidato"%>
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
        String n_partido = request.getParameter("n_partido");
        String nome = request.getParameter("nome");
        String cargo = request.getParameter("cargo");
        
        Banco banco = new Banco();
        Candidato candidato = new Candidato();

        candidato.setNumero(Integer.parseInt(numero));
        candidato.setNumPrev(Integer.parseInt(numprev));
        candidato.setN_Partido(Integer.parseInt(n_partido));
        candidato.setNome(nome);
        candidato.setCargoString(cargo);

        String classe = "ok";
        String h2 = "Parabéns!";
        String h3 = "Candidato <b>" + candidato.getNome() + "</b> alterado com sucesso!";

        banco.setCandidato(candidato);

        if(candidato.getNumero() != candidato.getNumPrev())
        {
            banco.conectar();
            boolean existe = banco.procuraCandidato(candidato.getNumero());
            banco.desconectar();
            if(existe == true)
            {
                classe = "failed";
                h2 = "Falha!";
                h3 = "O número <b>" + candidato.getNumero() + "</b> já está cadastrado pelo candidato <b>" + banco.getCandidato().getNome() + "</b>!";
            }   
            else
            {
                banco.conectar();
                banco.alterarCandidato();
                banco.desconectar();  
            } 
        }
        else
        {
            banco.conectar();
            banco.alterarCandidato();
            banco.desconectar();    
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