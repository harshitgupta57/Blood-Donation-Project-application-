package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView signin;

    private TextInputEditText emailid,enterpassword;

    private TextView forgotpassword;

    private  Button loginbutton;

    private ProgressDialog userlogin ;

    private FirebaseAuth userauthentication;

    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userauthentication = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = userauthentication.getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        emailid = findViewById(R.id.emailid);
        enterpassword = findViewById(R.id.enterpassword);
        forgotpassword = findViewById(R.id.forgotpassword);
        loginbutton = findViewById(R.id.loginbutton);

        userlogin = new ProgressDialog(this);


        signin = findViewById(R.id.signupbutton);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailid.getText().toString().trim();
                final String password = enterpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailid.setError("Email is requrired");

                }
                if (TextUtils.isEmpty(password)) {
                    enterpassword.setError("Password is requrired");

                } else {
                    userlogin.setMessage("Waiting for login");
                    userlogin.setCanceledOnTouchOutside(false);
                    userlogin.show();

                    userauthentication.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                            userlogin.dismiss();
                        }
                    });

                }

            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    emailid.setError(("Email is required"));
                }else {
                    userauthentication.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Reset password sent to your email",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this,"Failed to send reset link",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userauthentication.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        userauthentication.removeAuthStateListener(authStateListener);

    }
}