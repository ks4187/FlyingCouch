package com.tir.flyingcouch;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.Random;

/**
 * Created by Karan Shah on 4/18/2016.
 */
public class ChatActivity extends ListActivity {
    private static final String FIREBASE_URL = "https://torrid-inferno-8261.firebaseio.com/";

    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        setupUsername();

        setTitle(mUsername);

        mFirebaseRef = new Firebase(FIREBASE_URL).child("customerChat");

        EditText inputMessage = (EditText) findViewById(R.id.input_msg);
        inputMessage.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN){
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.inputButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        final ListView listView = getListView();

        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUsername);
        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        /*mConnectedListener = mFirebaseRef.getRoot().child("./info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (boolean) dataSnapshot.getValue();
                if(connected){
                    Toast.makeText(ChatActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

    }

    @Override
    public void onStop(){
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void setupUsername(){
        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if(mUsername == null){
            Random r = new Random();

            mUsername = "TestAccount #" + r.nextInt(10);
            prefs.edit().putString("username", mUsername).commit();
        }
    }

    private void sendMessage(){
        EditText editText = (EditText) findViewById(R.id.input_msg);
        String input = editText.getText().toString();
        if(!input.equals("")){
            Chat chat = new Chat(input, mUsername);

            mFirebaseRef.push().setValue(chat);
            editText.setText("");
        }
    }

}
