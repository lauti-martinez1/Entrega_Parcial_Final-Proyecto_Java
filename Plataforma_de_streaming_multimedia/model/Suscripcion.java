package Plataforma_de_streaming_multimedia.model;

public class Suscripcion {
    private int idSuscripcion;
    private String tipoPlan;
    private double precio;
    private int idUsuario;

    public Suscripcion(int idSuscripcion, String tipoPlan, double precio, int idUsuario) {
        this.idSuscripcion = idSuscripcion;
        this.tipoPlan = tipoPlan;
        this.precio = precio;
        this.idUsuario = idUsuario;
    }

    public String getTipoPlan() { return tipoPlan; }
    public double getPrecio() { return precio; }
}