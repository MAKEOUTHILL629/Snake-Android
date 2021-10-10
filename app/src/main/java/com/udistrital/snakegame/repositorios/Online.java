package com.udistrital.snakegame.repositorios;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.udistrital.snakegame.modelos.SesionMultijugador;

import java.util.Observable;

public class Online extends Observable {

    public static SesionMultijugador oponente = new SesionMultijugador("-1oponente");
    private SesionMultijugador jugador = new SesionMultijugador("-1jugador");

    public void setJugador(SesionMultijugador jugador) {
        ConexionFirebase.guardar(jugador);
        this.jugador = jugador;
    }

    public Online() {

        ValueEventListener sesionMultijugadorListerner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot ultimoRegistro = null;
                for (DataSnapshot data : snapshot.getChildren()) {
                    ultimoRegistro = data;
                    // try {

                    SesionMultijugador multiplayer = new SesionMultijugador();
                    multiplayer.setNombreUser(ultimoRegistro.child("nombreUser").getValue().toString());
                    multiplayer.setEstado(ultimoRegistro.child("estado").getValue().toString());
                    multiplayer.setListo(Boolean.parseBoolean(ultimoRegistro.child("listo").getValue().toString()));
                    multiplayer.setOponente(ultimoRegistro.child("oponente").getValue().toString());
                    multiplayer.setPuntuacion(Integer.parseInt(String.valueOf((long) ultimoRegistro.child("puntuacion").getValue())));
                    System.out.println(multiplayer);
                    if (oponente != null && oponente.getNombreUser().equals(multiplayer.getNombreUser()) && !multiplayer.getOponente().equals("")) {

                        oponente.setOponente(multiplayer.getOponente());
                        oponente.setListo(multiplayer.isListo());
                        oponente.setPuntuacion(multiplayer.getPuntuacion());
                        oponente.setEstado(multiplayer.getEstado());
                        oponente.setNombreUser(multiplayer.getNombreUser());

                    }
                    procesarMultijugador(multiplayer);
                    //  } catch (Exception e) {
                    //   System.out.println(e);
                    //  }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Error", "loadPost:onCancelled", error.toException());
            }
        };
        ConexionFirebase.getDatabaseReference().child("multijugador").addValueEventListener(sesionMultijugadorListerner);
    }


    private void procesarMultijugador(SesionMultijugador multijugador) {

        if (
                (!this.jugador.getNombreUser().equals("-1jugador") && !this.jugador.getEstado().equals("RESET") && !multijugador.getEstado().equals("RESET")) &&
                        (this.jugador.getOponente() == null || this.jugador.getOponente().equals("")) &&
                        (multijugador.getOponente() == null || multijugador.getOponente().equals("") || multijugador.getOponente().equals(this.jugador.getNombreUser())) &&
                        !multijugador.getNombreUser().equals(this.jugador.getNombreUser())) {
            this.jugador.setOponente(multijugador.getNombreUser());
            ConexionFirebase.guardar(this.jugador);
            this.setChanged();
            this.notifyObservers(multijugador);
            oponente = multijugador;
            System.out.println("Se asigno oponente " + oponente);


        }
    }


}
