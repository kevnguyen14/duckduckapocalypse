package com.kevnguyen14.game;

/**
 * Created by tony on 10/17/2015.
 */

/*
    Simulate login, will not show anything desktop
    For testing only.
 */
public class DesktopGoogleServices implements IGoogleServices {
    @Override
    public void signIn() {
        System.out.println("DesktopGoogleServices: signIn()");

    }

    @Override
    public void signOut() {

    }

    @Override
    public void rateGame() {

    }

    @Override
    public void submitScore(long score) {

    }

    @Override
    public void showScores() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }
}
