package com.kevnguyen14.game;

/**
 * Created by tony on 10/17/2015.
 */

/*
    interface required to access android properties
    to libgdx
 */

public interface IGoogleServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void submitScore(long score);
    public void showScores();
    public boolean isSignedIn();
}
