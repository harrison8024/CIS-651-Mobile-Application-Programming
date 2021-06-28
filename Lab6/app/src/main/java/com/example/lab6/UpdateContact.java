package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContact extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mLastnameEditText;
    private EditText mPhoneEditText;
    private Button mUpdateBtn;
    private MyDBHelper dbHelper;
    private long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        mNameEditText = (EditText)findViewById(R.id.contactName);
        mLastnameEditText = (EditText)findViewById(R.id.contactLastname);
        mPhoneEditText = (EditText)findViewById(R.id.contactPhone);
        mUpdateBtn = (Button)findViewById(R.id.updateButton);
        dbHelper = new MyDBHelper(this);
        try {
            contactId = getIntent().getLongExtra("CONTACT_ID", 1);
        }catch (Exception e){
            e.printStackTrace();
        }
        Contact c = dbHelper.getContact(contactId);
        mNameEditText.setText(c.getName());
        mLastnameEditText.setText(c.getLastname());
        mPhoneEditText.setText(c.getPhone_number());
        mUpdateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateContact();
            }
        });
    }

    private void updateContact(){
        String name = mNameEditText.getText().toString().trim();
        String lastname = mLastnameEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }
        if(lastname.isEmpty()){
            Toast.makeText(this, "You must enter a age", Toast.LENGTH_SHORT).show();
        }
        if(phone.isEmpty()){
            Toast.makeText(this, "You must enter a occupation", Toast.LENGTH_SHORT).show();
        }
        Contact updatedContact = new Contact(name, lastname, phone);
        dbHelper.updateContact(contactId, this, updatedContact);
        finish();
    }
}