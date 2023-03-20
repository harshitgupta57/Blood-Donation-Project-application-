package com.example.blooddonationapp.Model;

public class users {
    String name,emailid,bloodgroup,address,phonenumber,type;

    public users() {
    }

    public users(String name, String emailid, String bloodgroup, String address, String phonenumber, String type) {
        this.name = name;
        this.emailid = emailid;
        this.bloodgroup = bloodgroup;
        this.address = address;
        this.phonenumber = phonenumber;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
