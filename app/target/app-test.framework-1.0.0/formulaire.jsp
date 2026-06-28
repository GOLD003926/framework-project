<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire</title>
</head>
<body>
    <h1>Formulaire</h1>
    <form method="post" action="<%= request.getContextPath() %>/validerForm">
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom">
        <br>
        <label for="prenom">Prenom :</label>
        <input type="text" id="prenom" name="prenom">
        <br>
        <input type="submit" value="Soumettre">
    </form>
</body>
</html>
