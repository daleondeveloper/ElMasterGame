package com.daleondeveloper;

import android.os.Bundle;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.daleondeveloper.Game.Ads.AdsController;
import com.daleondeveloper.Game.Ads.AdsShower;
import com.daleondeveloper.Game.Ads.AnaliticsController;
import com.daleondeveloper.Game.ElMaster;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.analytics.FirebaseAnalytics;

public class AndroidLauncher extends AndroidApplication implements AdsController, RewardedVideoAdListener, AnaliticsController {

	private RewardedVideoAd rewardedVideoAd;
	private FirebaseAnalytics fbAnalytics;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		fbAnalytics = FirebaseAnalytics.getInstance(this);
		levelUp(100);
		initialize(new ElMaster(this,this), config);

		rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
		rewardedVideoAd.setRewardedVideoAdListener(this);

			loadRewardedVideo();
	}

	@Override
	public void levelUp(int level) {
		Bundle bundle = new Bundle();
		bundle.putLong(FirebaseAnalytics.Param.LEVEL, level);
		fbAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_UP, bundle);
		fbAnalytics.toString();
	}

	@Override
	public void showRewardedVideo() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(rewardedVideoAd.isLoaded()){
					rewardedVideoAd.show();
				}else {
					loadRewardedVideo();
				}
			}
		});
	}

	@Override
	public void loadRewardedVideo() {
				rewardedVideoAd.loadAd("ca-app-pub-7212487374998214/3837973668",
						new AdRequest.Builder().build());

	}

	@Override
	public void onRewardedVideoAdLoaded() {
	}

	@Override
	public void onRewardedVideoAdOpened() {

	}

	@Override
	public void onRewardedVideoStarted() {

	}

	@Override
	public void onRewardedVideoAdClosed() {
		loadRewardedVideo();
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
//		Toast.makeText(this,"Reward" + rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
		AdsShower.getInstance().setAdsWatched(true);
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {

	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {

	}

	@Override
	public void onRewardedVideoCompleted() {

	}
}
