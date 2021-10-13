package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;

public class BlockCountToDeleteLine extends Upgrader {

    private int count;

    public BlockCountToDeleteLine(GameWorld gameWorld, int count) {
        super(gameWorld);
        this.count = count;
    }

    @Override
    protected void upgrade() {
        if(UpgraderConstats.getBlockCountToDelete() > -1 &&
        UpgraderConstats.getBlockCountToDelete() < 7) {
            UpgraderConstats.setBlockCountToDelete(count);
        }

    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        return info + "Змінити кількість блоків для стирання";
    }

}
