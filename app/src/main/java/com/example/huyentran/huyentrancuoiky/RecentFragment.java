package com.example.huyentran.huyentrancuoiky;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class RecentFragment extends Fragment {
    ListView listViewRecent;
    Button btnAdd;
    MainTabControlActivity mainTabControlActivity;
    MySQLite sqLite;
    MyAdapter adapter;
    List<SpendingModel> models;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        btnAdd = getActivity().findViewById(R.id.recent_btnAddItem);
        listViewRecent = getActivity().findViewById(R.id.recent_lv);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddItemActivity.class);
                startActivity(intent);
            }
        });
        mainTabControlActivity = (MainTabControlActivity)getActivity();
        sqLite = mainTabControlActivity.sqLite;
        models = sqLite.getSpendingByType(1,mainTabControlActivity.currentMonthDate.toString(),mainTabControlActivity.UserId);
        adapter = new MyAdapter(getContext(),R.layout.spending_item,models);
        listViewRecent.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (models == null){
            models = new ArrayList<>();
        }
        models.clear();
        models.addAll(sqLite.getSpendingByType(1,mainTabControlActivity.currentMonthDate.toString(),mainTabControlActivity.UserId));
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
        else{
            if (mainTabControlActivity.modelList.size() > 0){
                adapter = new MyAdapter(getContext(),R.layout.spending_item,models);
                listViewRecent.setAdapter(adapter);
            }
        }
    }
}
