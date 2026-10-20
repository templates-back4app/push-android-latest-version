package com.android.application;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final ActivityResultLauncher<String> notificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted ->
                    Log.d(TAG, granted
                            ? "Notification permission granted."
                            : "Notification permission denied, pushes will not be shown."));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestNotificationPermission();

        TextView textView = findViewById(R.id.textView);
        ParseObject firstObject = new ParseObject("FirstClass");
        firstObject.put("message", "Hey ! First message from android. Parse is now connected");

        firstObject.saveInBackground(e -> {
            if (e != null) {
                Log.e(TAG, "Object not saved: " + e.getMessage(), e);
                textView.setText(getString(R.string.object_not_saved, e.getMessage()));
            } else {
                Log.d(TAG, "Object saved.");
                textView.setText(getString(R.string.object_saved, firstObject.getObjectId()));
            }
        });
    }

    /** Android 13+ requires the user to opt in before notifications can be displayed. */
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}
