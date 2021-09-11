package com.udistrital.snakegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Culebrita {
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

        this.partesCulebra.add(new ParteCulebra(this.bitmap[4], x, y));

        for (int i = 1; i < this.length - 1; i++) {
            this.partesCulebra.add(new ParteCulebra(this.bitmap[6], this.partesCulebra.get(i - 1).getX() - VistaJuego.tamanioMap, y));
        }
        this.partesCulebra.add(new ParteCulebra(this.bitmap[11], this.partesCulebra.get(length - 2).getX() - VistaJuego.tamanioMap, y));

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
}
