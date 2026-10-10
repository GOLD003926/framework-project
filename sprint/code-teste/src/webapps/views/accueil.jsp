<%@ page import="models.Personne" %>

<%
    Personne p = (Personne) request.getAttribute("personne");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil</title>
</head>
<body>
    <table border="1">
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Classe</th>
        </tr>
        <tr>
            <td><%= p.getNom() %></td>
            <td><%= p.getPrenom() %></td>
            <td><%= p.getClasse() %></td>
        </tr>
    </table>
</body>
</html>