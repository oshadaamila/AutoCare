package com.example.amila.autocare.Views.Start;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amila.autocare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class profile extends AppCompatActivity implements View.OnClickListener{
    TextView cameraopen;
    EditText name;
    ImageView fileChooser;
    Button continuebtn;
    Uri uriprofileimage;
    String profImageUrl;
    FirebaseUser mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        cameraopen = findViewById(R.id.textView);
        name = findViewById(R.id.editTextNameView);
        continuebtn=findViewById(R.id.buttonContinue);
        fileChooser =findViewById(R.id.imageView);
        cameraopen.setOnClickListener(this);
        fileChooser.setOnClickListener(this);
        continuebtn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView:
                imageChooser();
                Log.d("oshada","image view clicked");
                break;
            case R.id.buttonContinue:
                if(name.getText().toString().trim().length()==0){
                    name.setError("Enter a name to continue");
                    name.requestFocus();
                    break;
                }else if(uriprofileimage==null){
                Toast.makeText(getApplicationContext(), "Choose a image", Toast.LENGTH_LONG).show();
                break;
                }else {
                    Log.d("oshada","button_clicked");
                    saveUserInfo();
                    break;
                }
        }
    }

    private void saveUserInfo() {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name.getText().toString())
                .setPhotoUri(Uri.parse(profImageUrl))
                .build();

        mAuth.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"User Profile Updated!",Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent =   new Intent(getApplicationContext(),home.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void imageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose an image to profile photo!"),101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK && data!= null && data.getData()!=null){
            uriprofileimage=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriprofileimage);
                fileChooser.setImageBitmap(bitmap);
                uploadimagetofirebasestorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadimagetofirebasestorage() {
        StorageReference profPicReference = FirebaseStorage.
                        getInstance().getReference("profPics/"+System.currentTimeMillis()+".jpg");
        if(uriprofileimage!=null){
            StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = profPicReference.putFile(uriprofileimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profImageUrl=taskSnapshot.getDownloadUrl().toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(profile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
