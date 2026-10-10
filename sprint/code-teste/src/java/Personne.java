package models;

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private String classe;

    public Personne() {}

    public Personne(int id, String nom, String prenom, String classe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getClasse() {
        return classe;
    }
}