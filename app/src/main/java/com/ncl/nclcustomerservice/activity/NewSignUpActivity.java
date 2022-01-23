package com.ncl.nclcustomerservice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewSignUpActivity extends AppCompatActivity implements RetrofitResponseListener {

    @BindView(R.id.textInput_username)
    TextInputLayout textInput_username;
    @BindView(R.id.textInput_email)
    TextInputLayout textInput_email;
    @BindView(R.id.textInput_password)
    TextInputLayout textInput_password;
    @BindView(R.id.textInput_confirmpassword)
    TextInputLayout textInput_confirmpassword;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_passsword)
    EditText confirm_password;
    @BindView(R.id.signup)
    TextView signup;
    @BindView(R.id.account)
    TextView account;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sign_up);
        ButterKnife.bind(this);

        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (email.getText().toString().matches(emailPattern) && s.length() > 0) {
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

    }

    private boolean isVailidated() {
        boolean isValidated = true;
        if (username.getText().toString().trim().length() == 0) {
            textInput_username.setError("Please fill username");
            textInput_username.requestFocus();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            textInput_username.startAnimation(shake);
            isValidated = false;
        } else if (!email.getText().toString().matches(emailPattern)) {
            textInput_email.setError("Please Enter vaild Email address");
            textInput_email.requestFocus();
            textInput_username.setError(null);
            isValidated = false;
        } else if (password.getText().toString().trim().length() == 0) {
            textInput_password.setError("Please fill password");
            textInput_password.requestFocus();
            textInput_email.setError(null);
            isValidated = false;
        } else if (confirm_password.getText().toString().trim().length() == 0) {
            textInput_confirmpassword.setError("Please fill confirm password");
            textInput_confirmpassword.requestFocus();
            textInput_password.setError(null);
            isValidated = false;

        } else if (!password.getText().toString().trim().equals(confirm_password.getText().toString().trim())) {
            textInput_confirmpassword.setError("Both Paswords should be Same");
            textInput_confirmpassword.requestFocus();
            isValidated = false;
        }
        return isValidated;
    }

    @OnClick(R.id.signup)
    void setSignup() {
        if (!isVailidated()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    @OnClick(R.id.account)
    void setAccount() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {

    }
}
