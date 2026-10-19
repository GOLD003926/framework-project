package models;

public class Artiste {
    private int id;
    private String nom;
    private String age;
    private Genre genre;

    public Artiste() {}

    public Artiste(int id, String nom, String age, Genre genre) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.genre = genre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }
}
