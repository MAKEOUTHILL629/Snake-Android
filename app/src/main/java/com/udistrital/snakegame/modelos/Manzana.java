package com.udistrital.snakegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Manzana {
    private Bitmap bitmap;
    private int x;
    private int y;
    private Rect rect;

    public Manzana(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
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

    public Rect getRect() {
        return new Rect(this.x,this.y, this.x + VistaJuego.tamanioMap , this.y + VistaJuego.tamanioMap);
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void reset(int nuevaX, int nuevaY) {
        this.x = nuevaX;
        this.y = nuevaY;
    }
}
