package com.androidtecknowlogy.directory.interfaces;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by nezspencer on 10/19/16.
 */

public interface InterfaceSignIn {

    /**called when signIn procedure is successful*/
    void logInWithFB();

    void loginWIthGoogle();

    void proceedToDirectoryHomeActivity(FirebaseUser user);

    void onSignInError(String errorMsg);


}
