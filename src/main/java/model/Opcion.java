package model;

public class Opcion {
    private int votos;
    private double porcentaje;
    private String nombre;

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Opcion(int votos, double porcentaje, String nombre) {
        this.votos = votos;
        this.porcentaje = porcentaje;
        this.nombre = nombre;
    }
}
