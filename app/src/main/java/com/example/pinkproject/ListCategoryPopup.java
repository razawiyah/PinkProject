package com.example.pinkproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pinkproject.appmodel.ImageModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ListCategoryPopup extends AppCompatActivity {

    EditText listNameEditText;
    Button ok_button,cancel_button,upload_button;
    Uri uri=null;
    ImageView uploadIV;
    StorageReference file;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category_popup);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        listNameEditText = findViewById(R.id.list_name_edittext);
        ok_button = findViewById(R.id.ok_button);
        cancel_button = findViewById(R.id.cancel_button);
        upload_button = findViewById(R.id.upload_button);
        uploadIV = findViewById(R.id.uploadIV);

        ok_button.setOnClickListener(new View.OnClickListener()  {
    @Override
    public void onClick(View v) {
        String name = listNameEditText.getText().toString().trim();

        if(name.isEmpty()){
            Toast.makeText(ListCategoryPopup.this,"Fill in the details!",Toast.LENGTH_LONG).show();
        }else if(uri.equals("")){
            Toast.makeText(ListCategoryPopup.this,"Fill in the details!",Toast.LENGTH_LONG).show();

        }else{
            file = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
            file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ImageModel imageModel = new ImageModel(uri.toString());
                            //daatbase path betulkan
                            databaseReference.child("ListCategory").child(id).child(name).setValue(imageModel);
                            databaseReference.child("ListCategory").child(id).child(name).child("name").setValue(name);

                            Toast.makeText(ListCategoryPopup.this, "Data uploaded!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ListCategoryPopup.this, ListCategory.class));
                            finish();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ListCategoryPopup.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
});

        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListCategoryPopup.this, ListCategory.class));
                finish();            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();

        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 && resultCode==RESULT_OK && data!=null){
            uri=data.getData();
            uploadIV.setImageURI(uri);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListCategoryPopup.this, ListCategory.class));
        finish();
    }
}