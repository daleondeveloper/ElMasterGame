package com.daleondeveloper.Screens.GUI.Button;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Screens.ListenerHelper;

public class ScrollArrowButtonLeft extends GameButton {

    private ScrollPane scrollPane;
    private  int scrollNumber;

    public ScrollArrowButtonLeft(ScrollPane scrollPane, int scrollNumber) {
        super(new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonLeft()));
        this.scrollPane = scrollPane;
        this.scrollNumber =scrollNumber;
    }

    @Override
    public void defineTexture() {

    }

    @Override
    public void addAction() {
        this.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                scrollPane.setScrollY(scrollPane.getVisualScrollY() - scrollNumber);
                scrollPane.updateVisualScroll();
            }
        }));
    }
}
