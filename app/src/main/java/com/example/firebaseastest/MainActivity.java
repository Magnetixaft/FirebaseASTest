package com.example.firebaseastest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSignup;
    private Button buttonLogIn;
    private EditText editEmailText;
    private EditText editPasswordText;
    private TextView displayText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(this);
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        buttonLogIn.setOnClickListener(this);

        displayText = (TextView) findViewById(R.id.displayText);
        editEmailText = (EditText) findViewById(R.id.editEmailText);
        editPasswordText = (EditText) findViewById(R.id.editPasswordText);
    }

    private void registerUser(){
        String email = editEmailText.getText().toString();
        String password = editPasswordText.getText().toString();
        displayText.setText(email);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Registred succesfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                    }else{
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            assert e != null;
                            Toast.makeText(MainActivity.this, "Could not register: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                }


        });
    }
    @Override
    public void onClick(View view) {
        if(view ==  buttonSignup){
            registerUser();
        }if(view == buttonLogIn){
            launchActivity();
        }
    }
    private void launchActivity(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}