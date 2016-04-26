package com.tir.flyingcouch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Karan Shah on 4/24/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button registerButton = (Button) findViewById(R.id.regButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView emailID = (TextView) findViewById(R.id.regEmail);
                TextView pass = (TextView) findViewById(R.id.regPass);

                final String emailText = emailID.getText().toString();
                final String passText = pass.getText().toString();

                final Firebase mRef = new Firebase("https://torrid-inferno-8261.firebaseio.com");


                mRef.createUser(emailText, passText, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        Toast.makeText(RegisterActivity.this, "Account Created! ", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                        Matcher mat = pattern.matcher(emailText);
                        if (passText.equals("")) {
                            Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                        } else if (emailText.equals("")) {
                            Toast.makeText(RegisterActivity.this, "Enter Email ID", Toast.LENGTH_SHORT).show();
                        } else {
                            if (mat.matches()) {
                                Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }
        });

    }
}
