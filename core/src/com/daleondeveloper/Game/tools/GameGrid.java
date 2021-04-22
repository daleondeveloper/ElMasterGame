package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Sprites.AbstractDynamicObject;

public class GameGrid {

    private Grid<AbstractDynamicObject> gameGridImpl;

    public GameGrid (){
        gameGridImpl = new GridImpl<AbstractDynamicObject>(10,30);
    }

    public Vector2 findObject (AbstractDynamicObject objToFind){
        Vector2 objCordinate = new Vector2();
        for(int i = 0; i < gameGridImpl.getGrid().length; i++){
            for(int j = 0; j < gameGridImpl.getGrid()[0].length; j++){
                if(gameGridImpl.getElementByCordinate(i,j) == objToFind){
                    objCordinate.set(i,j);
                    return objCordinate;
                }
            }
        }
        return objCordinate.set(-1,-1);
    }

}
