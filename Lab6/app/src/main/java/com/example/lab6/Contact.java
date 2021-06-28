package com.example.lab6;

public class Contact {
    private long id;
    private String name;
    private String lastname;
    private String phone_number;
    public Contact(){}
    public Contact(String name, String lastname, String phone_number){
        this.name = name;
        this.lastname = lastname;
        this.phone_number = phone_number;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
