package com.spdcash.app_spdcash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText et_pseudo, et_email, et_password, et_password2;
    private RequestQueue queue;
    TextView tv_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_send = (Button) findViewById(R.id.btn_send);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_error = (TextView) findViewById(R.id.tv_error);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                tv_error.setText(email + " " + password);

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                String url = "http://192.168.1.55/test_qrcode/test_db2.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                                tv_error.setText(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv_error.setText(error.toString());
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", et_email.getText().toString());
                        params.put("password", et_password.getText().toString());

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });


    }



}
