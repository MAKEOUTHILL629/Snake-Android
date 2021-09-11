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
    private Bitmap bitmapSnake;
    private Bitmap bitMapGrass;
    private Bitmap bitMapGrassAux;
    public static int tamanioMap = 75 * SCREEN_WIDTH / 1080;
    private int height = 21;
    private int weigth = 12;
    private ArrayList<Pasto> arrayPasto = new ArrayList<>();
    private Culebrita culebrita;

    public VistaJuego(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.bitMapGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        this.bitMapGrass = Bitmap.createScaledBitmap(bitMapGrass, tamanioMap, tamanioMap, true);

        this.bitMapGrassAux = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
        this.bitMapGrassAux = Bitmap.createScaledBitmap(bitMapGrassAux, tamanioMap, tamanioMap, true);

        this.bitmapSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        this.bitmapSnake = Bitmap.createScaledBitmap(bitmapSnake, 14 * tamanioMap, tamanioMap, true);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weigth; j++) {

//                if(){
                arrayPasto.add(new Pasto((i + j) % 2 == 0 ? bitMapGrass : bitMapGrassAux, j * tamanioMap + SCREEN_WIDTH / 2 - (weigth / 2) * tamanioMap, i * tamanioMap + 100 * SCREEN_HEIGHT / 1920, tamanioMap, tamanioMap));
//                }else{
//                    arrayPasto.add(new Pasto(, j * tamanioMap + SCREEN_WIDTH / 2 - (weigth / 2) * tamanioMap , i * tamanioMap + 100 * SCREEN_HEIGHT / 1920, tamanioMap, tamanioMap));
//                }

            }
        }

        this.culebrita = new Culebrita(bitmapSnake, this.arrayPasto.get(126).getX(), this.arrayPasto.get(126).getY(), 4);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0xFF2ECC71);
        for (Pasto pastito :
                this.arrayPasto) {
            canvas.drawBitmap(pastito.getBitmap(), pastito.getX(), pastito.getY(), null);
        }
        culebrita.draw(canvas);
    }
}
