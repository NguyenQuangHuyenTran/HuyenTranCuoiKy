package com.example.huyentran.huyentrancuoiky;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huyentran.huyentrancuoiky.model.MyApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainTabControlActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    public static TextView tvTotalMoney;
    public MySQLite sqLite;
    int UserId;
    ImageView imageView;
    StringBuilder currentMonthDate;
    List<SpendingModel> modelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab_control);
        modelList = new ArrayList<>();
        onInit();
        sqLite = new MySQLite(MainTabControlActivity.this);
        sqLite.OpenDB();
        mTabLayout.setupWithViewPager(mViewPager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PreviousFragment(),"Previous Month");
        adapter.addFragment(new RecentFragment(),"Recent Month");
        mViewPager.setAdapter(adapter);
        tvTotalMoney.setText(String.valueOf(sqLite.getMoneyByUserIḍ̣(UserId)));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTabControlActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        Calendar calendar = Calendar.getInstance();
        currentMonthDate = new StringBuilder().append(calendar.get(Calendar.YEAR)).append("-").append(calendar.get(Calendar.MONTH ) + 1).append("-").append("01");

    }

    private void onInit() {
        mTabLayout = findViewById(R.id.mTabLayout);
        mViewPager = findViewById(R.id.mViewPager);
        tvTotalMoney = findViewById(R.id.tv_totalMoney);
        imageView = findViewById(R.id.imv_addMoney);
        MyApplication myApplication = (MyApplication)getApplicationContext();
        UserId = myApplication.getUserId();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvTotalMoney.setText(String.valueOf(sqLite.getMoneyByUserIḍ̣(UserId)));
    }
}
