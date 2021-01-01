package com.example.btr490project;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;


import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment {

    public Uri imageUri;
    private BottomNavigationView bottomNavigationView;
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
        return inflater.inflate(R.layout.fragment_home, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.imgButton); // set image fragment selected

        // perform ItemSelectedListener
        bottomNavigationView
                .setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    Fragment fragment = new ImageFragment();

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        boolean selected = true;

                        switch (item.getItemId()) {
                            case R.id.imgButton:
                                fragment = new ImageFragment();
                                break;

                            case R.id.uploadButton:
                                imageSelect();
                                selected = false;
                                break;
                            case R.id.fileUploadButton:
                                selected = false;
                                fileInfoDialog();
                                break;

                            case R.id.fileButton:
                                fragment = new FileFragment();
                                break;
                        }
                        if (fragment != null) {
                            FragmentManager fm = getChildFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.fragment_place2, fragment).commit();
                        }
                        return selected;
                    }
                });

    }

    // responsible for uploading images
    public void imageSelect() {
        CropImage.activity().start(getContext(), this);
    }

    public void fileInfoDialog() {

        View view = getLayoutInflater().inflate(R.layout.file_info_adder_dialog, null);

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


        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view)
                .setCancelable(false).setPositiveButton("Create", null)
                .setNegativeButton("Cancel", null).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button buttonPositive = ((AlertDialog) dialog)
                        .getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // storing the data from inputs in variables
                        String txt_fileName = fileName.getText().toString().trim();

                        String txt_startMonth = startMonth.getText().toString().trim();
                        String txt_startDay = startDay.getText().toString().trim();
                        String txt_startYear = startYear.getText().toString().trim();

                        String txt_endMonth = endMonth.getText().toString().trim();
                        String txt_endDay = endDay.getText().toString().trim();
                        String txt_endYear = endYear.getText().toString().trim();

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
                            FileUpload fileInfo = new FileUpload(txt_fileName, startDate, endDate, fileCategory,"URL not associated");

                            // Id for each File inside of database
                            String fileId = ref.push().getKey();
                            // adding image info to file id
                            ref.child(fileId).setValue(fileInfo);
                            Toast.makeText(getActivity(), "File Information Uploaded", Toast.LENGTH_SHORT)
                                    .show();
                            alertDialog.dismiss();
                        }
                    }
                });

                Button buttonNegative = ((AlertDialog) dialog)
                        .getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                setImageUri(result.getUri());
                openDialog();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void openDialog() {
        ImageInfoDialog d = new ImageInfoDialog();
        d.setImageUri(imageUri);
        d.show(getFragmentManager(), "image dialog");
    }


    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

}

