package com.example.myapplication;

public class UserHelperClass {
    String unames,kmail,phoneno,pswd,repswd;

    public UserHelperClass() {
    }

    public UserHelperClass(String unames, String kmail, String phoneno, String pswd, String repswd) {
        this.unames=unames;
        this.kmail=kmail;
        this.phoneno=phoneno;
        this.pswd=pswd;
        this.repswd = repswd;
    }

    public String getUname() {
        return unames;
    }

    public void setUname(String uname) {
        this.unames = uname;
    }

    public String getMail() {
        return kmail;
    }

    public void setMail(String mail) {
        this.kmail = mail;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getRepswd() {
        return repswd;
    }

    public void setRepswd(String repswd) {
        this.repswd = repswd;
    }
}
