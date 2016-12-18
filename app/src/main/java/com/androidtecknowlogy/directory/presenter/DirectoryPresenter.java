package com.androidtecknowlogy.directory.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidtecknowlogy.directory.AppInstance;
import com.androidtecknowlogy.directory.interfaces.InterfaceDirectory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by nezspencer on 12/3/16.
 */

public class DirectoryPresenter implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private static InterfaceDirectory mainInterface;

    public DirectoryPresenter(InterfaceDirectory interfaceMainActivity) {
        mainInterface=interfaceMainActivity;
    }

    public void signInUser()
    {
        mainInterface.signIn();
    }

    public void signOutUser(){
        mainInterface.signOut();
    }

    public void createAuthStateListener()
    {
        AppInstance.appAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =firebaseAuth.getCurrentUser();

                if (user !=null)
                {
                    String name=user.getDisplayName();
                    String email=user.getEmail();
                    String photoUrl=user.getPhotoUrl()==null? null: user.getPhotoUrl().toString();


                    mainInterface.onSignedIn(name,email,photoUrl);

                }


                else {
                    mainInterface.onSignedOut();
                    signInUser();
                }
            }
        };
    }

    public void addAuthStateListener(){
     AppInstance.getAppInstance().getAuth().addAuthStateListener(AppInstance.appAuthStateListener);
    }

    public void removeAuthStateListener(){
        AppInstance.getAppInstance().getAuth().removeAuthStateListener(AppInstance.appAuthStateListener);
    }

    public void getUserBirthDayAndGender(){

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
