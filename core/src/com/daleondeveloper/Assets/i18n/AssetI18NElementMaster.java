package com.daleondeveloper.Assets.i18n;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.I18NBundle;

public class AssetI18NElementMaster {
    private  static final String TAG = AssetI18NElementMaster.class.getName();

    private I18NBundle i18NElmasterBundle;

    public AssetI18NElementMaster(AssetManager assetManager){
        i18NElmasterBundle = assetManager.get("i18n/I18NElMasterBundle", I18NBundle.class);
    }

    public I18NBundle getI18NElmasterBundle() {
        return i18NElmasterBundle;
    }
}
