package com.example.huyentran.huyentrancuoiky;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huyentran.huyentrancuoiky.model.MyApplication;
import com.example.huyentran.huyentrancuoiky.model.UserModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPassword;
    Button btnLogin;
    MySQLite sqLite;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLite = new MySQLite(MainActivity.this);
        sqLite.OpenDB();
        int count = sqLite.countUser();
        if (count < 1){
            sqLite.addUser("Tran","1606020066");
        }
        onInit();
    }

    public void onLogin(){
        if (onValidateForm()){
            UserModel model = sqLite.validateLogin(edtUserName.getText().toString(),edtPassword.getText().toString());
            if (model != null){
                if (checkBox.isChecked()){
                    sharedPreferences = getSharedPreferences("STORE_USER",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Id",model.getId());
                    editor.putString("Name",model.getUserName());
                    editor.commit();
                }
                Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                MyApplication myApplication = (MyApplication)getApplicationContext();
                myApplication.setUserId(model.getId());
                int Money = sqLite.getMoneyByUserIḍ̣(model.getId());
                Redirect(Money);
            }
//            if (edtUserName.getText().toString().equals("Tran") && edtPassword.getText().toString().equals("1606020066")){
//                Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
//                startActivity(intent);
//            }
            else{
                Toast.makeText(MainActivity.this,"Đăng nhập thất bại!",Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean onValidateForm() {
        if(edtUserName.getText().toString().length() < 1){
            edtUserName.setError("Vui lòng nhập tên");
            return false;
        }
        if(edtPassword.getText().toString().length() < 1){
            edtPassword.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        return  true;
    }

    private void onInit() {
        sharedPreferences = getSharedPreferences("STORE_USER",MODE_PRIVATE);
        if(sharedPreferences != null && sharedPreferences.getString("Name","").length() > 0){
            int UserId = sharedPreferences.getInt("Id",0);
            MyApplication myApplication = (MyApplication)getApplicationContext();
            myApplication.setUserId(UserId);
            int Money = sqLite.getMoneyByUserIḍ̣(UserId);
            Redirect(Money);
        }
        else {
            edtUserName = findViewById(R.id.edtUserName);
            edtPassword = findViewById(R.id.edtPassword);
            btnLogin = findViewById(R.id.main_btnLogin);
            checkBox = findViewById(R.id.ckbRemember);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLogin();
                }
            });
        }
    }

    private void Redirect(int Money){
        if (Money == 0){
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(MainActivity.this,MainTabControlActivity.class);
            startActivity(intent);
        }
    }

}
