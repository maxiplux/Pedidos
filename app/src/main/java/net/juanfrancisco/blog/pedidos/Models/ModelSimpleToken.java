package net.juanfrancisco.blog.pedidos.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by franc on 02/09/2016.
 */
public class ModelSimpleToken {
    public ModelSimpleToken()
    {

        non_field_errors=new  ArrayList<String>();
        detail="";
        key="";
        username=new  ArrayList<String>();
        password1=new  ArrayList<String>();
        password2=new  ArrayList<String>();
        email=new  ArrayList<String>();
       Token token=new Token();

    }

    @Override
    public String toString() {
        return "ModelSimpleToken{" +
                "non_field_errors=" + non_field_errors +
                ", detail='" + detail + '\'' +
                ", key='" + key + '\'' +
                ", username=" + username.toString() +
                ", password1=" + password1.toString() +
                ", password2=" + password2.toString() +
                ", email=" + email +
                '}';
    }

    private List<String> non_field_errors;
    private String detail;
    private String key;

    private List<String> username;
    private List<String> password1;
    private List<String> password2;
    private List<String> email;
    private Token token=new Token();



    public boolean tokenValido()
    {
        return key.trim().length()!=0;
    }

    public Token mytoken()
    {
        if (key.trim().length()==0)
        {
            token.setToken(key);
            return token;
        }
        return token;

    }


    public List<String> getNon_field_errors()
    {
        return non_field_errors;
    }

    public void setNon_field_errors(List<String> non_field_errors) {
        this.non_field_errors = non_field_errors;
    }


    public String getNon_field_errorsMsg()
    {
        if (non_field_errors.size()>0)
        {
            return  this.getNon_field_errors().get(0);
        }
        return "";
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getUsername() {
        return username;
    }

    public String getUsernameMsg()
    {
        if (username.size()>0)
        {
            return  this.getUsername().get(0);
        }
        return "";
    }

    public String getEmailMsg()
    {
        if (email.size()>0)
        {
            return  this.getEmail().get(0);
        }
        return "";
    }



    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getPassword1()
    {
        return password1;
    }


    public String getPassword1Msg()
    {
        if (password1.size()>0)
        {
            return  this.getPassword1().get(0);
        }
        return "";
    }


    public void setPassword1(List<String> password1) {
        this.password1 = password1;
    }

    public List<String> getPassword2() {
        return password2;
    }

    public void setPassword2(List<String> password2) {
        this.password2 = password2;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }






}
