package com.example.telegrammessagesender;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.security.PrivilegedAction;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText editText;
    String packagename="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        editText = findViewById(R.id.msgtextfield);
        if(appInstalledOrNot("org.thunderdog.challegram"))packagename="org.thunderdog.challegram";
        else if(appInstalledOrNot("org.telegram.messenger"))packagename="org.telegram.messenger";
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (packagename.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Telegram tapilmadi", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    try {
                        Intent telegramIntent = new Intent(Intent.ACTION_SEND);
                        telegramIntent.setPackage(packagename);
                        telegramIntent.setType("text/plain");
                        telegramIntent.putExtra(Intent.EXTRA_SUBJECT, editText.getText().toString());
                        startActivityForResult(telegramIntent, 152);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (packagename.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Telegram tapilmadi", Toast.LENGTH_LONG).show();
            return;
        } else {
            try {
                Intent telegramIntent = new Intent(Intent.ACTION_SEND);
                telegramIntent.setPackage(packagename);
                telegramIntent.setType("text/plain");
                telegramIntent.putExtra(Intent.EXTRA_SUBJECT, editText.getText().toString());
                startActivityForResult(telegramIntent, 152);
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
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }
}