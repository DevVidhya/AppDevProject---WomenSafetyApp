package com.example.login.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.login.DatabaseHelper;
import com.example.login.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.login.DatabaseHelper.col1;
import static com.example.login.DatabaseHelper.col2;
import static com.example.login.DatabaseHelper.col3;
import static com.example.login.DatabaseHelper.col4;

public class MyAdapter extends BaseAdapter {

    Context context;
    DatabaseHelper db ;
    ArrayList<HashMap<String, String>> userList = db.GetUsers();
    public MyAdapter(Context context,ArrayList<HashMap<String,String>> userList)
    {
        this.context=context;
        this.userList=userList;
    }
    public String getItemId(String position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.list_row,null);
            TextView t1_id = (TextView)convertView.findViewById(R.id.id);
            TextView t2_id =(TextView)convertView.findViewById(R.id.name);
            TextView t3_id = (TextView)convertView.findViewById(R.id.email);
            TextView t4_id =(TextView)convertView.findViewById(R.id.password);

           HashMap<String,String> user = userList.get(position);


            t1_id.setText((user.get("id")));



        return null;
    }

    @Override
    public int getCount() {
        return this.userList.size();
    }
}
