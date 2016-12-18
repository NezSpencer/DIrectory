package com.androidtecknowlogy.directory.interfaces;

/**
 * Created by nezspencer on 12/3/16.
 */

public interface InterfaceDirectory {

    void onSignedIn(String displayName, String email, String photoUrl);

    void onSignedOut();

    void onSignInFailed();

    void onSignOutFailed();

    void signIn();

    void signOut();

}
