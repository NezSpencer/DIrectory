package com.androidtecknowlogy.directory;

import android.app.Application;

import com.androidtecknowlogy.directory.presenter.SignInPresenter;
import com.androidtecknowlogy.directory.view.SignInActivity;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Arrays;

/**
 * Created by nezspencer on 11/27/16.
 */

public class AppInstance extends Application {

    private CallbackManager fbCallbackManager;
    private static AppInstance appInstance;

    /*firebase variables*/
    private static FirebaseDatabase firebaseDatabase;
    private static FirebaseStorage firebaseStorage;
    private static FirebaseAnalytics firebaseAnalytics;
    private FirebaseAuth auth;
    public static FirebaseAuth.AuthStateListener appAuthStateListener;



    private static SignInPresenter signInPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        appInstance=this;



        setUpFirebase();
        signInPresenter=new SignInPresenter(SignInActivity.getSignInActivity());

        //fbCallbackManager=CallbackManager.Factory.create();

        /*LoginManager.getInstance().registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInPresenter.onFbSignInSuccess(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                signInPresenter.onSignInError(error);
            }
        });*/
    }

    public static AppInstance getAppInstance()
    {
        return appInstance;
    }

    public CallbackManager getFbCallbackManager(){
        return fbCallbackManager;
    }

    private void setUpFirebase(){
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAnalytics=FirebaseAnalytics.getInstance(this);
        firebaseStorage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();

    }

    public static FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public static FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    public static FirebaseAnalytics getFirebaseAnalytics() {
        return firebaseAnalytics;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void performFbLogin()
    {
        LoginManager.getInstance().logInWithReadPermissions(SignInActivity.getSignInActivity(),
                Arrays.asList("email","public_profile"));
    }

    public void performGoogleLogin(){

    }

    public static SignInPresenter getSignInPresenter()
    {
        if (signInPresenter==null)
        {
            throw new UnsupportedOperationException("Sign in presenter must be initialized first");
        }
        else return signInPresenter;

    }
}
