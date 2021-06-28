package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentTracker {
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private GestureDetectorCompat mDetector;
    public static PersonInfo pi=new PersonInfo();
    private int next=1;

    public static class Fragment1 extends Fragment {

        private FragmentTracker ft;
        private View v;
        public static final String fragmentTitle="Personal Info";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Toast.makeText(getContext(),"Visible",Toast.LENGTH_SHORT).show();
            ft.fragmentVisible(fragmentTitle);
            // Inflate the layout for this fragment
            v= inflater.inflate(R.layout.fragment_1, container, false);
            Button b_next=v.findViewById(R.id.next_button);
            b_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText uname=v.findViewById(R.id.u_name);
                    EditText lname=v.findViewById(R.id.u_lastname);

                    ft.saveNameAndLastName(uname.getText().toString(),lname.getText().toString());
                    ft.goNext();
                }
            });
            return v;
        }
        @Override
        public void onAttach(Context context){
            super.onAttach(context);
            try{
                ft=(FragmentTracker) context;
            }
            catch (ClassCastException ex)
            {
                throw new ClassCastException(context.toString()+"must implement FragmentTracker");
            }
        }
        @Override
        public void onDetach() {
            super.onDetach();

            v=null;
        }
    }
    public static class Fragment2 extends Fragment {

        private FragmentTracker ft;
        private View v;
        public static final String fragmentTitle="Personal Info";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //Toast.makeText(getContext(),"Visible",Toast.LENGTH_SHORT).show();
            ft.fragmentVisible(fragmentTitle);
            // Inflate the layout for this fragment
            v= inflater.inflate(R.layout.fragment_2, container, false);
            Button b_next=v.findViewById(R.id.next_button_2);
            b_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //save city and zip
                    EditText city=v.findViewById(R.id.city);
                    EditText zip=v.findViewById(R.id.zip);

                    ft.saveCityAndZip(city.getText().toString(), zip.getText().toString());
                    ft.goNext();
                }
            });
            Button b_back=v.findViewById(R.id.back_button_2);
            b_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { ft.goBack(); }
            });
            return v;
        }
        @Override
        public void onAttach(Context context){
            super.onAttach(context);
            try{
                ft=(FragmentTracker) context;
            }
            catch (ClassCastException ex)
            {
                throw new ClassCastException(context.toString()+"must implemet FragmentTracker");
            }
        }
        @Override
        public void onDetach() {
            super.onDetach();

            v=null;
//        Toast.makeText(getContext(),city.getText(),Toast.LENGTH_SHORT).show();
        }
    }
    public static class Fragment3 extends Fragment {

        private FragmentTracker ft;
        private View v;
        public static final String fragmentTitle = "Personal Info";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //Toast.makeText(getContext(),"Visible",Toast.LENGTH_SHORT).show();
            ft.fragmentVisible(fragmentTitle);
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_3, container, false);
            Button b_finish = v.findViewById(R.id.finish_button);
            b_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //save detail
                    EditText detail_1 = v.findViewById(R.id.detail_1);
                    EditText detail_2 = v.findViewById(R.id.detail_2);
                    EditText detail_3 = v.findViewById(R.id.detail_3);
                    EditText detail_4 = v.findViewById(R.id.detail_4);
                    EditText detail_5 = v.findViewById(R.id.detail_5);
                    String detail = detail_1.getText().toString() + detail_2.getText().toString()
                            + detail_3.getText().toString() + detail_4.getText().toString()
                            + detail_5.getText().toString();

                    ft.saveDetail(detail);
                    ft.finished();
                }
            });
            Button b_back = v.findViewById(R.id.back_button_3);
            b_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ft.goBack();
                }
            });

            return v;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try {
                ft = (FragmentTracker) context;
            } catch (ClassCastException ex) {
                throw new ClassCastException(context.toString() + "must implement FragmentTracker");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();

            v = null;
//        Toast.makeText(getContext(),uname.getText(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        mDetector = new GestureDetectorCompat(getApplicationContext(),new MyGestureListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTheFragment(fragment1);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if (event1.getX() < event2.getX()){
                Toast toast = Toast.makeText(MainActivity.this, "Fling right",
                        Toast.LENGTH_SHORT);
                 toast.show();
                goNext();
            }
            else
            {
                Toast toast = Toast.makeText(MainActivity.this, "Fling left",
                        Toast.LENGTH_SHORT);
                toast.show();
                goBack();
            }
            return true;
        }
    }

    private void loadTheFragment(Fragment f)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,f);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finished() {
        Intent i=new Intent(this,SummaryActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("pi",pi);
        startActivity(i);
    }

    public void fragmentVisible(String s){
        TextView tv = findViewById(R.id.title);
        tv.setText(s);
    }
    public void goNext(){
        if(next == 1){
            loadTheFragment(fragment2);
            next++;
        } else if(next == 2){
            loadTheFragment(fragment3);
            next++;
        }
    }
    public void goBack(){
        if(next ==2){
            loadTheFragment(fragment1);
            next--;
        } else if(next == 3){
            loadTheFragment(fragment2);
            next--;
        }
    }
    public void saveNameAndLastName(String name, String lname){
        pi.setName(name);
        pi.setLastname(lname);

    }
    public void saveCityAndZip(String city, String zip){
        pi.setCity(city);
        pi.setZip(zip);
    }
    public void saveDetail(String detail){
        pi.setDetail(detail);
    }



}