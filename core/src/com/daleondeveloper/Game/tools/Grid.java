package com.daleondeveloper.Game.tools;

public interface Grid<E> {
    void put(E putObject, int x, int y);
    E getElementByCordinate(int x, int y);
    E[][] getGrid();
    E deleteElementFromCellByCordinate(int x, int y);
    void deleteAllElement();
}
