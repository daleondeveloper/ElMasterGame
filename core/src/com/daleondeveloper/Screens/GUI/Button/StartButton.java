package com.daleondeveloper.Screens.GUI.Button;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.GUI.filler.TeacherMenuFiller;
import com.daleondeveloper.Screens.ListenerHelper;

public class StartButton extends GameButton {

    private MenuScreen menuScreen;
    private TeacherMenuFiller teacherMenuFiller;

    public StartButton(MenuScreen menuScreen,TeacherMenuFiller teacherMenuFiller) {
        super(new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonStart()));
        this.menuScreen = menuScreen;
        this.teacherMenuFiller = teacherMenuFiller;
    }

    @Override
    public void defineTexture() {

    }

    @Override
    public void addAction() {
        this.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(teacherMenuFiller.getTasksBoolean()) {
                    menuScreen.hideMenuScreen();
                }else {
                    teacherMenuFiller.tasksShow();
                }
            }
        }));
    }
}
