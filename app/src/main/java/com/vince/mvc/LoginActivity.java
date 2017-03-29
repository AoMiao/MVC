package com.vince.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private TextView username;
    private TextView password;
    private Button Sign;
    private TextView result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setOnClick();
    }

    public void init(){
        username = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        Sign = (Button) findViewById(R.id.email_sign_in_button);
        result = (TextView) findViewById(R.id.result);
    }

    public void setOnClick(){
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = (String) username.getText();
                String password1 = (String) password.getText();
                HttpUrlSend.send(name,password1);
            }
        });
    }
}

