package com.udistrital.snakegame;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//Hiden status bar
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constantes.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constantes.SCREEN_HEIGHT = displayMetrics.heightPixels;
        setContentView(R.layout.activity_main);
    }
}