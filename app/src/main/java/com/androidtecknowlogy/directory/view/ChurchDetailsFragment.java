package com.androidtecknowlogy.directory.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidtecknowlogy.directory.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by nezspencer on 12/8/16.
 */

public class ChurchDetailsFragment extends Fragment {

    @Bind(R.id.radio_liturgical)RadioGroup radioLitugicalGrp;
    @Bind(R.id.welfare)CheckBox welfareCheckbox;
    @Bind(R.id.publicity)CheckBox publicityCheckbox;
    @Bind(R.id.rock)CheckBox rockTheatreCheckbox;
    @Bind(R.id.crusader)CheckBox crusaderCheckbox;
    @Bind(R.id.academics)CheckBox academicsCheckbox;
    @Bind(R.id.fab_submit)FloatingActionButton submitFab;


    String liturgicalGrp="";
    List<String> societyList;

    private ProfileActivity profileActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_church_details,container,false);
        ButterKnife.bind(this,view);
        societyList=new ArrayList<>();

        ProfileActivity.person.setLiturgicalGroup("None");
        radioLitugicalGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button=(RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId());
                liturgicalGrp=button.getText().toString();
                ProfileActivity.person.setLiturgicalGroup(liturgicalGrp);
                Log.e("Checker ",liturgicalGrp);
            }
        });

        return view;
    }

    @OnClick(R.id.fab_submit)
    public void submitDetails()
    {
        Log.e("details"," "+(ProfileActivity.person==null));
        Log.e("details",ProfileActivity.person.getFirstName());
        Log.e("details",ProfileActivity.person.getSurname());
        Log.e("details",ProfileActivity.person.getEmailAddress());
        Log.e("details",ProfileActivity.person.getGender());
        Log.e("details",ProfileActivity.person.getBirthMonth());
        Log.e("details",ProfileActivity.person.getBirthday());
        Log.e("details",ProfileActivity.person.getFaculty());
        Log.e("details",ProfileActivity.person.getDepartment());
        Log.e("details",ProfileActivity.person.getLevel());
        Log.e("details",ProfileActivity.person.getHallOfResidence());
        Log.e("details",ProfileActivity.person.getBlockName_RoomNumber());
        Log.e("details",ProfileActivity.person.getRoomNumber());
        Log.e("details",ProfileActivity.person.getLiturgicalGroup());
        Log.e("details",""+(ProfileActivity.person.getChurchSocieties()==null));
        //Log.e("details",ProfileActivity.person.getPhoneNumber());


        if (!TextUtils.isEmpty(ProfileActivity.person.getFirstName())
                && !TextUtils.isEmpty(ProfileActivity.person.getSurname())
                && !TextUtils.isEmpty(ProfileActivity.person.getEmailAddress())
                && !TextUtils.isEmpty((ProfileActivity.person.getPhoneNumber()))
                && !TextUtils.isEmpty(ProfileActivity.person.getFaculty())
                && !TextUtils.isEmpty(ProfileActivity.person.getDepartment())
                && !TextUtils.isEmpty(ProfileActivity.person.getLevel())
                && !TextUtils.isEmpty(ProfileActivity.person.getGender())
                && !TextUtils.isEmpty(ProfileActivity.person.getBirthMonth())
                && !TextUtils.isEmpty(ProfileActivity.person.getBirthday())
                && !TextUtils.isEmpty(ProfileActivity.person.getHallOfResidence()))
        {
            if (ProfileActivity.person.isResidesInTown())
            {
                //stays in town
                if (!TextUtils.isEmpty(ProfileActivity.person.getRoomNumber()))
                {
                    //save details
                    getSocietiesSelected();
                    profileActivity.submitProfileInfo();


                }
                else {
                    Log.e("details"," here 1");
                    Snackbar.make(this.getView(),"Complete all details",Snackbar.LENGTH_SHORT).show();}
            }

            else {
                //stays in school
                if (!TextUtils.isEmpty(ProfileActivity.person.getBlockName_RoomNumber())
                        && !TextUtils.isEmpty(ProfileActivity.person.getRoomNumber()))
                {
                    //save details
                    getSocietiesSelected();
                    profileActivity.submitProfileInfo();
                }
                else {
                    Log.e("details"," here 2");
                    Snackbar.make(this.getView(),"Complete all details",Snackbar.LENGTH_SHORT)
                        .show();}
            }
        }

        else {
            Log.e("details"," here 3");
            Snackbar.make(this.getView(),"Complete all details",Snackbar.LENGTH_SHORT).show();}
    }

    public void getSocietiesSelected(){
        societyList.clear();
        if (welfareCheckbox.isChecked())
            societyList.add(welfareCheckbox.getText().toString());

        if (publicityCheckbox.isChecked())
            societyList.add(publicityCheckbox.getText().toString());

        if (rockTheatreCheckbox.isChecked())
            societyList.add(rockTheatreCheckbox.getText().toString());

        if (crusaderCheckbox.isChecked())
            societyList.add(crusaderCheckbox.getText().toString());

        if (academicsCheckbox.isChecked())
            societyList.add(academicsCheckbox.getText().toString());

        ProfileActivity.person.setChurchSocieties(societyList);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        profileActivity=(ProfileActivity)activity;
    }

}
