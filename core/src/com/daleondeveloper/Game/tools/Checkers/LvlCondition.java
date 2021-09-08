package com.daleondeveloper.Game.tools.Checkers;

import com.daleondeveloper.Game.tools.Level.ElementSaved;

public abstract class LvlCondition implements ElementSaved {
    private static final String TAG = LvlCondition.class.getName();

    protected boolean conditionFulfilled;

    public abstract void checkCondition();
    public abstract void update(float deltaTime);

    public boolean isConditionFulfilled() {
        return conditionFulfilled;
    }
}
