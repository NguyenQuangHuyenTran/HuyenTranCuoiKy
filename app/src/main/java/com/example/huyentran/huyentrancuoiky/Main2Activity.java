package com.example.huyentran.huyentrancuoiky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huyentran.huyentrancuoiky.model.MyApplication;

public class Main2Activity extends AppCompatActivity {

    EditText edtSD;
    Button btnAddSD;
    int UserId;
    MySQLite sqLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edtSD = findViewById(R.id.EdtSD);
        btnAddSD = findViewById(R.id.btn_addSD);
        MyApplication myApplication = (MyApplication)getApplicationContext();
        UserId = myApplication.getUserId();
        sqLite = new MySQLite(Main2Activity.this);
        sqLite.OpenDB();
        btnAddSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSD.getText().toString().length() > 0){
                    int currentMoney = sqLite.getMoneyByUserIḍ̣(UserId);
                    int totalMoney = currentMoney + Integer.parseInt(edtSD.getText().toString());
                    long i = sqLite.updateUserMoney(UserId,totalMoney);
                    if (i > 0){
                        Toast.makeText(Main2Activity.this,"Thêm số dư thành công",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Main2Activity.this,MainTabControlActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Main2Activity.this,"Thêm số dư thất bại",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    edtSD.setError("Hãy nhập số dư");
                }
            }
        });
    }
}
