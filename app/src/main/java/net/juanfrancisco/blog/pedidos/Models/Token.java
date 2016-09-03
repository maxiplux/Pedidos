package net.juanfrancisco.blog.pedidos.Models;

/**
 * Created by franc on 02/09/2016.
 */
public class Token
{
    String token;

    public Token() {
    }

    public Token(String token)
    {
        this.token=token;
    }


    public String getTokenFull()
    {
        return "TOKEN "+token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
