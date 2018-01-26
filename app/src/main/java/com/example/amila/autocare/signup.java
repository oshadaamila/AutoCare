package com.example.amila.autocare;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity implements View.OnClickListener{

    EditText editTextemail,editTextPassword,editTextReenterpassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextemail = findViewById(R.id.editTextemail);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextReenterpassword=findViewById(R.id.editTextReenterPassword);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        findViewById(R.id.buttonSignup).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignup:
                registerUser();
                Log.d("mytag","Signupclicked!!!!");
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this,MainActivity.class));
                Log.d("mytag","loginclicked!!!!");
                break;
        }

    }
    private void registerUser(){
        String email= editTextemail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String reenterpassword= this.editTextReenterpassword.getText().toString().trim();
        if(email.isEmpty()){
            editTextemail.setError("Enter email!");
            editTextemail.requestFocus();
            //Toast.makeText(getApplicationContext(), "Enter email!", Toast.LENGTH_LONG).show();
        }else if(!reenterpassword.equals(password)){
            Toast.makeText(getApplicationContext(), "Password Mismatch!", Toast.LENGTH_LONG).show();
        }else
        if(password.isEmpty()){
            editTextPassword.setError("Enter a password!");
            editTextPassword.requestFocus();
        }else
        if(reenterpassword.isEmpty()){
            editTextReenterpassword.setError("Re enter password!");
            editTextReenterpassword.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Enter a valid email");
            editTextemail.requestFocus();
        }else if(password.length()<8){
            editTextPassword.setError("minimum length should be 8");
            editTextPassword.requestFocus();
        }else{

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "User Registered Successfully!!", Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });

        }

}
}
