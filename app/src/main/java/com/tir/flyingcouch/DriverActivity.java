package com.tir.flyingcouch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Karan Shah on 4/22/2016.
 */
public class DriverActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity);

        Button toChatPage = (Button) findViewById(R.id.chatButton);
        toChatPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}
