package com.daleondeveloper.Game.tools;

public interface Grid<E> {
    boolean put(E putObject, int x, int y);
    E getElementByCordinate(int x, int y);
    E[][] getGrid();
    E getLowerObjectRelativeToCoordinates(int x,int y);
    E getTopObjectRelativeToCoordinates(int x,int y);
    E getLeftObjectRelativeToCoordinates(int x,int y);
    E getRightObjectRelativeToCoordinates(int x,int y);
    E deleteElementFromCellByCordinate(int x, int y);
    void deleteAllElement();
}
