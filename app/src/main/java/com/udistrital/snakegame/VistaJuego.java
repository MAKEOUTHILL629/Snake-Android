package com.udistrital.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
    private boolean move = false;
    private float movimientoX;
    private float movimientoY;
    private Handler handler;
    private Runnable runnable;
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
        this.handler = new Handler();
        this.runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch (a){
            case MotionEvent.ACTION_MOVE:{
                if(move == false){
                    movimientoX = event.getX();
                    movimientoY = event.getY();
                    move = true;
                }else {
                    if(movimientoX -  event.getX() > 100 *  SCREEN_WIDTH/ 1000 && !culebrita.isDerecha()){
                        movimientoX = event.getX();
                        movimientoY = event.getY();
                        culebrita.setIzquierda(true);
                    }else if(event.getX()-movimientoX    > 100 *  SCREEN_WIDTH/ 1000 && !culebrita.isIzquierda()){
                        movimientoX = event.getX();
                        movimientoY = event.getY();
                        culebrita.setDerecha(true);
                    }else if(movimientoY -  event.getY() > 100 *  SCREEN_WIDTH/ 1000 && !culebrita.isAbajo()){
                        movimientoX = event.getX();
                        movimientoY = event.getY();
                        culebrita.setArriba(true);
                    }else if(event.getY() - movimientoY   > 100 *  SCREEN_WIDTH/ 1000 && !culebrita.isArriba()){
                        movimientoX = event.getX();
                        movimientoY = event.getY();
                        culebrita.setAbajo(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                movimientoX =0;
                movimientoY =0;
                move = false;
                break;
            }
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0xFF2ECC71);
        for (Pasto pastito :
                this.arrayPasto) {
            canvas.drawBitmap(pastito.getBitmap(), pastito.getX(), pastito.getY(), null);
        }
        culebrita.update();
        culebrita.draw(canvas);
        handler.postDelayed(runnable, 100);
    }
}
