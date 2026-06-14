package Plataforma_de_streaming_multimedia.reproductor;

import Plataforma_de_streaming_multimedia.model.Contenido;

public class ReproductorVideo implements ReproductorMedia{

    @Override
    public void reproducir(Contenido contenido) {
        System.out.println( "Reproduciendo: " + contenido.getTitulo() );
    }

}
