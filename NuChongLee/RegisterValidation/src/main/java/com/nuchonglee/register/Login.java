package com.nuchonglee.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button registerActivity;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registerActivity = findViewById(R.id.registerActivity);
        login = findViewById(R.id.login);
    }

    private void setupListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUsername();
            }

        });

        registerActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Login.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }


        void checkUsername() {
            boolean isValid = true;
            if (isEmpty(username)) {
                username.setError("You must enter username to login!");
                isValid = false;
            } else {
                if (!isEmail(username)) {
                    username.setError("Enter valid email!");
                    isValid = false;
                }
            }

            if (isEmpty(password)) {
                password.setError("You must enter password to login!");
                isValid = false;
            } else {
                if (password.getText().toString().length() < 4) {
                    password.setError("Password must be at least 4 chars long!");
                    isValid = false;
                }
            }

            //check email and password
            //IMPORTANT: here should be call to backend or safer function for local check; For example simple check is cool
            //For example simple check is cool
            if (isValid) {
                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                if (usernameValue.equals("test@test.com") && passwordValue.equals("password1234")) {
                    //everything checked we open new activity
                    Intent i = new Intent(Login.this, FirstActivity.class);
                    startActivity(i);
                    //we close this activity
                    this.finish();
                } else {
                    Toast t = Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }



    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}
