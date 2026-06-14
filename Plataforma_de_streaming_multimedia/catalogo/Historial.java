package Plataforma_de_streaming_multimedia.catalogo;

import Plataforma_de_streaming_multimedia.gestorDB.GestorBaseDatos;
import Plataforma_de_streaming_multimedia.model.Contenido;
import Plataforma_de_streaming_multimedia.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Historial {
    private List<Contenido> contenidosVistos;
    private GestorBaseDatos db;

    public Historial() {
        contenidosVistos = new ArrayList<>();
        db = new GestorBaseDatos();
    }

    public void agregarContenido(Usuario usuario, Contenido contenido) {
        contenidosVistos.add(contenido);
        db.registrarHistorial(usuario.getId(), contenido.getId()); 
    }

    public void mostrarHistorial() {
        System.out.println("\n    HISTORIAL DE LA SESIÓN    ");
        if (contenidosVistos.isEmpty()) {
            System.out.println("No hay contenidos visualizados.");
            return;
        }
        for (int i = 0; i < contenidosVistos.size(); i++) {
            System.out.println((i + 1) + ". " + contenidosVistos.get(i).getTitulo());
        }
    }


    public List<Contenido> getContenidosVistos() {
        return contenidosVistos;
    }
}
