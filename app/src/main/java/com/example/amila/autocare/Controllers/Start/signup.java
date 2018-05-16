package com.example.amila.autocare.Controllers.Start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amila.autocare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity implements View.OnClickListener{

    EditText editTextemail,editTextPassword,editTextReenterpassword,editTextTelephone;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextemail = findViewById(R.id.editTextemail);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextReenterpassword=findViewById(R.id.editTextReenterPassword);
        editTextTelephone = findViewById(R.id.editTexttelephone);
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
            editTextemail.setError("Enter editTextemail!");
            editTextemail.requestFocus();
            //Toast.makeText(getApplicationContext(), "Enter editTextemail!", Toast.LENGTH_LONG).show();
        }else if(!reenterpassword.equals(password)){
            Toast.makeText(getApplicationContext(), "Password Mismatch!", Toast.LENGTH_LONG).show();
        }else
        if(password.isEmpty()){
            editTextPassword.setError("Enter a editTextPassword!");
            editTextPassword.requestFocus();
        }else
        if(reenterpassword.isEmpty()){
            editTextReenterpassword.setError("Re enter editTextPassword!");
            editTextReenterpassword.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Enter a valid editTextemail");
            editTextemail.requestFocus();
        }else if(password.length()<8) {
            editTextPassword.setError("minimum length should be 8");
            editTextPassword.requestFocus();
        }else{

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "User Registered Successfully!!", Toast.LENGTH_LONG).show();
                                //FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent =   new Intent(getApplicationContext(),phone_number_request.class);
                                startActivity(intent);
                                MainActivity.login.finish();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(), "Username already registered!!", Toast.LENGTH_LONG).show();

                                }else if (task.getException() instanceof FirebaseAuthInvalidUserException){
                                    Toast.makeText(getApplicationContext(), "Invalid User!!", Toast.LENGTH_LONG).show();

                                }else{
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                                //updateUI(null);
                            }

                            // ...
                        }
                    });



        }

    }

}
