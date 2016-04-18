package com.tir.flyingcouch;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.client.collection.LLRBNode;


/**
 * Created by Karan Shah on 4/18/2016.
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat> {

    private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername ){
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
    }


    protected  void populateView(View view, Chat chat){

        String sender = chat.getSender();
        TextView senderText = (TextView) view.findViewById(R.id.sender);
        senderText.setText(sender + ": ");

        if(sender != null && sender.equals(mUsername)){
            senderText.setTextColor(Color.BLACK);
        } else {
            senderText.setTextColor(Color.BLUE);
        }

        ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());

    }
}
