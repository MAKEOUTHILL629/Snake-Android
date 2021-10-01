package com.udistrital.snakegame;

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
import com.udistrital.snakegame.utilidades.Constantes;

import static com.udistrital.snakegame.utilidades.Constantes.MEJOR_PUNTUACION;

public class MainActivity extends AppCompatActivity {
    public static ImageView imgDeslizar;
    public static Dialog dialogPuntuacion;
    private VistaJuego vistaJuego;
    public static TextView textViewPuntuacion;
    public static TextView textViewMejorPuntuacion;
    public static TextView textViewDialogPuntuacion;
    public static TextView textViewDialogMejorPuntuacion;
    public static EditText textViewnombreJugador;

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
        dialogoPuntuacion();
        dialogPuntuacion.show();
    }

    private void dialogoPuntuacion(){
        int mejorPuntuacion =0;
        SharedPreferences sharedPreferences = this.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            mejorPuntuacion = sharedPreferences.getInt(MEJOR_PUNTUACION,0);
        }

        MainActivity.textViewMejorPuntuacion.setText(mejorPuntuacion + "");

        this.dialogPuntuacion = new Dialog(this);
        this.dialogPuntuacion.setContentView(R.layout.dialog_comenzar);
        textViewDialogPuntuacion = dialogPuntuacion.findViewById(R.id.txt_dialog_puntuacion);
        textViewDialogMejorPuntuacion = dialogPuntuacion.findViewById(R.id.txt_dialog_mejor_puntuacion);
        textViewDialogMejorPuntuacion.setText(mejorPuntuacion + "");
        textViewnombreJugador = dialogPuntuacion.findViewById(R.id.editTextTextPersonName);
        dialogPuntuacion.setCanceledOnTouchOutside(false);
        RelativeLayout relativeLayoutComenzar = dialogPuntuacion.findViewById(R.id.relativelayout_comenzar);
        relativeLayoutComenzar.setOnClickListener( (view) -> {
            if(!textViewnombreJugador.getText().toString().equals("")){
                textViewnombreJugador.setBackgroundColor(Color.BLACK);
                imgDeslizar.setVisibility(View.VISIBLE);
                vistaJuego.reset();
                vistaJuego.iniciarGameMultiplayer(textViewnombreJugador.getText().toString());
                dialogPuntuacion.dismiss();
            }else{
                textViewnombreJugador.setBackgroundColor(Color.RED);
            }
        });
            

    }
}