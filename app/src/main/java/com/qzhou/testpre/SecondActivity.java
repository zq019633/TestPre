package com.qzhou.testpre;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initData();
        SecondActivityPermissionsDispatcher.initDataWithPermissionCheck(SecondActivity.this);
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
     void initData() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Log.e("zq","大地啊乃");

            }
        });
    }
}
