package com.daleondeveloper;

import android.os.Bundle;

import com.daleondeveloper.Game.Ads.AnaliticsController;
import com.google.firebase.analytics.FirebaseAnalytics;

public class AndroidAnalytics implements AnaliticsController {
    private FirebaseAnalytics mFirebaseAnalytics;

    public AndroidAnalytics(FirebaseAnalytics firebaseAnalytics){
        mFirebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public void tutorialBegin(int tutorialStage) {
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.VALUE, tutorialStage);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_BEGIN, bundle);
    }

    @Override
    public void tutorialEnd(int tutorialStage) {
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.VALUE, tutorialStage);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_COMPLETE, bundle);
    }

    @Override
    public void levelEnd(int level, long timelevelcompleted) {
        Bundle bundle = new Bundle();
        bundle.putLong(FirebaseAnalytics.Param.LEVEL, level);
        bundle.putLong(FirebaseAnalytics.Param.VALUE,timelevelcompleted);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END, bundle);
    }

    @Override
    public void levelStart(int level) {
        Bundle bundle = new Bundle();
        bundle.putLong(FirebaseAnalytics.Param.LEVEL, level);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_START, bundle);
    }
}
