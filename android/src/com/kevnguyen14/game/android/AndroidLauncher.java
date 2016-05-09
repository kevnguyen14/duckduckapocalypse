package com.kevnguyen14.game.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.GameHelper;
import com.kevnguyen14.game.IGoogleServices;
import com.kevnguyen14.game.duckgame;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices  {

	private final static int REQUEST_CODE_UNUSED = 9002;

	private GameHelper _gameHelper;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create the GameHelper.
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(true);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInSucceeded()
			{
			}

			@Override
			public void onSignInFailed()
			{
				Gdx.app.log("MainActivity", "I'm tyring to log in failed :(:)");
			}
		};

		_gameHelper.setup(gameHelperListener);

// The rest of your onCreate() code here...

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new duckgame(this), config);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		_gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		_gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void signIn() {
		Gdx.app.log("MainActivity", "I'm tyring to log in :)");
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}

	}

	@Override
	public void signOut() {
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					_gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}

	}

	@Override
	public void rateGame() {

	}

	@Override
	public void submitScore(long score) {
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_long_run), score);
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_long_run)), REQUEST_CODE_UNUSED);
		}
		else
		{
// Maybe sign in here then redirect to submitting score?
		}
	}

	@Override
	public void showScores() {
		if (isSignedIn() == true)
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_long_run)), REQUEST_CODE_UNUSED);
		else
		{
// Maybe sign in here then redirect to showing scores?
		}

	}

	@Override
	public boolean isSignedIn() {
		return _gameHelper.isSignedIn();
	}
}
