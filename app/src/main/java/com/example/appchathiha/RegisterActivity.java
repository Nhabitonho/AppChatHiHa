package com.example.appchathiha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    TextInputLayout email,username,password;
    Button mBtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolBar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng Kí");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.textUserName);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassWord);

        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getEditText().getText().toString();
                String txt_email = email.getEditText().getText().toString();
                String txt_pass = password.getEditText().getText().toString();

                if(txt_username.isEmpty() || txt_email.isEmpty() || txt_pass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Nhập Đầy Đủ Thông Tin !!", Toast.LENGTH_SHORT).show();
                }else if(txt_pass.length()<6){
                    Toast.makeText(RegisterActivity.this, "Mật Khẩu Phải Nhiều Hơn 6 Kí Tự", Toast.LENGTH_SHORT).show();
                }else{
                    register(txt_username, txt_email, txt_pass);
                }
            }
        });
    }
    private void register(final String username, String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            Map<String,String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username",username);
                            hashMap.put("imageURL","default");
                            db.getReference("Users").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    if(task.isSuccessful()){
                                  Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                  Toast.makeText(RegisterActivity.this, "Đăng Kí Thành Công ", Toast.LENGTH_SHORT).show();
                                  startActivity(intent);
                                  finish();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}