package com.androidtecknowlogy.directory.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidtecknowlogy.directory.AppInstance;
import com.androidtecknowlogy.directory.R;
import com.androidtecknowlogy.directory.interfaces.ProfilePictureListener;
import com.androidtecknowlogy.directory.model.Constants;
import com.androidtecknowlogy.directory.view.helper.OnSwipeListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nezspencer on 12/6/16.
 */

public class PersonalDetailsFragment extends Fragment implements DatePickerDialog
        .OnDateSetListener, ProfilePictureListener {

    @Bind(R.id.fname) EditText firstNameEditText;
    @Bind(R.id.lname) EditText lastNameEditText;
    @Bind(R.id.email) EditText emailEditText;
    @Bind(R.id.phone) EditText phoneNumberEditText;
    @Bind(R.id.profile_photo) CircleImageView photoView;
    @Bind(R.id.gallery_profile) ImageButton buttonPickPhoto;
    @Bind(R.id.date_picker) ImageButton buttonPickBirthDay;
    @Bind(R.id.gender_spinner) Spinner genderSpinner;
    @Bind(R.id.birth)EditText birthDayEditText;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String photoUrl;
    private String birthMonth;
    private String birthday;
    private String gender;


    private DatePickerDialog birthdayDialog;

    private static final int RC_PHOTO=231;

    private ProfileActivity profileActivity;

    String [] months={"January","February","March","April","May","June","July","August",
    "September","October","November","December"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_personal_details,container,false);
        ButterKnife.bind(this,view);
        setUpTextWatchers();
        setUpOnTouchListener(view);
        setUpGenderSpinner();
        initializeDateDialog();

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());


        email=preferences.getString(Constants.KEY_EMAIL,"Anonymous");
        String username=preferences.getString(Constants.KEY_USERNAME,null);
        lastName=username==null?"":username.split(" ")[0];
        firstName=username==null?"":username.split(" ")[1];
        photoUrl=preferences.getString(Constants.KEY_PHOTO,null);

        firstNameEditText.setText(firstName);
        lastNameEditText.setText(lastName);
        emailEditText.setText(email);

        ProfileActivity.person.setFirstName(firstName);
        ProfileActivity.person.setSurname(lastName);
        ProfileActivity.person.setEmailAddress(email);
        ProfileActivity.person.setPhotoUrl(photoUrl);

        Log.e("PhotoURL viewCreated ",photoUrl);

        if (photoUrl!=null)
        Glide.with(getActivity()).load(photoUrl).into(photoView);


        //TODO initialize datePickerDialog to pick only month and day

        return view;
    }


    public void initializeDateDialog(){
        final Calendar calendar=Calendar.getInstance();
        birthdayDialog=DatePickerDialog.newInstance(this,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    @OnClick(R.id.date_picker)
    public void openBirthDayPicker()
    {
        birthdayDialog.show(getActivity().getFragmentManager(),"dpd");

        /*birthdayDialog.findViewById(Resources.getSystem().getIdentifier("year","id",
                "android")).setVisibility(View.GONE);*/
    }

    public void setUpGenderSpinner(){
        genderSpinner.setPrompt("select");

        ArrayAdapter<CharSequence> genderAdapter=ArrayAdapter.createFromResource(getActivity(),R.array
                .gender,android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 1");
                gender =adapterView.getItemAtPosition(i).toString();
                ProfileActivity.person.setGender(gender);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setUpOnTouchListener(final View view)
    {
        view.setOnTouchListener(
                new OnSwipeListener(getActivity()){
                    @Override
                    public void onSwipeRight() {}

                    @Override
                    public void onSwipeLeft() {
                        goToNext();
                    }
                }
        );
    }

    /**textWatchers for the editTexts to monitor when inputs are being entered*/
    public void setUpTextWatchers(){

        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                firstName=charSequence.toString();
                ProfileActivity.person.setFirstName(firstName);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastName=charSequence.toString();
                ProfileActivity.person.setSurname(lastName);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email=charSequence.toString();
                ProfileActivity.person.setEmailAddress(email);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                phoneNumber=charSequence.toString();
                ProfileActivity.person.setPhoneNumber(phoneNumber);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void goToNext(){
        ProfileActivity.person.setFirstName(firstName);
        ProfileActivity.person.setSurname(lastName);
        ProfileActivity.person.setEmailAddress(email);
        ProfileActivity.person.setPhoneNumber(phoneNumber);
        ProfileActivity.person.setPhotoUrl(photoUrl);
        ProfileActivity.person.setBirthMonth(birthMonth);
        ProfileActivity.person.setBirthday(birthday);
        ProfileActivity.person.setGender(gender);

        Log.e("PhotoURL",photoUrl);
        Log.e("Fname ",firstName);
        Log.e("Lname ",lastName);
        Log.e("email ",email);
        Log.e("number ",phoneNumber);

        Log.e("Fname ",ProfileActivity.person.getFirstName());
        Log.e("Lname ",ProfileActivity.person.getSurname());



        //profileActivity.loadFragmentIntoAdapter(new SchoolDetailsFragment(),"School details");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        profileActivity=(ProfileActivity)activity;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Log.e("Month",""+monthOfYear);
        birthMonth=months[monthOfYear];
        birthday=""+dayOfMonth;

        birthDayEditText.setText(birthMonth+", "+birthday);
        ProfileActivity.person.setBirthMonth(birthMonth);
        ProfileActivity.person.setBirthday(birthday);
    }

    @Override
    public void onPause() {
        super.onPause();
        goToNext();
    }

    @Override
    public void onPictureUploadSuccessful(String pictureDownloadUrl) {

        firstName=firstNameEditText.getText().toString();
        lastName=lastNameEditText.getText().toString();
        email=emailEditText.getText().toString();
        phoneNumber=phoneNumberEditText.getText().toString();
        photoUrl=pictureDownloadUrl;

        ProfileActivity.person.setFirstName(firstName);
        ProfileActivity.person.setSurname(lastName);
        ProfileActivity.person.setEmailAddress(email);
        ProfileActivity.person.setPhoneNumber(phoneNumber);
        ProfileActivity.person.setPhotoUrl(photoUrl);
        ProfileActivity.person.setBirthMonth(birthMonth);
        ProfileActivity.person.setBirthday(birthday);

    }

    @Override
    public void onPictureUploadFailed() {

        Toast.makeText(getActivity(),"Photo upload failed. Retry",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_PHOTO)
        {
            if (resultCode ==getActivity().RESULT_OK)
            {
                Uri photoUri = data.getData();
                boolean isSizeLessThan1MB =checkImageSize(photoUri);

                if (!isSizeLessThan1MB)
                {
                    Toast.makeText(getActivity(),"Image size should not be more than 1MB",Toast
                            .LENGTH_LONG).show();
                }
                else {
                    StorageReference imageRef=AppInstance.getFirebaseStorage().getReference()
                            .child(Constants.KEY_PHOTO);
                    imageRef.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         Toast.makeText(getActivity(),"Picture Upload Successful",Toast.LENGTH_SHORT)
                                 .show();
                            if (taskSnapshot.getDownloadUrl()!=null)
                            photoUrl=taskSnapshot.getDownloadUrl().toString();
                            ProfileActivity.person.setPhotoUrl(photoUrl);
                            Glide.with(getActivity()).load(photoUrl).into(photoView);
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),"Image upload failed",Toast.LENGTH_SHORT)
                                            .show();
                                    Log.e("Image Upload Failed: ",e.getMessage());
                                }
                            });

                }
            }
        }
    }

    /**This checks the size of uploaded images to ensure it is at most 1MB*
     * @param imageUri uri of uploaded image
     * @author nezspencer
     */
    public boolean checkImageSize(Uri imageUri)
    {
        File file =new File(imageUri.getPath());
        long sizeInBytes=file.length();
        long sizeInMB=sizeInBytes/(1024*1024);

        return sizeInMB<=1;
    }


    @OnClick(R.id.gallery_profile)
    public void openGallery()
    {
        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent,RC_PHOTO);
    }


}
