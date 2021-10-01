package com.udistrital.snakegame.modelos;

public class SesionMultijugador {
    private Integer puntuacion;
    private Integer velocidad;
    private String nombreUser;
    private Boolean listo;

    public SesionMultijugador(String nombreUser) {
        this.nombreUser = nombreUser;
        this.puntuacion = 0;
        this.velocidad = 300;
        this.listo = false;
    }

    public void setVelocidad(Integer velocidad) {
        this.velocidad = velocidad;
    }

    public void estaListo(){
        this.listo = true;
    }

    public void noEstaListo(){
        this.listo = false;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public Integer getVelocidad() {
        return velocidad;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public Boolean getListo() {
        return listo;
    }
}
