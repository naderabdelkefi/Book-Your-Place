package com.example.naderabdelkefi.bookyourplace;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Button signUpButton ;
    EditText editTextUsername, editTextPassword;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signUpButton=(Button)findViewById(R.id.signUpButton);
        editTextUsername=(EditText)findViewById(R.id.editTextUsername);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        login=(Button)findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser lu=new LoginUser();
                lu.execute();

            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
        findViewById(R.id.learnMoreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                //TO DO
                finish();
                startActivity(new Intent(LoginActivity.this, LearnActivity.class));

            }
        });

    }
    class LoginUser extends AsyncTask<Void, Void, String> {
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler req=new RequestHandler();


            //creating request parameters
            HashMap<String,String> params=new HashMap<>();
            params.put("username",username);
            params.put("password",password);

            //returing the response
            return  req.sendPostRequest("http://192.168.1.6:80/android/Login.php",params);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject obj = null;
            try {
                obj = new JSONObject(s);
                if(obj.getString("message").equals("Success to login")){
                    startActivity(new Intent(LoginActivity.this, ReservationActivity.class));
                    JSONObject userJson = obj.getJSONObject("user");
//creating a new user object
                    User user = new User(
                            userJson.getString("username"),
                            userJson.getString("password"),
                            userJson.getString("email")
                    );

                }
                else{
                    Toast.makeText(LoginActivity.this, "\n" +
                            "Check your UserName or Password", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
