package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewContact extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mLastnmaeEditText;
    private EditText mPhoneEditText;
    private Button mAddBtn;
    private MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_layout);
        mNameEditText = (EditText)findViewById(R.id.contactName);
        mLastnmaeEditText = (EditText)findViewById(R.id.contactLastname);
        mPhoneEditText = (EditText)findViewById(R.id.contactPhone);
        mAddBtn = (Button)findViewById(R.id.addNewContactButton);
        mAddBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveContact();
            }
        });
    }
    private void saveContact(){
        String name = mNameEditText.getText().toString().trim();
        String lastname = mLastnmaeEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
        dbHelper = new MyDBHelper(this);
        if(name.isEmpty()){
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastname.isEmpty()){
            Toast.makeText(this, "You must enter a lastname", Toast.LENGTH_SHORT).show();
        }
        if(phone.isEmpty()){
            Toast.makeText(this, "You must enter a phone number", Toast.LENGTH_SHORT).show();
        }
        Contact c = new Contact(name, lastname, phone);
        dbHelper.saveNewContact(c, this);
        finish();
    }
}
