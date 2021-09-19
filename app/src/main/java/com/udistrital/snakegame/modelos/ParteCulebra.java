package com.udistrital.snakegame;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class ParteCulebra {
    private Bitmap bitmap;
    private int x;
    private int y;
    private Rect rectBody;
    private Rect rectTop;
    private Rect rectBottom;
    private Rect rectLeft;
    private Rect rectRight;

    public ParteCulebra(Bitmap bitmap, int x, int y) {
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

    public Rect getRectBody() {
        return new Rect(this.x, this.y, this.x + VistaJuego.tamanioMap, this.y + VistaJuego.tamanioMap);
    }

    public void setRectBody(Rect rectBody) {
        this.rectBody = rectBody;
    }

    public Rect getRectTop() {
        return new Rect(this.x, this.y - 10 * Constantes.SCREEN_HEIGHT / 1920, this.x + VistaJuego.tamanioMap, this.y);
    }

    public void setRectTop(Rect rectTop) {
        this.rectTop = rectTop;
    }

    public Rect getRectBottom() {
        return new Rect(this.x, this.y + VistaJuego.tamanioMap, this.x + VistaJuego.tamanioMap, this.y + VistaJuego.tamanioMap + 10 * Constantes.SCREEN_HEIGHT / 1920);
    }

    public void setRectBottom(Rect rectBottom) {
        this.rectBottom = rectBottom;
    }

    public Rect getRectLeft() {
        return new Rect(this.x - 10 * Constantes.SCREEN_WIDTH / 1080, this.y, this.x, this.y + VistaJuego.tamanioMap);
    }

    public void setRectLeft(Rect rectLeft) {
        this.rectLeft = rectLeft;
    }

    public Rect getRectRight() {
        return new Rect(this.x + VistaJuego.tamanioMap, this.y, this.x + VistaJuego.tamanioMap + 10 * Constantes.SCREEN_WIDTH / 1080, this.y + VistaJuego.tamanioMap);
    }

    public void setRectRight(Rect rectRight) {
        this.rectRight = rectRight;
    }
}
