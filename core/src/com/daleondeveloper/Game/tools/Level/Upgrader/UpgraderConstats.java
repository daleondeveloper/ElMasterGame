package com.daleondeveloper.Game.tools.Level.Upgrader;

public class UpgraderConstats {
    private static float BLOCK_SPEED = 0;
    private static float BLOCK_TIME_SPAWN = 0;


    public static float getBlockSpeed() {
        return BLOCK_SPEED;
    }

    public static void setBlockSpeed(float blockSpeed) {
        BLOCK_SPEED += blockSpeed;
    }

    public static float getBlockTimeSpawn() {
        return BLOCK_TIME_SPAWN;
    }

    public static void setBlockTimeSpawn(float blockTimeSpawn) {
        BLOCK_TIME_SPAWN += blockTimeSpawn;
    }
}
