package com.daleondeveloper.Game.tools.Checkers;

public class TimeLvlCondition extends LvlCondition {

    private int endTime;
    private float currentTime;

    public TimeLvlCondition(int time){
        this.endTime = time;
        currentTime = 0;
    }

    @Override
    public void checkCondition() {
        conditionFulfilled = currentTime >= endTime;
    }

    @Override
    public void update(float deltaTime) {
        currentTime += deltaTime;
        if(!conditionFulfilled){
            checkCondition();
        }

    }
}
