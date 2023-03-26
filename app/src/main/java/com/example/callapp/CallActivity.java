package com.example.callapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;

public class CallActivity extends AppCompatActivity {

    private EditText _txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActivityCompat.requestPermissions(CallActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1 );

        _txtPhone = (EditText) findViewById(R.id.txtPhone);
        FloatingActionButton fabCall = (FloatingActionButton) findViewById(R.id.fabCall);
        FloatingActionButton fabClean = (FloatingActionButton) findViewById(R.id.fabClean);

        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(_txtPhone.getText().toString().trim().length() == 0) {
                    Snackbar.make(view, R.string.app_phone_empty, Snackbar.LENGTH_LONG)
                            .setAction(R.string.app_ok, null).show();
                    return;
                }

                try {

                    if(ActivityCompat.checkSelfPermission(CallActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Snackbar.make(view, R.string.app_call_not_granted, Snackbar.LENGTH_LONG)
                                .setAction(R.string.app_ok, null).show();
                        return;
                    }

                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse(getString(R.string.app_country_code) + _txtPhone.getText().toString()));
                    startActivity(call);
                    CallActivity.this.finish();
                }
                catch (Exception ex) {
                    Snackbar.make(view, R.string.app_call_error, Snackbar.LENGTH_LONG)
                            .setAction(R.string.app_ok, null).show();
                    return;
                }
            }
        });

        fabClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _txtPhone.setText("");
                _txtPhone.requestFocus();
            }
        });
    }
}
