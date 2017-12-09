package net.juanfrancisco.blog.pedidos.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.mobsandgeeks.saripaar.annotation.Password;

import net.juanfrancisco.blog.pedidos.Models.ModelSimpleToken;
import net.juanfrancisco.blog.pedidos.R;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {


    public ProgressDialog loginDialog;
    @NotEmpty
    @Email(sequence = 1, message = "El email es obligatorio")
    @BindView(R.id.input_email)
    EditText inputEmail;
    @NotEmpty
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.BtnLogin)
    AppCompatButton BtnLogin;
    @BindView(R.id.BtnRegistrar)
    TextView linkSignup;
    Validator validator;
    Context context;
    SweetAlertDialog dialogo;
    ModelSimpleToken model_simple_token;
    SharedPreferences pref ;
    String token = "";
    private String URL_BASE = "http://servicios.testbox.site/";
    private String LOGIN_URI = "rest-auth/login/";
    private String REGISTER_URI = "rest-auth/registration/";
    private String LOGOUT_URI = "rest-auth/registration/";
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context=this;
        loginDialog = new ProgressDialog( getApplicationContext());




        validator = new Validator(this);
        validator.setValidationListener(this);


        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        token = pref.getString("TOKEN", null); // si tiene token enviarlo a la lista
        if (token != null)
        {
            if (token.length() > 30) {
                Intent intent = new Intent(getApplicationContext(), ListaProductosActivity.class);
                intent.putExtra("TOKEN", token);
                startActivity(intent);
            }


        }




    }


    @OnClick({R.id.BtnLogin, R.id.BtnRegistrar})
    public void onClick(View view) {



        switch (view.getId()) {

            case R.id.BtnLogin:
                Login(view);
                break;

            case R.id.BtnRegistrar:
                Registrar(view);
                break;

        }


    }


    private void Registrar(View view) {

        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }

    private void Login(View view)
    {


        spinner.setVisibility(View.VISIBLE);

        String url = URL_BASE + LOGIN_URI;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        String Username = this.inputEmail.getText().toString();
        String Password = this.inputPassword.getText().toString();


        params.put("username", Username);
        params.put("password", Password);



        //client.setMaxRetriesAndTimeout(10,30);
        client.post(url, params, new BaseJsonHttpResponseHandler()
        {


            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable
            {
                Gson gson = new GsonBuilder().create();

                JSONObject jsonObject = new JSONObject(rawJsonData);

                model_simple_token = gson.fromJson(jsonObject.toString(), ModelSimpleToken.class);




                if (model_simple_token.tokenValido() == false )
                {

                    if (model_simple_token.getUsername().size() > 0)
                    {
                        runOnUiThread(new Runnable() {
                            public void run()
                            {

                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(model_simple_token.getUsernameMsg())
                                        .show();
                            }
                        });


                    }
                    else if (model_simple_token.getNon_field_errors().size() > 0)
                    {
                        runOnUiThread(new Runnable() {
                            public void run()
                            {
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Error"+model_simple_token.getNon_field_errorsMsg())
                                        .show();
                            }
                        });

                    }

                    else if (model_simple_token.getPassword1().size() > 0)
                    {
                        runOnUiThread(new Runnable() {
                            public void run()
                            {
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(model_simple_token.getPassword1Msg())
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
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Exito")
                                    .setContentText("Bienvenido!")
                                    .setConfirmClickListener(
                                            new SweetAlertDialog.OnSweetClickListener()
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
            public void onFailure(int statusCode, Header[] headers, Throwable t, String rawJsonData, Object errorResponse) {

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

        for (ValidationError error : errors)
        {
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
