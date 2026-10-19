package models;

public class Genre {
    private int id;
    private String libelle;

    public Genre() {}
    public Genre(String libelle) { this.libelle = libelle; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
