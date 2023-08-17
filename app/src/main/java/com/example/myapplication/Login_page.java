package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login_page extends AppCompatActivity {
    Animation rightanim;

    TextInputLayout usernames,pswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);
        rightanim = AnimationUtils.loadAnimation(this,R.anim.right_anim);
        ImageView img =findViewById(R.id.logo2);
        img.setAnimation(rightanim);
        Button butt = findViewById(R.id.SignUp);

        usernames=findViewById(R.id.username);

        pswd=findViewById(R.id.password);
        Button button=findViewById(R.id.buttonPanel);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_page.this,SignUp_page.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
            }
        });


    }

    private boolean validateName(){
        String valnames = usernames.getEditText().getText().toString();

        if (valnames.isEmpty()) {
            usernames.setError("Please fill the mail or username");
            return false;
        }
        else {
            usernames.setError(null);
            usernames.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatenpswd(){
        String valnames =pswd.getEditText().getText().toString();

        if (valnames.isEmpty()) {
            pswd.setError("Please fill the password");
            return false;
        } else {
            pswd.setError(null);
            return true;
        }
    }
    public void loginUser(View view){
        if (!validateName() | !validatenpswd()){
            return;
        }
        else{
            validUser();
        }
    }
    private void validUser(){
        String userEnteredname = usernames.getEditText().getText().toString().trim();
        String userEnteredpass = pswd.getEditText().getText().toString().trim();
        DatabaseReference ref = FirebaseDatabase.getInstance("https://my-application-eddeb-default-rtdb.firebaseio.com").getReference("users");
        Query checkuser = ref.orderByChild("uname").equalTo(userEnteredname);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    usernames.setError(null);
                    usernames.setErrorEnabled(false);

                    String passwordfromDB=snapshot.child(userEnteredname).child("pswd").getValue(String.class);
                    if (passwordfromDB.equals(userEnteredpass)) {

                        usernames.setError(null);
                        usernames.setErrorEnabled(false);

                        String unamefromDB=snapshot.child(userEnteredname).child("uname").getValue(String.class);
                        String mailfromDB=snapshot.child(userEnteredname).child("mail").getValue(String.class);
                        String phonefromDB=snapshot.child(userEnteredname).child("phoneno").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Dashboard2.class);
                        intent.putExtra("uname",unamefromDB);
                        intent.putExtra("mail",mailfromDB);
                        intent.putExtra("phoneno",phonefromDB);
                        startActivity(intent);
                    }
                    else{
                        pswd.setError("Wrong Password");
                        pswd.requestFocus();
                    }
                }
                else {
                    usernames.setError("No such User exist");
                    usernames.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}