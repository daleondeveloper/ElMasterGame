package com.daleondeveloper.Sprites;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractDynamicObject extends AbstractGameObject {

    public Vector2 positionInGameGrid;

    public abstract Vector2 getBodyPosition();

    public Vector2 getPositionInGameGrid() {
        return positionInGameGrid;
    }
    protected abstract void updatePositionInGrid();
}
