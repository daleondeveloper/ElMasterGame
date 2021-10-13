package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;

public class ReviveCountUpdate extends Upgrader {

    private int count;

    public ReviveCountUpdate(GameWorld gameWorld, int count) {
        super(gameWorld);
        this.count = count;
    }

    @Override
    protected void upgrade() {
        UpgraderConstats.setReviveCount(count);

    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        String s = "";
        if(count > 0){
            s = "Збільшити кількість \n воскресінь";
        }else if(count < 0){
            s = "Зменшити кількість воскресінь";
        }
        return info + s;
    }

}
