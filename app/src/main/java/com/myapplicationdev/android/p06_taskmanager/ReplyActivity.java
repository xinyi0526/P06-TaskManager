package com.myapplicationdev.android.p06_taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ReplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        CharSequence reply = null;
        Intent intent = getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            reply = remoteInput.getCharSequence("status");
        }

        if(reply != null){
            Toast.makeText(ReplyActivity.this, "You have indicated: " + reply,
                    Toast.LENGTH_SHORT).show();
        }

    }
}
