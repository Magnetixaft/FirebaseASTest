package com.example.firebaseastest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String NAME_KEY = "name";
    public static final String RATING_KEY = "rating";
    public String TAG = "Names";
    public String userName = "Email";
    private FirebaseAuth firebaseAuth;
    private Button buttonAdd;
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("TestData/Names");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextRating = (EditText) findViewById(R.id.editTextRating);
        String nameText = editTextName.getText().toString();
        String ratingText = editTextRating.getText().toString();
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
    }
    public void saveName() {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        assert user != null;
        String userName = user.getEmail();

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        String nameText = editTextName.getText().toString();
        EditText editTextRating = (EditText) findViewById(R.id.editTextRating);
        String ratingText = editTextRating.getText().toString();
        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        if(nameText.isEmpty()) {
            return;
        }
        Map<String, Object> users = new HashMap<String, Object>();
        users.put(NAME_KEY, nameText);
        users.put(RATING_KEY, ratingText);
        

        FirebaseFirestore.getInstance().collection("users").document(userName)
                .set(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Task failed", e);
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == buttonAdd){
            saveName();
        }
    }
}
