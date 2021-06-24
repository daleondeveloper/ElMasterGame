package com.daleondeveloper.Game.tools.Checkers;

public abstract class LvlCondition {
    private static final String TAG = LvlCondition.class.getName();

    protected boolean conditionFulfilled;

    public abstract void checkCondition();
    public abstract void update(float deltaTime);

    public boolean isConditionFulfilled() {
        return conditionFulfilled;
    }
}
