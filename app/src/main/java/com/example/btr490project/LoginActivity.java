package com.example.btr490project;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 0;
    private CallbackManager mCallbackManager;
    private LoginButton fb;
    private static final String TAG = "FacebookAuthentication";
    private String FEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.loginButton);
        Button btnGoogle = findViewById(R.id.googleButton);
        Button btnFaceBook = findViewById(R.id.facebookButton);
        TextView textRegister = findViewById(R.id.textViewRegister);
        fb = findViewById(R.id.fb_Button);
        mAuth = FirebaseAuth.getInstance();

        //############## Sign In with Email and Password ####################

        //After clicking the login button it checks the fields
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email)) {
                    email.setError("Please enter your email");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(txt_password)) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(txt_email) && TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Password must be more than 6 digits", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_email, txt_password);
                }

            }
        });

        //############## Google Sign In ####################

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        //############## FaceBook Sign In ####################

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        fb.setReadPermissions(Arrays.asList("email", "public_profile"));
        fb.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFaceBookToken(loginResult.getAccessToken());

                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            FEmail = object.getString("email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError" + error);
            }


        });

        btnFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // click on button is just like clicking on other button
                fb.performClick();
            }
        });

        // Send user to registration page
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

    }

    private void handleFaceBookToken(AccessToken token) {
        Log.d(TAG, "handleFaceBookToken" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();

                    // adding information of current user to database
                    HashMap<String, Object> userInfo = new HashMap<>();
                    userInfo.put("Name", user.getDisplayName());
                    userInfo.put("Email", FEmail);
                    userInfo.put("login_type", "FaceBook");
                    userInfo.put("profilePicUrl", "default");
                    FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).updateChildren(userInfo);
                    updateUI(user);
                } else {
                    Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }


    // It checks information that user entered with firebase to give permission for login
    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                Toast.makeText(LoginActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    HashMap<String, Object> userInfo = new HashMap<>();
                    userInfo.put("Name", user.getDisplayName());
                    userInfo.put("Email", user.getEmail());
                    userInfo.put("login_type", "Google");
                    userInfo.put("profilePicUrl", "default");
                    FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).updateChildren(userInfo);

                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

}
