package com.daleondeveloper.Game.Settings;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Game.ElMaster;

public class BlockLoad {
    private static final String TAG = BlockLoad.class.getName();

    private Vector2 position;
    private int blockType;

    public BlockLoad(Vector2 position, int blockType) {
        position = new Vector2();
        this.position = position;
        this.blockType = blockType;
    }

    public BlockLoad(float x, float y, int blockType) {
        position = new Vector2();
        position.x = x;
        position.y = y;
        this.blockType = blockType;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }
}
