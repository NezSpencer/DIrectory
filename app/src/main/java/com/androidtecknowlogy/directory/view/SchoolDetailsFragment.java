package com.androidtecknowlogy.directory.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidtecknowlogy.directory.R;
import com.androidtecknowlogy.directory.view.helper.OnSwipeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 12/8/16.
 */

public class SchoolDetailsFragment extends Fragment {

    @Bind(R.id.level_ans)Spinner levelSpinner;
    @Bind(R.id.faculty_ans) Spinner facultySpinner;
    @Bind(R.id.dept_ans)Spinner deptSpinner;

    String faculty="";
    String dept="";
    String level="";
    List<String> depts;
    private ArrayAdapter<String> deptAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_school_detail,container,false);
        ButterKnife.bind(this,view);
        depts=new ArrayList<String >();
        setUpSwipeListener(view);
        setUpAdapters();

        return view;
    }

    public void setUpSwipeListener(View view){
        view.setOnTouchListener(new OnSwipeListener(getActivity()){
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                saveSchoolDetails();

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        saveSchoolDetails();
    }

    public void saveSchoolDetails()
    {
        ProfileActivity.person.setFaculty(faculty);
        ProfileActivity.person.setDepartment(dept);
        ProfileActivity.person.setLevel(level);

        Toast.makeText(getActivity(),"Saved",Toast.LENGTH_SHORT).show();
    }



    public void setUpAdapters() {

        levelSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout
                .simple_dropdown_item_1line,getResources().getStringArray(R.array.level)));

        deptAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout
                .simple_dropdown_item_1line,depts);
        deptSpinner.setAdapter(deptAdapter);

        facultySpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout
                .simple_dropdown_item_1line, getResources().getStringArray(R.array.faculty)));

        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 2");
                faculty = adapterView.getItemAtPosition(i).toString();
                ProfileActivity.person.setFaculty(faculty);


                switch (faculty) {
                    case "Administration":
                        depts = Arrays.asList(getResources().getStringArray(R.array.administration));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.administration)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Agriculture":
                        depts = Arrays.asList(getResources().getStringArray(R.array.agriculture));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.agriculture)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Arts":
                        depts =Arrays.asList(getResources().getStringArray(R.array.arts));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.arts)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Education":
                        depts = Arrays.asList(getResources().getStringArray(R.array.education));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.education)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Environmental Design and Management":
                        depts = Arrays.asList(getResources().getStringArray(R.array.environmental_design_management));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.environmental_design_management)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Basic Medical Sciences":
                        depts = Arrays.asList(getResources().getStringArray(R.array.basic_medical_sciences));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.basic_medical_sciences)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Clinical Sciences":
                        depts = Arrays.asList(getResources().getStringArray(R.array.clinical_sciences));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.clinical_sciences)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Dentistry":
                        depts = Arrays.asList(getResources().getStringArray(R.array.dentistry));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.dentistry)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Law":
                        depts = Arrays.asList(getResources().getStringArray(R.array.law));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.law)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Pharmacy":
                        depts = Arrays.asList(getResources().getStringArray(R.array.pharmacy));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.pharmacy)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Sciences":
                        depts = Arrays.asList(getResources().getStringArray(R.array.sciences));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.sciences)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Technology":
                        depts = Arrays.asList(getResources().getStringArray(R.array.technology));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.technology)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    case "Social Sciences":
                        depts = Arrays.asList(getResources().getStringArray(R.array.social_science));
                        deptAdapter.clear();
                        deptAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.social_science)));
                        deptAdapter.notifyDataSetChanged();
                        break;
                    default:
                        depts=new ArrayList<String>();
                        deptAdapter.clear();
                        deptAdapter.addAll(dept);
                        deptAdapter.notifyDataSetChanged();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 3");
                dept=adapterView.getItemAtPosition(i)==null?"":adapterView.getItemAtPosition(i).toString();
                ProfileActivity.person.setDepartment(dept);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("checker"," 3");
                level=adapterView.getItemAtPosition(i).toString();
                ProfileActivity.person.setLevel(level);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



}
