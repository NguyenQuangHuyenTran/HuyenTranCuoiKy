package com.example.huyentran.huyentrancuoiky;

import android.content.Context;
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


public class PreviousFragment extends Fragment {
    ListView listViewPrevious;
    Button btnAdd;
    MainTabControlActivity mainTabControlActivity;
    MySQLite sqLite;
    MyAdapter adapter;
    List<SpendingModel> models;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        listViewPrevious = getActivity().findViewById(R.id.privious_lv);
        mainTabControlActivity = (MainTabControlActivity)getActivity();
        sqLite = mainTabControlActivity.sqLite;
        models = new ArrayList<>();
        models = sqLite.getSpendingByType(0,mainTabControlActivity.currentMonthDate.toString(),mainTabControlActivity.UserId);
        adapter = new MyAdapter(getContext(),R.layout.spending_item,models);
        listViewPrevious.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (models == null){
            models = new ArrayList<>();
        }
        models.clear();
        models.addAll(sqLite.getSpendingByType(0,mainTabControlActivity.currentMonthDate.toString(),mainTabControlActivity.UserId));
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
        else{
            if (mainTabControlActivity.modelList.size() > 0){
                adapter = new MyAdapter(getContext(),R.layout.spending_item,models);
                listViewPrevious.setAdapter(adapter);
            }
        }
    }
}
