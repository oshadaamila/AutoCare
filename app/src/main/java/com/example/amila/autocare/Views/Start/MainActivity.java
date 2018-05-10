package com.example.amila.autocare.Views.Start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amila.autocare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public static Activity login;
    EditText editTextemail, editTextPassword;
    FirebaseAuth mAuth;


    public MainActivity() {
        mAuth = FirebaseAuth.getInstance();
    }

    protected void onCreate(Bundle savedInstanceState) {
        /*login=this;

        if(mAuth.getCurrentUser()!=null){*/
        super.onCreate(savedInstanceState);
            finish();

        startActivity(new Intent(this,navigationDrawer.class));
        /*}else {
            setContentView(R.layout.activity_main);
            editTextemail = findViewById(R.id.email);
            editTextPassword = findViewById(R.id.password);
            findViewById(R.id.textViewSignup).setOnClickListener(this);
            findViewById(R.id.button).setOnClickListener(this);
        }*/

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewSignup:
                startActivity(new Intent(this,signup.class));
                break;
            case R.id.button_update:
                userlogin();
                break;
        }
    }

    private void userlogin(){
        String email = editTextemail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(password.isEmpty()){
            editTextPassword.setError("Enter Password Here");
            editTextPassword.requestFocus();
        }else if(email.isEmpty()){
            editTextemail.setError("Enter email here");
            editTextemail.requestFocus();
        }else{
           mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        finish();
                        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()==null){
                            Intent intent =   new Intent(getApplicationContext(),profile.class);
                            startActivity(intent);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        }else{
                            startActivity(new Intent(getApplicationContext(),home.class));
                        }


                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                        Log.d("login","login task not successfull");
                    }
               }
           });
        }

    }
}





