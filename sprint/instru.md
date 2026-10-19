# Binder un objet `Sexe` directement depuis le formulaire

Il faut deux choses : que le formulaire envoie `sexe.libelle` au lieu de `sexeObj`, et que `bindObject` sache remplir un objet imbriqué récursivement.

## 1. Formulaire

```html
<p>
    Sexe: <input type="text" name="sexe.libelle">
</p>
```

Le préfixe `sexe.` indique au binder qu'il s'agit du champ `libelle` de l'objet `sexe`. Ça reste un input texte, la vérification se fait toujours côté base.

## 2. `FrontControllerServlet`

**a) Appel dans `processRequest`** : ajoute le préfixe vide.

```java
arguments[i] = bindObject(parameterType, req, "");
```

**b) Remplace `bindObject` par cette version récursive**, et ajoute les deux méthodes utilitaires :

```java
private Object bindObject(Class<?> objectClass, HttpServletRequest req, String prefix)
throws Exception
{
    Object object = objectClass.getDeclaredConstructor().newInstance();

    for (Method method : objectClass.getMethods()) {
        String methodName = method.getName();

        if (!methodName.startsWith("set") || methodName.length() <= 3) continue;
        if (method.getParameterCount() != 1) continue;

        String fieldName = Character.toLowerCase(methodName.charAt(3))
                + methodName.substring(4);
        String paramName = prefix + fieldName;
        Class<?> type = method.getParameterTypes()[0];

        if (isSimpleType(type)) {
            String value = req.getParameter(paramName);
            if (value == null || value.isEmpty()) continue;
            method.invoke(object, convertValue(value, type));
        } else if (hasNestedParam(req, paramName + ".")) {
            // objet imbriqué : on rappelle bindObject avec le préfixe "sexe."
            method.invoke(object, bindObject(type, req, paramName + "."));
        }
    }
    return object;
}

private boolean isSimpleType(Class<?> type) {
    return type.isPrimitive()
            || type == String.class
            || Number.class.isAssignableFrom(type)
            || type == Boolean.class
            || type == Character.class;
}

private boolean hasNestedParam(HttpServletRequest req, String prefix) {
    java.util.Enumeration<String> names = req.getParameterNames();
    while (names.hasMoreElements()) {
        if (names.nextElement().startsWith(prefix)) return true;
    }
    return false;
}
```

Le `value.isEmpty()` évite aussi un `NumberFormatException` quand un champ numérique est laissé vide.

## 3. `Sexe.java`

Le binder a besoin d'un constructeur sans argument et d'un setter par champ :

```java
package models;

public class Sexe {
    private int id;
    private String libelle;

    public Sexe() {}
    public Sexe(String libelle) { this.libelle = libelle; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
```

Adapte les noms si ton `Sexe` actuel a d'autres champs : le nom après `sexe.` dans le formulaire doit correspondre au setter (`setLibelle` → `sexe.libelle`).

## 4. `Personne.java`

Supprime `sexeObj`, `setSexe()`, `setSexe(String)`, `getSexeString()` et ne garde qu'un getter/setter classique :

```java
package models;

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private String classe;
    private Sexe sexe;

    public Personne() {}

    public Personne(int id, String nom, String prenom, String classe, Sexe sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.sexe = sexe;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }

    public Sexe getSexe() { return sexe; }
    public void setSexe(Sexe sexe) { this.sexe = sexe; }
}
```

Dans `Test2Controller`, mets à jour les appels :

```java
new Personne(1, "Rakoto", "Jean", "S4", new Sexe("Masculin"))
```

(il y en a deux : `/test` et `/api/v1`, et n'oublie pas `import models.Sexe;`).

## 5. `PersonneDAO.save`

Remplace `p.getSexeString()` par le libellé de l'objet :

```java
String libelle = (p.getSexe() == null) ? null : p.getSexe().getLibelle();
int idSexe = findSexeId(con, libelle);
if (idSexe == -1) {
    throw new IllegalArgumentException(
        "Sexe invalide : '" + libelle + "' (absent de la table sexe)");
}
```

## Résultat

Avec `sexe.libelle=Masculin` dans le formulaire, le binder crée `Personne`, détecte que `setSexe(Sexe)` attend un type complexe, trouve les paramètres préfixés par `sexe.`, construit le `Sexe` et l'injecte. Le DAO enregistre ensuite l'`id` du sexe dans la colonne `id_sexe`. Le JSON retourné contiendra :

```json
{"id":1,"nom":"Rakoto","prenom":"Jean","classe":"S4","sexe":{"id":0,"libelle":"Masculin"}}
```

Si tu veux que l'`id` du sexe soit aussi rempli dans la réponse, fais-le dans le DAO après `findSexeId` avec `p.getSexe().setId(idSexe);`.
