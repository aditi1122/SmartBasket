package com.wordpress.csediwakar.smartbasket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity
{
    TextView _email;
    TextView _password;
    Button _login;
    private final String em="d@g.com";
    private  final String pas="1234";
    TextView _sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("0000");
        setContentView(R.layout.activity_login);

        _email =(TextView)findViewById(R.id.input_email);
        _password =(TextView)findViewById(R.id.input_password);
        _login =(Button)findViewById(R.id.btn_login);
        _sign=(TextView) findViewById(R.id.link_signup);

        _login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                _sign.append(_email.getText().toString());
                _sign.append(_password.getText().toString());

                _password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                System.out.println("1111");
                if(validate())
                {
                    System.out.println("22222");
                    /*final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 3000);
*/
                    System.out.println(em + "++++" +pas);
                    System.out.println(_email.getText() + "--1-" +_password.getText());
                    if(em.equals(_email.getText().toString()) &&
                            pas.equals(_password.getText().toString()))
                    {
                        System.out.println(_email.getText() + "-2--" +_password.getText());

                        Intent i = new Intent(Login.this,CustomerBasket.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Login Error", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });



    }

    public boolean validate() {
        boolean valid = true;

        String email = _email.getText().toString();
        String password = _password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _email.setError("enter a valid email address");
            valid = false;
        }
        else
        {
            _email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _password.setError(null);
        }

        return valid;
    }
}
