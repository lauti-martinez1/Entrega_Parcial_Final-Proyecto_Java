package Plataforma_de_streaming_multimedia.ui;

import Plataforma_de_streaming_multimedia.gestorDB.GestorBaseDatos;
import Plataforma_de_streaming_multimedia.model.Usuario;
import Plataforma_de_streaming_multimedia.model.Suscripcion;
import Plataforma_de_streaming_multimedia.model.Contenido;
import Plataforma_de_streaming_multimedia.catalogo.CatalogoPeliculas;
import Plataforma_de_streaming_multimedia.catalogo.CatalogoAudios;
import Plataforma_de_streaming_multimedia.catalogo.Historial;
import Plataforma_de_streaming_multimedia.reproductor.*;

import java.util.Scanner;

public class MenuPrincipal {
    private GestorBaseDatos db;
    private Historial historial;
    private Scanner scanner;

    public MenuPrincipal() {
        this.db = new GestorBaseDatos();
        this.historial = new Historial();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("--- BIENVENIDO ---");
        System.out.print("Ingrese ID del usuario para iniciar sesión (ej. 1 o 2): ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = db.obtenerUsuario(id);

        if (usuario == null) {
            System.out.println("Usuario no encontrado en la base de datos. Saliendo...");
            return;
        }

        Suscripcion suscripcion = db.obtenerSuscripcion(id);
        String plan = (suscripcion != null) ? suscripcion.getTipoPlan() : "Sin plan activo";

        System.out.println("\n¡Hola, " + usuario.getNombre() + "!");
        System.out.println("Tu plan actual es: " + plan);

        int opcion;

        do {
            System.out.println("\n      MENÚ      ");
            System.out.println("1. Reproducir video");
            System.out.println("2. Reproducir audio");
            System.out.println("3. Ver historial de sesión");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    reproducirVideo(usuario);
                    break;
                case 2:
                    reproducirAudio(usuario);
                    break;
                case 3:
                    historial.mostrarHistorial();
                    break;
                case 4:
                    System.out.println("\nGracias por utilizar la plataforma.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    private void reproducirVideo(Usuario usuario) {
        CatalogoPeliculas catalogoPeliculas = new CatalogoPeliculas();
        catalogoPeliculas.mostrarCatalogo();
        System.out.print("\nSeleccione una película: ");
        int opcionPelicula = scanner.nextInt();

        try {
            Contenido pelicula = catalogoPeliculas.obtenerPelicula(opcionPelicula - 1);
            ReproductorMedia video = new ReproductorVideo();
            PlataformaStreaming plataformaVideo = new PlataformaStreaming(video);

            plataformaVideo.verContenido(pelicula);
            historial.agregarContenido(usuario, pelicula);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Opción inválida.");
        }
    }

    private void reproducirAudio(Usuario usuario) {
        CatalogoAudios catalogoAudios = new CatalogoAudios();
        catalogoAudios.mostrarCatalogo();
        System.out.print("\nSeleccione un audio: ");
        int opcionAudio = scanner.nextInt();

        try {
            Contenido audioSeleccionado = catalogoAudios.obtenerAudio(opcionAudio - 1);
            ReproductorMedia audio = new ReproductorAudio();
            PlataformaStreaming plataformaAudio = new PlataformaStreaming(audio);

            plataformaAudio.verContenido(audioSeleccionado);
            historial.agregarContenido(usuario, audioSeleccionado);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Opción inválida.");
        }
    }
}