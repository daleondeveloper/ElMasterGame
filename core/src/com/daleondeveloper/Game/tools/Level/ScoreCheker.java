package com.daleondeveloper.Game.tools.Level;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderDirector;
import com.daleondeveloper.Screens.Play.PlayScreen;

import java.util.Stack;

public class ScoreCheker {

    private GameWorld gameWorld;
    private PlayScreen playScreen;
    private UpgraderDirector upgraderDirector;

    private Stack<Upgrader> scoreStack;
    private FileHandle currentLevelXml;
    private XmlReader.Element xmlLevel;
    private int score;
    private String type;

    public ScoreCheker(PlayScreen playScreen,GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.playScreen = playScreen;
        upgraderDirector = new UpgraderDirector(gameWorld);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            currentLevelXml = Gdx.files.internal("levels/levelUpgrade.xml");
        } else {
            currentLevelXml = Gdx.files.internal(System.getProperty("user.dir") + "/levels/levelUpgrade.xml"); // хак для desktop проекта, так как он почему-то не видел этих файлов. Создайте символическую ссылку папки assets в в корне desktop-проекта на папку assets android-проекта
        }
        xmlLevel = new XmlReader().parse(currentLevelXml);
        getNewChecker();
    }

    public void getNewChecker(){
        XmlReader.Element checker = xmlLevel.getChildByName("level" + gameWorld.getLevel());
        score = checker.getIntAttribute("score");
        type = checker.getAttribute("type");
        upgraderDirector.updateBuilders();
    }

    public void checkScore(int score){
        if(score > this.score) {
            playScreen.upgradeLevel();
        }
    }
    public Upgrader getUpgrader(){
            return upgraderDirector.buildUpgrader(type);
    }
}
