package Plataforma_de_streaming_multimedia.model;

public class Contenido {
    private int id;
    private String titulo;
    private String genero;

    public Contenido(int id, String titulo, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
}
