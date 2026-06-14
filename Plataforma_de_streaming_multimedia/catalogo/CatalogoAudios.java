package Plataforma_de_streaming_multimedia.catalogo;
import Plataforma_de_streaming_multimedia.gestorDB.GestorBaseDatos;
import Plataforma_de_streaming_multimedia.model.Contenido;

import java.util.List;

public class CatalogoAudios {
    private List<Contenido> audios;

    public CatalogoAudios() {
        GestorBaseDatos db = new GestorBaseDatos();
        audios = db.obtenerContenidosPorTipo("Audio");
    }

    public void mostrarCatalogo() {
        System.out.println("\n    CATALOGO DE AUDIOS   ");
        for (int i = 0; i < audios.size(); i++) {
            System.out.println((i + 1) + ". " + audios.get(i).getTitulo() + " (" + audios.get(i).getGenero() + ")");
        }
    }
    public Contenido obtenerAudio(int indice) { return audios.get(indice); }


    public List<Contenido> getAudios() {
        return audios;
    }
}