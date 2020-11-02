package com.example.btr490project;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.theartofdev.edmodo.cropper.CropImage;




import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btnImg;
    private Button btnUpload;
    private Button btnFile;
    public Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnImg = view.findViewById(R.id.imgButton);
        btnImg.setOnClickListener(this);
        btnUpload = view.findViewById(R.id.uploadButton);
        btnUpload.setOnClickListener(this);
        btnFile = view.findViewById(R.id.fileButton);
        btnFile.setOnClickListener(this);

    }

    // three buttons in side of home page

    @Override
    public void onClick(View v) {

        Fragment fragment = null;

        switch (v.getId()) {

            case R.id.imgButton:
                fragment = new ImageFragment();
                break;

            case R.id.uploadButton:
                CropImage.activity().start(getContext(), this);
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

