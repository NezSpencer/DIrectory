package com.androidtecknowlogy.directory.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.androidtecknowlogy.directory.AppInstance;
import com.androidtecknowlogy.directory.R;
import com.androidtecknowlogy.directory.interfaces.InterfaceSignIn;
import com.androidtecknowlogy.directory.model.Constants;
import com.facebook.AccessToken;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by nezspencer on 10/19/16.
 */

public class SignInActivity extends AppCompatActivity implements InterfaceSignIn,GoogleApiClient.OnConnectionFailedListener {

    private LoginButton fbLoginButton;
    private SignInButton googleLoginButton;
    public static GoogleApiClient googleApiClient;
    private static final int GOOGLE_LOGIN_REQ=9001;


    private static SignInActivity signInActivity;




    @Override
    public void logInWithFB() {

    }

    @Override
    public void onSignInError(String errorMsg) {
        Snackbar.make(getWindow().getDecorView().getRootView(),errorMsg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loginWIthGoogle() {

    }

    @Override
    public void proceedToDirectoryHomeActivity(FirebaseUser user) {

        Intent intent=new Intent(this,DirectoryActivity.class);

        String name=user.getDisplayName();
        String email=user.getEmail();

        intent.putExtra(Constants.DISPLAY_NAME,name);
        intent.putExtra(Constants.EMAIL,email);

        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signInActivity=this;

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        fbLoginButton=(LoginButton)findViewById(R.id.fb_login_button);
        googleLoginButton=(SignInButton)findViewById(R.id.google_login_button);

        fbLoginButton.setReadPermissions("emailEditText","public_profile");
        //fbLoginButton.setLoginBehavior(LoginBehavior.DEVICE_AUTH);

        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppInstance.getSignInPresenter().onFbSignInClicked();
            }
        });

        googleLoginButton.setSize(SignInButton.SIZE_STANDARD);
        googleLoginButton.setColorScheme(SignInButton.COLOR_DARK);

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent googleSignInIntent= Auth.GoogleSignInApi.getSignInIntent(SignInActivity
                        .googleApiClient);
                startActivityForResult(googleSignInIntent,GOOGLE_LOGIN_REQ);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GOOGLE_LOGIN_REQ)
        {
            if (resultCode==RESULT_OK)
            {
                AppInstance.getSignInPresenter().onGoogleSignInClicked(data);
            }
            else {
                Toast.makeText(this,"error signing in",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            AppInstance.getAppInstance().getFbCallbackManager().onActivityResult(requestCode,
                    resultCode,data);
        }

    }

    public static SignInActivity getSignInActivity(){
        return signInActivity;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
