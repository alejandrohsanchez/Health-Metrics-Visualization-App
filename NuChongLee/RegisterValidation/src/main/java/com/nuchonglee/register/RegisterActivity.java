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

public class RegisterActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText newPassword;
    EditText confirmPassword;
    EditText address;
    EditText email;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.fName);
        lastName = findViewById(R.id.lName);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(checkDataEntered()) {

                   Intent I = new Intent(RegisterActivity.this, BackToLogin.class);

                   startActivity(I);
               }
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

   boolean checkDataEntered() {
        if (isEmpty(firstName)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
            lastName.setError("First name is required!");
        }

        if (isEmpty(lastName)) {
            Toast t = Toast.makeText(this, "You must enter last name to register!", Toast.LENGTH_SHORT);
            t.show();
            lastName.setError("Last name is required!");
        }

        if (isEmpty(newPassword)) {
            Toast t = Toast.makeText(this, "You must enter a password to register!", Toast.LENGTH_SHORT);
            t.show();
            newPassword.setError("Password is required!");
        }

        if (isEmpty(confirmPassword)) {
            Toast t = Toast.makeText(this, "Your password must match to register!", Toast.LENGTH_SHORT);
            t.show();
            confirmPassword.setError("Confirm your is password!");
        }

        if (!isEmail(email)) {
            Toast t = Toast.makeText(this, "You must enter a valid email to register!", Toast.LENGTH_SHORT);
            t.show();
            email.setError("Enter valid email!");
            } else{
            return true;
        }
       return false;
   }
}
