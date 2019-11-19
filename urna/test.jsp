
<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Partido"%>

<html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="ie=edge">
            <title>Incluir Partido</title>
            
        
            <link rel="stylesheet" href="css/main.css">
            <link rel="stylesheet" href="css/cadastro.css">
        </head>
<body>
    <%
        String numero = "22";
        String nome = "nome123";
        
        Banco banco = new Banco();
        Partido partido = new Partido();

        partido.setNumero(Integer.parseInt(numero));
        partido.setNome(nome);

        banco.setPartido(partido);
        
        banco.conectar();
        banco.inserirPartido();
        banco.desconectar();
    %>

    <div class="ok">Partido <%out.print(partido.getNome());%> inserido com sucesso!</div>

</body>
</html>