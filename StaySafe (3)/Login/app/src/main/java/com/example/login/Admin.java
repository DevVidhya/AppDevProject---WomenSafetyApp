package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db;
    private Button viewData;
    private EditText EnterEmail;
    private Button delete;
    private Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        db = new DatabaseHelper(this);
        viewData = (Button) findViewById(R.id.button1);
        EnterEmail = (EditText) findViewById(R.id.etEnter);
        delete = (Button) findViewById(R.id.button2);
        log =(Button)findViewById(R.id.logout);

        viewData.setOnClickListener(this);
        delete.setOnClickListener(this);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences.Editor editor = prf.edit();
                //editor.clear();
                //editor.commit();
                Intent intent = new Intent(Admin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == viewData) {
            String email = EnterEmail.getText().toString();
            Cursor cursor = db.getData(email);
            if (cursor.getCount() == 0) {
                Toast.makeText(Admin.this, "No Data", Toast.LENGTH_LONG).show();
            } else {
                while (cursor.moveToNext()) {
                    Toast.makeText(Admin.this, "ID:" + cursor.getString(0) + "Username:" + cursor.getString(1) + " Mail:" + cursor.getString(2) + " ", Toast.LENGTH_LONG).show(); //" Password: " + cursor.getString(3) +
                }
                //Intent intent = new Intent(Admin.this, DetailsActivity.class);
                //startActivity(intent);

            }
        }

            if (v == delete) {
                if (EnterEmail.getText().toString().trim().length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter Email",
                            Toast.LENGTH_SHORT);

                    toast.show();

                    return;
                }
                boolean check = db.deleteValue(EnterEmail.getText().toString());
                if (check == true) {
                    Toast toast = Toast.makeText(this, "Success! record deleted",
                            Toast.LENGTH_SHORT);

                    toast.show();
                } else {
                    Toast toast = Toast.makeText(this,
                            "Invalid Email",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
            }


        }
    }

