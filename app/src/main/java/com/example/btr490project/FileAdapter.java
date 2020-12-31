package com.example.btr490project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private Context mContext;
    private List<FileUpload> mUploads;


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

        final FileUpload uploadCurrent = mUploads.get(position);

        holder.fileName.setText(uploadCurrent.getFileName());
        holder.startDate.setText(uploadCurrent.getStartDate());
        holder.endDate.setText(uploadCurrent.getEndDate());

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
                                Toast.makeText(mContext, "delete coming soon!", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case R.id.file_share:
                                Toast.makeText(mContext, "share coming soon!", Toast.LENGTH_SHORT)
                                        .show();
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


    public class FileViewHolder extends RecyclerView.ViewHolder {

        public TextView buttonViewOption;
        public TextView fileName;
        public TextView startDate;
        public TextView endDate;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);

            buttonViewOption = (TextView) itemView.findViewById(R.id.file_textViewOptions);
            fileName = (TextView) itemView.findViewById(R.id.fileName_textView);
            startDate = (TextView) itemView.findViewById(R.id.start_textView);
            endDate = (TextView) itemView.findViewById(R.id.end_textView);
        }
    }
}
