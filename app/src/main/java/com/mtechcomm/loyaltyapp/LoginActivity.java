package com.mtechcomm.loyaltyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mtechcomm.loyaltyapp.classes.AppUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    Button btnLoginActivity, btnSignUpActivity, btnAppLogin;
    ImageView imvLoginFacebook, imvLoginGoogle, imvLoginTwitter, imvLoginCompanyLogo;
    EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializations
        btnLoginActivity = findViewById(R.id.login_btnLogin_change);
        btnSignUpActivity = findViewById(R.id.login_btnSignup_change);
        btnAppLogin = (Button) findViewById(R.id.login_btn_login);
        imvLoginFacebook = (ImageView) findViewById(R.id.login_imv_facebook);
        imvLoginGoogle = (ImageView) findViewById(R.id.login_imv_google);
        imvLoginTwitter = (ImageView) findViewById(R.id.login_imv_twitter);
        imvLoginCompanyLogo = (ImageView) findViewById(R.id.login_imv_company_logo);

        edtUsername = findViewById(R.id.login_edt_username);
        edtPassword = findViewById(R.id.login_edt_password);

        btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "You are here already", Toast.LENGTH_SHORT).show();
            }
        });

        btnSignUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim);
                finish();
            }
        });

        imvLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LoginActivity.this, "Yet to be implemented", Toast.LENGTH_SHORT).show();
            }
        });

        imvLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Yet to be implemented", Toast.LENGTH_SHORT).show();
            }
        });

        imvLoginTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Yet to be implemented", Toast.LENGTH_SHORT).show();
            }
        });

        btnAppLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.getText().toString().length() < 1) {
                    edtUsername.setError("Username Required");
                } else if (edtPassword.getText().toString().length() < 1) {
                    edtPassword.setError("Password is Required");
                } else {
                    String email = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();

//                    new connectToRst(email, password).execute();
                    new getUserInfo(email, password).execute();

                }


            }
        });
    }

    private class connectToRst extends AsyncTask<Void, Void, JSONArray>{
        private ProgressDialog mProgressDialog;
        private String email;
        private String password;

        public connectToRst(String email, String password){
            this.email = email;
            this.password = password;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = ProgressDialog.show(
                    LoginActivity.this,
                    "Please wait", // Title
                    "Checking",
                    true);

        }

        @Override
        protected JSONArray doInBackground(Void... aVoid) {

            try {

                Log.d("Clicked +++", "onClick: ");
                URL url = new URL("https://ebeano-7383.restdb.io/rest/Users?q={\"email\": \"" + email + "\", \"password\" : \"" + password + "\"}");
                HttpURLConnection client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");
                client.setRequestProperty("Content-Type","application/json");
                client.setRequestProperty("x-apikey", "21ae9c1d8e28ce96587ac90f3dde2b9afe383");

                client.connect();
                String jsonReply;

                InputStream response = client.getInputStream();
                jsonReply = convertStreamToString(response);

                JSONArray jsonObject = new JSONArray(jsonReply);

                return jsonObject;

            } catch (Exception e) {
                Log.d("Error Message", "onClick: " + e.getMessage());
                Log.d("Error 2", "onClick: " + e.getStackTrace().toString());
                return null;
            }


        }

        @Override
        protected void onPostExecute(JSONArray array) {
            super.onPostExecute(array);

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            if (array != null ){
                Log.d("Request Response", "onClick: " + array.toString());

                Toast.makeText(LoginActivity.this, "Welcome online " + email,Toast.LENGTH_SHORT).show();

//                new getUserInfo(this.email).execute();

            }else{
                Toast.makeText(LoginActivity.this, "email or password may be wrong",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class getUserInfo extends AsyncTask<Void, Void, JSONArray>{
        private ProgressDialog mProgressDialog;
        private String email;
        private String password;

        public getUserInfo(String email, String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = ProgressDialog.show(
                    LoginActivity.this,
                    "Please wait", // Title
                    "Fetching Store Details",
                    true);

        }

        @Override
        protected JSONArray doInBackground(Void... aVoid) {

            try {

                Log.d("Clicked +++", "onClick: ");
                URL url = new URL("https://ebeano-7383.restdb.io/rest/Customers?q={\"email\": \"" + email + "\", \"password\" : \"" + password + "\"}");
                HttpURLConnection client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");
                client.setRequestProperty("Content-Type","application/json");
                client.setRequestProperty("x-apikey", "21ae9c1d8e28ce96587ac90f3dde2b9afe383");

                client.connect();
                InputStream response = client.getInputStream();

                String jsonReply;
                jsonReply = convertStreamToString(response);

                JSONArray jsonObject = new JSONArray(jsonReply);

                Log.d("Response Message", "onClick: " + jsonObject.toString());

                return jsonObject;

            } catch (Exception e) {
                Log.d("Error Message", "onClick: " + e.getMessage());
                Log.d("Error 2", "onClick: " + e.getStackTrace().toString());
                return null;
            }


        }

        @Override
        protected void onPostExecute(JSONArray array) {
            super.onPostExecute(array);

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            if (array != null && array.length() > 0){
                Log.d("Request Response", "onClick: " + array.toString());

                Toast.makeText(LoginActivity.this, "Store Details for " + email + " has been fetched.",Toast.LENGTH_SHORT).show();

                JSONObject eachUser = new JSONObject();

                for (int i = 0; i <= array.length(); i++){
                   try {
                       eachUser = array.getJSONObject(i);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                }

                if (eachUser != null){
                    Log.d("Scailed", "onPostExecute: " + eachUser.toString());
                    try {
                        AppUser user = new AppUser(eachUser.getString("email"),eachUser.getString("phone_number"),(eachUser.getString("last_name") + " " + eachUser.getString("first_name")), eachUser.getString("date_registered"), eachUser.getString("customer_id"), eachUser.getInt("reward_points"), eachUser.getInt("points_this_month"), eachUser.getString("store_credit"));

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",user);

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }catch (Exception e){
                        Log.d("error", "onPostExecute: " + e.getMessage());
                        e.printStackTrace();
                    }

                }

//                overridePendingTransition(R.anim.slide_right_anim, R.anim.slide_left_anim);

            }else{
                Toast.makeText(LoginActivity.this, "Username or password might have been wrong",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
