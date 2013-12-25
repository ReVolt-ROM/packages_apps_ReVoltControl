package com.revolt.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launchRV = new Intent(this, MainActivity.class);
        startActivity(launchRV);
        finish();
    }
}
