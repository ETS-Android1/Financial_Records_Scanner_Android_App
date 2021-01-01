package com.example.btr490project;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;


public class FileFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FileAdapter mAdapter;
    private DatabaseReference mDatabaseReference;
    private List<FileUpload> mUploads;
    private ProgressBar mProgressBar;
    private ArrayList<String> keysArray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.file_progress_circle);
        mRecyclerView = view.findViewById(R.id.recycler_view_file);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        keysArray = new ArrayList<>();
        mUploads = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Files");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FileUpload upload = postSnapshot.getValue(FileUpload.class);
                    // setting file ids when we getting them from fire base
                    upload.setFileKey(postSnapshot.getKey());

                    mUploads.add(upload);
                }

                mAdapter = new FileAdapter(getActivity(), mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(View.INVISIBLE);
                mAdapter.setOnItemClickListener(new FileAdapter.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onItemClick(final int position) {

                        if (mUploads.get(position).getFileStatus().equals(" ")) {
                            changeItemStatus(position, "Selected");
                            keysArray.add(mUploads.get(position).getFileKey());
                        } else {
                            changeItemStatus(position, " ");
                            keysArray.removeIf(new Predicate<String>() {
                                @Override
                                public boolean test(String n) {
                                    return (n.equals(mUploads.get(position).getFileKey()));
                                }
                            });
                        }

                        keysUpload k = new keysUpload(keysArray);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("SelectedFileKeys").setValue(k);
                    }

                });
            }

            public void changeItemStatus(int position, String text) {
                mDatabaseReference.child(mUploads.get(position).getFileKey()).child("fileStatus")
                        .setValue(text);

                mAdapter.notifyItemChanged(position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}

