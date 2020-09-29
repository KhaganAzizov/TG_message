package com.example.telegrammessagesender;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ShareFrom extends AppCompatActivity {
    String packagename = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (appInstalledOrNot("org.thunderdog.challegram"))
            packagename = "org.thunderdog.challegram";
        else if (appInstalledOrNot("org.telegram.messenger"))
            packagename = "org.telegram.messenger";
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
            else if (type.startsWith("video/")) {
                handleSendVideo(intent); // Handle single image being sent
            }
        }
    }

    String sharedText;

    void handleSendText(Intent intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            if (packagename.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Telegram tapilmadi", Toast.LENGTH_LONG).show();
                return;
            } else {
                try {
                    Intent telegramIntent = new Intent(Intent.ACTION_SEND);
                    telegramIntent.setPackage(packagename);
                    telegramIntent.setType("text/plain");
                    telegramIntent.putExtra(Intent.EXTRA_SUBJECT, sharedText);
                    startActivityForResult(telegramIntent, 133);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    Uri imageUri;

    void handleSendImage(Intent intent) {
        imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
            if (packagename.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Telegram tapilmadi", Toast.LENGTH_LONG).show();
                return;
            } else {
                try {
                    Intent telegramIntent = new Intent(Intent.ACTION_SEND);
                    telegramIntent.setPackage(packagename);
                    telegramIntent.setType("image/*");
                    telegramIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivityForResult(telegramIntent, 111);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    Uri videoUri;
    void handleSendVideo(Intent intent) {
        videoUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (videoUri != null) {
            // Update UI to reflect image being shared
            if (packagename.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Telegram tapilmadi", Toast.LENGTH_LONG).show();
                return;
            } else {
                try {
                    Intent telegramIntent = new Intent(Intent.ACTION_SEND);
                    telegramIntent.setPackage(packagename);
                    telegramIntent.setType("video/*");
                    telegramIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
                    startActivityForResult(telegramIntent, 222);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (appInstalledOrNot("org.thunderdog.challegram"))
            packagename = "org.thunderdog.challegram";
        else if (appInstalledOrNot("org.telegram.messenger"))
            packagename = "org.telegram.messenger";
        if (packagename.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Telegram tapilmadi", Toast.LENGTH_LONG).show();
            return;
        } else {
            try {
                Intent telegramIntent = new Intent(Intent.ACTION_SEND);
                telegramIntent.setPackage(packagename);
                if (requestCode == 133) {
                    telegramIntent.setType("text/plain");
                    telegramIntent.putExtra(Intent.EXTRA_TEXT, sharedText);
                    startActivityForResult(telegramIntent, 133);
                } else if (requestCode == 111) {
                    telegramIntent.setType("image/*");
                    telegramIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivityForResult(telegramIntent, 111);
                }
                else if (requestCode == 222) {
                    telegramIntent.setType("video/*");
                    telegramIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
                    startActivityForResult(telegramIntent, 222);
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
            }
        }
    }


    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
