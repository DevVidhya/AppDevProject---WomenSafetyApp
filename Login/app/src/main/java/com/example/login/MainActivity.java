package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    String pass;
    Cursor ans1;
    private EditText userMail;
    private EditText Password;
    private Button Login;
    private TextView Info, Reg;
    private int counter = 5;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(this);


        userMail = (EditText)findViewById(R.id.etUsermail);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btLogin);
        Info = (TextView)findViewById(R.id.tvInfo);
        Reg = (TextView)findViewById(R.id.tvRegister);
        pref= getSharedPreferences("user_details",MODE_PRIVATE);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userMail.getText().toString(), Password.getText().toString());
            }
        });

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
    }

    private void validate(String mail, String password){

        if(mail.equals("admin@nitt.edu") && password.equals("nitt")){
            Intent intent = new Intent(MainActivity.this, Admin.class);
            startActivity(intent);
        }
        else{
            ans1 = mydb.getPassword(mail);
            if(ans1.getCount()==0){
                Toast.makeText(MainActivity.this,"Register to login",Toast.LENGTH_LONG).show();
            }
            else{
                while(ans1.moveToNext()){
                    pass = ans1.getString(3);
                }
                if(password.equals(pass)) {
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("mail",mail);
                    editor.putString("password",password);
                    editor.commit();
                    Toast.makeText(MainActivity.this,"Login Successful!",Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(MainActivity.this, Student.class);
                    startActivity(intent1);
                }
                else {
                    Toast.makeText(MainActivity.this,"Invalid Credentials!",Toast.LENGTH_LONG).show();
                    counter--;
                    Info.setText("Number of attempts remaining : " + String.valueOf(counter));

                    if (counter == 0) {
                        Login.setEnabled(false);
                    }
                }
            }
        }
    }
}
