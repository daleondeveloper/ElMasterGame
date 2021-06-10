package com.daleondeveloper.tools;

public class GameConstants {
    private static final String TAG = GameConstants.class.getName();

    //Константи ігрових режимів
    public static final int GAME_MODE_CLASSIC = 0;
    public static final int GAME_MODE_LIGHT = 1;
    public static final int GAME_MODE_SNOW = 2;
    public static final int GAME_MODE_FIRE = 3;
    public static final int GAME_MODE_WATER = 4;
    public static final int GAME_MODE_DARK = 5;
    public static final int GAME_MODE_SPECIAL = 6;

    //Константи блоків
    public static final int BLOCK_CLASSIC = 0;
    public static final int BLOCK_LIGHT = 1;
    public static final int BLOCK_SNOW = 2;
    public static final int BLOCK_FIRE = 3;
    public static final int BLOCK_WATER = 4;
    public static final int BLOCK_DARK = 5;

    //константи розмірів GUI елементі
    public static final float BUTTON_WIDTH = 252f;
    public static final float BUTTON_HEIGHT = 43f;
    public static final float BUTTON_ARROW_WIDTH = 50f;
    public static final float BUTTON_ARROW_HEIGHT = 58f;

    //константи розмірів світу
    public static final int WORLD_WIDTH_CELLS = 10;
    public static final int WORLD_HEIGHT_CELLS = 20;

    //Кількість пікселів у одній ігровій клітинці
    public static final int PIX_IN_CELL = 10;

    public static int getBlockTypeByName(String name){
        name = name.toLowerCase().trim();
        if(name.equals("default") || name.equals("classic")){
            return BLOCK_CLASSIC;
        }else if(name.equals("light")){
            return BLOCK_LIGHT;
        }else if(name.equals("snow")){
            return BLOCK_SNOW;
        }else if(name.equals("fire")){
            return BLOCK_FIRE;
        }else if(name.equals("water")){
            return BLOCK_WATER;
        }else if(name.equals("dark")){
            return BLOCK_DARK;
        }
        return -1;
    }
    public static int getBlockControllerTypeByName(String name){
        name = name.toLowerCase().trim();
        if(name.equals("default") || name.equals("classic")){
            return GAME_MODE_CLASSIC;
        }else if(name.equals("light")){
            return GAME_MODE_LIGHT;
        }else if(name.equals("snow")){
            return GAME_MODE_SNOW;
        }else if(name.equals("fire")){
            return GAME_MODE_FIRE;
        }else if(name.equals("water")){
            return GAME_MODE_WATER;
        }else if(name.equals("dark")){
            return GAME_MODE_DARK;
        }else if(name.equals("special")){
            return GAME_MODE_SPECIAL;
        }
        return -1;
    }
}
