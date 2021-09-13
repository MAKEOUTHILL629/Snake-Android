package com.udistrital.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;


import static com.udistrital.snakegame.Constantes.SCREEN_HEIGHT;
import static com.udistrital.snakegame.Constantes.SCREEN_WIDTH;

public class VistaJuego extends View {
    private Bitmap bitmapApple;
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
    private Manzana manzana;
    public VistaJuego(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.bitMapGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        this.bitMapGrass = Bitmap.createScaledBitmap(bitMapGrass, tamanioMap, tamanioMap, true);

        this.bitMapGrassAux = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
        this.bitMapGrassAux = Bitmap.createScaledBitmap(bitMapGrassAux, tamanioMap, tamanioMap, true);

        this.bitmapSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        this.bitmapSnake = Bitmap.createScaledBitmap(bitmapSnake, 14 * tamanioMap, tamanioMap, true);

        this.bitmapApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        this.bitmapApple = Bitmap.createScaledBitmap(bitmapApple, tamanioMap, tamanioMap, true);

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
        int xy[] = this.randomManzano();
        this.manzana = new Manzana(bitmapApple, this.arrayPasto.get(xy[0]).getX(), this.arrayPasto.get(xy[1]).getY());
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
        manzana.draw(canvas);
        if(this.culebrita.getPartesCulebra().get(0).getRectBody().intersect(manzana.getRect())){
            int xy[] = randomManzano();
            this.manzana.reset(this.arrayPasto.get(xy[0]).getX(),  this.arrayPasto.get(xy[1]).getY());
            this.culebrita.aumentarTamanio();
        }
        handler.postDelayed(runnable, 100);
    }

    public int[] randomManzano(){
        int xy[] = new int[2];
        Random random = new Random();
        xy[0] = random.nextInt(this.arrayPasto.size() - 1);
        xy[1] = random.nextInt(this.arrayPasto.size() - 1);

        Rect rect = new Rect(this.arrayPasto.get(xy[0]).getX(), this.arrayPasto.get(xy[1]).getY(),
                            this.arrayPasto.get(xy[0]).getX() + tamanioMap, this.arrayPasto.get(xy[1]).getY() + tamanioMap);

        boolean check = true;

        while (check){
            check = false;
            for(int i=0 ; i < this.culebrita.getPartesCulebra().size() ;i++){

                if(rect.intersect(this.culebrita.getPartesCulebra().get(i).getRectBody())){
                    check = true;
                    xy[0] = random.nextInt(this.arrayPasto.size() - 1);
                    xy[1] = random.nextInt(this.arrayPasto.size() - 1);

                    rect = new Rect(this.arrayPasto.get(xy[0]).getX(), this.arrayPasto.get(xy[1]).getY(),
                            this.arrayPasto.get(xy[0]).getX() + tamanioMap, this.arrayPasto.get(xy[1]).getY() + tamanioMap);

                }

            }
        }

        return  xy;
    }
}
