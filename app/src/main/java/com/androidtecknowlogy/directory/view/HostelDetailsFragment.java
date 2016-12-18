package com.androidtecknowlogy.directory.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.androidtecknowlogy.directory.R;
import com.androidtecknowlogy.directory.view.helper.OnSwipeListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 12/8/16.
 */

public class HostelDetailsFragment extends Fragment {

    @Bind(R.id.radio_school_hostel)RadioButton radioSchool;
    @Bind(R.id.radio_town_hostel)RadioButton radioTown;
    @Bind(R.id.hostel_ans)AppCompatAutoCompleteTextView schoolHostelList;
    @Bind(R.id.block_name_spinner)Spinner blockNameSpinner;
    @Bind(R.id.room_first_number)Spinner firstDigitOfRoomNumberSpinner;
    @Bind(R.id.room_second_number)Spinner secondDigitOfRoomNumberSpinner;
    @Bind(R.id.room_third_number)Spinner thirdDigitOfRoomNumberSpinner;
    @Bind(R.id.hostel_town_ans)TextInputEditText townHostel;
    @Bind(R.id.room_town_ans)TextInputEditText townRoom;

    String hostelName="";
    String block=null;
    String roomNumber="";

    boolean livesInTown;

    String firstDigitOfRoomNumber="";
    String secondDigitOfRoomNumber="";
    String thirdDigitOfRoomNumber="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_hostel_details,container,false);
        ButterKnife.bind(this,view);


        radioSchool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                schoolHostelList.setEnabled(b);
                blockNameSpinner.setEnabled(b);
                livesInTown=b;

                radioTown.setChecked(!b);


            }
        });

        radioTown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                townHostel.setEnabled(b);
                townRoom.setEnabled(b);
                livesInTown=b;

                radioSchool.setChecked(!b);
            }
        });

        setUpAdapters();
        setUpTouchListener(view);

        return view;
    }

    /**Adapters for the autocompleteTextViews are set up here*/
    public void setUpAdapters()
    {
        schoolHostelList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout
                .simple_dropdown_item_1line,getResources().getStringArray(R.array.hostel_name)));
        schoolHostelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 4");
                hostelName=adapterView.getItemAtPosition(i).toString();
                schoolHostelList.performCompletion();
                ProfileActivity.person.setHallOfResidence(hostelName);
                Log.e("checker",hostelName);
            }
        });

        ArrayAdapter<String> roomNumberAdapter=new ArrayAdapter<String>(getActivity(),android.R
                .layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.room_number));

        firstDigitOfRoomNumberSpinner.setAdapter(roomNumberAdapter);
        secondDigitOfRoomNumberSpinner.setAdapter(roomNumberAdapter);
        thirdDigitOfRoomNumberSpinner.setAdapter(roomNumberAdapter);

        blockNameSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout
                .simple_dropdown_item_1line,getResources().getStringArray(R.array.block_name)));

        firstDigitOfRoomNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 5");
                firstDigitOfRoomNumber=adapterView.getItemAtPosition(i).toString();
                roomNumber=firstDigitOfRoomNumber+secondDigitOfRoomNumber+thirdDigitOfRoomNumber;
                ProfileActivity.person.setRoomNumber(roomNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        secondDigitOfRoomNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 6");
                secondDigitOfRoomNumber=adapterView.getItemAtPosition(i).toString();
                roomNumber=firstDigitOfRoomNumber+secondDigitOfRoomNumber+thirdDigitOfRoomNumber;
                ProfileActivity.person.setRoomNumber(roomNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        thirdDigitOfRoomNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 7");
                thirdDigitOfRoomNumber=adapterView.getItemAtPosition(i).toString();
                roomNumber=firstDigitOfRoomNumber+secondDigitOfRoomNumber+thirdDigitOfRoomNumber;
                ProfileActivity.person.setRoomNumber(roomNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        blockNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 8");
                block="Block "+adapterView.getItemAtPosition(i).toString();
                ProfileActivity.person.setBlockName_RoomNumber(block+"_"+roomNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        goToNext();
    }

    public void goToNext()
    {
        ProfileActivity.person.setResidesInTown(livesInTown);

        if (!livesInTown)
        {
            ProfileActivity.person.setHallOfResidence(hostelName);
            ProfileActivity.person.setBlockName_RoomNumber(block+"_"+roomNumber);
            ProfileActivity.person.setRoomNumber(roomNumber);

        }
        else {
            ProfileActivity.person.setHallOfResidence(townHostel.getText().toString());
            ProfileActivity.person.setBlockName_RoomNumber(null);
            ProfileActivity.person.setRoomNumber(townRoom.getText().toString());
        }


    }

    public void setUpTouchListener(View view)
    {
        view.setOnTouchListener(new OnSwipeListener(getActivity()){
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {

                goToNext();
            }
        });
    }


}
