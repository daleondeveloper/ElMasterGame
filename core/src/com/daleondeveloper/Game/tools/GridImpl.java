package com.daleondeveloper.Game.tools;

import com.daleondeveloper.Exception.ExceptionBadCordinate;

public class GridImpl<E> implements Grid<E>{
    private static final String TAG = GridImpl.class.getName();

    private E playGrid[][];
    private int width,height;

    public GridImpl(E playGrid[][]){
        this.playGrid = playGrid;
        width = playGrid.length;
        height = playGrid[0].length;
    }

    public boolean put(E putObject,int x, int y){
        if(isCordinateCorrect(x,y)) {
            playGrid[x][y] = putObject;
            return  true;
        }
        return false;
    }
    public E getElementByCordinate(int x, int y){
        if(isCordinateCorrect(x,y)) {
            return playGrid[x][y];
        }
        return  null;
    }
    public E getLowerObjectRelativeToCoordinates(int x,int y){
        return getElementByCordinate(x,y - 1);
    }
    public E getTopObjectRelativeToCoordinates(int x, int y){
        return getElementByCordinate(x,y + 1);
    }
    public E getLeftObjectRelativeToCoordinates(int x, int y){
        return getElementByCordinate(x - 1,y);
    }
    public E getRightObjectRelativeToCoordinates(int x, int y){
        return getElementByCordinate(x + 1,y);
    }
    public E[][] getGrid(){
        return playGrid;
    }
    public E deleteElementFromCellByCordinate(int x, int y) {
        if(isCordinateCorrect(x,y)) {
            E e = playGrid[x][y];
            playGrid[x][y] = null;
            return e;
        }
        throw new ExceptionBadCordinate();
    }
    public void deleteAllElement(){
        for(int i = 0; i < playGrid.length; i++){
            for(int j = 0; j < playGrid[0].length; j++){
                playGrid[i][j] = null;
            }
        }
    }
    private boolean isCordinateCorrect(int x,int y){
        return (x >= 0 && x < playGrid.length &&
                        y >= 0 && y < playGrid[0].length);
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
