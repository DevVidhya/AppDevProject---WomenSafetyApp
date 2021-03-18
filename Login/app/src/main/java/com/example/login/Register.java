package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper mydb1;

    private EditText Username, Email, Userpassword;
    private Button Register;
    private TextView AlreadyReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mydb1 = new DatabaseHelper(this);

        Username = (EditText)findViewById(R.id.etUserName);
        Email = (EditText)findViewById(R.id.etEmail);
        Userpassword = (EditText)findViewById(R.id.etUserPassword);
        Register = (Button)findViewById(R.id.btRegister);
        AlreadyReg = (TextView)findViewById(R.id.tvAlreadyReg);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAction();
            }
        });

        AlreadyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Register.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }

    private void databaseAction(){
        String name1 = Username.getText().toString();
        String email = Email.getText().toString();
        String password1 = Userpassword.getText().toString();

        if(name1.isEmpty() || email.isEmpty() || password1.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else {
            boolean isInserted = mydb1.insertData(name1, email, password1);
            if(isInserted)
                Toast.makeText(this, "Successfully Registered !", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Registrartion Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
}
