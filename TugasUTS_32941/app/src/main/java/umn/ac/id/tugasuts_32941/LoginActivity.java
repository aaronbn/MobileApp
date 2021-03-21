package umn.ac.id.tugasuts_32941;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btnlog;
    private EditText user;
    private EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_login);

        btnlog = (Button) findViewById(R.id.btnLogin);
        user = (EditText) findViewById(R.id.username);
        pw = (EditText) findViewById(R.id.password);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin(user.getText().toString(),
                         pw.getText().toString());
            }
        });
    }

    private void validateLogin(String userName, String upassword) {
        if((userName.equals("uasmobile")) && (upassword.equals("uasmobilegenap"))) {
            Intent intentLogin2 = new Intent(LoginActivity.this, SongsActivity.class);
                    startActivity(intentLogin2);
                    finish();
        }
    }
}
