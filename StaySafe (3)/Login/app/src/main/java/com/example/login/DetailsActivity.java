package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DatabaseHelper db = new DatabaseHelper(this);
        ListView lv = findViewById(R.id.user_list);

        ArrayList<HashMap<String, String>> userList = db.GetUsers();

        ListAdapter adapter = new SimpleAdapter(DetailsActivity.this, userList, R.layout.list_row,new String[]{"id","name","email","password"}, new int[]{R.id.id, R.id.name, R.id.email,R.id.password});
        lv.setAdapter(adapter);
        Button back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailsActivity.this,Admin.class);
                startActivity(intent);
            }
        });

    }
}
