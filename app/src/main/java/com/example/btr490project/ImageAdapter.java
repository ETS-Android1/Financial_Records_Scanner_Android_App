package com.example.btr490project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<ImageUpload> mUploads;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseReference;

    public ImageAdapter(Context context, List<ImageUpload> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final ImageUpload uploadCurrent = mUploads.get(position);
        Picasso.get().load(uploadCurrent.getImageUrl()).placeholder(R.drawable.ic_image_black_24dp)
                .fit().centerCrop().into(holder.imageView);


        mStorage = FirebaseStorage.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Images");

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.more_option_img);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()) {
                            case R.id.image_delete:
                                StorageReference imageRef = mStorage
                                        .getReferenceFromUrl(uploadCurrent.getImageUrl());
                                imageRef.delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // if we removed the item from storage we remove it from database too.
                                                mDatabaseReference
                                                        .child(uploadCurrent.getImageKey())
                                                        .removeValue();
                                                Toast.makeText(mContext, "Image Deleted", Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(mContext, "Delectation failed", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });
                                break;
                            case R.id.image_scan:
                                // Create Json Body Requests
                                JSONObject jsonBody = new JSONObject();
                                JSONObject data = new JSONObject();

                                try {
                                    jsonBody.put("json_ID", "data_test.json");
                                    jsonBody.put("spreadsheet_ID", "spreadsheet1");
                                    jsonBody.put("image_ID", uploadCurrent.getImageName());
                                    data.put("Image_Request_1", jsonBody);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                scanImageAPI(data);
                                break;

                            case R.id.image_share:
                                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                                if (SDK_INT > 8) {
                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                            .permitAll().build();
                                    StrictMode.setThreadPolicy(policy);

                                    try {

                                        URL url = new URL(uploadCurrent.getImageUrl());
                                        Bitmap bitmap = BitmapFactory
                                                .decodeStream(url.openConnection()
                                                                      .getInputStream());

                                        File file = new File(mContext.getExternalCacheDir(), File.separator + uploadCurrent
                                                .getImageName());
                                        FileOutputStream fOut = new FileOutputStream(file);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                                        fOut.flush();
                                        fOut.close();
                                        file.setReadable(true, false);

                                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Uri photoURI = FileProvider
                                                .getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", file);
                                        intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        intent.setType("image/*"); // set type of the image
                                        mContext.startActivity(Intent.createChooser(intent, "Share image via")); // title of share window

                                    } catch (IOException e) {
                                        System.out.println(e);
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


    private void scanImageAPI(final JSONObject requestBody) {
        final RequestQueue queue = Volley.newRequestQueue(mContext);
        final String url = "http://192.168.0.15:8080/process_images";
        final String requestBodyString = requestBody.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                final String apiResponse = ("Response is: " + response);
                Toast.makeText(mContext, apiResponse, Toast.LENGTH_LONG).show();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Request Connection Failed", Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                try {
                    return requestBodyString == null ? null : requestBodyString.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog
                            .wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBodyString, "utf-8");
                    return null;
                }

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences preferences = mContext
                        .getSharedPreferences("key_preferences", Context.MODE_PRIVATE);
                headers.put("Authorization", preferences.getString("API_KEY", "NULL"));
                return headers;
            }

        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView buttonViewOption;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view_upload);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);

        }

    }

}
