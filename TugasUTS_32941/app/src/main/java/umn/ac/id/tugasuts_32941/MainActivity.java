package umn.ac.id.tugasuts_32941;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnProfil, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProfil = findViewById(R.id.btnprofil);
        btnLogin = findViewById(R.id.btnlogin);

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfil = new Intent(MainActivity.this,
                        ProfilActivity.class);
                startActivityForResult(intentProfil, 1);
            }
            public void onActivityResult(int requestCode,
                                         int resultCode, Intent data) {
                MainActivity.super.onActivityResult(requestCode, resultCode, data);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivityForResult(intentLogin, 1);
            }
            public void onActivityResult(int requestCode,
                                         int resultCode, Intent data) {
                MainActivity.super.onActivityResult(requestCode, resultCode, data);
                finish();
            }
        });
    }
}