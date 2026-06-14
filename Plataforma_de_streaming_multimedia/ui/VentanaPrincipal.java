package Plataforma_de_streaming_multimedia.ui;

import Plataforma_de_streaming_multimedia.gestorDB.GestorBaseDatos;
import Plataforma_de_streaming_multimedia.model.Contenido;
import Plataforma_de_streaming_multimedia.model.Suscripcion;
import Plataforma_de_streaming_multimedia.model.Usuario;
import Plataforma_de_streaming_multimedia.catalogo.CatalogoPeliculas;
import Plataforma_de_streaming_multimedia.catalogo.CatalogoAudios;
import Plataforma_de_streaming_multimedia.catalogo.Historial;
import Plataforma_de_streaming_multimedia.reproductor.PlataformaStreaming;
import Plataforma_de_streaming_multimedia.reproductor.ReproductorAudio;
import Plataforma_de_streaming_multimedia.reproductor.ReproductorVideo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private GestorBaseDatos db;
    private Historial historial;
    private Usuario usuarioLogueado;
    private Suscripcion suscripcionLogueada;

    private JPanel panelContenedor;
    private CardLayout cardLayout;

    private JLabel lblBienvenida;
    private JLabel lblPlan;
    private JButton btnMejorarPlan;

    public VentanaPrincipal() {
        db = new GestorBaseDatos();
        historial = new Historial();

        setTitle("Plataforma de Streaming");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Agregamos las tres pantallas al contenedor principal
        panelContenedor.add(crearPanelLogin(), "Login");
        panelContenedor.add(crearPanelRegistro(), "Registro");
        panelContenedor.add(crearPanelMenu(), "Menu");

        add(panelContenedor);
    }

    private JPanel crearPanelLogin() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // 1. TÍTULO
        JLabel titulo = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // 2. FORMULARIO
        JPanel panelFormulario = new JPanel(new GridLayout(4, 1, 5, 5));
        JTextField txtEmail = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Contraseña:"));
        panelFormulario.add(txtPassword);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // 3. BOTONES
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton btnIngresar = new JButton("Ingresar");
        JButton btnIrARegistro = new JButton("¿No tienes cuenta? Regístrate");

        btnIngresar.addActionListener(e -> {
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());

            usuarioLogueado = db.autenticarUsuario(email, password);

            if (usuarioLogueado != null) {
                configurarSesionMenu();
                txtEmail.setText("");
                txtPassword.setText("");
                cardLayout.show(panelContenedor, "Menu");
            } else {
                JOptionPane.showMessageDialog(this, "Email o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnIrARegistro.addActionListener(e -> cardLayout.show(panelContenedor, "Registro"));

        panelBotones.add(btnIngresar);
        panelBotones.add(btnIrARegistro);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    private JPanel crearPanelRegistro() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // 1. TÍTULO
        JLabel titulo = new JLabel("REGISTRO DE USUARIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // 2. FORMULARIO
        JPanel panelFormulario = new JPanel(new GridLayout(6, 1, 2, 2));
        JTextField txtNombre = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        panelFormulario.add(new JLabel("Nombre Completo:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Contraseña:"));
        panelFormulario.add(txtPassword);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // 3. BOTONES
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnVolver = new JButton("Volver al Login");

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String pass = new String(txtPassword.getPassword());

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (db.registrarUsuario(nombre, email, pass)) {
                JOptionPane.showMessageDialog(this, "Registro exitoso. ¡Ahora puedes iniciar sesión!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtNombre.setText("");
                txtEmail.setText("");
                txtPassword.setText("");
                cardLayout.show(panelContenedor, "Login");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar. Es posible que el email ya exista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "Login"));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        lblBienvenida = new JLabel("¡Hola!", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 14));
        lblPlan = new JLabel("Plan actual: ", SwingConstants.CENTER);

        JButton btnVideo = new JButton("1. Reproducir Video");
        JButton btnAudio = new JButton("2. Reproducir Audio");
        JButton btnHistorial = new JButton("3. Ver Historial");

        btnMejorarPlan = new JButton("⭐ Mejorar Plan a Premium");
        btnMejorarPlan.setBackground(new Color(255, 215, 0));

        JButton btnSalir = new JButton("Cerrar Sesión");

        btnVideo.addActionListener(e -> reproducirContenido("Video"));
        btnAudio.addActionListener(e -> reproducirContenido("Audio"));
        btnHistorial.addActionListener(e -> mostrarHistorial());

        btnMejorarPlan.addActionListener(e -> mejorarPlanPremium());

        btnSalir.addActionListener(e -> {
            usuarioLogueado = null;
            suscripcionLogueada = null;
            cardLayout.show(panelContenedor, "Login");
        });

        panel.add(lblBienvenida);
        panel.add(lblPlan);
        panel.add(btnMejorarPlan);
        panel.add(btnVideo);
        panel.add(btnAudio);
        panel.add(btnHistorial);
        panel.add(btnSalir);

        return panel;
    }

    private void configurarSesionMenu() {
        suscripcionLogueada = db.obtenerSuscripcion(usuarioLogueado.getId());
        String nombrePlan = (suscripcionLogueada != null) ? suscripcionLogueada.getTipoPlan() : "Sin plan";

        lblBienvenida.setText("¡Hola, " + usuarioLogueado.getNombre() + "!");
        lblPlan.setText("Plan actual: " + nombrePlan);

        historial = new Historial(); // Reiniciamos historial para esta sesión

        if (nombrePlan.equalsIgnoreCase("Premium")) {
            btnMejorarPlan.setEnabled(false);
            btnMejorarPlan.setText("Ya eres Premium");
        } else {
            btnMejorarPlan.setEnabled(true);
            btnMejorarPlan.setText("⭐ Mejorar Plan a Premium");
        }
    }

    private void mejorarPlanPremium() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "El plan Premium cuesta $5000.00 y te da acceso a todas las películas.\n¿Deseas mejorar tu cuenta ahora?",
                "Mejorar a Premium",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (db.mejorarPlan(usuarioLogueado.getId())) {
                JOptionPane.showMessageDialog(this, "¡Felicidades! Ahora eres Premium.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                configurarSesionMenu();
            } else {
                JOptionPane.showMessageDialog(this, "Ocurrió un error al procesar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void reproducirContenido(String tipo) {
        if (tipo.equals("Video")) {
            if (suscripcionLogueada == null || suscripcionLogueada.getTipoPlan().equalsIgnoreCase("Basico")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tu plan actual (Básico) no permite la reproducción de videos.\nActualiza a Premium para acceder a este contenido.",
                        "Acceso Restringido",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        List<Contenido> lista;
        if (tipo.equals("Video")) {
            lista = new CatalogoPeliculas().getPeliculas();
        } else {
            lista = new CatalogoAudios().getAudios();
        }

        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay " + tipo.toLowerCase() + "s disponibles.");
            return;
        }

        String[] opciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            opciones[i] = lista.get(i).getTitulo() + " (" + lista.get(i).getGenero() + ")";
        }

        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione un " + tipo.toLowerCase() + ":", "Catálogo", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            for (Contenido c : lista) {
                if (seleccion.startsWith(c.getTitulo())) {
                    if (tipo.equals("Video")) {
                        new PlataformaStreaming(new ReproductorVideo()).verContenido(c);
                    } else {
                        new PlataformaStreaming(new ReproductorAudio()).verContenido(c);
                    }
                    historial.agregarContenido(usuarioLogueado, c);
                    JOptionPane.showMessageDialog(this, "Reproduciendo: " + c.getTitulo(), "Reproductor", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }

    private void mostrarHistorial() {
        List<Contenido> vistos = historial.getContenidosVistos();
        if (vistos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay contenidos visualizados en esta sesión.", "Historial", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("--- HISTORIAL DE LA SESIÓN ---\n\n");
        for (int i = 0; i < vistos.size(); i++) {
            sb.append((i + 1)).append(". ").append(vistos.get(i).getTitulo()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Historial", JOptionPane.INFORMATION_MESSAGE);
    }
}