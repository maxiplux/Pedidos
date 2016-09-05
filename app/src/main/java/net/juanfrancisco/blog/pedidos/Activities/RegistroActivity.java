package net.juanfrancisco.blog.pedidos.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import net.juanfrancisco.blog.pedidos.Models.ModelSimpleToken;
import net.juanfrancisco.blog.pedidos.R;

import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity implements Validator.ValidationListener {


    private String URL_BASE = "http://boiling-harbor-95559.herokuapp.com/";
    private String LOGIN_URI = "rest-auth/login/";
    private String REGISTER_URI = "rest-auth/registration/";
    private String LOGOUT_URI = "rest-auth/registration/";

    @InjectView(R.id.TxtNombre)     EditText TxtNombre;


    @NotEmpty
    @Email
    @InjectView(R.id.TxtEmail)     EditText inputEmail;

    @NotEmpty
    @InjectView(R.id.TxtPassword)    EditText TxtPassword;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @InjectView(R.id.BtnCrearCuenta)
    AppCompatButton BtnCrearCuenta;
    @InjectView(R.id.BtnLogin)
    AppCompatButton BtnLogin;





    Validator validator;


    ModelSimpleToken model_simple_token;

    private ProgressBar spinner;
    SharedPreferences pref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        model_simple_token=new ModelSimpleToken();



        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode






    }

    @OnClick({R.id.BtnCrearCuenta, R.id.BtnLogin})
    public void onClick(View view)
    {


        switch (view.getId())
        {
            case R.id.BtnCrearCuenta:
                CrearCuenta();
                break;
            case R.id.BtnLogin:
                Login();
                break;
        }
    }

    private void Login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void CrearCuenta()
    {

        spinner.setVisibility(View.VISIBLE);
        //validator.validate();
        //Intent intent = new Intent(this, ListaProductosActivity.class);
        //startActivity(intent);





        String url = URL_BASE + REGISTER_URI;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        String email = this.inputEmail.getText().toString(); ;
        String Username = this.inputEmail.getText().toString();
        String Password = this.TxtPassword.getText().toString();;


        params.put("email", email);
        params.put("username", Username);
        params.put("password1", Password);
        params.put("password2", Password);






        //client.setMaxRetriesAndTimeout(10,30);
        client.post(url, params, new BaseJsonHttpResponseHandler()
        {


            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable
            {
                Gson gson = new GsonBuilder().create();

                JSONObject jsonObject = new JSONObject(rawJsonData);

                model_simple_token = gson.fromJson(jsonObject.toString(), ModelSimpleToken.class);

                Log.e("como fue", rawJsonData);
                Log.e("como fue", rawJsonData);
                Log.e("tokenvalido", String.valueOf(model_simple_token.tokenValido()));
                Log.e("email", String.valueOf(model_simple_token.getEmail().size()));
                Log.e("username", String.valueOf(model_simple_token.getUsername().size()));




                if (model_simple_token.tokenValido() == false )
                {

                    if (model_simple_token.getUsername().size() > 0)
                    {
                        runOnUiThread(new Runnable() {
                            public void run()
                            {
                                spinner.setVisibility(View.INVISIBLE);

                                new SweetAlertDialog(RegistroActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Usuario: "+model_simple_token.getUsernameMsg())
                                        .show();
                            }
                        });


                    }
                    else if (model_simple_token.getEmail().size() > 0)
                    {
                        runOnUiThread(new Runnable() {
                            public void run()
                            {
                                spinner.setVisibility(View.INVISIBLE);
                                new SweetAlertDialog(RegistroActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Email: "+model_simple_token.getEmailMsg())
                                        .show();
                            }
                        });

                    }

                    else if (model_simple_token.getPassword1().size() > 0)
                    {
                        runOnUiThread(new Runnable() {
                            public void run()
                            {
                                spinner.setVisibility(View.INVISIBLE);
                                new SweetAlertDialog(RegistroActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Password: "+model_simple_token.getPassword1Msg())
                                        .show();
                            }
                        });

                    }


                }
                else {



                    runOnUiThread(new Runnable()

                    {
                        public void run()
                        {
                            spinner.setVisibility(View.INVISIBLE);
                            new SweetAlertDialog(RegistroActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Exito")
                                    .setContentText("Usuario creado con exito!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                    {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog)
                                        {
                                            sDialog.dismissWithAnimation();

                                            SharedPreferences.Editor editor = pref.edit();

                                            editor.putString("TOKEN", model_simple_token.getKey()); // Storing boolean - true/false
                                            editor.commit(); // commit changes




                                            Intent intent = new Intent(getApplicationContext(), ListaProductosActivity.class);
                                            intent.putExtra("TOKEN", model_simple_token.getKey());
                                            startActivity(intent);

                                        }
                                    }).show();


                        }
                    });

                }

                return null;
            }



            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {


                // Handle resulting parsed JSON response here

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, String rawJsonData, Object errorResponse)

            {

                spinner.setVisibility(View.INVISIBLE);

                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("codigo", String.valueOf(statusCode));
                Log.e("el que", t.toString());
            }


        });

    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Ok! Podemos continuar!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }


    }
}
