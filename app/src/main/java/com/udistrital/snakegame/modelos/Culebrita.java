package com.udistrital.snakegame.modelos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.udistrital.snakegame.VistaJuego;

import java.util.ArrayList;

public class Culebrita {
    private boolean derecha;
    private boolean arriba;
    private boolean izquierda;
    private boolean abajo;
    private Bitmap bitmap[];
    private int x;
    private int y;
    private int length;
    private ArrayList<ParteCulebra> partesCulebra = new ArrayList<>();

    public Culebrita(Bitmap bitmap, int x, int y, int length) {
        this.bitmap = new Bitmap[15];
        this.bitmap[0] = bitmap;
        this.x = x;
        this.y = y;
        this.length = length;
        this.bitmap[10] = Bitmap.createBitmap(bitmap, 0, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[9] = Bitmap.createBitmap(bitmap, VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[6] = Bitmap.createBitmap(bitmap, 2 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[8] = Bitmap.createBitmap(bitmap, 3 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[7] = Bitmap.createBitmap(bitmap, 4 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[5] = Bitmap.createBitmap(bitmap, 5 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[2] = Bitmap.createBitmap(bitmap, 6 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[3] = Bitmap.createBitmap(bitmap, 7 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[4] = Bitmap.createBitmap(bitmap, 8 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[1] = Bitmap.createBitmap(bitmap, 9 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[13] = Bitmap.createBitmap(bitmap, 10 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[11] = Bitmap.createBitmap(bitmap, 11 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[12] = Bitmap.createBitmap(bitmap, 12 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        this.bitmap[14] = Bitmap.createBitmap(bitmap, 13 * VistaJuego.tamanioMap, 0, VistaJuego.tamanioMap, VistaJuego.tamanioMap);
        setDerecha(true);
        this.partesCulebra.add(new ParteCulebra(this.bitmap[4], x, y));

        for (int i = 1; i < this.length - 1; i++) {
            this.partesCulebra.add(new ParteCulebra(this.bitmap[6], this.partesCulebra.get(i - 1).getX() - VistaJuego.tamanioMap, y));
        }
        this.partesCulebra.add(new ParteCulebra(this.bitmap[11], this.partesCulebra.get(length - 2).getX() - VistaJuego.tamanioMap, y));


    }


    public void update(){
        for(int i = length-1; i > 0; i-- ){
            this.partesCulebra.get(i).setX(this.partesCulebra.get(i-1).getX());
            this.partesCulebra.get(i).setY(this.partesCulebra.get(i-1).getY());
        }

        if(derecha){
            this.partesCulebra.get(0).setX(this.partesCulebra.get(0).getX() + VistaJuego.tamanioMap);
            this.partesCulebra.get(0).setBitmap(this.bitmap[4]);
        }else if(izquierda){
            this.partesCulebra.get(0).setX(this.partesCulebra.get(0).getX() - VistaJuego.tamanioMap);
            this.partesCulebra.get(0).setBitmap(this.bitmap[3]);
        }else if(arriba){
            this.partesCulebra.get(0).setY(this.partesCulebra.get(0).getY() - VistaJuego.tamanioMap);
            this.partesCulebra.get(0).setBitmap(this.bitmap[1]);
        }else if(abajo){
            this.partesCulebra.get(0).setY(this.partesCulebra.get(0).getY() + VistaJuego.tamanioMap);
            this.partesCulebra.get(0).setBitmap(this.bitmap[2]);
        }

        for(int i =1 ; i< length-1; i++){
            if(this.partesCulebra.get(i).getRectLeft().intersect(this.partesCulebra.get(i+1).getRectBody()) &&
               this.partesCulebra.get(i).getRectBottom().intersect(this.partesCulebra.get(i-1).getRectBody()) ||
               this.partesCulebra.get(i).getRectLeft().intersect(this.partesCulebra.get(i-1).getRectBody()) &&
                       this.partesCulebra.get(i).getRectBottom().intersect(this.partesCulebra.get(i + 1).getRectBody())){
                this.partesCulebra.get(i).setBitmap(this.bitmap[10]);
            }else if(this.partesCulebra.get(i).getRectRight().intersect(this.partesCulebra.get(i+1).getRectBody()) &&
                    this.partesCulebra.get(i).getRectBottom().intersect(this.partesCulebra.get(i-1).getRectBody()) ||
                    this.partesCulebra.get(i).getRectRight().intersect(this.partesCulebra.get(i-1).getRectBody()) &&
                            this.partesCulebra.get(i).getRectBottom().intersect(this.partesCulebra.get(i + 1).getRectBody())){
                this.partesCulebra.get(i).setBitmap(this.bitmap[9]);
            }else if(this.partesCulebra.get(i).getRectLeft().intersect(this.partesCulebra.get(i+1).getRectBody()) &&
                    this.partesCulebra.get(i).getRectTop().intersect(this.partesCulebra.get(i-1).getRectBody()) ||
                    this.partesCulebra.get(i).getRectLeft().intersect(this.partesCulebra.get(i-1).getRectBody()) &&
                    this.partesCulebra.get(i).getRectTop().intersect(this.partesCulebra.get(i + 1).getRectBody())){
                this.partesCulebra.get(i).setBitmap(this.bitmap[8]);
            }else if(this.partesCulebra.get(i).getRectRight().intersect(this.partesCulebra.get(i+1).getRectBody()) &&
                    this.partesCulebra.get(i).getRectTop().intersect(this.partesCulebra.get(i-1).getRectBody()) ||
                    this.partesCulebra.get(i).getRectRight().intersect(this.partesCulebra.get(i-1).getRectBody()) &&
                    this.partesCulebra.get(i).getRectTop().intersect(this.partesCulebra.get(i + 1).getRectBody())){
                this.partesCulebra.get(i).setBitmap(this.bitmap[7]);
            }else if(this.partesCulebra.get(i).getRectTop().intersect(this.partesCulebra.get(i+1).getRectBody()) &&
                    this.partesCulebra.get(i).getRectBottom().intersect(this.partesCulebra.get(i-1).getRectBody()) ||
                    this.partesCulebra.get(i).getRectTop().intersect(this.partesCulebra.get(i-1).getRectBody()) &&
                    this.partesCulebra.get(i).getRectBottom().intersect(this.partesCulebra.get(i + 1).getRectBody())){
                this.partesCulebra.get(i).setBitmap(this.bitmap[5]);
            }else if(this.partesCulebra.get(i).getRectLeft().intersect(this.partesCulebra.get(i+1).getRectBody()) &&
                    this.partesCulebra.get(i).getRectRight().intersect(this.partesCulebra.get(i-1).getRectBody()) ||
                    this.partesCulebra.get(i).getRectLeft().intersect(this.partesCulebra.get(i-1).getRectBody()) &&
                   this.partesCulebra.get(i).getRectRight().intersect(this.partesCulebra.get(i + 1).getRectBody())){
                this.partesCulebra.get(i).setBitmap(this.bitmap[6]);
            }
        }


        if(this.partesCulebra.get(length -1).getRectRight().intersect(this.partesCulebra.get(length-2).getRectBody())){
            this.partesCulebra.get(length -1 ).setBitmap(this.bitmap[11]);
        }else if(this.partesCulebra.get(length -1).getRectLeft().intersect(this.partesCulebra.get(length-2).getRectBody())){
            this.partesCulebra.get(length -1 ).setBitmap(this.bitmap[12]);
        }else if(this.partesCulebra.get(length -1).getRectTop().intersect(this.partesCulebra.get(length-2).getRectBody())){
            this.partesCulebra.get(length -1 ).setBitmap(this.bitmap[13]);
        }else if(this.partesCulebra.get(length -1).getRectBottom().intersect(this.partesCulebra.get(length-2).getRectBody())){
            this.partesCulebra.get(length -1 ).setBitmap(this.bitmap[14]);
        }
    }

    public void draw(Canvas canvas) {
        for (ParteCulebra parte : this.partesCulebra) {
            canvas.drawBitmap(parte.getBitmap(), parte.getX(), parte.getY(), null);
        }

    }

    public Bitmap[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap[] bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<ParteCulebra> getPartesCulebra() {
        return partesCulebra;
    }

    public void setPartesCulebra(ArrayList<ParteCulebra> partesCulebra) {
        this.partesCulebra = partesCulebra;
    }

    public boolean isDerecha() {

        return derecha;
    }

    public void setDerecha(boolean derecha) {
        restablecerMovimiento();
        this.derecha = derecha;
    }

    public boolean isArriba() {
        return arriba;
    }

    public void setArriba(boolean arriba) {
        restablecerMovimiento();
        this.arriba = arriba;
    }


    public boolean isIzquierda() {
        return izquierda;
    }

    public void setIzquierda(boolean izquierda) {
        restablecerMovimiento();
        this.izquierda = izquierda;
    }

    public boolean isAbajo() {
        return abajo;
    }

    public void setAbajo(boolean abajo) {
        restablecerMovimiento();
        this.abajo = abajo;
    }

    public boolean verificarCuerpoHorizontal(){
        return this.partesCulebra.get(1).getBitmap() == this.bitmap[6];
    }

    public boolean verificarCuerpoVertical(){
        return this.partesCulebra.get(1).getBitmap() == this.bitmap[5];
    }

    public void restablecerMovimiento(){
        this.derecha = false;
        this.arriba = false;
        this.izquierda = false;
        this.abajo = false;
    }

    public void aumentarTamanio() {
        ParteCulebra parteNueva = this.partesCulebra.get(length -1);
        this.length++;
        if(parteNueva.getBitmap() == this.bitmap[11]){
            this.partesCulebra.add(new ParteCulebra(this.bitmap[11],  parteNueva.getX() - VistaJuego.tamanioMap, parteNueva.getY()  ));
        }else if(parteNueva.getBitmap() == this.bitmap[12]){
            this.partesCulebra.add(new ParteCulebra(this.bitmap[12],  parteNueva.getX() + VistaJuego.tamanioMap, parteNueva.getY()  ));
        }else if(parteNueva.getBitmap() == this.bitmap[13]){
            this.partesCulebra.add(new ParteCulebra(this.bitmap[13],  parteNueva.getX() , parteNueva.getY() + VistaJuego.tamanioMap));
        }else if(parteNueva.getBitmap() == this.bitmap[14]){
            this.partesCulebra.add(new ParteCulebra(this.bitmap[14],  parteNueva.getX() , parteNueva.getY() - VistaJuego.tamanioMap ));
        }
    }
}
