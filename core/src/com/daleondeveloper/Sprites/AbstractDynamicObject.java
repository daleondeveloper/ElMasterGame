package com.daleondeveloper.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class AbstractDynamicObject extends AbstractGameObject {

    public Vector2 positionInGameGrid;

    public abstract Vector2 getBodyPosition();
    protected Body body;
    protected Vector2 returnPosition;


    public Vector2 getPositionInGameGrid() {
        return positionInGameGrid;
    }
    protected abstract void updatePositionInGrid();

    public AbstractDynamicObject getUpInOneCells(){
            returnPosition.y += 10;
            body.setTransform(returnPosition,0);
        return this;
    }
}
