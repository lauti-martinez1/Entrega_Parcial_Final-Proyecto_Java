package Plataforma_de_streaming_multimedia.gestorDB;

import Plataforma_de_streaming_multimedia.conexionDB.ConexionDB;
import Plataforma_de_streaming_multimedia.model.Contenido;
import Plataforma_de_streaming_multimedia.model.Suscripcion;
import Plataforma_de_streaming_multimedia.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorBaseDatos {

   
    public Usuario autenticarUsuario(String email, String password) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND contraseña = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("email"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null; // Si no existe o la contraseña está mal
    }

  
    public boolean registrarUsuario(String nombre, String email, String password) {
        String sqlUser = "INSERT INTO usuario (nombre, email, contraseña) VALUES (?, ?, ?)";
        String sqlSub = "INSERT INTO suscripcion (tipo_plan, precio, id_usuario) VALUES ('Basico', 3000.00, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement psUser = con.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {

            psUser.setString(1, nombre);
            psUser.setString(2, email);
            psUser.setString(3, password);
            psUser.executeUpdate();

           
            ResultSet rs = psUser.getGeneratedKeys();
            if (rs.next()) {
                int nuevoId = rs.getInt(1);
                // Le creamos su suscripción Básica
                try (PreparedStatement psSub = con.prepareStatement(sqlSub)) {
                    psSub.setInt(1, nuevoId);
                    psSub.executeUpdate();
                }
                return true;
            }
        } catch (Exception e) {
           
            System.out.println("Error al registrar: " + e.getMessage());
        }
        return false;
    }

    
    public boolean mejorarPlan(int idUsuario) {
        String sql = "UPDATE suscripcion SET tipo_plan = 'Premium', precio = 5000.00 WHERE id_usuario = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

   
    public Usuario obtenerUsuario(int id) {  return null; }

    public Suscripcion obtenerSuscripcion(int idUsuario) {
        String sql = "SELECT * FROM suscripcion WHERE id_usuario = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Suscripcion(rs.getInt("id_suscripcion"), rs.getString("tipo_plan"), rs.getDouble("precio"), rs.getInt("id_usuario"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Contenido> obtenerContenidosPorTipo(String tipo) {
        List<Contenido> lista = new ArrayList<>();
        String sql = "SELECT * FROM contenido WHERE IFNULL(tipo, 'Audio') = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Contenido(rs.getInt("id_contenido"), rs.getString("titulo"), rs.getString("genero")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public void registrarHistorial(int idUsuario, int idContenido) {
        String sql = "INSERT INTO historial (fecha_visualizacion, id_usuario, id_contenido) VALUES (NOW(), ?, ?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idContenido);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
