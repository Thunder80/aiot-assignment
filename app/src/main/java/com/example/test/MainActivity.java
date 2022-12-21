package com.example.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MyContentObserver obs;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermission(Manifest.permission.READ_CALL_LOG, 100);
        checkPermission(Manifest.permission.WRITE_CALL_LOG, 100);
        checkPermission(Manifest.permission.SEND_SMS, 100);

        obs = new MyContentObserver(new Handler(), getApplicationContext(), "", "");
        this.getApplicationContext()
                .getContentResolver()
                .registerContentObserver(
                        android.provider.CallLog.Calls.CONTENT_URI, true, obs);

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obs.message = binding.message.getText().toString();
                obs.number = binding.number.getText().toString();
            }
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
    }
}