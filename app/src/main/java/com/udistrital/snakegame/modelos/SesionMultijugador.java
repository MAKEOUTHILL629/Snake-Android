package com.udistrital.snakegame.modelos;


public class SesionMultijugador {
    private int puntuacion;
    private String estado;
    private String nombreUser;
    private boolean listo;
    private String oponente;


    public SesionMultijugador(String nombreUser) {
        this.nombreUser = nombreUser;
        this.puntuacion = 0;
        this.estado = "JUGANDO";
        this.listo = false;
        this.oponente = "";
    }

    public SesionMultijugador() {
    }


    public SesionMultijugador(int puntuacion, String estado, String nombreUser, boolean listo, String oponente) {
        this.puntuacion = puntuacion;
        this.estado = estado;
        this.nombreUser = nombreUser;
        this.listo = listo;
        this.oponente = oponente;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public boolean isListo() {
        return listo;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }

    public void estaListo() {
        this.listo = true;
    }

    public void noEstaListo() {
        this.listo = false;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreUser() {
        return nombreUser;
    }


    public String getOponente() {
        return oponente;
    }

    public void setOponente(String oponente) {
        this.oponente = oponente;
    }

    @Override
    public String toString() {
        return "SesionMultijugador{" +
                "puntuacion=" + puntuacion +
                ", estado='" + estado + '\'' +
                ", nombreUser='" + nombreUser + '\'' +
                ", listo=" + listo +
                ", oponente='" + oponente + '\'' +
                '}';
    }
}


