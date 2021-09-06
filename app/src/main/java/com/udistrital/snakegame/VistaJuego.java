package com.udistrital.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import static com.udistrital.snakegame.Constantes.SCREEN_HEIGHT;
import static com.udistrital.snakegame.Constantes.SCREEN_WIDTH;

public class VistaJuego extends View {
    private Bitmap bitmap;
    private Bitmap bitMapGrass;
    private Bitmap bitMapGrassAux;
    public static int tamanioMap = 75 * SCREEN_WIDTH/1080;
    private int height =21;
    private int weigth =12;
    private ArrayList<Pasto> arrayPasto = new ArrayList<>();
    public VistaJuego(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.bitMapGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        this.bitMapGrass = Bitmap.createScaledBitmap(bitMapGrass,tamanioMap, tamanioMap, true);

        this.bitMapGrassAux = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
        this.bitMapGrassAux = Bitmap.createScaledBitmap(bitMapGrassAux,tamanioMap, tamanioMap, true);


        for(int i =0; i< height; i++){
            for (int j=0; j< weigth; j++){

                if((i + j) % 2 == 0){
                    arrayPasto.add(new Pasto(bitMapGrass, j * tamanioMap + SCREEN_WIDTH / 2 - (weigth / 2) * tamanioMap , i * tamanioMap + 100 * SCREEN_HEIGHT / 1920, tamanioMap, tamanioMap));
                }else{
                    arrayPasto.add(new Pasto(bitMapGrassAux, j * tamanioMap + SCREEN_WIDTH / 2 - (weigth / 2) * tamanioMap , i * tamanioMap + 100 * SCREEN_HEIGHT / 1920, tamanioMap, tamanioMap));
                }

            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0xFF2ECC71);
        for (Pasto pastito:
             this.arrayPasto) {
            canvas.drawBitmap(pastito.getBitmap(), pastito.getX(), pastito.getY(), null );
        }
    }
}
