package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.GameGrid;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Sprites.AbstractDynamicObject;

import java.util.Random;

public class CreateBlock extends Upgrader {

    private int count;
    private Random rnd;
    private GameGrid gameGrid;
    private int type;

    public CreateBlock(GameWorld gameWorld, int count,int type) {
        super(gameWorld);
        this.count = count;
        this.type = type;
        gameGrid = gameWorld.getGameGrid();
        rnd = new Random();
    }

    @Override
    protected void upgrade() {
        for(int i = 0; i < count; i++){
            int x = rnd.nextInt(9);
            AbstractDynamicObject gameObject  = gameGrid.getBlockByCordinate(x,0);
            for(int j = 0; j < gameGrid.getGridHeightLength(); j ++){
                gameObject = gameGrid.getBlockByCordinate(x,j);
                if(gameObject != null){
                    gameObject.getUpInOneCells();
                }
            }
            gameWorld.getBlockController().addBlock((x+1) * 50,150,type);
        }

    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        return info + "Додати блоки";
    }

}
