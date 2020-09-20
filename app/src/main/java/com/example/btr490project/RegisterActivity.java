package com.example.btr490project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ImageView backArrow;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText configPass;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backArrow = findViewById(R.id.back_arrow);
        name = findViewById(R.id.editTextUserName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        configPass = findViewById(R.id.configPassword);
        btnRegister = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        // To come back to login page
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        // After clicking the register button it checks fields
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_configPass = configPass.getText().toString();
                String txr_user_name = name.getText().toString();

                if (TextUtils.isEmpty(txr_user_name)) {
                    name.setError("Please enter your user name");
                    name.requestFocus();
                } else if (TextUtils.isEmpty(txt_email)) {
                    email.setError("Please enter your email");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(txt_password)) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(txt_configPass)) {
                    configPass.setError("Please enter your configure password");
                    configPass.requestFocus();
                } else if (!txt_password.equalsIgnoreCase(txt_configPass)) {
                    Toast.makeText(RegisterActivity.this, "Passwords are not match", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txt_email) && TextUtils.isEmpty(txt_password) && TextUtils.isEmpty(txt_configPass)) {
                    Toast.makeText(RegisterActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be more than 6 digits", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txr_user_name, txt_email, txt_password);
                }
            }
        });
    }

    // It will register user to firebase and then transfer user to home page
    private void registerUser(final String userName, final String email, String password) {
        pd.setMessage("Please wait...");
        pd.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                // adding information of our user to the database
                FirebaseUser user = mAuth.getCurrentUser();
                HashMap<String, Object> userInfo = new HashMap<>();
                userInfo.put("Name", userName);
                userInfo.put("Email", email);
                userInfo.put("login_type", "email-pass");
                userInfo.put("profilePicUrl", "default");

                FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "Registering user was successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
