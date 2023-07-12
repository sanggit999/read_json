package com.example.read_json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button btnUpload;

    private ProgressBar progressBar;

    private String api = "http://api.open-notify.org/astros.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUpload = findViewById(R.id.btnUpload);
        progressBar = findViewById(R.id.progressBar);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest objectRequest = new JsonObjectRequest(api, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Load dữ liệu thành công về đây;
                        try {
                            int number = response.getInt("number");
                            String message = response.getString("message");
                            JSONArray jsonArray = response.getJSONArray("people");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                Log.e("resVolley",object.getString("name")+","+object.getString("craft"));
                            }
                            Toast.makeText(getApplicationContext(),message+","+number,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Bị lỗi trả về đây;
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("ErrorVolley", error.toString());
                    }
                });
                requestQueue.add(objectRequest);
            }
        });
    }
}