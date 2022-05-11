package com.spdcash.app_spdcash;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // partie qrcode

    Button scanBtn;
    TextView messageText, messageFormat, resbox, resbox1,resbox2, resbox3, resbox4;
    private RequestQueue queue;

    @Override
    public void onClick(View v) {

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                messageText.setText(intentResult.getContents());
                messageFormat.setText(intentResult.getFormatName());

                String link = intentResult.getContents();

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                //this is the url where you want to send the request
                //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
                String url = "http://192.168.1.55/pa2/Speed-Cash-Website/php/test_db.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                final String SEPARATEUR = "-";

                                String mots[] = response.split(SEPARATEUR);
                                // Display the response string.
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                resbox.setText("code: "+ mots[0]);
                                resbox1.setText("number: "+ mots[1]);
                                resbox2.setText("date expiration: "+ mots[2]);
                                resbox3.setText("cvc: "+mots[3]);
                                resbox4.setText("nom: "+mots[4] + " " + mots[5]);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resbox.setText(error.toString());
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", link);
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        } else {
            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    // partie slide menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        //startActivity(intent);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // referencing and initializing
        // the button and textviews
        scanBtn = findViewById(R.id.scanBtn);
        resbox = findViewById(R.id.resbox);
        resbox1 = findViewById(R.id.resbox1);
        resbox2 = findViewById(R.id.resbox2);
        resbox3 = findViewById(R.id.resbox3);
        resbox4 = findViewById(R.id.resbox4);
        messageText = findViewById(R.id.textContent);
        messageFormat = findViewById(R.id.textFormat);

        // adding listener to the button
        scanBtn.setOnClickListener(this);

        //Définir un bouton sur le bord gauche de l'ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //L'icône ↓ utilise l'icône de tri standard, mais ce sera bien de la changer en ≡.
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //Affichage du menu
            FragmentManager fragmentManager = getSupportFragmentManager();
            SlideInMenuFragment fragment = SlideInMenuFragment.newInstance();
            fragment.show(fragmentManager, fragment.getClass().getSimpleName());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}