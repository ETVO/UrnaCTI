<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="java.util.*" %>
<%@page language="Java" import="database.Banco"%>
<%@page language="Java" import="database.Candidato"%>
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
    String num = request.getParameter("numero");
    Banco banco = new Banco();
    Candidato candidato = new Candidato();

    banco.conectar();
    banco.procuraCandidato(Integer.parseInt(num));
    banco.desconectar();

    candidato = banco.getCandidato();

    int numero;
    String nome;
    
    int freq = 2;
    banco.conectar();
    ArrayList tudo = banco.tudoPartido();
    banco.desconectar();
%>
<body>
    <div class="cadcontent">
        <div class="tab">
            <h3>Alterar Candidato</h3>
        </div>
        <div class="form">
            <form method="post" action="salvaraltcandidato.jsp">
                <label for="numero">Número</label>
                <input type="number" name="numero" id="numero" max="99" min="10" step="1" autocomplete="off" required  value="<% out.print(candidato.getNumero()); %>">
                
                <br>
                
                <small id="reminder" title="Número atual">
                    <i>
                        <% out.print(candidato.getNumero()); %>
                    </i>
                </small>
                
                <input type="hidden" name="numprev" value="<% out.print(candidato.getNumero()); %>">
        
                <br><br>
                
                <label for="n_partido">Partido</label>
                <select name="n_partido" id="n_partido" required>
                    <option value='' disabled>-- Selecione um partido --</option>
                    <%
                    for(int i = 0; i < tudo.size(); i += freq)
                    {
                        numero = Integer.parseInt(tudo.get(i).toString());                        
                        nome = tudo.get(i+1).toString();

                        String req = (numero == candidato.getN_Partido()) ? "selected" : "";

                        String partido = "<option value='" + numero + "'" + req + ">" + numero + " - " + nome + "</option>";
                        out.print(partido);
                    }
                    %>
                </select>
                
                <br>
                
                <small id="reminder" title="Partido atual">
                    <i>
                        <% 
                            banco.conectar();
                            banco.procuraPartido(candidato.getN_Partido());
                            banco.desconectar();
                            
                            out.print(banco.getPartido().getNumero() + " - " + banco.getPartido().getNome()); 
                        %>
                    </i>
                </small>
        
                <br><br>
                
                <label for="nome">Nome do candidato</label>
                <input type="text" name="nome" id="nome" autocomplete="off" required value="<% out.print(candidato.getNome()); %>">
                
                <br>
                
                <small id="reminder" title="Nome atual">
                    <i>
                        <% out.print(candidato.getNome()); %>
                    </i>
                </small>
        
                <br><br>
                
                <label for="cargo">Cargo</label>
                <select name="cargo" id="cargo" required>
                    <option value='' disabled>-- Selecione um cargo --</option>
                    <option value='Deputado Federal' <% if(candidato.getCargoString().equals("Deputado Federal")) out.print("selected"); %>>Deputado Federal</option>
                    <option value='Senador' <% if(candidato.getCargoString().equals("Senador")) out.print("selected"); %>>Senador</option>
                    <option value='Presidente' <% if(candidato.getCargoString().equals("Presidente")) out.print("selected"); %>>Presidente</option>
                    <option value='Deputado Estadual' <% if(candidato.getCargoString().equals("Deputado Estadual")) out.print("selected"); %>>Deputado Estadual</option>
                    <option value='Governador' <% if(candidato.getCargoString().equals("Governador")) out.print("selected"); %>>Governador</option>
                </select>
                
                <br>
                
                <small id="reminder" title="Cargo atual">
                    <i>
                        <% out.print(candidato.getCargoString()); %>
                    </i>
                </small>
        
                <br><br>

                <input type="submit" name="subAltPartido" value="Alterar">
            </form>
        </div>
    </div>
</body>
    
</html>