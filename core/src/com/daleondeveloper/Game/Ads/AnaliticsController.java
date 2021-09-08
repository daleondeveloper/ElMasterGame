package com.daleondeveloper.Game.Ads;

public interface AnaliticsController {
    public void levelEnd(int level,long timelevelcompleted);
    public void levelStart(int level);
    public void tutorialBegin(int tutorialStage);
    public void tutorialEnd(int tutorialStage);
}
