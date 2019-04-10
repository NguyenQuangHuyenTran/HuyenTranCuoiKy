package com.example.huyentran.huyentrancuoiky;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<SpendingModel> {
    Context mContext;
    int mResource;
    List<SpendingModel> models;
    MySQLite sqLite;
    public MyAdapter(Context context, int resource, List<SpendingModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.models = objects;
        sqLite = new MySQLite(mContext);
        sqLite.OpenDB();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            convertView = LayoutInflater.from(mContext).inflate(mResource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvDate = convertView.findViewById(R.id.tv_datetime);
            viewHolder.tvCatalogName = convertView.findViewById(R.id.tv_catalogName);
            viewHolder.tvMoney = convertView.findViewById(R.id.tv_MoneySpending);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final SpendingModel model = getItem(position);
        viewHolder.tvDate.setText(model.getDate());
        viewHolder.tvMoney.setText(String.valueOf(model.getMoney()) + "VND");
        String CatalogName = sqLite.getCatalogNameById(model.getCatalogId());
        viewHolder.tvCatalogName.setText(CatalogName);
        return convertView;
    }
    class ViewHolder{
        TextView tvDate;
        TextView tvCatalogName;
        TextView tvMoney;
    }
}
