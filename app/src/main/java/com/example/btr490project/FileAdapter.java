package com.example.btr490project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private Context mContext;
    private List<FileUpload> mUploads;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseReference;
    private OnItemClickListener mListener;


    public FileAdapter(Context context, List<FileUpload> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public FileAdapter.FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.file_item, parent, false);
        return new FileAdapter.FileViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final FileAdapter.FileViewHolder holder, final int position) {

        mStorage = FirebaseStorage.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Files");


        final FileUpload uploadCurrent = mUploads.get(position);

        holder.fileName.setText(uploadCurrent.getFileName());
        holder.startDate.setText(uploadCurrent.getStartDate());
        holder.endDate.setText(uploadCurrent.getEndDate());
        holder.fileStatus.setText(uploadCurrent.getFileStatus());

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.more_option_file);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.file_delete:

                                if (uploadCurrent.getFileUrl().equals("URL not associated")) {
                                    mDatabaseReference.child(uploadCurrent.getFileKey())
                                            .removeValue();
                                    Toast.makeText(mContext, "File Deleted", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    StorageReference fileRef = mStorage
                                            .getReferenceFromUrl(uploadCurrent.getFileUrl());
                                    fileRef.delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // if we removed the item from storage we remove it from database too.
                                                    mDatabaseReference
                                                            .child(uploadCurrent.getFileKey())
                                                            .removeValue();
                                                    Toast.makeText(mContext, "File Deleted", Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(mContext, "Deletion failed", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    });
                                }
                                break;
                            case R.id.file_share:
                                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                                if (SDK_INT > 8) {
                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                            .permitAll().build();
                                    StrictMode.setThreadPolicy(policy);

                                    if (uploadCurrent.getFileUrl()
                                            .equals("URL not associated")) {
                                        Toast.makeText(mContext, "File is empty", Toast.LENGTH_SHORT)
                                                .show();
                                    } else {

                                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                        shareIntent.setType("application/*");
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, uploadCurrent.getFileUrl());
                                        mContext.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                                    }
                                }
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {

        public TextView buttonViewOption;
        public TextView fileName;
        public TextView startDate;
        public TextView endDate;
        public TextView fileStatus;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);

            buttonViewOption = (TextView) itemView.findViewById(R.id.file_textViewOptions);
            fileName = (TextView) itemView.findViewById(R.id.fileName_textView);
            startDate = (TextView) itemView.findViewById(R.id.start_textView);
            endDate = (TextView) itemView.findViewById(R.id.end_textView);
            fileStatus = (TextView) itemView.findViewById(R.id.fileStatus);

            // when we click on each file
            // any part of it
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
