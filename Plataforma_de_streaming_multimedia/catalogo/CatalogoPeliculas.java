package Plataforma_de_streaming_multimedia.catalogo;
import Plataforma_de_streaming_multimedia.gestorDB.GestorBaseDatos;
import Plataforma_de_streaming_multimedia.model.Contenido;

import java.util.List;

public class CatalogoPeliculas {
    private List<Contenido> peliculas;

    public CatalogoPeliculas() {
        GestorBaseDatos db = new GestorBaseDatos();
        peliculas = db.obtenerContenidosPorTipo("Video");
    }

    public void mostrarCatalogo() {
        System.out.println("\n    CATALOGO DE PELICULAS    ");
        for (int i = 0; i < peliculas.size(); i++) {
            System.out.println((i + 1) + ". " + peliculas.get(i).getTitulo() + " (" + peliculas.get(i).getGenero() + ")");
        }
    }
    public Contenido obtenerPelicula(int indice) { return peliculas.get(indice); }

    public List<Contenido> getPeliculas() {
        return peliculas;
    }
}