package com.daleondeveloper.Sprites.Blocks;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Effects.ParticleEffectManager;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;

public class SnowBlock extends Block {
    private static final String TAG = SnowBlock.class.getName();

    private float FREEZING_TIME;

    private float freezingTime;


    public SnowBlock(GameWorld gameWorld, BlockController blockController, float x, float y, float width, float height, float freezingTime) {
        super(gameWorld, blockController, x, y, width, height);

        textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockSnow();
        blockType = BlockType.SNOW;
        this.freezingTime = 0;
        FREEZING_TIME = freezingTime;

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(!isIdle()){
//            freezingTime = 0;
        }
        freezeBlock(deltaTime);
        //        if(currentState != State.IDLE){
//            if(effect != null) {
//                effect.reset();
//            }
//            effect = null;
//        }else if(effect == null){
//            addColdEffect();
//        }


    }
    private void addColdEffect(){
        effect =effectManager.getPoolParticleEffect(ParticleEffectManager.COLD_BLOCK_EFFECT);
        effect.setPosition(this.getBodyPosition().x,this.getBodyPosition().y);
        effect.start();
    }

    @Override
    protected void stateIdle(float deltaTime) {
        super.stateIdle(deltaTime);


    }
    private void freezeBlock(float deltaTime) {
        boolean isFreezingTimeIncreased = false;

        if (freezingTime < FREEZING_TIME) {
//            int posMasY = (int) (getReturnCellsPositionY() / 10) - 15;
//            int posMasX = (int) (getReturnCellsPosition() / 10) - 5;

            if (gameGrid.getLeftBlockRelativeToObject(this) != null) {
                Block block = gameGrid.getLeftBlockRelativeToObject(this);
                if (freezingTime < (FREEZING_TIME * block.coefficientFrostbite)) {
                    block.idle();
                    block.stateIdle(0);
                    if(!isFreezingTimeIncreased){
                    freezingTime += deltaTime;
                    isFreezingTimeIncreased = true;
                    }
                }
            }
            if (gameGrid.getRightBlockRelativeToObject(this) != null) {
                Block block = gameGrid.getRightBlockRelativeToObject(this);
                if (freezingTime < (FREEZING_TIME * block.coefficientFrostbite)) {
                    block.idle();
                    block.stateIdle(0);
                    if(!isFreezingTimeIncreased) {
                        freezingTime += deltaTime;
                        isFreezingTimeIncreased = true;
                    }
                }
            }
            if (gameGrid.getLowerBlockRelativeToObject(this) != null) {
                Block block = gameGrid.getLowerBlockRelativeToObject(this);
                if (freezingTime < (FREEZING_TIME * block.coefficientFrostbite)) {
                    block.idle();
                    if(!isFreezingTimeIncreased) {
                        freezingTime += deltaTime;
                        isFreezingTimeIncreased = true;
                    }
                }
            }
            if (gameGrid.getTopBlockRelativeToObject(this) != null) {
                Block block = gameGrid.getTopBlockRelativeToObject(this);
                if (freezingTime < (FREEZING_TIME * block.coefficientFrostbite)) {
                    block.idle();
                    if(!isFreezingTimeIncreased) {
                        freezingTime += deltaTime;
                        isFreezingTimeIncreased = true;
                    }
                }
            }
        }
        if((FREEZING_TIME - freezingTime) < 7f){
            if(textureRegionBlock == Assets.getInstance().getAssetBlock().getBlockSnow()){
            textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockWater();}
            else textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockSnow();
            setRegion(textureRegionBlock);
        }
    }

    public void setFreezingTime(float freezingTime){
        FREEZING_TIME = freezingTime;
    }
}
