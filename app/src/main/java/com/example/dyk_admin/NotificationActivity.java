package com.example.dyk_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dyk_admin.fcm.FcmNotificationsSender;
import com.example.dyk_admin.fcm.FirebaseMessagingService;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationActivity extends AppCompatActivity {

    TextInputLayout notificationTitle, notificationMessage;
    TextView sendAllNotificationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        notificationTitle = findViewById(R.id.notificationTitle);
        notificationMessage = findViewById(R.id.notificationMessage);
        sendAllNotificationBtn = findViewById(R.id.sendAllNotificationBtn);
        sendAllNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = notificationTitle.getEditText().getText().toString();
                String msg = notificationMessage.getEditText().getText().toString();
                if (title.isEmpty() && msg.isEmpty()){
                    Toast.makeText(NotificationActivity.this, "Detail's required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    FcmNotificationsSender sender = new FcmNotificationsSender("/topics/Users",title,msg,getApplicationContext(),NotificationActivity.this);
                    sender.SendNotifications();
                }
            }
        });
    }
}