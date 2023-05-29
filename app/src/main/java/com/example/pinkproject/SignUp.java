package com.example.pinkproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pinkproject.appmodel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText nameET,emailET,passET;
    Button signupBtn;
    TextInputLayout accountET;
    AutoCompleteTextView accountATV;
    ArrayAdapter<String> accountAdapter;
    String[] accountArray = {"Administrator","User"};
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passET);
        signupBtn = findViewById(R.id.signupBtn);


        //dropdown account
        accountATV = findViewById(R.id.accountData);
        accountET = findViewById(R.id.accountET);

        accountATV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Check if the EditText has focus
                if (hasFocus) {
                    // If the EditText has focus, set the hint to an empty string
                    accountET.setHint("");
                }
            }
        });

        accountAdapter = new ArrayAdapter<String>(SignUp.this, R.layout.dropdown_item,accountArray);
        accountATV.setAdapter(accountAdapter);
        accountATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(SignUp.this, "Account: "+ item, Toast.LENGTH_SHORT).show();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountATV.getText().toString();
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String pass = passET.getText().toString();

                if (name.isEmpty()){
                    Toast.makeText(SignUp.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (email.isEmpty()){
                    Toast.makeText(SignUp.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (pass.isEmpty()){
                    Toast.makeText(SignUp.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (account.isEmpty()){
                    Toast.makeText(SignUp.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this,"Sign Up Successful!",Toast.LENGTH_LONG).show();
                                fUser = mAuth.getCurrentUser();
                                id = fUser.getUid();
                                //add UserModel to DB
                                UserModel user = new UserModel(name,email,pass,account,id);
                                databaseReference.child(id).setValue(user);
                                mAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUp.this, MainActivity.class));
        finish();
    }
}