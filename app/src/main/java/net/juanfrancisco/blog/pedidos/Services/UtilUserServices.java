package net.juanfrancisco.blog.pedidos.Services;

import android.net.PskKeyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONObject;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobsandgeeks.saripaar.annotation.Password;

import net.juanfrancisco.blog.pedidos.Models.ModelSimpleToken;
import net.juanfrancisco.blog.pedidos.Models.Token;

import org.json.JSONObject;

/**
 * Created by franc on 02/09/2016.
 */
public class UtilUserServices
{

    private String URL_BASE="http://boiling-harbor-95559.herokuapp.com/";
    private String LOGIN_URI = "rest-auth/login/";
    private String REGISTER_URI = "rest-auth/registration/";
    private String LOGOUT_URI = "rest-auth/registration/";

    public UtilUserServices() {
    }

    public  ModelSimpleToken registerUsername(String Username, String  Password,String email )
    {


        String url = URL_BASE + REGISTER_URI;
        final ModelSimpleToken[] model_simple_token = {new ModelSimpleToken()};

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("username", Username);
        params.put("password", Password);
        params.put("password", Password);



        //client.setMaxRetriesAndTimeout(10,30);
        client.post(url, params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)

            {
                 Gson gson = new GsonBuilder().create();

                 model_simple_token[0] = (ModelSimpleToken) gson.fromJson(response.toString(), ModelSimpleToken.class);

                // Handle resulting parsed JSON response here
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("codigo", String.valueOf(statusCode));
                Log.e("el que", t.toString());
            }
        });

        return model_simple_token[0];



    }


}
