package com.example.naderabdelkefi.bookyourplace;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUsername, editTextEmail,editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser ru=new RegisterUser();
                ru.execute();}



        });
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }
    class RegisterUser extends AsyncTask<Void, Void, String> {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) { // TO DO
            //creating request handler object
            RequestHandler req=new RequestHandler();


            //creating request parameters
            HashMap<String,String> params=new HashMap<>();
            params.put("username",username);
            params.put("email",email);
            params.put("password",password);

            //returing the response
            return  req.sendPostRequest("http://192.168.1.6:80/android/Register.php",params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject obj = null;
            try {
                obj = new JSONObject(s);
                if(obj.getString("message").equals("User registred")){
                    JSONObject userJson = obj.getJSONObject("user");
//creating a new user object
                    User user = new User(
                            userJson.getString("username"),
                            userJson.getString("password"),
                            userJson.getString("email")
                    );
                    Toast.makeText(RegisterActivity.this, "\n" +
                            "user added successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}

