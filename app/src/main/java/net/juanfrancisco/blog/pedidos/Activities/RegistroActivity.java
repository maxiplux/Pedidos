package net.juanfrancisco.blog.pedidos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import net.juanfrancisco.blog.pedidos.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegistroActivity extends AppCompatActivity implements Validator.ValidationListener {

    @InjectView(R.id.TxtNombre)     EditText TxtNombre;


    @NotEmpty
    @Email
    @InjectView(R.id.TxtEmail)     EditText TxtEmail;

    @NotEmpty
    @InjectView(R.id.TxtPassword)    EditText TxtPassword;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @InjectView(R.id.BtnCrearCuenta)
    AppCompatButton BtnCrearCuenta;
    @InjectView(R.id.BtnLogin)
    AppCompatButton BtnLogin;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.BtnCrearCuenta, R.id.BtnLogin})
    public void onClick(View view) {
        validator.validate();

        switch (view.getId()) {
            case R.id.BtnCrearCuenta:
                CrearCuenta();
            case R.id.BtnLogin:
                Login();
        }
    }

    private void Login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void CrearCuenta() {
        Intent intent = new Intent(this, ListaProductosActivity.class);
        startActivity(intent);
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
