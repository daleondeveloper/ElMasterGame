package com.daleondeveloper.Game.tools.Checkers;

import com.daleondeveloper.Game.Settings.GameSettings;

public class ScoreLvlCondition extends LvlCondition {

    private int endScore;
    private GameSettings prefs;

    public ScoreLvlCondition(int endScore) {
        prefs = GameSettings.getInstance();
        this.endScore = endScore;
    }

    @Override
    public void checkCondition() {
        conditionFulfilled = endScore <= prefs.getLastPlayScore();
    }

    @Override
    public void update(float deltaTime) {
        if(!conditionFulfilled)checkCondition();
    }

    @Override
    public String save() {
        String s = "<levelTasks " +
                "type = \"score\" value = \"" + endScore + "\"" +
                "/>\n";
        return s;
    }
}
