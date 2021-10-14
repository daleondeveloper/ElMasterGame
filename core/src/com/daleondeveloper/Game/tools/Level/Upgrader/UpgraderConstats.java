package com.daleondeveloper.Game.tools.Level.Upgrader;

public class UpgraderConstats {
    private static float BLOCK_SPEED = 0;
    private static float BLOCK_TIME_SPAWN = 0;
    private static int REVIVE_COUNT = 0;
    private static int BLOCK_COUNT_TO_DELETE = 0;

    public static void resetParameter(){
        BLOCK_SPEED = 0;
        BLOCK_TIME_SPAWN = 0;
        REVIVE_COUNT = 0;
        BLOCK_COUNT_TO_DELETE = 0;
    }

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

    public static int getReviveCount() {
        return REVIVE_COUNT;
    }

    public static void setReviveCount(int reviveCount) {
        REVIVE_COUNT += reviveCount;
    }

    public static int getBlockCountToDelete() {
        return BLOCK_COUNT_TO_DELETE;
    }

    public static void setBlockCountToDelete(int blockCountToDelete) {
        BLOCK_COUNT_TO_DELETE = blockCountToDelete;
    }
    public static String save() {
        return "<upgradeConst " +
                "  speed = \"" + getBlockSpeed() + "\"" +
                " time = \"" + getBlockTimeSpawn() + "\""+
                " countToDelete = \"" + getBlockCountToDelete() + "\""+
                " revive = \"" + getReviveCount() + "\""+
                "/>";
    }
}
