package com.androidtecknowlogy.directory.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.androidtecknowlogy.directory.AppInstance;
import com.androidtecknowlogy.directory.interfaces.InterfaceSignIn;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by nezspencer on 10/19/16.
 */

public class SignInPresenter {

    private InterfaceSignIn interfaceSignIn;

    public SignInPresenter(InterfaceSignIn signIn){

        interfaceSignIn=signIn;
    }

    /*called when user signs in using google signIn*/
    public void onGoogleSignInClicked(Intent data){

        GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess())
        {
            onGoogleSignInSuccess(result.getSignInAccount());
        }
    }

    /*called when the user uses a Facebook signIn*/
    public void onFbSignInClicked(){

        AppInstance.getAppInstance().performFbLogin();
    }

    public void onFbSignInSuccess(LoginResult loginResult){

        AccessToken token=loginResult.getAccessToken();

        AppInstance.getAppInstance().getAuth().signInWithCredential(FacebookAuthProvider
                .getCredential(token.getToken())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    //fb sign in successful
                    FirebaseUser user=task.getResult().getUser();
                    interfaceSignIn.proceedToDirectoryHomeActivity(user);
                }

                else {
                    //sign in not successful

                }
            }
        });
    }

    public void onGoogleSignInSuccess(GoogleSignInAccount account)
    {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        AppInstance.getAppInstance().getAuth().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user=task.getResult().getUser();
                            interfaceSignIn.proceedToDirectoryHomeActivity(user);
                        }
                        else {

                        }
                    }
                });
    }

    public void onSignInError(FacebookException e){
        interfaceSignIn.onSignInError(e.getMessage());
    }

    public void onSignInError(String error){

    }


}
