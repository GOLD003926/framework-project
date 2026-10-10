<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire personne</title>
</head>
<body>
    <form action="submit-personne-object" method="post">
        <p>
            ID: <input type="number" name="id" min="1">
        </p>
        <p>
            Nom: <input type="text" name="nom">
        </p>
        <p>
            Prenom: <input type="text" name="prenom">
        </p>
        <p>
            Classe: <input type="text" name="classe">
        </p>
        <p><button type="submit">Valider</button></p>
    </form>
</body>
</html>