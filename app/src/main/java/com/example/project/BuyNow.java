package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BuyNow extends AppCompatActivity {
    JSONObject jObj;
    TextView tvName, tvEmail, tvPhone, tvVenmo;
    String strName, strEmail, strPhone, strVenmo;
    ImageButton venmo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buy_now);

        initialization();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", Store.selectedItemObject.getItemSellerEmail());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "https://us-central1-faceclass-221a7.cloudfunctions.net/nodecruz/thriftsluguserinfo";

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

//                    System.out.println(myResponse);
                    try
                    {
                        JSONArray jArray = new JSONArray(myResponse);
                        jObj = jArray.getJSONObject(0);

                        strName = jObj.getString("name");
                        strEmail = jObj.getString("email");
                        strVenmo = jObj.getString("venmoid");
                        strPhone = jObj.getString("phonenumber");

                    }
                    catch (JSONException e)
                    {

                    }


//                    Log.d(TAG, "onResponse:" + myResponse);
                    BuyNow.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvName.setText(strName);
                            tvPhone.setText(strPhone);
                            tvEmail.setText(strEmail);
                            tvVenmo.setText(strVenmo);
                        }
                    });
                }
            }
        });

        ImageButton venmo = (ImageButton) findViewById(R.id.venmoBtn);
        venmo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_MAIN);
                PackageManager managerclock = getPackageManager();
                i = managerclock.getLaunchIntentForPackage("com.venmo");
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i);
            }
        });
    }


    private void initialization()
    {
        tvName = findViewById(R.id.textViewContactName);
        tvEmail = findViewById(R.id.textviewContactEmail);
        tvPhone = findViewById(R.id.textviewContactPhone);
        tvVenmo = findViewById(R.id.textViewContactVenmo);
    }
}
