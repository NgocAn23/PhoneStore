package com.manager.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanhang.R;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                }catch (Exception ex) {

                }finally {
                    if (Paper.book().read("user") == null) {
                        //nếu giá trị của người dùng null thì vẫn ở màn hình đăng nhập
                        Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        // còn ko thì chuyển sang màn hình chính
                        Intent home = new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(home);
                        finish();
                    }

                }
            }
        };
        thread.start();
    }
}