package Plataforma_de_streaming_multimedia.reproductor;

import Plataforma_de_streaming_multimedia.model.Contenido;

public class PlataformaStreaming {

    private ReproductorMedia reproductor;

    public PlataformaStreaming(ReproductorMedia reproductor) {
        this.reproductor = reproductor;
    }

    public void verContenido(Contenido contenido) {
        reproductor.reproducir(contenido);
    }

}
