package com.example.btr490project;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;


import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    private TextView textViewName;
    private TextView textVIewEmail;
    private TextView textViewLogin;
    private ImageView pro_image_view;
    private FloatingActionButton pro_image_button;
    private DatabaseReference mDatabaseReference;
    private Uri imageUri;
    private String imageUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewName = view.findViewById(R.id.txt_name);
        textVIewEmail = view.findViewById(R.id.txt_email);
        textViewLogin = view.findViewById(R.id.txt_login_type);
        pro_image_view = view.findViewById(R.id.profile_image);
        pro_image_button = view.findViewById(R.id.pro_btn_image);

        // getting information of user from firebase and setting them to the elements in side of xml file
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                textViewName.setText(name);

                String email = dataSnapshot.child("Email").getValue().toString();
                textVIewEmail.setText(email);

                String loginType = dataSnapshot.child("login_type").getValue().toString();
                textViewLogin.setText(loginType);

                String imgUrl = dataSnapshot.child("profilePicUrl").getValue().toString();
                if (imgUrl != "default") {
                    Picasso.get().load(imgUrl).into(pro_image_view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // uploading image profile after clicking the image button
        pro_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(getContext(), ProfileFragment.this);
            }
        });

    }

    // after user picked his profile image
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                UploadImage();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    // uploading image profile
    private void UploadImage() {

        if (imageUri != null) {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference filePath = FirebaseStorage.getInstance().getReference("ProfileImages").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            StorageTask uploadTask = filePath.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    Uri downloadUri = task.getResult();
                    imageUrl = downloadUri.toString();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilePicUrl");


                    // adding image url and image category to image id
                    ref.setValue(imageUrl);

                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    // it returns type of image we are trying to send to firebase
    private String getFileExtension(Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(getActivity().getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters.
            // This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }
}
