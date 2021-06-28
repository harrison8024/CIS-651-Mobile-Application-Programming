package com.example.lab3;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonInfo implements Parcelable {
    private String name, lastname, city, zip, detail;
    public PersonInfo(){
        name="";
        lastname="";
        city="";
        zip="";
        detail ="";
    }
    public PersonInfo(String n, String l, String c, String z, String la){
        name=n;
        lastname=l;
        city=c;
        zip=z;
        detail =la;
    }
    public PersonInfo(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        name = data[0];
        lastname = data[1];
        city=data[2];
        zip=data[3];
        detail =data[4];
    }
    public void setName(String n){
        name=n;
    }
    public void setLastname(String l)
    {
        lastname=l;
    }
    public void setCity(String c)
    {
        city=c;
    }
    public void setZip(String z)
    {
        zip=z;
    }
    public void setDetail(String l){
        detail =l;
    }
    public String getName(){
        return name;
    }
    public String getLastname()
    {
        return lastname;
    }
    public String getCity()
    {
        return city;
    }
    public String getZip()
    {
        return zip;
    }
    public String getDetail(){
        return detail;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                name, lastname, city, zip, detail
        });
    }

    public static final Creator<PersonInfo>CREATOR = new Creator<PersonInfo>(){
        @Override
        public PersonInfo createFromParcel(Parcel parcel) {
            return new PersonInfo(parcel);
        }
        @Override
        public PersonInfo[] newArray(int i) {
            return new PersonInfo[i];
        }
    };

}
