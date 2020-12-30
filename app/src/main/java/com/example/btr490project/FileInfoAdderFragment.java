package com.example.btr490project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FileInfoAdderFragment extends Fragment {

    private EditText fileName;
    private EditText startYear;
    private EditText startMonth;
    private EditText startDay;
    private EditText endYear;
    private EditText endMonth;
    private EditText endDay;
    private Spinner fileSpinner;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Files");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_info_adder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initializing file category
        fileSpinner = view.findViewById(R.id.spinner_fileCategory);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileSpinner.setAdapter(arrayAdapter);

        // getting info from user interface
        fileName = view.findViewById(R.id.fileName_input);
        startYear = view.findViewById(R.id.year_input);
        startMonth = view.findViewById(R.id.month_input);
        startDay = view.findViewById(R.id.day_input);
        endYear = view.findViewById(R.id.year_input2);
        endMonth = view.findViewById(R.id.month_input2);
        endDay = view.findViewById(R.id.day_input2);
        Button sendInfoBtn = view.findViewById(R.id.sendBtn);

        sendInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // storing the data from inputs in variables
                String txt_fileName = fileName.getText().toString();

                String txt_startMonth = startMonth.getText().toString();
                String txt_startDay = startDay.getText().toString();
                String txt_startYear = startYear.getText().toString();

                String txt_endMonth = endMonth.getText().toString();
                String txt_endDay = endDay.getText().toString();
                String txt_endYear = endYear.getText().toString();

                String fileCategory = fileSpinner.getSelectedItem().toString();

                // validating inputs
                if (TextUtils.isEmpty(txt_fileName)) {
                    fileName.setError("Please enter your file name");
                    fileName.requestFocus();
                } else if (TextUtils.isEmpty(txt_startMonth)) {
                    startMonth.setError("set the start month");
                    startMonth.requestFocus();
                } else if (TextUtils.isEmpty(txt_startDay)) {
                    startDay.setError("set the start day");
                    startDay.requestFocus();
                } else if (TextUtils.isEmpty(txt_startYear)) {
                    startYear.setError("set the start Year");
                    startYear.requestFocus();
                } else if (TextUtils.isEmpty(txt_endMonth)) {
                    endMonth.setError("set the start month");
                    endMonth.requestFocus();
                } else if (TextUtils.isEmpty(txt_endDay)) {
                    endDay.setError("set the start day");
                    endDay.requestFocus();
                } else if (TextUtils.isEmpty(txt_endYear)) {
                    endYear.setError("set the start Year");
                    endYear.requestFocus();
                } else {
                    String startDate = txt_startMonth + "/" + txt_startDay + "/" + txt_startYear;
                    String endDate = txt_endMonth + "/" + txt_endDay + "/" + txt_endYear;
                    FileUpload fileInfo = new FileUpload(txt_fileName, startDate, endDate, fileCategory);

                    // Id for each File inside of database
                    String fileId = ref.push().getKey();
                    // adding image info to file id
                    ref.child(fileId).setValue(fileInfo);

                    Toast.makeText(getActivity(), "File Information Uploaded", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


    }
}