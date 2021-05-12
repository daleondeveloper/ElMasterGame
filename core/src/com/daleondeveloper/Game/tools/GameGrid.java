package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Sprites.AbstractDynamicObject;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.tools.GameConstants;

public class GameGrid {

    private Grid<AbstractDynamicObject> gameGridImpl;

    public GameGrid (int width, int height){
        gameGridImpl = new GridImpl<AbstractDynamicObject>(width,height);
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

    public AbstractDynamicObject findObjectByCordinate(int x,int y){
        if(checkCordinateInCorrection(x,y,10,30)){
            return gameGridImpl.getElementByCordinate(x,y);
        }
        return null;
    }
    public Block findBlockByCordinate(int x, int y){
        if(checkCordinateInCorrection(x,y,10,30)){
            AbstractDynamicObject obj =  gameGridImpl.getElementByCordinate(x,y);
            if(obj instanceof Block){
                return (Block)obj;
            }
        }
        return null;
    }
    //Обєкти шукаються відносно нижнього лівої клітинки обєкта
    //Можливе повернення цього ж обєкта
    public AbstractDynamicObject findObjNearObj(AbstractDynamicObject obj,int x,int y){
        int findingObjX = (int)obj.positionInGameGrid.x + x;
        int findingObjY = (int)obj.positionInGameGrid.y + y;
        if(checkObjectCordinate(obj) &&
                checkCordinateInCorrection(findingObjX, findingObjY,
                        gameGridImpl.getGrid().length,gameGridImpl.getGrid()[0].length)){
            AbstractDynamicObject foundObj =  gameGridImpl.getElementByCordinate(findingObjX, findingObjY);
            return foundObj;
        }
        return null;
    }
    public boolean addObject(AbstractDynamicObject addObject, int x, int y){
        if(checkCordinateInCorrection(x,y,gameGridImpl.getGrid().length,gameGridImpl.getGrid()[0].length)){
            int objWidth = (int)(addObject.getWidth() / GameConstants.PIX_IN_CELL);
            int objHeight = (int)(addObject.getHeight() / GameConstants.PIX_IN_CELL);
            for(int i = 0; i <= objWidth; i++){
                for(int j = 0; j <= objHeight; j++){
                    if(gameGridImpl.getElementByCordinate(x+i, y+j) != null){
                        throw new RuntimeException();
                    }
                }
            }
            deleteObjectFromGrid(addObject);
            for(int i = 0; i <= objWidth; i++){
                for(int j = 0; j <= objHeight; j++){
                    gameGridImpl.put(addObject,x + i,y + j);
                }
            }
        }
        return false;
    }
    public void deleteObjectFromGrid(AbstractDynamicObject deleteObj){
        for(int i = 0; i < gameGridImpl.getGrid().length; i++){
            for(int j = 0; j < gameGridImpl.getGrid()[0].length; j++){
                if(gameGridImpl.getElementByCordinate(i,j) == deleteObj){
                    gameGridImpl.deleteElementFromCellByCordinate(i,j);
                }
            }
        }
    }

    public boolean checkObjectCordinate(AbstractDynamicObject obj){
        Vector2 objCordinate = obj.positionInGameGrid;
        if(gameGridImpl.getElementByCordinate((int)objCordinate.x, (int)objCordinate.y) == obj){
            return true;
        }
        return false;
    }
    public boolean checkCordinateInCorrection(int x, int y, int xMax, int yMax){
        if(x >= 0 && x < xMax && y >= 0 && y < yMax){
            return true;
        }else {
            return false;
        }
    }

}
