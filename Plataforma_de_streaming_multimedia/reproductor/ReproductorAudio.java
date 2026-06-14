package Plataforma_de_streaming_multimedia.reproductor;

import Plataforma_de_streaming_multimedia.model.Contenido;

public class ReproductorAudio implements ReproductorMedia{

    @Override
    public void reproducir(Contenido contenido) {
        System.out.println("Reproduciendo audio: " + contenido.getTitulo());
    }

}
