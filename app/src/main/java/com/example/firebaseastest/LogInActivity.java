package com.example.firebaseastest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogIn;
    private EditText editEmailText;
    private EditText editPasswordText;
    private Button buttonSignup;
    private TextView displayText;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        editEmailText = (EditText) findViewById(R.id.editEmailText);
        editPasswordText = (EditText) findViewById(R.id.editPasswordText);
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        buttonLogIn.setOnClickListener(this);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(this);

    }

    private void signIn() {
        String email = editEmailText.getText().toString();
        String password = editPasswordText.getText().toString();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            finish();
                        }else{
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            assert e != null;
                            Toast.makeText(LogInActivity.this, "Could not register: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogIn) {
            signIn();
        }
        if (view == buttonSignup) {
            launchActivity();
        }
    }

    private void launchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}