package com.udistrital.snakegame.repositorios;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udistrital.snakegame.modelos.SesionMultijugador;

public class ConexionFirebase {
    private final static ConexionFirebase INSTANCE = new ConexionFirebase();
    private static DatabaseReference databaseReference;

    public static DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public static ConexionFirebase getInstance(){
        return INSTANCE;
    }

    public ConexionFirebase(){

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static void guardar(SesionMultijugador sesion){
        databaseReference.child("multijugador").child(sesion.getNombreUser()).setValue(sesion);
    }
}
