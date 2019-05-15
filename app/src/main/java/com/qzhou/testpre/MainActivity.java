package com.qzhou.testpre;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
        MainActivityPermissionsDispatcher.zhouWithPermissionCheck(MainActivity.this);

    }

    // 2.Annotate a method which performs the action that requires one or more permissions.
    //   注解一个执行需要获取一个或多个权限的操作的方法。[必须的注解]
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR,Manifest.permission.CALL_PHONE})
    void zhou() {
        Toast.makeText(this, "已获取到相机权限", Toast.LENGTH_SHORT).show();
    }

    // 3.Annotate a method which explains why the permission/s is/are needed. It passes in a
    // PermissionRequest object which can be used to continue or abort the current
    // permission request upon user input.
    // 注解一种解释为什么需要权限的方法(注解获取权限时给出提示的方法)。它通过PermissionRequest对象，
    // 该对象可用于在用户输入时继续或中止当前权限请求。[非必须的注释]
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR,Manifest.permission.CALL_PHONE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("权限")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    // 4.Annotate a method which is invoked if the user doesn't grant the permissions
    //  注解一个用户拒绝授权时回调的方法。[非必须的注释]
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        Toast.makeText(this, "相机权限许可被拒绝，请考虑给予权限以便进行拍照操作。", Toast.LENGTH_SHORT).show();
    }

    // 5.Annotate a method which is invoked if the user chose to have the device "never ask again"
    // about a permission
    // 注解一个用户选择"不再询问"时进行提示的回调方法。[非必须的注释]
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        Toast.makeText(this, "相机许可被拒绝，不再进行询问。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        // 注意：将权限处理委托给生成的方法。
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}