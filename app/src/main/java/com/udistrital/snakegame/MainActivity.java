package com.udistrital.snakegame;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.udistrital.snakegame.modelos.SesionMultijugador;
import com.udistrital.snakegame.repositorios.Online;
import com.udistrital.snakegame.utilidades.Constantes;

import java.util.Observable;
import java.util.Observer;

import static com.udistrital.snakegame.utilidades.Constantes.MEJOR_PUNTUACION;

public class MainActivity extends AppCompatActivity implements Observer {
    public static ImageView imgDeslizar;
    public static Dialog dialogPuntuacion;
    private VistaJuego vistaJuego;
    public static TextView textViewPuntuacion;
    public static TextView textViewMejorPuntuacion;
    public static TextView textViewDialogPuntuacion;
    public static TextView textViewDialogMejorPuntuacion;
    public static EditText textViewnombreJugador;
    public static TextView textOponenteName;
    public static TextView textViewMultiplayer;
    private Online online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//Hiden status bar
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constantes.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constantes.SCREEN_HEIGHT = displayMetrics.heightPixels;
        setContentView(R.layout.activity_main);
        imgDeslizar = findViewById(R.id.img_deslizar);
        vistaJuego = findViewById(R.id.vista_juego);
        textViewPuntuacion = findViewById(R.id.txt_puntuacion);
        textViewMejorPuntuacion = findViewById(R.id.txt_mejor_puntuacion);
        textOponenteName = findViewById(R.id.nameOponente);
        FirebaseApp.initializeApp(this);
        this.online = new Online();
        this.online.addObserver(this);
        dialogoPuntuacion();
        dialogPuntuacion.show();

    }

    private void dialogoPuntuacion() {
        int mejorPuntuacion = 0;
        SharedPreferences sharedPreferences = this.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            mejorPuntuacion = sharedPreferences.getInt(MEJOR_PUNTUACION, 0);
        }

        MainActivity.textViewMejorPuntuacion.setText(mejorPuntuacion + "");

        this.dialogPuntuacion = new Dialog(this);
        this.dialogPuntuacion.setContentView(R.layout.dialog_comenzar);
        textViewDialogPuntuacion = dialogPuntuacion.findViewById(R.id.txt_dialog_puntuacion);
        textViewDialogMejorPuntuacion = dialogPuntuacion.findViewById(R.id.txt_dialog_mejor_puntuacion);
        this.textViewMultiplayer = dialogPuntuacion.findViewById(R.id.textViewMultiplayer);
        textViewDialogMejorPuntuacion.setText(mejorPuntuacion + "");
        textViewnombreJugador = dialogPuntuacion.findViewById(R.id.editTextTextPersonName);
        dialogPuntuacion.setCanceledOnTouchOutside(false);
        RelativeLayout relativeLayoutComenzar = dialogPuntuacion.findViewById(R.id.relativelayout_comenzar);
        relativeLayoutComenzar.setOnClickListener((view) -> {
            if (!textViewnombreJugador.getText().toString().equals("")) {
                textViewnombreJugador.setBackgroundColor(Color.BLACK);
                imgDeslizar.setVisibility(View.VISIBLE);
                vistaJuego.reset();
                vistaJuego.iniciarGameMultiplayer(textViewnombreJugador.getText().toString());



                online.setJugador(vistaJuego.getSesionMultijugador());

                relativeLayoutComenzar.setVisibility(View.INVISIBLE);


                if (Online.oponente == null || vistaJuego.getSesionMultijugador().getOponente().equals("")) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Espera a tu oponente", Toast.LENGTH_LONG);


                    toast1.show();
                } else {

                }


            } else {
                textViewnombreJugador.setBackgroundColor(Color.RED);
            }
        });


    }


    @SuppressLint("SetTextI18n")
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Llego el oponente");
        SesionMultijugador oponente = (SesionMultijugador) arg;

        dialogPuntuacion.dismiss();
        RelativeLayout relativeLayoutComenzar = dialogPuntuacion.findViewById(R.id.relativelayout_comenzar);
        relativeLayoutComenzar.setVisibility(View.VISIBLE);
        vistaJuego.setPuntuacionOponente(Online.oponente.getPuntuacion());
        textOponenteName.setText("Tu oponente es " + oponente.getNombreUser());

    }
}