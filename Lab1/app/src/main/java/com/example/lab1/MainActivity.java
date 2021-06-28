package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout main_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_container = (LinearLayout) findViewById(R.id.mainContainer);

    }
    public void AddNew(View view) {
        EditText et = (EditText)findViewById(R.id.edittxtview);
        if(et.getText().toString().equalsIgnoreCase("h")){
            //Add number button
            addNumberButton();
        } else {
            //Add text button
            addTextButton(view);
        }
    }

    public void AddNewInLandscape(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.su_logo);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200,200);
        imageView.setLayoutParams(lp);
        LinearLayout layout = (LinearLayout)findViewById(R.id.main_container);
        layout.addView(imageView);
    }

    public void addTextButton(View view) {
        //Get EditText object
        ViewGroup parent = (ViewGroup)  view.getParent();
        EditText et = (EditText) parent.getChildAt(0);

        //Get text
        String txt = et.getText().toString();


        //LinearLayout
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(0,5,0,0);
        ll.setWeightSum(3);

        //EditText
        EditText editText = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
        editText.setLayoutParams(params);
        Button b = new Button(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        b.setLayoutParams(params2);

        //Set text
        b.setText(txt);

        //Add text to view
        ll.addView(editText);
        ll.addView(b);

        //Add OnClick listener
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get EditText object
                ViewGroup parent = (ViewGroup)  v.getParent();
                EditText et = (EditText) parent.getChildAt(0);
                if(et.getText().toString().equalsIgnoreCase("h")){
                    addNumberButton();
                } else {
                    addTextButton(v);
                }
            }
        });

        //Add ViewGroup to root
        main_container.addView(ll);
    }

    public void addNumberButton() {
        //Horizontal Scroll View
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
        LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        horizontalScrollView.setLayoutParams(scrollViewParams);

        //Crate linear layout view group
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(0,5,0,0);

        //Create new button
        Button b = new Button(this);
        Integer count=ll.getChildCount()+1;
        b.setText(count.toString());

        //Add onClick Listener
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = new Button(MainActivity.this);
                Integer count = ll.getChildCount()+1;
                b.setText(count.toString());
                b.setEnabled(false);
                ll.addView(b);
            }
        });


        //Add button to ll view group
        ll.addView(b);

        //Insert ll into horizontal scroll and add horizontal scroll to main
        horizontalScrollView.addView(ll);
        main_container.addView(horizontalScrollView);
    }


}
