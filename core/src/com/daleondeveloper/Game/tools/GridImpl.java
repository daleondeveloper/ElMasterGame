package com.daleondeveloper.Game.tools;

import com.daleondeveloper.Exception.ExceptionBadCordinate;

public class GridImpl<E> implements Grid<E>{
    private static final String TAG = GridImpl.class.getName();

    private E playGrid[][];
    private int width,height;

    public GridImpl(int width, int height){
        this.width = width;
        this.height = height;
        playGrid = (E[][]) new Object[width][height];
    }

    public void put(E putObject,int x, int y){
        isCordinateCorrect(x,y);
        playGrid[x][y] = putObject;
    }
    public E getElementByCordinate(int x, int y){
        isCordinateCorrect(x,y);
        return playGrid[x][y];
    }
    public E[][] getGrid(){
        return playGrid;
    }
    public E deleteElementFromCellByCordinate(int x, int y) {
        isCordinateCorrect(x,y);
            E e = playGrid[x][y];
            playGrid[x][y] = null;
            return e;

    }
    public void deleteAllElement(){
        playGrid = (E[][]) new Object[width][height];
    }
    private boolean isCordinateCorrect(int x,int y){
                if(x < width && y < height){
                    return true;
                }else {
                    throw new ExceptionBadCordinate();
                }
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
