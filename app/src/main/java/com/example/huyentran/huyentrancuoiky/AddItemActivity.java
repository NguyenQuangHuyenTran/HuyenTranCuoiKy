package com.example.huyentran.huyentrancuoiky;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.huyentran.huyentrancuoiky.model.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    List<String> catalogStrings;
    EditText edtMoney;
    EditText edtDate;
    EditText edtDescription;
    Button btnAdd;
    Spinner spinner;
    MySQLite sqLite;
    ImageView imageView;
    Calendar calendar;
    int UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        onInit();
        sqLite = new MySQLite(AddItemActivity.this);
        sqLite.OpenDB();
        calendar = Calendar.getInstance();
        MyApplication myApplication =  (MyApplication)getApplicationContext();
        UserId = myApplication.getUserId();
        onSetValue();
        ArrayAdapter spinnerAdapter = new ArrayAdapter(AddItemActivity.this,android.R.layout.simple_dropdown_item_1line,sqLite.getCatalogNamẹ());
        spinner.setAdapter(spinnerAdapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AddItemActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        edtDate.setText(new StringBuilder().append(i).append("-").append(i1 + 1).append("-").append(i2));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
//                dialog.getDatePicker().setMinDate(calendar.get(Calendar.DATE));
                dialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpendingModel model = validateForm();
                if (model != null){
                    int totalMoney = sqLite.getMoneyByUserIḍ̣(UserId);
                    int spendMoney = Integer.parseInt(edtMoney.getText().toString());
                    int caculate = totalMoney - spendMoney;
                    if (caculate >= 0){
                        long i = sqLite.addSpending(model);
                        if (i > 0){
                            long k = sqLite.updateUserMoney(UserId,caculate);
                            if (k > 0){
                                Toast.makeText(AddItemActivity.this,"Thêm chi tiêu thành công",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                Toast.makeText(AddItemActivity.this,"Thêm chi tiêu thất bại",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(AddItemActivity.this,"Thêm chi tiêu thất bại",Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(AddItemActivity.this,"Khoản chi tiêu vượt quá mức tiền",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public SpendingModel validateForm(){
        SpendingModel model = new SpendingModel();
        if (edtMoney.getText().toString().length() > 0){
            model.setMoney(Integer.parseInt(edtMoney.getText().toString()));
        }
        else {
            edtMoney.setError("Xin nhập số tiền");
            return  null;
        }
        if (edtDate.getText().toString().length() > 0){
            model.setDate(edtDate.getText().toString());
        }
        else{
            edtDate.setError("Xin nhập ngày chi tiêu");
            return  null;
        }
        model.setDescription(edtDescription.getText().toString());
        model.setUserId(UserId);
        int i = sqLite.getCatalogIdByName(spinner.getSelectedItem().toString());
        model.setCatalogId(i);
        return model;
    }

    private void onSetValue() {
        int i = sqLite.countCatalog();
        if (i < 1){
            sqLite.addCatalog("Ăn uống");
            sqLite.addCatalog("Hoá dơn và tiện ích");
            sqLite.addCatalog("Di chuyển");
            sqLite.addCatalog("Mua sắm");
            sqLite.addCatalog("Bạn bè và người yêu");
            sqLite.addCatalog("Giải trí");
            sqLite.addCatalog("Du lịch");
            sqLite.addCatalog("Sức khoẻ");
            sqLite.addCatalog("Kinh doanh");
            sqLite.addCatalog("Đầu tư");
            sqLite.addCatalog("Bảo hiểm");
            sqLite.addCatalog("Khoản chi khác");
        }
    }

    private void onInit() {
        edtMoney = findViewById(R.id.add_edt_money);
        edtDate = findViewById(R.id.add_edt_time);
        edtDescription = findViewById(R.id.add_edt_people);
        btnAdd = findViewById(R.id.add_btn_additem);
        spinner = findViewById(R.id.add_spn_catalory);
        imageView = findViewById(R.id.add_imv_datepicker);
    }
}
