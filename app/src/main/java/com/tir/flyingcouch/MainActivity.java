package com.tir.flyingcouch;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int GPS_ERRORDIALOG_REQUEST = 10328;
    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);


        setContentView(R.layout.activity_main);

        TextView regAcc = (TextView) findViewById(R.id.registerLink);
        regAcc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                return true;
            }
        });

        Button toSearchPage = (Button) findViewById(R.id.loginButton);
        toSearchPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView emailID = (TextView) findViewById(R.id.loginEmail);
                TextView pass = (TextView) findViewById(R.id.loginPass);

                final String emailText = emailID.getText().toString();
                final String passText = pass.getText().toString();

                final Firebase mRef = new Firebase("https://torrid-inferno-8261.firebaseio.com");

                mRef.authWithPassword(emailText, passText, new Firebase.AuthResultHandler(){

                    @Override
                    public void onAuthenticated(AuthData authData) {

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("emailID", emailText);
                        mRef.child("users").child(authData.getUid()).setValue(map);

                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(MainActivity.this, "Invalid Email ID/Password", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }


}
