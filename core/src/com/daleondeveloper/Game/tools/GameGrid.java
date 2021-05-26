package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Sprites.AbstractDynamicObject;
import com.daleondeveloper.Sprites.Blocks.Block;

public class GameGrid {

    private Grid<AbstractDynamicObject> gameGridImpl;

    public GameGrid (int width, int height){
        gameGridImpl = new GridImpl<AbstractDynamicObject>(new AbstractDynamicObject[width][height]);
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

    //Методи для отримання обєктів з сітки
    public Block getBlockByCordinate(int x, int y){
         return getBlockIfObjectIsBlock(gameGridImpl.getElementByCordinate(x,y));

    }
    //Блоки відносно кординати
    public Block getLowerBlockRelativeToCoordinates(int x,int y){
        return getBlockIfObjectIsBlock(gameGridImpl.getLowerObjectRelativeToCoordinates(x,y));
    }
    public Block getTopBlockRelativeToCoordinates(int x, int y){
        return getBlockIfObjectIsBlock(gameGridImpl.getTopObjectRelativeToCoordinates(x,y));
    }
    public Block getLeftBlockRelativeToCoordinates(int x, int y){
        return getBlockIfObjectIsBlock(gameGridImpl.getLeftObjectRelativeToCoordinates(x,y));
    }
    public Block getRightBlockRelativeToCoordinates(int x, int y){
        return getBlockIfObjectIsBlock(gameGridImpl.getRightObjectRelativeToCoordinates(x,y));
    }
    //Блоки відносно обєкта
    public Block getLowerBlockRelativeToObject(AbstractDynamicObject obj){
        return getBlockIfObjectIsBlock(gameGridImpl.getLowerObjectRelativeToCoordinates(
                (int)obj.positionInGameGrid.x,(int)obj.positionInGameGrid.y
        ));
    }
    public Block getTopBlockRelativeToObject(AbstractDynamicObject obj){
        return getBlockIfObjectIsBlock(gameGridImpl.getTopObjectRelativeToCoordinates(
                (int)obj.positionInGameGrid.x,(int)obj.positionInGameGrid.y
        ));
    }
    public Block getLeftBlockRelativeToObject(AbstractDynamicObject obj){
        return getBlockIfObjectIsBlock(gameGridImpl.getLeftObjectRelativeToCoordinates(
                (int)obj.positionInGameGrid.x,(int)obj.positionInGameGrid.y
        ));
    }
    public Block getRightBlockRelativeToObject(AbstractDynamicObject obj){
        return getBlockIfObjectIsBlock(gameGridImpl.getRightObjectRelativeToCoordinates(
                (int)obj.positionInGameGrid.x,(int)obj.positionInGameGrid.y
        ));
    }

    //Обєкти шукаються відносно нижнього лівої клітинки обєкта
    //Можливе повернення цього ж обєкта
    public AbstractDynamicObject findObjNearObj(AbstractDynamicObject obj,int x,int y){
        int findingObjX = (int)obj.positionInGameGrid.x + x;
        int findingObjY = (int)obj.positionInGameGrid.y + y;
        if(checkObjectCordinate(obj) &&
                checkCordinateInCorrection(findingObjX, findingObjY,
                        gameGridImpl.getGrid().length,gameGridImpl.getGrid()[0].length)){
            return  gameGridImpl.getElementByCordinate(findingObjX, findingObjY);
        }
        return null;
    }
    public boolean addObject(AbstractDynamicObject addObject, int x, int y){
        if(checkCordinateInCorrection(x,y,gameGridImpl.getGrid().length,gameGridImpl.getGrid()[0].length)){
            deleteObjectFromGrid(addObject);
            gameGridImpl.put(addObject,x ,y);
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
        return (gameGridImpl.getElementByCordinate((int)objCordinate.x, (int)objCordinate.y) == obj);
    }
    public boolean checkCordinateInCorrection(int x, int y, int xMax, int yMax){
        return (x >= 0 && x < xMax && y >= 0 && y < yMax);
    }
    private Block getBlockIfObjectIsBlock(AbstractDynamicObject obj){
        if(obj instanceof Block){
            return (Block)obj;
        }
        return  null;
    }

    public int getGridWidthLength(){return gameGridImpl.getGrid().length;}
    public int getGridHeightLength(){return gameGridImpl.getGrid()[0].length;}
    public Grid<AbstractDynamicObject> getGameGridImpl() {
        return gameGridImpl;
    }
}
