package com.daleondeveloper.Game.tools.Level;

import com.daleondeveloper.Game.tools.Checkers.LvlCondition;

import java.util.ArrayList;

public class LvlEndConditionController {

    private ArrayList<LvlCondition> conditions;

    public LvlEndConditionController (){
        conditions = new ArrayList<LvlCondition>();
    }

    public void addCondition(LvlCondition lvlCondition){
        conditions.add(lvlCondition);
    }
    public boolean checkComplianceConditions(){
        for (LvlCondition lvlCondition : conditions){
            if(!lvlCondition.isConditionFulfilled()){
                return false;
            }
        }
        return true;
    }
    public void update(float deltaTime){
        for(LvlCondition lvlCondition : conditions){
            lvlCondition.update(deltaTime);
        }
    }
}
