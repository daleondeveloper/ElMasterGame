package com.daleondeveloper;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.daleondeveloper.Game.Ads.AdsController;
import com.daleondeveloper.Game.ElMaster;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements AdsController, RewardedVideoAdListener {

	private RewardedVideoAd rewardedVideoAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new ElMaster(this), config);

		rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
		rewardedVideoAd.setRewardedVideoAdListener(this);

		loadRewardedVideo();
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
		rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
				new AdRequest.Builder().build());
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		Toast.makeText(this,"Ad Loaded",Toast.LENGTH_SHORT).show();
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
		Toast.makeText(this,"Reward" + rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
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
