package com.example.pinkproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Login extends AppCompatActivity {

    EditText emailET,passET;
    Button loginBtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passET);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                String password = passET.getText().toString().trim();

                if (email.isEmpty()){
                    Toast.makeText(Login.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (password.isEmpty()){
                    Toast.makeText(Login.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                                fUser = mAuth.getCurrentUser();
                                id = fUser.getUid();

                                startActivity(new Intent(Login.this, Homepage.class));
                                finish();

                                //have to make if else patient or doctor
                                /*Query patientQ = FirebaseDatabase.getInstance().getReference("User").child(id);
                                patientQ.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DataSnapshot snapshot = task.getResult();
                                            String account = snapshot.child("account").getValue().toString();

                                            if(account.equals("User")){
                                                startActivity(new Intent(Login.this, UserHomepage.class));
                                                finish();
                                            }else{
                                                startActivity(new Intent(Login.this, AdminHomepage.class));
                                                finish();
                                            }
                                        }
                                    }
                                });*/

                            }else{
                                Toast.makeText(Login.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }
}