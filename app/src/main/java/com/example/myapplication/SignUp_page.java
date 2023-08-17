package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_page extends AppCompatActivity {
    Animation rightanim;
    TextInputLayout username,mail,phone_no,pwd,repwd;
    Button regsing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_page);
        rightanim = AnimationUtils.loadAnimation(this,R.anim.right_anim);
        ImageView img =findViewById(R.id.logo3);
        img.setAnimation(rightanim);
        Button butt = findViewById(R.id.login_acc);
        username=findViewById(R.id.name);
        mail=findViewById(R.id.email);
        phone_no=findViewById(R.id.phonenum);
        pwd=findViewById(R.id.password);
        repwd=findViewById(R.id.repassword);
        regsing=findViewById(R.id.sign);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_page.this,Login_page.class);
                startActivity(intent);
            }
        });

        regsing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                registterUser(view);

            }
        });


    }
    private boolean validateName(){
        String valnames = username.getEditText().getText().toString();
        if (valnames.isEmpty()) {
            username.setError("Please fill the name");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateMail(){
        String valnames = mail.getEditText().getText().toString();
        String emailpattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (valnames.isEmpty()) {
            mail.setError("Please fill the mail");
            return false;
        } else if (!valnames.matches(emailpattern)) {
            mail.setError("Invalid email address");
            return false;
        } else {
            mail.setError(null);
            return true;
        }
    }
    private boolean validatephone(){
        String valnames = phone_no.getEditText().getText().toString();
        if (valnames.isEmpty()) {
            phone_no.setError("Please fill the phone number");
            return false;
        } else {
            phone_no.setError(null);
            return true;
        }
    }
    private boolean validatenpswd(){
        String valnames =pwd.getEditText().getText().toString();
        String passverify="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";
        if (valnames.isEmpty()) {
            pwd.setError("Please fill the password");
            return false;
        }else if (!valnames.matches(passverify)) {
            pwd.setError("Password is to weak");
            return false;
        } else {
            pwd.setError(null);
            return true;
        }
    }
    private boolean validaterepswd(){
        String valnames = repwd.getEditText().getText().toString();
        String valnamespwd =pwd.getEditText().getText().toString();
        String passverify="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";
        if (valnames.isEmpty()) {
            repwd.setError("Please fill the password");
            return false;
        }else if (!valnamespwd.equals(valnames)) {
            pwd.setError("The password is not matched");
            return false;
        }
        else if (!valnames.matches(passverify)) {
            pwd.setError("Password is to weak");
            return false;
        }
        else {
            repwd.setError(null);
            return true;
        }
    }

    public void registterUser(View view){
        if(!validateName() | !validateMail() | !validatephone() | !validatenpswd() | !validaterepswd()){
            return;
        }
        DatabaseReference ref = FirebaseDatabase.getInstance("https://my-application-eddeb-default-rtdb.firebaseio.com").getReference("users");
        String unames=username.getEditText().getText().toString();
        String kmail=mail.getEditText().getText().toString();
        String phoneno=phone_no.getEditText().getText().toString();
        String pswd=pwd.getEditText().getText().toString();
        String repswd= repwd.getEditText().getText().toString();
        UserHelperClass hlp=new UserHelperClass(unames,kmail,phoneno,pswd,repswd);
        ref.child(unames).setValue(hlp);

        Toast toast = Toast.makeText(getApplicationContext(), "Sign_up Is Successful", Toast.LENGTH_SHORT);
        toast.show();

    }




}