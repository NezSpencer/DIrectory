package com.androidtecknowlogy.directory.view;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.androidtecknowlogy.directory.AppInstance;
import com.androidtecknowlogy.directory.R;
import com.androidtecknowlogy.directory.model.Constants;
import com.androidtecknowlogy.directory.model.Person;
import com.androidtecknowlogy.directory.view.adapter.ProfileAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 12/8/16.
 */

public class ProfileActivity extends AppCompatActivity {

    @Bind(R.id.profile_viewpager) ViewPager viewPager;
    private ProfileAdapter adapter;
    public static Person person;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        person=new Person();
        if (getSupportActionBar() !=null)
            getSupportActionBar().hide();

        adapter=new ProfileAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        loadFragmentIntoAdapter();


    }

    /**This adds the given fragment to the pagerAdapter
     */
    public void loadFragmentIntoAdapter(){

        adapter.addFragment(new PersonalDetailsFragment(),"personal details");
        adapter.addFragment(new SchoolDetailsFragment(),"School details");
        adapter.addFragment(new HostelDetailsFragment(),"Hostel details");
        adapter.addFragment(new ChurchDetailsFragment(),"Church details");
        adapter.notifyDataSetChanged();
    }

    public void finishActivity(){
        finish();
    }

    public void submitProfileInfo()
    {
        DatabaseReference usersRef= AppInstance.getFirebaseDatabase().getReference();

        HashMap<String ,Object> userMap=person.toMap();
        HashMap<String, Object> refMap=new HashMap<>();
        refMap.put(Constants.KEY_ALL_USERS+"/"+modifyEmail(person.getEmailAddress()),person);
        refMap.put(Constants.KEY_FACULTY+"/"+person.getFaculty()
                +"/"+modifyEmail(person.getEmailAddress()),person);
        refMap.put(Constants.KEY_LITURGICAL+"/"+person.getLiturgicalGroup()+"/"+
                modifyEmail(person.getEmailAddress()),person);
        //refMap.put(Constants.KEY_SOCIETIES+"/"+person.getChurchSocieties(),person);
        refMap.put(Constants.KEY_LEVEL+"/"+person.getLevel()+"/"+
                modifyEmail(person.getEmailAddress()),person);
        refMap.put(Constants.KEY_BIRTH_INFO+"/"+person.getBirthMonth()+"/"
                +person.getBirthday() +"/"+modifyEmail(person.getEmailAddress()), person);
        if (person.isResidesInTown())
        {
            //user lives in town
            refMap.put(Constants.KEY_HOSTEL+"/"+Constants.KEY_TOWN_HOSTEL+"/"+
                    person.getHallOfResidence()+"/"+modifyEmail(person.getEmailAddress()),person);
        }
        else {
            //user lives in campus
            refMap.put(Constants.KEY_HOSTEL+"/"+Constants.KEY_SCHOOL_HOSTEL+"/"+
                    person.getHallOfResidence()+"/"+modifyEmail(person.getEmailAddress()), person);
        }

        for (String society: person.getChurchSocieties())
        {
            refMap.put(Constants.KEY_SOCIETIES+"/"+society+"/"+modifyEmail(person.getEmailAddress()),person);
        }
        usersRef.updateChildren(refMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError!=null)
                {
                    Toast.makeText(ProfileActivity.this,"Data saved",Toast.LENGTH_SHORT).show();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                            .edit().putBoolean(Constants.KEY_SHOW_PROFILE,false).apply();
                    finish();
                    startActivity(new Intent(ProfileActivity.this,DirectoryActivity.class));
                }

            }
        });

    }
    public String modifyEmail(String email)
    {
        return email.replace(".",",");
    }
}
