package com.udistrital.snakegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;

import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


import com.udistrital.snakegame.modelos.Culebrita;
import com.udistrital.snakegame.modelos.Manzana;
import com.udistrital.snakegame.modelos.Pasto;
import com.udistrital.snakegame.modelos.SesionMultijugador;
import com.udistrital.snakegame.repositorios.ConexionFirebase;
import com.udistrital.snakegame.repositorios.Online;

import java.util.ArrayList;
import java.util.Random;

import static com.udistrital.snakegame.utilidades.Constantes.*;

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
    public static boolean estaJugando = false;
    public static int puntuacion = 0;
    public static int mejorPuntuacion = 0;
    private Context context;
    private int sonidoComer;
    private int sonidoMorir;
    private float volumenSonido;
    private boolean cargandoSonido;
    private SoundPool soundPool;
    private SesionMultijugador sesionMultijugador;
    private Integer puntuacionOponente = 0;


    public SesionMultijugador getSesionMultijugador() {
        return sesionMultijugador;
    }

    public VistaJuego(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            mejorPuntuacion = sharedPreferences.getInt("mejor_puntuacion", 0);
        }


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

                Bitmap bitmapAuxiliar = bitMapGrass;
                arrayPasto.add(new Pasto(bitmapAuxiliar,
                        j * bitmapAuxiliar.getWidth() + SCREEN_WIDTH / 2 - (weigth / 2) * bitmapAuxiliar.getWidth(),
                        i * bitmapAuxiliar.getHeight() + 50 * SCREEN_HEIGHT / 1920, bitmapAuxiliar.getWidth(), bitmapAuxiliar.getHeight()));
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

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
        this.soundPool = builder.build();


        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                cargandoSonido = true;
            }
        });
        sonidoComer = this.soundPool.load(context, R.raw.eat, 1);
        sonidoMorir = this.soundPool.load(context, R.raw.die, 1);

        this.sesionMultijugador = new SesionMultijugador("");


    }

    public void iniciarGameMultiplayer(String nombre) {
        this.sesionMultijugador.setNombreUser(nombre);
        this.sesionMultijugador.setListo(true);
        this.sesionMultijugador.setEstado("JUGANDO");
        ConexionFirebase.guardar(this.sesionMultijugador);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch (a) {
            case MotionEvent.ACTION_MOVE: {
                if (move == false) {

                    movimientoX = event.getX();
                    movimientoY = event.getY();
                    move = true;
                } else {
                    if (movimientoX - event.getX() > 95 && !culebrita.isDerecha() && !culebrita.verificarCuerpoHorizontal()) {

                        movimientoX = event.getX();
                        movimientoY = event.getY();
                        culebrita.setIzquierda(true);
                        estaJugando = true;
                        MainActivity.imgDeslizar.setVisibility(INVISIBLE);

                    } else if (event.getX() - movimientoX > 95 && !culebrita.isIzquierda() && !culebrita.verificarCuerpoHorizontal()) {

                        movimientoX = event.getX();
                        movimientoY = event.getY();
                        culebrita.setDerecha(true);
                        estaJugando = true;
                        MainActivity.imgDeslizar.setVisibility(INVISIBLE);

                    } else if (event.getY() - movimientoY > 95 && !culebrita.isArriba() && !culebrita.verificarCuerpoVertical()) {


                        movimientoX = event.getX();
                        movimientoY = event.getY();
                        culebrita.setAbajo(true);
                        estaJugando = true;
                        MainActivity.imgDeslizar.setVisibility(INVISIBLE);

                    } else if (movimientoY - event.getY() > 95 && !culebrita.isAbajo() && !culebrita.verificarCuerpoVertical()) {

                        movimientoX = event.getX();
                        movimientoY = event.getY();
                        culebrita.setArriba(true);
                        estaJugando = true;
                        MainActivity.imgDeslizar.setVisibility(INVISIBLE);

                    }

                }
                break;
            }
            case MotionEvent.ACTION_UP: {

                movimientoX = 0;
                movimientoY = 0;
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


        if (estaJugando) {

            if (this.culebrita.getPartesCulebra().get(0).getX() < this.arrayPasto.get(0).getX()
                    || this.culebrita.getPartesCulebra().get(0).getY() < this.arrayPasto.get(0).getY()
                    || this.culebrita.getPartesCulebra().get(0).getY() + tamanioMap > this.arrayPasto.get(this.arrayPasto.size() - 1).getY() + tamanioMap
                    || this.culebrita.getPartesCulebra().get(0).getX() + tamanioMap > this.arrayPasto.get(this.arrayPasto.size() - 1).getX() + tamanioMap) {

                juegoTerminado();
            } else {
                this.culebrita.update();
            }

            for (int i = 1; i < this.culebrita.getPartesCulebra().size(); i++) {
                if (this.culebrita.getPartesCulebra().get(0).getRectBody().intersect(this.culebrita.getPartesCulebra().get(i).getRectBody())) {

                    juegoTerminado();
                }
            }

        }
        culebrita.draw(canvas);
        manzana.draw(canvas);


        if (this.culebrita.getPartesCulebra().get(0).getRectBody().intersect(manzana.getRect())) {
            if (cargandoSonido) {
                int pistaId = this.soundPool.play(this.sonidoComer, (float) 0.5, (float) 0.5, 1, 0, 1f);
            }
            int xy[] = randomManzano();
            this.manzana.reset(this.arrayPasto.get(xy[0]).getX(), this.arrayPasto.get(xy[1]).getY());
            this.culebrita.aumentarTamanio();
            puntuacion++;
            this.sesionMultijugador.setPuntuacion(puntuacion);
            ConexionFirebase.guardar(this.sesionMultijugador);
            MainActivity.textViewPuntuacion.setText(String.valueOf(puntuacion));
            if (puntuacion > mejorPuntuacion) {
                mejorPuntuacion = puntuacion;
                SharedPreferences sharedPreferences = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(MEJOR_PUNTUACION, mejorPuntuacion);
                editor.apply();
                MainActivity.textViewMejorPuntuacion.setText(String.valueOf(mejorPuntuacion));
            }

        }


        if (Online.oponente != null) {

            if (this.puntuacionOponente < Online.oponente.getPuntuacion()) {
                this.puntuacionOponente = Online.oponente.getPuntuacion();
                this.culebrita.aumentarTamanio();
            }

            handler.postDelayed(runnable, 350 - (Online.oponente.getPuntuacion()));
        } else {
            handler.postDelayed(runnable, 350);
        }

    }

    public void juegoTerminado() {

        if ((Online.oponente.getEstado().equals("PERDIO") || Online.oponente.getEstado().equals("RESET")) && Online.oponente.getPuntuacion() <= puntuacion) {
            MainActivity.textViewMultiplayer.setText("Le haz ganado a " + Online.oponente.getNombreUser() + " felicidades!!");
        } else if (Online.oponente.getEstado().equals("JUGANDO")) {
            MainActivity.textViewMultiplayer.setText("Haz perdido, te ha ganado " + Online.oponente.getNombreUser());
        }
        this.sesionMultijugador.setEstado("PERDIO");
        this.sesionMultijugador.setOponente("");
        ConexionFirebase.guardar(this.sesionMultijugador);
        estaJugando = false;
        MainActivity.dialogPuntuacion.show();
        MainActivity.textViewDialogMejorPuntuacion.setText(mejorPuntuacion + "");
        MainActivity.textViewDialogPuntuacion.setText(puntuacion + "");
        if (cargandoSonido) {
            int pistaId = this.soundPool.play(this.sonidoMorir, (float) 0.5, (float) 0.5, 1, 0, 1f);
        }

        this.sesionMultijugador.setEstado("RESET");
        this.sesionMultijugador.setPuntuacion(0);
        this.sesionMultijugador.setListo(true);
        this.sesionMultijugador.setOponente("");
        this.sesionMultijugador.setNombreUser(getSesionMultijugador().getNombreUser());
        ConexionFirebase.guardar(this.sesionMultijugador);
    }

    public int[] randomManzano() {
        int xy[] = new int[2];
        Random random = new Random();
        xy[0] = random.nextInt(this.arrayPasto.size() - 1);
        xy[1] = random.nextInt(this.arrayPasto.size() - 1);

        Rect rect = new Rect(this.arrayPasto.get(xy[0]).getX(), this.arrayPasto.get(xy[1]).getY(),
                this.arrayPasto.get(xy[0]).getX() + tamanioMap, this.arrayPasto.get(xy[1]).getY() + tamanioMap);

        boolean check = true;

        while (check) {
            check = false;
            for (int i = 0; i < this.culebrita.getPartesCulebra().size(); i++) {

                if (rect.intersect(this.culebrita.getPartesCulebra().get(i).getRectBody())) {
                    check = true;
                    xy[0] = random.nextInt(this.arrayPasto.size() - 1);
                    xy[1] = random.nextInt(this.arrayPasto.size() - 1);

                    rect = new Rect(this.arrayPasto.get(xy[0]).getX(), this.arrayPasto.get(xy[1]).getY(),
                            this.arrayPasto.get(xy[0]).getX() + tamanioMap, this.arrayPasto.get(xy[1]).getY() + tamanioMap);
                }

            }
        }

        return xy;
    }

    public void reset() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weigth; j++) {

                Bitmap bitmapAuxiliar = bitMapGrass;

                arrayPasto.add(new Pasto(bitmapAuxiliar,
                        j * bitmapAuxiliar.getWidth() + SCREEN_WIDTH / 2 - (weigth / 2) * bitmapAuxiliar.getWidth(),
                        i * bitmapAuxiliar.getHeight() + 50 * SCREEN_HEIGHT / 1920, bitmapAuxiliar.getWidth(), bitmapAuxiliar.getHeight()));
            }
        }
        this.culebrita = new Culebrita(bitmapSnake, this.arrayPasto.get(126).getX(), this.arrayPasto.get(126).getY(), 4);
        int xy[] = this.randomManzano();
        this.manzana = new Manzana(bitmapApple, this.arrayPasto.get(xy[0]).getX(), this.arrayPasto.get(xy[1]).getY());

        puntuacion = 0;
        MainActivity.textViewPuntuacion.setText(String.valueOf(puntuacion));
    }

    public Integer getPuntuacionOponente() {
        return puntuacionOponente;
    }

    public void setPuntuacionOponente(Integer puntuacionOponente) {
        this.puntuacionOponente = puntuacionOponente;
    }
}
