package com.androidtecknowlogy.directory.view;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtecknowlogy.directory.R;
import com.androidtecknowlogy.directory.interfaces.InterfaceDirectory;
import com.androidtecknowlogy.directory.model.Constants;
import com.androidtecknowlogy.directory.presenter.DirectoryPresenter;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.Scopes;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nezspencer on 11/27/16.
 */

public class DirectoryActivity extends AppCompatActivity implements InterfaceDirectory,
        NavigationView.OnNavigationItemSelectedListener {

    private DirectoryPresenter presenter;
    private static String mUsername;
    private static String mEmail;
    private static String mPhotoUrl;
    private static final int RC_AUTH=9010;

    private CircleImageView photoView;
    private TextView emailView;
    private TextView nameView;
    @Bind(R.id.app_drawer) DrawerLayout drawerLayout;
    @Bind(R.id.nav_drawer) NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        ButterKnife.bind(this);

        if (getSupportActionBar() !=null)
            getSupportActionBar().show();

        photoView =(CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.user_image);
        emailView=(TextView)navigationView.getHeaderView(0).findViewById(R.id.user_email);
        nameView=(TextView)navigationView.getHeaderView(0).findViewById(R.id.username);
        presenter=new DirectoryPresenter(this);
        presenter.createAuthStateListener();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string
                .navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_profile:
                Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();

                break;
            case R.id.nav_notification:
                Toast.makeText(this,"notification",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_search:
                Toast.makeText(this,"search",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show();
                signOut();
                break;

            case R.id.nav_settings:
                Toast.makeText(this,"settings",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onSignedIn(String displayName, String email, String photoUrl) {

        mUsername=displayName;
        mEmail=email;

        emailView.setText(mEmail);
        nameView.setText(mUsername);
        if (photoUrl!=null)
        {
            mPhotoUrl=photoUrl;
            Glide.with(this).load(mPhotoUrl).into(photoView);
        }

        boolean showProfile= PreferenceManager.getDefaultSharedPreferences(this).getBoolean
                (Constants.KEY_SHOW_PROFILE,true);

        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString(Constants.KEY_EMAIL,mEmail)
                .putString(Constants.KEY_USERNAME,mUsername)
                .putString(Constants.KEY_PHOTO,mPhotoUrl)
                .apply();

        if (showProfile)
        {

            startActivity(new Intent(this,ProfileActivity.class));
        }


    }

    @Override
    public void onSignedOut() {

        mEmail=null;
        mUsername=null;

    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignOutFailed() {

    }

    @Override
    public void signIn() {

        AuthUI.IdpConfig facebookIdp = new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER)
                .setPermissions(Arrays.asList("email","public_profile","user_birthday"))
                .build();

        AuthUI.IdpConfig googleIdp=new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER)
                .setPermissions(Arrays.asList(Scopes.PLUS_ME,Scopes.PLUS_LOGIN))
                .build();

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setIsSmartLockEnabled(false)
                .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                        googleIdp,
                        facebookIdp)
                        )
                .setTheme(R.style.AppTheme)
                .build(),RC_AUTH);
    }

    @Override
    public void signOut() {
        AuthUI.getInstance().signOut(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.addAuthStateListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.removeAuthStateListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_AUTH)
        {
            if (resultCode == RESULT_OK)
            {

            }

            else if (resultCode ==RESULT_CANCELED)
            {
                Toast.makeText(DirectoryActivity.this,"Sign in cancelled!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
